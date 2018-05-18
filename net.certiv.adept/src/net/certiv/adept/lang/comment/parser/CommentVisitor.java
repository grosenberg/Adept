/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang.comment.parser;

import net.certiv.adept.format.CommentProcessor;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.BlankContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.BlockContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.CodeContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.DescContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.DocContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.FlowContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.HdretContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.InlineContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.ItemContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.LineContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.ListContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.ParamContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.PreformContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.WordContext;
import net.certiv.adept.lang.comment.parser.gen.CommentParserBaseListener;

public class CommentVisitor extends CommentParserBaseListener {

	private CommentProcessor helper;

	public CommentVisitor(CommentProcessor helper) {
		this.helper = helper;
	}

	// -----------------------------------------------

	@Override
	public void exitDoc(DocContext ctx) {
		helper.renderDocComment(ctx);
	}

	@Override
	public void exitBlock(BlockContext ctx) {
		helper.renderBlockComment(ctx.desc());
	}

	@Override
	public void exitLine(LineContext ctx) {
		helper.renderLineComment(ctx);
	}

	// -----------------------------------------------

	@Override
	public void exitDesc(DescContext ctx) {
		helper.description(ctx);
	}

	@Override
	public void exitParam(ParamContext ctx) {
		helper.param(ctx, ctx.at, ctx.word(), ctx.desc());
	}

	@Override
	public void exitCode(CodeContext ctx) {
		helper.code(ctx, ctx.mark, ctx.word());
	}

	@Override
	public void exitPreform(PreformContext ctx) {
		helper.preform(ctx, ctx.PREFORM().getSymbol());
	}

	@Override
	public void exitInline(InlineContext ctx) {
		helper.inline(ctx, ctx.INLINE().getSymbol());
	}

	@Override
	public void exitHdret(HdretContext ctx) {
		helper.hdret(ctx, ctx.HDRET().getSymbol());
	}

	@Override
	public void exitFlow(FlowContext ctx) {
		helper.flow(ctx, ctx.FLOW().getSymbol());
	}

	@Override
	public void exitList(ListContext ctx) {
		helper.list(ctx, ctx.LIST().getSymbol());
	}

	@Override
	public void exitItem(ItemContext ctx) {
		helper.item(ctx, ctx.ITEM().getSymbol());
	}

	@Override
	public void exitWord(WordContext ctx) {
		helper.word(ctx);
	}

	@Override
	public void exitBlank(BlankContext ctx) {
		helper.blank(ctx);
	}
}
