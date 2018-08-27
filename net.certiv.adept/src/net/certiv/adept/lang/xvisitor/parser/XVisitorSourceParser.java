/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang.xvisitor.parser;

import static net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer.*;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import net.certiv.adept.Tool;
import net.certiv.adept.format.prep.Aligner;
import net.certiv.adept.format.prep.Indenter;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.AdeptTokenFactory;
import net.certiv.adept.lang.Builder;
import net.certiv.adept.lang.ParserErrorListener;
import net.certiv.adept.lang.SourceParser;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorLexer;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParser;
import net.certiv.adept.model.Document;
import net.certiv.adept.tool.ErrorDesc;
import net.certiv.adept.util.Utils;

public class XVisitorSourceParser extends SourceParser {

	@Override
	public void process(Tool tool, Builder builder, Document doc) throws RecognitionException, Exception {
		this.tool = tool;
		this.builder = builder;

		AdeptTokenFactory factory = new AdeptTokenFactory();
		setup(factory, doc.getContent());

		builder.parser.setTokenFactory(factory);
		builder.parser.removeErrorListeners();
		builder.parser.addErrorListener(new ParserErrorListener(this));

		builder.tree = ((XVisitorParser) builder.parser).grammarSpec();

		if (builder.tree == null || builder.tree instanceof ErrorNode || builder.errCount > 0) {
			tool.toolError(this, ErrorDesc.PARSE_ERROR, "Bad parse tree: " + doc.getPathname());
		}
	}

	@Override
	public void setup(TokenFactory<?> factory, String content) {
		builder.charStream = CharStreams.fromString(content);
		builder.lexer = new XVisitorLexer(builder.charStream);
		builder.lexer.setTokenFactory(factory);
		builder.tokenStream = new CommonTokenStream(builder.lexer);
		builder.parser = new XVisitorParser(builder.tokenStream);

		builder.VWS = VERT_WS;
		builder.HWS = HORZ_WS;
		builder.BLOCKCOMMENT = BLOCK_COMMENT;
		builder.LINECOMMENT = LINE_COMMENT;
		builder.ERR_TOKEN = ERRCHAR;
		// builder.ERR_RULE = XVisitorParser.RULE_other << 16;
	}

	@Override
	public void extractFeatures(Builder builder) {
		ParseTreeWalker walker = new ParseTreeWalker();
		XVFeatureVisitor visitor = new XVFeatureVisitor(builder);
		walker.walk(visitor, builder.tree);
	}

	@Override
	public void defineIndentation(Indenter indenter) {
		ParseTreeWalker walker = new ParseTreeWalker();
		XVIndentVisitor visitor = new XVIndentVisitor(indenter);
		walker.walk(visitor, builder.tree);
	}

	@Override
	public void locateAlignables(Aligner aligner) {
		ParseTreeWalker walker = new ParseTreeWalker();
		XVAlignVisitor visitor = new XVAlignVisitor(aligner);
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
	public List<AdeptToken> lex(String content) throws RecognitionException {
		CodePointCharStream cs = CharStreams.fromString(content);
		XVisitorLexer lexer = new XVisitorLexer(cs);
		lexer.setTokenFactory(getTokenFactory());
		lexer.addErrorListener(syntaxErrListener);
		CommonTokenStream ts = new CommonTokenStream(lexer);
		ts.fill();
		return Utils.upconvert(ts.getTokens());
	}
}
