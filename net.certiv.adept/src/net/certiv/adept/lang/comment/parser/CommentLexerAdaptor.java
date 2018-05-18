/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang.comment.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.LexerNoViableAltException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

public abstract class CommentLexerAdaptor extends Lexer {

	private boolean _mmore;	// modified more mode flag
	private int _mtype;		// token type to emit with content of 'mmore' matches
	private Token _mtoken;	// staged next token after modified more token

	private int _mStartCharIndex;		// state after last 'mmore' match
	private int _mStartCharPositionInLine;
	private int _mStartLine;

	public CommentLexerAdaptor(CharStream input) {
		super(input);
	}

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

					if (_type != SKIP) {
						// stage token that terminated the modified more
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

	public boolean atBol(boolean allowStar) {
		if (_tokenStartCharIndex == 0) return true;

		CodePointCharStream cpcs = (CodePointCharStream) _input;
		int off = _tokenStartCharIndex - cpcs.index(); // LA relative to beg of just matched token
		int bol = off - _tokenStartCharPositionInLine; // LA relative to beg of line
		for (int dot = off - 1; dot >= bol; dot--) {
			char c = (char) cpcs.LA(dot);
			switch (c) {
				case '\t':
				case ' ':
					continue;
				case '\r':
				case '\n':
					return true;
				case '*':
					if (allowStar) continue;
				default:
					return false;
			}
		}
		return true;
	}

	public boolean at(String s) {
		int len = s.length();
		String txt = _input.getText(new Interval(_input.index(), _input.index() + len - 1));
		System.err.println(String.format("%s == %s", s, txt));
		return s.equals(txt);
	}

	public boolean codeBeg() {
		System.err.println(String.format("%s %s", _input.LA(1), _input.LA(2)));
		if (_input.LA(1) == '{' && _input.LA(2) == '@') return true;
		return false;
	}
}
