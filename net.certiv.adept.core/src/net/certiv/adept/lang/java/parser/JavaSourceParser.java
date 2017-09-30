package net.certiv.adept.lang.java.parser;

import static net.certiv.adept.lang.java.parser.gen.JavaLexer.AND_ASSIGN;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.BLOCKCOMMENT;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.COLON;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.COMMA;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.DIV_ASSIGN;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.EQ;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.ERRCHAR;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.GE;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.GT;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.HWS;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.ID;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.LBRACE;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.LBRACK;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.LE;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.LEFT_ASSIGN;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.LINECOMMENT;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.LPAREN;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.LT;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.L_AND;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.L_OR;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.MINUS_ASSIGN;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.MOD_ASSIGN;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.MULT_ASSIGN;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.NEQ;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.NUM;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.OR_ASSIGN;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.PLUS_ASSIGN;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.QID;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.RARROW;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.RBRACE;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.RBRACK;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.RIGHT_ASSIGN;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.RPAREN;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.SEMI;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.STRING;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.UR_ASSIGN;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.VWS;
import static net.certiv.adept.lang.java.parser.gen.JavaLexer.XOR_ASSIGN;

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
import net.certiv.adept.lang.java.parser.gen.JavaLexer;
import net.certiv.adept.lang.java.parser.gen.JavaParser;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.parser.AdeptTokenFactory;
import net.certiv.adept.model.parser.Builder;
import net.certiv.adept.model.parser.ISourceParser;
import net.certiv.adept.model.parser.ParseData;
import net.certiv.adept.model.parser.ParserErrorListener;
import net.certiv.adept.tool.ErrorType;

public class JavaSourceParser implements ISourceParser {

	protected int errorCount;
	private Builder builder;

	@Override
	public void process(Builder builder, Document doc) throws RecognitionException, Exception {
		this.builder = builder;

		ParserErrorListener errors = new ParserErrorListener(this);
		fillCollector(doc.getContent());

		AdeptTokenFactory factory = new AdeptTokenFactory(builder.input);
		builder.lexer.setTokenFactory(factory);
		builder.parser.setTokenFactory(factory);
		builder.parser.removeErrorListeners();
		builder.parser.addErrorListener(errors);

		builder.tree = ((JavaParser) builder.parser).compilationUnit();
		builder.errCount = errorCount;

		if (builder.tree == null || builder.tree instanceof ErrorNode || builder.errCount > 0) {
			Tool.errMgr.toolError(ErrorType.PARSE_ERROR, "Bad parse tree: " + doc.getPathname());
		}
	}

	private void fillCollector(String content) {
		builder.input = CharStreams.fromString(content);
		builder.lexer = new JavaLexer(builder.input);
		builder.VWS = VWS;
		builder.HWS = HWS;
		builder.VARS = new int[] { ID, NUM, STRING, QID };
		builder.BLOCKCOMMENT = BLOCKCOMMENT;
		builder.LINECOMMENT = LINECOMMENT;
		builder.ALIGN_SAME = new int[] { ID, QID, NUM, STRING, BLOCKCOMMENT, LINECOMMENT };
		builder.ALIGN_ANY = new int[] { COLON, SEMI, COMMA, LT, GT, EQ, LE, GE, NEQ, L_AND, L_OR, LPAREN, RPAREN,
				LBRACE, RBRACE, LBRACK, RBRACK, RARROW, PLUS_ASSIGN, MINUS_ASSIGN, MULT_ASSIGN, DIV_ASSIGN, AND_ASSIGN,
				OR_ASSIGN, XOR_ASSIGN, MOD_ASSIGN, LEFT_ASSIGN, RIGHT_ASSIGN, UR_ASSIGN, };
		builder.ERR_TOKEN = ERRCHAR;
		// featureBuilder.ERR_RULE = JavaParser.RULE_other << 32;
		builder.stream = new CommonTokenStream(builder.lexer);
		builder.parser = new JavaParser(builder.stream);
	}

	@Override
	public void reportRecognitionError(Token offendingToken, int errorIdx, int line, int col, String msg,
			RecognitionException e) {
		errorCount++;	// TODO: record location and extent of errors in the model
	}

	@Override
	public void extractFeatures(Builder model) {
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
