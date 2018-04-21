package net.certiv.adept.format;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.comment.parser.CommentSourceParser;
import net.certiv.adept.lang.comment.parser.gen.CommentParser;
import net.certiv.adept.lang.comment.parser.gen.CommentParser.BlankContext;
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
import net.certiv.adept.util.Strings;

public class CommentProcessor extends AbstractProcessor {

	private static final String DocBeg = "/** ";
	private static final String BlkBeg = "/* ";
	private static final String BlkMid = " * ";
	private static final String BlkEnd = " */";
	private static final String LineBeg = "// ";
	private static final String CodeBeg = "{@";
	private static final Object CodeEnd = "}";

	private static final Pattern Star = Pattern.compile("\\s*\\*");

	private CommentSourceParser parser;
	private ParseTreeProperty<TypeToken> properties;
	private String oneTab;

	private int visCol;
	private String prefix1;
	private String prefixN;
	private String listPrefix1;
	private String itemPrefix1;
	private String itemPrefixN;
	private int lastBlankIndex;
	private int continguousBlanks;
	private int listLevel;

	private String comment; // formatted results

	public CommentProcessor(FormatterOps ops) {
		super(ops);

		parser = new CommentSourceParser(this);
		properties = new ParseTreeProperty<>();
		oneTab = Strings.spaces(ops.settings.tabWidth);
	}

	@Override
	public void dispose() {
		ops = null;
		parser.dispose();
		parser = null;
		properties = null;
		comment = null;
	}

	public void formatComments() {
		if (!ops.data.commentIndex.isEmpty()) {
			for (int idx = firstCmtIdx(); idx < ops.data.commentIndex.size(); idx++) {
				AdeptToken token = ops.data.commentIndex.get(idx);
				if (process(token)) {
					token.setText(this.comment);
				}
			}
		}
	}

	private int firstCmtIdx() {
		int idx = 0;
		if (!ops.settings.formatHdrComment) {
			AdeptToken firstCmt = ops.data.commentIndex.get(idx);
			AdeptToken prior = ops.data.getRealLeft(firstCmt.getTokenIndex());
			if (prior == null && !firstCmt.isLineComment()) idx++;
		}
		return idx;
	}

	public boolean process(AdeptToken comment) {
		return parser.process(comment);
	}

	public String getResult() {
		return comment;
	}

	// --- Listener callbacks -- primary --------------------------------

	public void renderDocComment(DocContext ctx) {
		StringBuilder content = new StringBuilder();

		prefix1 = BlkMid;
		prefixN = BlkMid;
		int num = buildDescription((DescToken) properties.get(ctx.desc()), content);

		if (content.length() > 0) content.append(Strings.EOL + BlkMid);

		for (ParamContext param : ctx.param()) {
			num += buildParam((ParamToken) properties.get(param), content);
		}

		if (num == 1) {
			content.replace(0, BlkMid.length(), DocBeg);
		} else {
			content.insert(0, DocBeg + Strings.EOL);
			content.append(Strings.EOL);
		}
		content.append(BlkEnd);
		comment = content.toString();
	}

	public void renderBlockComment(DescContext desc) {
		StringBuilder content = new StringBuilder();

		prefix1 = BlkMid;
		prefixN = BlkMid;
		int num = buildDescription((DescToken) properties.get(desc), content);

		if (num == 1) {
			content.replace(0, BlkMid.length(), BlkBeg);
		} else {
			content.insert(0, BlkBeg + Strings.EOL);
			content.append(Strings.EOL);
		}
		content.append(BlkEnd);
		comment = content.toString();
	}

	public void renderLineComment(LineContext ctx) {
		StringBuilder content = new StringBuilder();

		prefix1 = LineBeg;
		prefixN = LineBeg;
		DescToken rec = new DescToken(ctx.getRuleIndex());
		for (ParseTree child : ctx.children) {
			TypeToken prop = properties.get(child);
			if (prop != null) rec.add(prop);
		}
		buildDescription(rec, content);
		comment = content.toString();
	}

	// --- Listener callbacks -- support --------------------------------

	public void param(ParamContext ctx, Token at, Token form, Token name, DescContext desc) {
		ParamToken rec = new ParamToken();
		rec.type = ctx.getRuleIndex();
		rec.at = at.getText();
		rec.at += form != null ? form.getText() : "";
		rec.name = name != null ? name.getText() : null;
		rec.desc = (DescToken) properties.get(desc);
		properties.put(ctx, rec);
	}

	public void description(DescContext ctx) {
		DescToken rec = new DescToken(ctx.getRuleIndex());
		for (ParseTree node : ctx.children) {
			TypeToken token = properties.get(node);
			if (token != null) rec.add(token);
		}
		properties.put(ctx, rec);
	}

	public void word(WordContext ctx, Token token) {
		properties.put(ctx, new StrToken(ctx.getRuleIndex(), token.getText()));
	}

	public void code(CodeContext ctx, Token mark, List<Token> words) {
		StringBuilder code = new StringBuilder(CodeBeg);
		for (Token word : words) {
			code.append(word.getText() + Strings.SPACE);
		}
		code.setLength(code.length() - 1);
		code.append(CodeEnd);
		properties.put(ctx, new StrToken(ctx.getRuleIndex(), code.toString()));
	}

	public void preform(PreformContext ctx, Token token) {
		String[] lines = Strings.splitLines(token.getText());
		for (String line : lines) {
			line = BlkMid + trimMark(line);
		}
		properties.put(ctx, new PreToken(ctx.getRuleIndex(), lines));
	}

	public void inline(InlineContext ctx, Token token) {
		properties.put(ctx, new StrToken(ctx.getRuleIndex(), token.getText()));
	}

	public void hdret(HdretContext ctx, Token token) {
		properties.put(ctx, new StrToken(ctx.getRuleIndex(), token.getText()));
	}

	public void flow(FlowContext ctx, Token token) {
		properties.put(ctx, new StrToken(ctx.getRuleIndex(), token.getText()));
	}

	public void list(ListContext ctx, Token token) {
		properties.put(ctx, new ListToken(ctx.getRuleIndex(), token.getText()));
	}

	public void item(ItemContext ctx, Token token) {
		properties.put(ctx, new StrToken(ctx.getRuleIndex(), token.getText()));
	}

	public void blank(BlankContext ctx) {
		if (ops.settings.removeBlankLinesComment) {
			int index = ctx.BLANK().getSymbol().getTokenIndex();
			if (continguousBlanks == 0 || lastBlankIndex + 1 == index) {
				continguousBlanks++;
				if (continguousBlanks >= ops.settings.keepNumBlankLines) return;

			} else {
				continguousBlanks = 0;
			}
			lastBlankIndex = index;
		}
		properties.put(ctx, new StrToken(ctx.getRuleIndex(), ""));
	}

	// ---- utility ----------------------------------------------------

	private int buildParam(ParamToken rec, StringBuilder doc) {
		StringBuilder param = new StringBuilder();

		param.append(BlkMid + rec.at + Strings.SPACE);
		int wrapColN = visCol + param.length() + ops.settings.tabWidth;

		if (rec.name != null) param.append(rec.name + Strings.SPACE);
		wrapColN = Math.min(visCol + param.length(), wrapColN);

		prefix1 = param.toString();
		prefixN = BlkMid + Strings.spaces(wrapColN - visCol - BlkMid.length());

		StringBuilder desc = new StringBuilder();
		int num = buildDescription(rec.desc, desc);
		doc.append(desc);
		return num;
	}

	private int buildDescription(DescToken rec, StringBuilder sb) {
		int num = 0;
		List<TextToken> wrap = new ArrayList<>();
		for (TypeToken token : rec.desc) {
			switch (token.type) {
				case CommentParser.RULE_word:
				case CommentParser.RULE_code:
				case CommentParser.RULE_inline:
					wrap.add((StrToken) token);
					break;

				case CommentParser.RULE_preform:
					num += commitWrap(sb, num, wrap);
					wrap.clear();

					// force preformatted
					if (sb.length() > 0) sb.append(Strings.EOL);
					sb.append(String.join(Strings.EOL, ((PreToken) token).lines));
					break;

				case CommentParser.RULE_blank:
					num += commitWrap(sb, num, wrap);
					wrap.clear();
					listLevel = 0;

					// force blank line
					if (sb.length() > 0) sb.append(Strings.EOL);
					sb.append(prefix1);
					break;

				case CommentParser.RULE_flow:
					num += commitWrap(sb, num, wrap);
					wrap.clear();
					listLevel = 0;

					wrap.add((StrToken) token);
					break;

				case CommentParser.RULE_hdret:
					wrap.add((StrToken) token);
					num += commitWrap(sb, num, wrap);
					wrap.clear();
					break;

				case CommentParser.RULE_list:
					num += commitWrap(sb, num, wrap);
					wrap.clear();

					ListToken list = (ListToken) token;
					if (list.open) {
						listLevel++;
					} else {
						if (listLevel > 0) listLevel--;
					}

					int indents = Math.max(0, listLevel - 1);
					listPrefix1 = BlkMid + Strings.spaces(indents * ops.settings.tabWidth);
					itemPrefix1 = listPrefix1 + oneTab;
					itemPrefixN = itemPrefix1 + oneTab;

					wrap.add(list);
					break;

				case CommentParser.RULE_item:
					ItemToken item = (ItemToken) token;
					if (!item.open) wrap.add(item);
					num += commitWrap(sb, num, wrap);
					wrap.clear();

					if (item.open) wrap.add(item);
					break;
			}
		}
		num += commitWrap(sb, num, wrap);
		return num;
	}

	// forces prefix1 to start on a new line
	private int commitWrap(StringBuilder sb, int num, List<TextToken> wrap) {
		if (!wrap.isEmpty()) {
			String content = wordWrap(wrap);
			num += Strings.countVWS(content);

			if (sb.length() > 0) sb.append(Strings.EOL);
			sb.append(content);
		}
		return num;
	}

	private String wordWrap(List<TextToken> words) {
		StringBuilder wrap = new StringBuilder();
		StringBuilder line = new StringBuilder();
		int limit = ops.settings.commentWidth - visCol;
		String prefix = listLevel == 0 ? prefix1 : itemPrefix1;

		line.append(prefix);
		for (int idx = 0, len = words.size(); idx < len; idx++) {
			TextToken token = words.get(idx);

			// no space before certain tags
			if (token.tag && !token.open) {
				int end = line.length() - 1;
				if (end > -1 && line.charAt(end) == Strings.SPC) {
					line.deleteCharAt(end);
				}
			}

			line.append(token.text);

			if (line.length() + 5 > limit && idx < len - 1) {
				wrap.append(line + Strings.EOL);
				line.setLength(0);
				line.append(listLevel == 0 ? prefixN : itemPrefixN);

			} else if (idx < len - 1) {
				// no space at EOL and after certain tags
				if (!(token.tag && token.open)) {
					line.append(Strings.SPACE);
				}
			}
		}
		wrap.append(line);
		return wrap.toString();
	}

	private String trimMark(String line) {
		Matcher m = Star.matcher(line);
		if (m.find() && m.start() == 0) {
			line = line.substring(0, m.end() - 1) + Strings.SPACE;
		}
		return line;
	}

	// ---- data classes -----------------------------------------------

	private class DescToken extends TypeToken {
		List<TypeToken> desc = new ArrayList<>();

		DescToken(int type) {
			this.type = type;
		}

		void add(TypeToken token) {
			desc.add(token);
		}
	}

	private class ParamToken extends TypeToken {
		String at;
		String name;
		DescToken desc;
	}

	private class PreToken extends TypeToken {
		String[] lines;

		PreToken(int type, String[] lines) {
			this.type = type;
			this.lines = lines;
		}
	}

	private class StrToken extends TextToken {

		StrToken(int type, String text) {
			super(type, text);
		}
	}

	private class ListToken extends TextToken {

		ListToken(int type, String tag) {
			super(type, tag);
		}
	}

	private class ItemToken extends TextToken {

		ItemToken(int type, String tag) {
			super(type, tag);
		}
	}

	private abstract class TextToken extends TypeToken {
		String text;
		boolean tag;
		boolean open;

		TextToken(int type, String text) {
			this.type = type;
			this.text = text;
			this.tag = text.length() > 1 && text.charAt(0) == '<';
			this.open = text.length() > 1 && text.charAt(1) != '/';
		}

		@Override
		public String toString() {
			return String.format("%4d: %s", type, text);
		}
	}

	private abstract class TypeToken {
		int type;
	}
}
