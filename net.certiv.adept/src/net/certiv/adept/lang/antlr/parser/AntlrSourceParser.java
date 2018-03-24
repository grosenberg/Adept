package net.certiv.adept.lang.antlr.parser;

import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import net.certiv.adept.Tool;
import net.certiv.adept.format.align.Aligner;
import net.certiv.adept.format.indent.Indenter;
import net.certiv.adept.lang.AdeptTokenFactory;
import net.certiv.adept.lang.Builder;
import net.certiv.adept.lang.ISourceParser;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.lang.ParserErrorListener;
import net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer;
import net.certiv.adept.lang.antlr.parser.gen.Antlr4Parser;
import net.certiv.adept.model.Document;
import net.certiv.adept.tool.ErrorType;

public class AntlrSourceParser implements ISourceParser {

	private Builder builder;
	protected int errCount;

	@Override
	public void process(Builder builder, Document doc) throws RecognitionException, Exception {
		this.builder = builder;

		AdeptTokenFactory factory = new AdeptTokenFactory();
		setup(factory, doc.getContent());

		builder.parser.setTokenFactory(factory);
		builder.parser.removeErrorListeners();
		builder.parser.addErrorListener(new ParserErrorListener(this));

		builder.tree = ((Antlr4Parser) builder.parser).antlr();

		if (builder.tree == null || builder.tree instanceof ErrorNode || builder.errCount > 0) {
			Tool.errMgr.toolError(ErrorType.PARSE_ERROR, "Bad parse tree: " + doc.getPathname());
		}
	}

	private void setup(TokenFactory<?> factory, String content) {
		builder.charStream = CharStreams.fromString(content);
		builder.lexer = new Antlr4Lexer(builder.charStream);
		builder.lexer.setTokenFactory(factory);
		builder.tokenStream = new CommonTokenStream(builder.lexer);
		builder.parser = new Antlr4Parser(builder.tokenStream);

		builder.VWS = VWS;
		builder.HWS = HWS;
		builder.BLOCKCOMMENT = BLOCKCOMMENT;
		builder.LINECOMMENT = LINECOMMENT;
		builder.ERR_TOKEN = ERRCHAR;
		builder.ERR_RULE = Antlr4Parser.RULE_other << 16;
	}

	@Override
	public void reportRecognitionError(Token offendingToken, int errorIdx, int line, int col, String msg,
			RecognitionException e) {
		builder.errCount++;
	}

	@Override
	public void extractFeatures(Builder builder) {
		ParseTreeWalker walker = new ParseTreeWalker();
		FeatureVisitor visitor = new FeatureVisitor(builder);
		walker.walk(visitor, builder.tree);
	}

	@Override
	public void defineIndentation(Indenter indenter) {
		ParseTreeWalker walker = new ParseTreeWalker();
		IndentVisitor visitor = new IndentVisitor(indenter);
		walker.walk(visitor, builder.tree);
	}

	@Override
	public void locateAlignables(Aligner aligner) {
		ParseTreeWalker walker = new ParseTreeWalker();
		AlignVisitor visitor = new AlignVisitor(aligner);
		walker.walk(visitor, builder.tree);
	}

	@Override
	public List<Integer> excludedTypes() {
		List<Integer> excludes = new ArrayList<>();
		excludes.add(Token.EOF);
		excludes.add(Antlr4Parser.ERRCHAR);
		excludes.add(Antlr4Parser.ACT_CONTENT);
		excludes.add(Antlr4Parser.ARG_CONTENT);
		excludes.add(Antlr4Parser.RULE_other << 16);
		return excludes;
	}

	@Override
	public ParseTree getParseTree() {
		return builder.tree;
	}

	@Override
	public ParseRecord getParseData() {
		return builder;
	}

	@Override
	public List<String> getRuleNames() {
		if (builder == null) {
			builder = new Builder();
			setup(CommonTokenFactory.DEFAULT, "");
		}
		return Arrays.asList(builder.parser.getRuleNames());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<String> getTokenNames() {
		if (builder == null) {
			builder = new Builder();
			setup(CommonTokenFactory.DEFAULT, "");
		}
		return Arrays.asList(builder.lexer.getTokenNames());
	}
}
