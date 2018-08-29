/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang.comment.parser;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.LexerNoViableAltException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

import net.certiv.adept.util.Utils;

public abstract class CommentLexerAdaptor extends Lexer {

	private final Map<String, String> doc = new HashMap<>();
	private final Map<String, String> block = new HashMap<>();
	private final Map<String, String> line = new HashMap<>();

	public static enum CTYPE {
		DOC,
		BLOCK,
		LINE;
	}

	public static enum CPOS {
		BEG,
		END;
	}

	private boolean _mmore;	// modified more mode flag
	private int _mtype;		// token type to emit with content of 'mmore' matches
	private Token _mtoken;	// staged next token after modified more token

	private int _mStartCharIndex;		// state after last 'mmore' match
	private int _mStartCharPositionInLine;
	private int _mStartLine;

	public CommentLexerAdaptor(CharStream input) {
		super(input);
	}

	// ---------------------------
	// Comment mark specialization

	public void setDocStyles(String[][] doc) {
		this.doc.clear();
		if (doc != null) Utils.loadPairs(this.doc, doc);
	}

	public void setBlockStyles(String[][] block) {
		this.block.clear();
		if (block != null) Utils.loadPairs(this.block, block);
	}

	public void setLineStyles(String[][] line) {
		this.line.clear();
		if (line != null) Utils.loadPairs(this.line, line);
	}

	public boolean is(CTYPE type, CPOS pos) {
		Map<String, String> style = null;
		switch (type) {
			case DOC:
				style = doc;
				break;
			case BLOCK:
				style = block;
				break;
			case LINE:
				style = line;
				break;
		}

		String word = _input.getText(Interval.of(_input.index(), _input.index() + 5));
		switch (pos) {
			case BEG:
				for (String beg : style.keySet()) {
					if (word.startsWith(beg)) return true;
				}
				break;
			case END:
				for (String end : style.values()) {
					if (word.startsWith(end)) return true;
				}
				break;
		}

		return false;
	}

	// ----------------------
	// Enhanced 'more'

	@Override
	public void more() {
		_mmore = false;
		_type = MORE;
	}

	public void more(int mtype) {
		_type = MORE;
		_mmore = true;
		_mtype = mtype;
	}

	/** Return a token from this source; i.e., match a token on the char stream. */
	@Override
	public Token nextToken() {
		if (_input == null) {
			throw new IllegalStateException("nextToken requires a non-null input stream.");
		}

		if (_mtoken != null) {
			emit(_mtoken);
			_mtoken = null;
			return _token;
		}

		// Mark start location in char stream so unbuffered streams are
		// guaranteed at least have text of current token
		int tokenStartMarker = _input.mark();
		try {
			outer: while (true) {
				if (_hitEOF) {
					emitEOF();
					return _token;
				}

				_token = null;
				_channel = Token.DEFAULT_CHANNEL;
				_tokenStartCharIndex = _input.index();
				_tokenStartCharPositionInLine = getInterpreter().getCharPositionInLine();
				_tokenStartLine = getInterpreter().getLine();
				_text = null;
				do {
					_type = Token.INVALID_TYPE;
					int ttype;
					try {
						ttype = getInterpreter().match(_input, _mode);
					} catch (LexerNoViableAltException e) {
						notifyListeners(e); // report error
						recover(e);
						ttype = SKIP;
					}
					if (_input.LA(1) == IntStream.EOF) _hitEOF = true;
					if (_type == Token.INVALID_TYPE) _type = ttype;
					if (_type == SKIP && !_mmore) continue outer;
					if (_type == MORE && _mmore) {
						_mStartCharIndex = _input.index();
						_mStartCharPositionInLine = getInterpreter().getCharPositionInLine();
						_mStartLine = getInterpreter().getLine();
					}
				} while (_type == MORE);

				if (_mmore) {
					_mmore = false;

					// token with modified more content
					Token t = _factory.create(_tokenFactorySourcePair, _mtype, _text, _channel, _tokenStartCharIndex,
							_mStartCharIndex - 1, _tokenStartLine, _tokenStartCharPositionInLine);
					emit(t);

					if (_type != SKIP) { // stage token that terminated the modified more
						_mtoken = _factory.create(_tokenFactorySourcePair, _type, _text, _channel, _mStartCharIndex,
								getCharIndex() - 1, _mStartLine, _mStartCharPositionInLine);
					}
				}

				if (_token == null) emit();
				return _token;
			}
		} finally {
			// release marker after match or unbuffered char stream will keep buffering
			_input.release(tokenStartMarker);
		}
	}

	public boolean atBol() {
		return _tokenStartCharPositionInLine == 0;
	}

	public boolean at(String s) {
		int len = s.length();
		String txt = _input.getText(Interval.of(_input.index(), _input.index() + len - 1));
		System.err.println(String.format("%s == %s", s, txt));
		return s.equals(txt);
	}

	public boolean codeBeg() {
		System.err.println(String.format("%s %s", _input.LA(1), _input.LA(2)));
		if (_input.LA(1) == '{' && _input.LA(2) == '@') return true;
		return false;
	}
}
