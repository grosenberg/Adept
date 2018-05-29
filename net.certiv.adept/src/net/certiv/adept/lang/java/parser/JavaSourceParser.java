/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang.java.parser;

import static net.certiv.adept.lang.java.parser.gen.Java8Lexer.*;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import net.certiv.adept.Tool;
import net.certiv.adept.format.plan.Aligner;
import net.certiv.adept.format.plan.Indenter;
import net.certiv.adept.lang.SourceParser;
import net.certiv.adept.lang.AdeptTokenFactory;
import net.certiv.adept.lang.Builder;
import net.certiv.adept.lang.ParserErrorListener;
import net.certiv.adept.lang.java.parser.gen.Java8Lexer;
import net.certiv.adept.lang.java.parser.gen.Java8Parser;
import net.certiv.adept.model.Document;
import net.certiv.adept.tool.ErrorDesc;

public class JavaSourceParser extends SourceParser {

	@Override
	public void process(Tool tool, Builder builder, Document doc) throws RecognitionException, Exception {
		this.builder = builder;

		AdeptTokenFactory factory = new AdeptTokenFactory();
		setup(factory, doc.getContent());

		builder.parser.setTokenFactory(factory);
		builder.parser.removeErrorListeners();
		builder.parser.addErrorListener(new ParserErrorListener(this));

		builder.tree = ((Java8Parser) builder.parser).compilationUnit();
		builder.errCount = errCount;

		if (builder.tree == null || builder.tree instanceof ErrorNode || builder.errCount > 0) {
			tool.toolError(this, ErrorDesc.PARSE_ERROR, "Bad parse tree: " + doc.getPathname());
		}
	}

	@Override
	public void setup(TokenFactory<?> factory, String content) {
		builder.charStream = CharStreams.fromString(content);
		builder.lexer = new Java8Lexer(builder.charStream);
		builder.lexer.setTokenFactory(factory);
		builder.tokenStream = new CommonTokenStream(builder.lexer);
		builder.parser = new Java8Parser(builder.tokenStream);

		builder.VWS = VWS;
		builder.HWS = HWS;
		builder.BLOCKCOMMENT = BLOCKCOMMENT;
		builder.LINECOMMENT = LINECOMMENT;
		// builder.ERR_TOKEN = ERRCHAR;
		// featureBuilder.ERR_RULE = JavaParser.RULE_other << 16;
	}

	@Override
	public void extractFeatures(Builder model) {
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaFeatureVisitor visitor = new JavaFeatureVisitor(model);
		walker.walk(visitor, model.tree);
	}

	@Override
	public void defineIndentation(Indenter indenter) {
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaIndentVisitor visitor = new JavaIndentVisitor(indenter);
		walker.walk(visitor, builder.tree);
	}

	@Override
	public void locateAlignables(Aligner aligner) {
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaAlignVisitor visitor = new JavaAlignVisitor(aligner);
		walker.walk(visitor, builder.tree);
	}

	@Override
	public List<Integer> excludedTypes() {
		List<Integer> excludes = new ArrayList<>();
		excludes.add(Token.EOF);
		// excludes.add(JavaParser.ERRCHAR);
		// excludes.add(JavaParser.RULE_??? << 16);
		return excludes;
	}
}
