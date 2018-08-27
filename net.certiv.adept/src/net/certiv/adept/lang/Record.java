/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.adept.format.prep.Aligner;
import net.certiv.adept.format.prep.Group;
import net.certiv.adept.format.prep.Indenter;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.unit.TreeMultilist;
import net.certiv.adept.unit.TreeTable;
import net.certiv.adept.util.Utils;

/** Record of the information generated through the parsing of a single Document. */
public class Record {

	public static final int AncesLimit = 6; // ancestor path limit
	public static final int AssocLimit = 3; // associated token limit

	public Document doc;

	public Parser parser;
	public ParserRuleContext tree;

	public Lexer lexer;
	public CommonTokenStream tokenStream;
	public CodePointCharStream charStream;
	public int errCount;

	public int VWS;
	public int HWS;
	public int BLOCKCOMMENT = -2;
	public int LINECOMMENT = -2;
	public int ERR_RULE = -2 << 16;
	public int ERR_TOKEN = -2;

	// ---------------------------------------------------------

	public Indenter indenter;
	public Aligner aligner;

	// ---------------------------------------------------------

	/** key=real (non-ws) token; value=feature */
	public TreeMap<AdeptToken, Feature> index;

	/** key=token index; value=token; limited to real (non-ws) tokens */
	public TreeMap<Integer, AdeptToken> tokenIndex;

	/** key=feature id; value=feature */
	public HashMap<Integer, Feature> featureIdIndex;

	/** value=comment tokens */
	public List<AdeptToken> commentIndex;

	/** row=start token index; col=stop token index; value=context ancestors */
	public TreeTable<Integer, Integer, List<ParseTree>> contextIndex;

	/** alignment groups */
	public List<Group> groupIndex;

	/** key=line number; value=bol char offset */
	public HashMap<Integer, Integer> lineStartIndex;

	/** key=line number; value=blank? */
	public HashMap<Integer, Boolean> blanklines;

	/** key=line number; value=list of real tokens */
	public TreeMultilist<Integer, AdeptToken> lineTokensIndex;

	// ---------------------------------------------------------

	public Record(Document doc) {
		super();
		this.doc = doc;
		index = new TreeMap<>();
		tokenIndex = new TreeMap<>();
		featureIdIndex = new HashMap<>();
		groupIndex = new ArrayList<>();
		lineTokensIndex = new TreeMultilist<>();
		lineStartIndex = new HashMap<>();
		blanklines = new HashMap<>();
		commentIndex = new ArrayList<>();
		contextIndex = new TreeTable<>();

		indenter = new Indenter(this);
		aligner = new Aligner(this);
	}

	public void dispose() {
		index.clear();
		tokenIndex.clear();
		featureIdIndex.clear();
		groupIndex.clear();
		lineTokensIndex.clear();
		lineStartIndex.clear();
		blanklines.clear();
		commentIndex.clear();
		contextIndex.clear();

		indenter.clear();
		aligner.clear();
	}

	public Document getDocument() {
		return doc;
	}

	public String getTokenName(int type) {
		return lexer.getVocabulary().getDisplayName(type);
	}

	/** Returns the first real token prior to the given token index or {@code null}. */
	public AdeptToken getRealLeft(int idx) {
		if (!tokenIndex.isEmpty()) {
			Entry<Integer, AdeptToken> entry = tokenIndex.lowerEntry(idx);
			if (entry != null) return entry.getValue();
		} else {
			for (int jdx = idx - 1; jdx > -1; jdx--) {
				AdeptToken left = (AdeptToken) tokenStream.get(jdx);
				if (!left.isWhitespace()) return left;
			}
		}
		return null;
	}

	/** Returns the first real token after the given token index or {@code null}. */
	public AdeptToken getRealRight(int idx) {
		if (!tokenIndex.isEmpty()) {
			Entry<Integer, AdeptToken> entry = tokenIndex.higherEntry(idx);
			if (entry != null) return entry.getValue();
		} else {
			for (int jdx = idx + 1, len = tokenStream.size(); jdx < len; jdx++) {
				AdeptToken right = (AdeptToken) tokenStream.get(jdx);
				if (!right.isWhitespace()) return right;
			}
		}
		return null;
	}

	/** Returns a count of the real tokens in the given token index range, inclusive. */
	public int getRealTokenCount(int begIndex, int endIndex, boolean excludeComments) {
		int cnt = 0;
		for (AdeptToken token : getTokenInterval(begIndex, endIndex)) {
			if (token.isWhitespace()) continue;
			if (excludeComments && token.isComment()) continue;
			cnt++;
		}
		return cnt;
	}

	/**
	 * Returns a list of the real tokens in the given token index range, inclusive. Will not return
	 * {@code null}.
	 */
	public List<AdeptToken> getRealTokenInterval(int begIndex, int endIndex, boolean excludeComments) {
		List<AdeptToken> reals = new ArrayList<>();
		for (AdeptToken token : getTokenInterval(begIndex, endIndex)) {
			if (token.isWhitespace()) continue;
			if (excludeComments && token.isComment()) continue;
			reals.add(token);
		}
		return reals;
	}

	public CommonTokenStream getTokenStream() {
		return tokenStream;
	}

	/** Returns the list of tokens; will not be null. */
	public List<AdeptToken> getTokens() {
		return Utils.upconvert(tokenStream.getTokens());
	}

	/**
	 * Returns a list of the tokens in the given token index range, inclusive. Will not return
	 * {@code null}.
	 */
	public List<AdeptToken> getTokenInterval(int begIndex, int endIndex) {
		if (begIndex == -1) begIndex++;		// at BOF
		return Utils.upconvert(tokenStream.get(begIndex, endIndex));
	}

	/** Returns the token at the given token index. */
	public AdeptToken getToken(int index) {
		if (index == -1) return null;		// at BOF
		return (AdeptToken) tokenStream.get(index);
	}

	/** Returns the ref token for the token at the given token index. */
	public RefToken getTokenRef(int index) {
		AdeptToken token = getToken(index);
		return token != null ? token.refToken() : null;
	}

	/** Returns the text of the tokens in the given token index range, exclusive. */
	public String getTextBetween(int begIndex, int endIndex) {
		List<AdeptToken> tokens = getTokenInterval(begIndex, endIndex);
		if (tokens.size() < 3) return "";

		StringBuilder sb = new StringBuilder();
		for (AdeptToken token : tokens.subList(1, tokens.size() - 1)) {
			sb.append(token.getText());
		}
		return sb.toString();
	}

	/**
	 * Returns the text in the character stream between {@code beg} character index (inclusive) and
	 * {@code end} character index (exclusive).
	 */
	public String getTextSpan(int beg, int end) {
		return charStream.getText(Interval.of(beg, end - 1));
	}

	public ParserRuleContext getParseTreeRoot() {
		return tree;
	}

	public int getParseErrCount() {
		return errCount;
	}

	public String getRuleName(int idx) {
		return getRuleNames().get(idx);
	}

	public List<String> getRuleNames() {
		return Arrays.asList(parser.getRuleNames());
	}

	/** Fix omission in the ANTLR run-time. */
	@SuppressWarnings("deprecation")
	public List<String> getTokenNames() {
		return Arrays.asList(parser.getTokenNames());
	}

	/** Returns the token feature index for the document, ordered by token index. */
	public TreeMap<AdeptToken, Feature> getIndex() {
		TreeMap<AdeptToken, Feature> clone = new TreeMap<>();
		clone.putAll(index);
		return clone;
	}

	/** Returns the unique features created for the parsed document. */
	public List<Feature> getFeatures() {
		return new ArrayList<>(featureIdIndex.values());
	}

	public Feature getFeature(AdeptToken token) {
		return index.get(token);
	}

	public boolean isBlankLine(int line) {
		if (line < 0 && line >= blanklines.size()) return true;
		return blanklines.get(line);
	}
}
