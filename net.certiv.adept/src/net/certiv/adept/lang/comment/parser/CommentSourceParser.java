package net.certiv.adept.lang.comment.parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import net.certiv.adept.Tool;
import net.certiv.adept.format.render.CommentProcessor;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.comment.parser.gen.CommentLexer;
import net.certiv.adept.lang.comment.parser.gen.CommentParser;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.CommentContext;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.util.Log;

public class CommentSourceParser {

	private CommentVisitor visitor;
	private CommentContext tree;
	private CommentParser parser;
	private CommentLexer lexer;
	private CommonTokenStream tokenStream;
	private CodePointCharStream charStream;

	public CommentSourceParser(CommentProcessor helper) {
		visitor = new CommentVisitor(helper);
	}

	public boolean process(AdeptToken comment) {
		try {
			charStream = CharStreams.fromString(comment.getText());
			lexer = new CommentLexer(charStream);
			tokenStream = new CommonTokenStream(lexer);
			parser = new CommentParser(tokenStream);
			parser.removeErrorListeners();
			tree = parser.comment();

		} catch (RecognitionException e) {
			Log.error(this, ErrorType.PARSE_ERROR.msg + ": " + comment.toString());
			Tool.errMgr.toolError(ErrorType.PARSE_ERROR, comment.toString());
			return false;

		} catch (Exception e) {
			Log.error(this, ErrorType.PARSE_FAILURE.msg + ": " + comment.toString());
			Tool.errMgr.toolError(ErrorType.PARSE_FAILURE, e, comment.toString());
			return false;

		} finally {
			if (tree == null || tree instanceof ErrorNode) {
				Tool.errMgr.toolError(ErrorType.PARSE_ERROR, "Bad parse tree: " + comment);
				return false;
			}
		}

		ParseTreeWalker.DEFAULT.walk(visitor, tree);
		return true;
	}
}
