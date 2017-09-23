package net.certiv.adept.lang.xvisitor.parser;

import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.BLOCK_COMMENT;
import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.COLON;
import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.COMMA;
import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.ERRCHAR;
import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.HORZ_WS;
import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.ID;
import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.LBRACE;
import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.LINE_COMMENT;
import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.LITERAL;
import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.OR;
import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.RBRACE;
import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.SEMI;
import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.VERT_WS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import net.certiv.adept.Tool;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParser;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.load.parser.AdeptTokenFactory;
import net.certiv.adept.model.load.parser.FeatureFactory;
import net.certiv.adept.model.load.parser.ISourceParser;
import net.certiv.adept.model.load.parser.ParserErrorListener;
import net.certiv.adept.tool.ErrorType;

public class XVisitorSourceParser implements ISourceParser {

	protected int errorCount;
	private FeatureFactory featureFactory;

	@Override
	public void process(FeatureFactory featureFactory, Document doc) throws RecognitionException, Exception {
		this.featureFactory = featureFactory;

		ParserErrorListener errors = new ParserErrorListener(this);
		fillCollector(doc.getContent());

		AdeptTokenFactory factory = new AdeptTokenFactory(featureFactory.input);
		featureFactory.lexer.setTokenFactory(factory);
		featureFactory.parser.setTokenFactory(factory);
		featureFactory.parser.removeErrorListeners();
		featureFactory.parser.addErrorListener(errors);

		featureFactory.tree = ((XVisitorParser) featureFactory.parser).grammarSpec();
		featureFactory.errCount = errorCount;

		if (featureFactory.tree == null || featureFactory.tree instanceof ErrorNode || featureFactory.errCount > 0) {
			Tool.errMgr.toolError(ErrorType.PARSE_ERROR, "Bad parse tree: " + doc.getPathname());
		}
	}

	private void fillCollector(String content) {
		featureFactory.input = CharStreams.fromString(content);
		featureFactory.lexer = new XVisitorLexer(featureFactory.input);
		featureFactory.VWS = VERT_WS;
		featureFactory.HWS = HORZ_WS;
		featureFactory.BLOCKCOMMENT = BLOCK_COMMENT;
		featureFactory.LINECOMMENT = LINE_COMMENT;
		featureFactory.VARS = new int[] { ID, LITERAL };
		featureFactory.ALIGN_SAME = new int[] { ID, LITERAL, BLOCK_COMMENT, LINE_COMMENT };
		featureFactory.ALIGN_ANY = new int[] { COLON, OR, SEMI, COMMA, LBRACE, RBRACE, };
		featureFactory.ERR_TOKEN = ERRCHAR;
		// featureFactory.ERR_RULE = XVisitorParser.RULE_other << 32;
		featureFactory.stream = new CommonTokenStream(featureFactory.lexer);
		featureFactory.parser = new XVisitorParser(featureFactory.stream);
	}

	@Override
	public void reportRecognitionError(Token offendingToken, int errorIdx, int line, int col, String msg,
			RecognitionException e) {
		errorCount++;	// TODO: record location and extent of errors in the model
	}

	@Override
	public void extractFeatures(FeatureFactory featureFactory) {
		ParseTreeWalker walker = new ParseTreeWalker();
		XVisitorVisitor visitor = new XVisitorVisitor(featureFactory);
		walker.walk(visitor, featureFactory.tree);
	}

	@Override
	public List<Integer> excludedTypes() {
		List<Integer> excludes = new ArrayList<>();
		excludes.add(Token.EOF);
		excludes.add(XVisitorParser.ERRCHAR);
		// excludes.add(XVisitorParser.RULE_??? << 32);
		return excludes;
	}

	@Override
	public ParseTree getParseTree() {
		return featureFactory.tree;
	}

	@Override
	public List<String> getRuleNames() {
		if (featureFactory == null) {
			featureFactory = new FeatureFactory();
			fillCollector("");
		}
		return Arrays.asList(featureFactory.parser.getRuleNames());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<String> getTokenNames() {
		if (featureFactory == null) {
			featureFactory = new FeatureFactory();
			fillCollector("");
		}
		return Arrays.asList(featureFactory.lexer.getTokenNames());
	}
}
