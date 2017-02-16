package net.certiv.adept.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.model.Feature;

public class ParseData {

	public ANTLRInputStream input;
	public CommonTokenStream stream;
	public Lexer lexer;
	public int VWS;
	public int HWS;
	public int BLOCKCOMMENT = -2;
	public int LINECOMMENT = -2;
	public int ERR_TOKEN = -2;
	public int ERR_RULE = -2;
	public int[] VARS = { -2 }; // token types whose underlying values can be ignored

	public Parser parser;
	public ParserRuleContext tree;
	public int errCount;

	// key=node feature token; value=token's node
	public Map<Token, TerminalNode> nodeIndex;

	// key=rule feature start token; value=token's context
	public Map<Token, ParserRuleContext> contextIndex;

	// key=rule context; value=the owning feature
	public Map<ParserRuleContext, Feature> ruleIndex;

	// key=token; value=the owning feature
	public Map<TerminalNode, Feature> terminalIndex;

	// key=line number; value=tokens of line
	public Map<Integer, List<Token>> lineIndex;
	
	// key=feature type
	public HashSet<Integer> typeSet;

	public ParseData() {
		super();
		nodeIndex = new HashMap<>();
		contextIndex = new HashMap<>();
		ruleIndex = new HashMap<>();
		terminalIndex = new HashMap<>();
		lineIndex = new HashMap<>();
		typeSet = new HashSet<>();
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
}
