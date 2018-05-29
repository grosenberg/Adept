/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang.antlr.parser;

import static net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer.*;

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
import net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer;
import net.certiv.adept.lang.antlr.parser.gen.Antlr4Parser;
import net.certiv.adept.model.Document;
import net.certiv.adept.tool.ErrorDesc;

public class AntlrSourceParser extends SourceParser {

	@Override
	public void process(Tool tool, Builder builder, Document doc) throws RecognitionException, Exception {
		this.tool = tool;
		this.builder = builder;

		AdeptTokenFactory factory = new AdeptTokenFactory();
		setup(factory, doc.getContent());

		builder.parser.setTokenFactory(factory);
		builder.parser.removeErrorListeners();
		builder.parser.addErrorListener(new ParserErrorListener(this));

		builder.tree = ((Antlr4Parser) builder.parser).antlr();

		if (builder.tree == null || builder.tree instanceof ErrorNode || builder.errCount > 0) {
			tool.toolError(this, ErrorDesc.PARSE_ERROR, "Bad parse tree: " + doc.getPathname());
		}
	}

	@Override
	public void setup(TokenFactory<?> factory, String content) {
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
}
