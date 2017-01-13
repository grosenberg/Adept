package net.certiv.adept.java.parser;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
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

	@Override
	public void process(Collector collector, Document doc) throws RecognitionException, Exception {
		ParserErrorListener errors = new ParserErrorListener(this);
		CharStream input = new ANTLRInputStream(doc.getContent());
		collector.lexer = new JavaLexer(input);
		collector.VWS = JavaLexer.VWS;
		collector.HWS = JavaLexer.HWS;
		collector.BLOCKCOMMENT = JavaLexer.BLOCKCOMMENT;
		collector.LINECOMMENT = JavaLexer.LINECOMMENT;

		AdeptTokenFactory factory = new AdeptTokenFactory(input);
		collector.lexer.setTokenFactory(factory);
		collector.stream = new CommonTokenStream(collector.lexer);

		collector.parser = new JavaParser(collector.stream);
		collector.parser.setTokenFactory(factory);
		collector.parser.removeErrorListeners();
		collector.parser.addErrorListener(errors);
		collector.tree = ((JavaParser) collector.parser).compilationUnit();

		collector.errCount = errorCount;

		if (collector.tree == null || collector.tree instanceof ErrorNode || collector.errCount > 0) {
			Tool.errMgr.toolError(ErrorType.PARSE_ERROR, "Bad parse tree: " + doc.getPathname());
		}
	}

	@Override
	public void reportRecognitionError(Token offendingToken, int errorIdx, int line, int col, String msg,
			RecognitionException e) {
		errorCount++;	// TODO: record location and extent of errors in the model
	}

	@Override
	public void annotate(Collector model) {
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaVisitor visitor = new JavaVisitor(model);
		walker.walk(visitor, model.tree);

	}

	@Override
	public List<Integer> excludedTypes() {
		List<Integer> excludes = new ArrayList<>();
		excludes.add(JavaParser.ERRCHAR);
		// excludes.add(JavaParser.RULE_??? << 10);
		return excludes;
	}
}
