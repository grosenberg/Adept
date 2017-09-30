package net.certiv.adept.lang.antlr.parser;

import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.BLOCKCOMMENT;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.COLON;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.COMMA;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.EQ;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.ERRCHAR;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.HWS;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.ID;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.INT;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.LBRACE;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.LBRACK;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.LEOF;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.LINECOMMENT;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.LMORE;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.LPAREN;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.LSKIP;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.MODE;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.OR;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.PLUSEQ;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.POPMODE;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.PUSHMODE;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.RANGE;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.RARROW;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.RBRACE;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.RBRACK;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.RPAREN;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.SEMI;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.SET;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.STRING;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.TYPE;
import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.VWS;

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
import net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer;
import net.certiv.adept.lang.antlr.parser.gen.Antlr4Parser;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.parser.AdeptTokenFactory;
import net.certiv.adept.model.parser.Builder;
import net.certiv.adept.model.parser.ISourceParser;
import net.certiv.adept.model.parser.ParseData;
import net.certiv.adept.model.parser.ParserErrorListener;
import net.certiv.adept.tool.ErrorType;

public class AntlrSourceParser implements ISourceParser {

	private Builder builder;
	protected int errorCount;

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

		builder.tree = ((Antlr4Parser) builder.parser).adept();
		builder.errCount = errorCount;

		if (builder.tree == null || builder.tree instanceof ErrorNode || builder.errCount > 0) {
			Tool.errMgr.toolError(ErrorType.PARSE_ERROR, "Bad parse tree: " + doc.getPathname());
		}
	}

	private void fillCollector(String content) {
		builder.input = CharStreams.fromString(content);
		builder.lexer = new Antlr4Lexer(builder.input);
		builder.VWS = VWS;
		builder.HWS = HWS;
		builder.BLOCKCOMMENT = BLOCKCOMMENT;
		builder.LINECOMMENT = LINECOMMENT;
		builder.VARS = new int[] { ID, INT, SET, STRING };
		builder.ALIGN_SAME = new int[] { ID, INT, SET, STRING, RANGE, BLOCKCOMMENT, LINECOMMENT };
		builder.ALIGN_ANY = new int[] { COLON, OR, SEMI, COMMA, LPAREN, RPAREN, LBRACE, RBRACE, LBRACK, RBRACK,
				RARROW, EQ, PLUSEQ, LSKIP, LMORE, LEOF, MODE, PUSHMODE, POPMODE, TYPE, };
		builder.ERR_TOKEN = ERRCHAR;
		builder.ERR_RULE = Antlr4Parser.RULE_other << 32;
		builder.stream = new CommonTokenStream(builder.lexer);
		builder.parser = new Antlr4Parser(builder.stream);
	}

	@Override
	public void reportRecognitionError(Token offendingToken, int errorIdx, int line, int col, String msg,
			RecognitionException e) {
		errorCount++;	// TODO: record location and extent of errors in the model
	}

	@Override
	public void extractFeatures(Builder builder) {
		ParseTreeWalker walker = new ParseTreeWalker();
		AntlrVisitor visitor = new AntlrVisitor(builder);
		walker.walk(visitor, builder.tree);
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
