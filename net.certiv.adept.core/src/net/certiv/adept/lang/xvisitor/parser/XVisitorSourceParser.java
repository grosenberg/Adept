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
import net.certiv.adept.model.parser.AdeptTokenFactory;
import net.certiv.adept.model.parser.Builder;
import net.certiv.adept.model.parser.ISourceParser;
import net.certiv.adept.model.parser.ParseData;
import net.certiv.adept.model.parser.ParserErrorListener;
import net.certiv.adept.tool.ErrorType;

public class XVisitorSourceParser implements ISourceParser {

	protected int errorCount;
	private Builder builder;

	@Override
	public void process(Builder builder, Document doc) throws RecognitionException, Exception {
		this.builder = builder;

		ParserErrorListener errors = new ParserErrorListener(this);
		fillCollector(doc.getContent());

		AdeptTokenFactory factory = new AdeptTokenFactory(builder.charStream);
		builder.lexer.setTokenFactory(factory);
		builder.parser.setTokenFactory(factory);
		builder.parser.removeErrorListeners();
		builder.parser.addErrorListener(errors);

		builder.tree = ((XVisitorParser) builder.parser).grammarSpec();
		builder.errCount = errorCount;

		if (builder.tree == null || builder.tree instanceof ErrorNode || builder.errCount > 0) {
			Tool.errMgr.toolError(ErrorType.PARSE_ERROR, "Bad parse tree: " + doc.getPathname());
		}
	}

	private void fillCollector(String content) {
		builder.charStream = CharStreams.fromString(content);
		builder.lexer = new XVisitorLexer(builder.charStream);
		builder.VWS = VERT_WS;
		builder.HWS = HORZ_WS;
		builder.BLOCKCOMMENT = BLOCK_COMMENT;
		builder.LINECOMMENT = LINE_COMMENT;
		builder.VARS = new int[] { ID, LITERAL };
		builder.ALIGN_SAME = new int[] { ID, LITERAL, BLOCK_COMMENT, LINE_COMMENT };
		builder.ALIGN_ANY = new int[] { COLON, OR, SEMI, COMMA, LBRACE, RBRACE, };
		builder.ERR_TOKEN = ERRCHAR;
		// featureBuilder.ERR_RULE = XVisitorParser.RULE_other << 32;
		builder.tokenStream = new CommonTokenStream(builder.lexer);
		builder.parser = new XVisitorParser(builder.tokenStream);
	}

	@Override
	public void reportRecognitionError(Token offendingToken, int errorIdx, int line, int col, String msg,
			RecognitionException e) {
		errorCount++;	// TODO: record location and extent of errors in the model
	}

	@Override
	public void extractFeatures(Builder builder) {
		ParseTreeWalker walker = new ParseTreeWalker();
		XVisitorVisitor visitor = new XVisitorVisitor(builder);
		walker.walk(visitor, builder.tree);
	}

	@Override
	public List<Integer> excludedTypes() {
		List<Integer> excludes = new ArrayList<>();
		excludes.add(Token.EOF);
		excludes.add(XVisitorParser.ERRCHAR);
		// excludes.add(XVisitorParser.RULE_??? << 16);
		return excludes;
	}

	@Override
	public ParseTree getParseTree() {
		return builder.tree;
	}

	@Override
	public ParseData getParseData() {
		return builder;
	}

	@Override
	public List<String> getRuleNames() {
		if (builder == null) {
			builder = new Builder();
			fillCollector("");
		}
		return Arrays.asList(builder.parser.getRuleNames());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<String> getTokenNames() {
		if (builder == null) {
			builder = new Builder();
			fillCollector("");
		}
		return Arrays.asList(builder.lexer.getTokenNames());
	}
}
