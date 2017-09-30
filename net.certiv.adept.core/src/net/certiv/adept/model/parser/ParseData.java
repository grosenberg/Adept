package net.certiv.adept.model.parser;

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
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.google.common.collect.HashBiMap;

import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;

/** Collects the information generated through the parsing of a single Document. */
public class ParseData {

	public Document doc;

	public Parser parser;
	public ParserRuleContext tree;

	public Lexer lexer;
	public CommonTokenStream stream;
	public CodePointCharStream input;

	public int VWS;
	public int HWS;
	public int BLOCKCOMMENT = -2;
	public int LINECOMMENT = -2;
	public int[] VARS = { -2 }; // token types whose underlying values can be ignored
	public int[] ALIGN_ANY = { -2 }; // token types that may be recogized as aligned
	public int[] ALIGN_SAME = { -2 }; // token types that may be mutually recogized as aligned

	public int ERR_RULE = -2 << 16;
	public int ERR_TOKEN = -2;

	public int errCount;

	// ---------------------------------------------------------

	// key=terminal token; value=terminal
	public HashBiMap<Token, TerminalNode> tokenTerminalIndex;

	// key=start token; value=rule context
	public Map<Token, ParserRuleContext> tokenRuleIndex;

	// key=tree context; value=feature
	public HashBiMap<ParseTree, Feature> contextFeatureIndex;

	// key=token start index; value=feature
	public Map<Integer, Feature> tokenStartFeatureIndex;

	// key=line number; value=tokens in line
	public Map<Integer, List<Token>> lineTokensIndex;

	// key=token; value=visual offset
	public Map<Token, Integer> tokenVisOffsetIndex;

	// key=unique feature types
	public HashSet<Integer> typeSet;

	// ---------------------------------------------------------

	public ParseData(Document doc) {
		super();
		this.doc = doc;
		tokenTerminalIndex = HashBiMap.create();
		tokenRuleIndex = new HashMap<>();
		contextFeatureIndex = HashBiMap.create();
		tokenStartFeatureIndex = new HashMap<>();
		lineTokensIndex = new HashMap<>();
		tokenVisOffsetIndex = new HashMap<>();
		typeSet = new HashSet<>();
	}

	public Document getDocument() {
		return doc;
	}

	public CommonTokenStream getTokenStream() {
		return stream;
	}

	public List<Token> getTokens() {
		return stream.getTokens();
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

	@SuppressWarnings("deprecation")
	public List<String> getTokenNames() {
		return Arrays.asList(parser.getTokenNames());
	}

	public boolean isWsOrComment(int type) {
		if (type == VWS || type == HWS || type == BLOCKCOMMENT || type == LINECOMMENT) return true;
		return false;
	}
}
