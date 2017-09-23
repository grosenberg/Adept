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
import net.certiv.adept.model.load.parser.AdeptTokenFactory;
import net.certiv.adept.model.load.parser.FeatureFactory;
import net.certiv.adept.model.load.parser.ISourceParser;
import net.certiv.adept.model.load.parser.ParserErrorListener;
import net.certiv.adept.tool.ErrorType;

public class AntlrSourceParser implements ISourceParser {

	private FeatureFactory featureFactory;
	protected int errorCount;

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

		featureFactory.tree = ((Antlr4Parser) featureFactory.parser).adept();
		featureFactory.errCount = errorCount;

		if (featureFactory.tree == null || featureFactory.tree instanceof ErrorNode || featureFactory.errCount > 0) {
			Tool.errMgr.toolError(ErrorType.PARSE_ERROR, "Bad parse tree: " + doc.getPathname());
		}
	}

	private void fillCollector(String content) {
		featureFactory.input = CharStreams.fromString(content);
		featureFactory.lexer = new Antlr4Lexer(featureFactory.input);
		featureFactory.VWS = VWS;
		featureFactory.HWS = HWS;
		featureFactory.BLOCKCOMMENT = BLOCKCOMMENT;
		featureFactory.LINECOMMENT = LINECOMMENT;
		featureFactory.VARS = new int[] { ID, INT, SET, STRING };
		featureFactory.ALIGN_SAME = new int[] { ID, INT, SET, STRING, RANGE, BLOCKCOMMENT, LINECOMMENT };
		featureFactory.ALIGN_ANY = new int[] { COLON, OR, SEMI, COMMA, LPAREN, RPAREN, LBRACE, RBRACE, LBRACK, RBRACK,
				RARROW, EQ, PLUSEQ, LSKIP, LMORE, LEOF, MODE, PUSHMODE, POPMODE, TYPE, };
		featureFactory.ERR_TOKEN = ERRCHAR;
		featureFactory.ERR_RULE = Antlr4Parser.RULE_other << 32;
		featureFactory.stream = new CommonTokenStream(featureFactory.lexer);
		featureFactory.parser = new Antlr4Parser(featureFactory.stream);
	}

	@Override
	public void reportRecognitionError(Token offendingToken, int errorIdx, int line, int col, String msg,
			RecognitionException e) {
		errorCount++;	// TODO: record location and extent of errors in the model
	}

	@Override
	public void extractFeatures(FeatureFactory featureFactory) {
		ParseTreeWalker walker = new ParseTreeWalker();
		AntlrVisitor visitor = new AntlrVisitor(featureFactory);
		walker.walk(visitor, featureFactory.tree);
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
