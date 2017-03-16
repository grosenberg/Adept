package net.certiv.adept.antlr.parser;

import static net.certiv.adept.antlr.parser.gen.Antlr4Lexer.*;

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
import net.certiv.adept.antlr.parser.gen.Antlr4Lexer;
import net.certiv.adept.antlr.parser.gen.Antlr4Parser;
import net.certiv.adept.model.Document;
import net.certiv.adept.parser.AdeptTokenFactory;
import net.certiv.adept.parser.Collector;
import net.certiv.adept.parser.ISourceParser;
import net.certiv.adept.parser.ParserErrorListener;
import net.certiv.adept.tool.ErrorType;

public class AntlrSourceParser implements ISourceParser {

	private Collector collector;
	protected int errorCount;

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

		collector.tree = ((Antlr4Parser) collector.parser).adept();
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
		collector.lexer = new Antlr4Lexer(collector.input);
		collector.VWS = VWS;
		collector.HWS = HWS;
		collector.BLOCKCOMMENT = BLOCKCOMMENT;
		collector.LINECOMMENT = LINECOMMENT;
		collector.VARS = new int[] { ID, INT, SET, STRING };
		collector.ALIGN_SAME = new int[] { ID, INT, SET, STRING, RANGE };
		collector.ALIGN_ANY = new int[] { COLON, OR, SEMI, COMMA, LPAREN, RPAREN, LBRACE, RBRACE, LBRACK, RBRACK,
				RARROW, EQ, PLUSEQ, LSKIP, LMORE, LEOF, MODE, PUSHMODE, POPMODE, TYPE, };
		collector.ERR_TOKEN = ERRCHAR;
		collector.ERR_RULE = Antlr4Parser.RULE_other << 32;
		collector.stream = new CommonTokenStream(collector.lexer);
		collector.parser = new Antlr4Parser(collector.stream);
	}

	@Override
	public void reportRecognitionError(Token offendingToken, int errorIdx, int line, int col, String msg,
			RecognitionException e) {
		errorCount++;	// TODO: record location and extent of errors in the model
	}

	@Override
	public void annotateFeatures(Collector collector) {
		ParseTreeWalker walker = new ParseTreeWalker();
		AntlrVisitor visitor = new AntlrVisitor(collector);
		walker.walk(visitor, collector.tree);
	}

	@Override
	public List<Integer> excludedTypes() {
		List<Integer> excludes = new ArrayList<>();
		excludes.add(Token.EOF);
		excludes.add(Antlr4Parser.ERRCHAR);
		excludes.add(Antlr4Parser.ACT_CONTENT);
		excludes.add(Antlr4Parser.ARG_CONTENT);
		excludes.add(Antlr4Parser.RULE_other << 32);
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
