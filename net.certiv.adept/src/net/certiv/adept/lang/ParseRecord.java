package net.certiv.adept.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.util.Collect;
import net.certiv.adept.util.HashMultilist;

/** Record of the information generated through the parsing of a single Document. */
public class ParseRecord {

	public Document doc;

	public Parser parser;
	public ParserRuleContext tree;

	public Lexer lexer;
	public CommonTokenStream tokenStream;
	public CodePointCharStream charStream;

	public int VWS;
	public int HWS;
	public int BLOCKCOMMENT = -2;
	public int LINECOMMENT = -2;
	public int[] VARS = { -2 }; 				// ignore the underlying values of these
	public int[] ALIGN_IDENT = { -2 }; 			// align with identity
	public int[][] ALIGN_PAIR = { { -2, -2 } }; // align in pairs (this->above)

	public int ERR_RULE = -2 << 16;
	public int ERR_TOKEN = -2;

	public int errCount;

	// ---------------------------------------------------------

	public HashSet<Integer> vars;						// initialized from VARS
	public HashSet<Integer> align_ident;				// initialized from ALIGN_IDENT
	public HashMultilist<Integer, Integer> align_pair;	// initialized from ALIGN_PAIR

	// ---------------------------------------------------------

	// key=token; value=feature
	public HashMap<Token, Feature> featureIndex;

	// key=unique feature token types
	public HashSet<Integer> typeSet;

	// key=line number; value=tokens in line
	public Map<Integer, List<Token>> lineTokensIndex;

	// key=token; value=visual offset
	public Map<Token, Integer> tokenVisColIndex;

	// key=line number; value=is blank?
	public HashMap<Integer, Boolean> blankLines;

	// // key=terminal token; value=terminal
	// public HashBiMap<Token, TerminalNode> tokenTerminalIndex;

	// // key=start token; value=rule context
	// public Map<Token, ParserRuleContext> tokenRuleIndex;

	// // key=token start index; value=terminal/comment feature
	// public Map<Integer, Feature> tokenStartFeatureIndex;

	// // key=token type; value=terminal/comment features
	// public TreeMultimap<Integer, Feature> tokenTypeFeatureIndex;

	// ---------------------------------------------------------

	public ParseRecord(Document doc) {
		super();
		this.doc = doc;
		featureIndex = new HashMap<>();
		typeSet = new HashSet<>();
		lineTokensIndex = new HashMap<>();
		tokenVisColIndex = new HashMap<>();
		blankLines = new HashMap<>();

		// tokenTerminalIndex = HashBiMap.create();
		// tokenRuleIndex = new HashMap<>();
		// tokenStartFeatureIndex = new HashMap<>();
		// tokenTypeFeatureIndex = new TreeMultimap<>();

	}

	public void update() {
		vars = Collect.toSet(VARS);
		align_ident = Collect.toSet(ALIGN_IDENT);
		align_pair = Collect.toMap(ALIGN_PAIR);
	}

	public Document getDocument() {
		return doc;
	}

	public CommonTokenStream getTokenStream() {
		return tokenStream;
	}

	public List<Token> getTokens() {
		return tokenStream.getTokens();
	}

	/** Protect against null */
	public List<Token> getHiddenTokensToLeft(int tokenIndex) {
		List<Token> hidden = tokenStream.getHiddenTokensToLeft(tokenIndex);
		if (hidden == null) {
			hidden = new ArrayList<>();
		}
		return hidden;
	}

	/** Protect against null */
	public List<Token> getHiddenTokensToRight(int tokenIndex) {
		List<Token> hidden = tokenStream.getHiddenTokensToRight(tokenIndex);
		if (hidden == null) {
			hidden = new ArrayList<>();
		}
		return hidden;
	}

	public ParserRuleContext getTree() {
		return tree;
	}

	public int getParseErrCount() {
		return errCount;
	}

	public List<String> getRuleNames() {
		return Arrays.asList(parser.getRuleNames());
	}

	public String getRuleName(int idx) {
		return getRuleNames().get(idx);
	}

	@SuppressWarnings("deprecation")
	public List<String> getTokenNames() {
		return Arrays.asList(parser.getTokenNames());
	}

	public List<Feature> getFeatures() {
		return new ArrayList<>(featureIndex.values());
	}

	/**
	 * Returns the token on the given line (0..n) at the given visual column. Returns {@code null} if no
	 * matching token is found.
	 */
	public Token getVisualToken(int line, Integer visCol) {
		List<Token> tokens = lineTokensIndex.get(line);
		for (Token token : tokens) {
			int tokCol = tokenVisColIndex.get(token);
			if (tokCol < visCol) continue;
			if (tokCol > visCol) break;
			if (tokCol == visCol) return token;
		}
		return null;
	}

	public boolean isWhitespace(int type) {
		return type == HWS || type == VWS;
	}

	public boolean isVertWS(int type) {
		return type == VWS;
	}

	public boolean isHorzWS(int type) {
		return type == HWS;
	}

	public boolean isComment(int type) {
		return type == BLOCKCOMMENT || type == LINECOMMENT;
	}

	public boolean isWsOrComment(int type) {
		return type == VWS || type == HWS || type == BLOCKCOMMENT || type == LINECOMMENT;
	}

	public boolean isBlankLine(int line) {
		if (line < 0 && line >= lineTokensIndex.size()) return true;

		Boolean state = blankLines.get(line);
		if (state != null) return state;

		state = true;
		List<Token> tokens = lineTokensIndex.get(line);
		if (tokens != null) {
			for (Token token : tokens) {
				if (!isWhitespace(token.getType())) {
					state = false;
					break;
				}
			}
		}
		blankLines.put(line, state);
		return state;
	}

	public void dispose() {
		typeSet.clear();
		featureIndex.clear();
		lineTokensIndex.clear();
		tokenVisColIndex.clear();
		blankLines.clear();

		// tokenTerminalIndex.clear();
		// tokenRuleIndex.clear();
		// tokenStartFeatureIndex.clear();
		// tokenTypeFeatureIndex.clear();
	}
}
