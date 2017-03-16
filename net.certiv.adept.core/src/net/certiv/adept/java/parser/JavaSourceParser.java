package net.certiv.adept.java.parser;

import static net.certiv.adept.java.parser.gen.JavaLexer.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import net.certiv.adept.Tool;
import net.certiv.adept.java.parser.gen.JavaLexer;
import net.certiv.adept.java.parser.gen.JavaParser;
import net.certiv.adept.model.Document;
import net.certiv.adept.parser.AdeptTokenFactory;
import net.certiv.adept.parser.Collector;
import net.certiv.adept.parser.ISourceParser;
import net.certiv.adept.parser.ParserErrorListener;
import net.certiv.adept.tool.ErrorType;

public class JavaSourceParser implements ISourceParser {

	protected int errorCount;
	private Collector collector;

	@Override
	public void process(Collector collector, Document doc) throws RecognitionException, Exception {
		this.collector = collector;

		ParserErrorListener errors = new ParserErrorListener(this);
		fillCollector(doc.getContent());

		AdeptTokenFactory factory = new AdeptTokenFactory(collector.input);
		collector.lexer.setTokenFactory(factory);
		collector.parser.setTokenFactory(factory);
		collector.parser.removeErrorListeners();
		collector.parser.addErrorListener(errors);

		collector.tree = ((JavaParser) collector.parser).compilationUnit();
		collector.errCount = errorCount;

		if (collector.tree == null || collector.tree instanceof ErrorNode || collector.errCount > 0) {
			Tool.errMgr.toolError(ErrorType.PARSE_ERROR, "Bad parse tree: " + doc.getPathname());
		}
	}

	private void fillCollector(String content) {
		if (collector == null) {
			collector = new Collector(null);
		}
		collector.input = new ANTLRInputStream(content);
		collector.lexer = new JavaLexer(collector.input);
		collector.VWS = VWS;
		collector.HWS = HWS;
		collector.VARS = new int[] { ID, NUM, STRING, QID };
		collector.BLOCKCOMMENT = BLOCKCOMMENT;
		collector.LINECOMMENT = LINECOMMENT;
		collector.ALIGN_SAME = new int[] { ID, QID, NUM, STRING };
		collector.ALIGN_ANY = new int[] { COLON, SEMI, COMMA, LT, GT, EQ, LE, GE, NEQ, L_AND, L_OR, LPAREN, RPAREN,
				LBRACE, RBRACE, LBRACK, RBRACK, RARROW, PLUS_ASSIGN, MINUS_ASSIGN, MULT_ASSIGN, DIV_ASSIGN, AND_ASSIGN,
				OR_ASSIGN, XOR_ASSIGN, MOD_ASSIGN, LEFT_ASSIGN, RIGHT_ASSIGN, UR_ASSIGN, };
		collector.ERR_TOKEN = ERRCHAR;
		// collector.ERR_RULE = JavaParser.RULE_other << 32;
		collector.stream = new CommonTokenStream(collector.lexer);
		collector.parser = new JavaParser(collector.stream);
	}

	@Override
	public void reportRecognitionError(Token offendingToken, int errorIdx, int line, int col, String msg,
			RecognitionException e) {
		errorCount++;	// TODO: record location and extent of errors in the model
	}

	@Override
	public void annotateFeatures(Collector model) {
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaVisitor visitor = new JavaVisitor(model);
		walker.walk(visitor, model.tree);

	}

	@Override
	public List<Integer> excludedTypes() {
		List<Integer> excludes = new ArrayList<>();
		excludes.add(Token.EOF);
		excludes.add(JavaParser.ERRCHAR);
		// excludes.add(JavaParser.RULE_??? << 32);
		return excludes;
	}

	@Override
	public ParseTree getParseTree() {
		return collector.tree;
	}

	@Override
	public List<String> getRuleNames() {
		if (collector == null) fillCollector("");;
		return Arrays.asList(collector.parser.getRuleNames());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<String> getTokenNames() {
		if (collector == null) fillCollector("");;
		return Arrays.asList(collector.lexer.getTokenNames());
	}
}
