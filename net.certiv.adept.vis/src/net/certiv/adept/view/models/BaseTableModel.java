package net.certiv.adept.view.models;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Utils;

import net.certiv.adept.model.Bias;
import net.certiv.adept.model.Spacing;

public abstract class BaseTableModel extends AbstractTableModel {

	private List<String> ruleNames;
	private List<String> tokenNames;

	public BaseTableModel(List<String> ruleNames, List<String> tokenNames) {
		super();
		this.ruleNames = ruleNames;
		this.tokenNames = tokenNames;
	}

	protected static final Comparator<Number> NumComp = new Comparator<Number>() {

		@Override
		public int compare(Number o1, Number o2) {
			if (o1.doubleValue() < o2.doubleValue()) return -1;
			if (o1.doubleValue() > o2.doubleValue()) return 1;
			return 0;
		}
	};

	protected String evalTokenText(int type, String text) {
		String name = type == Token.EOF ? "EOF" : tokenNames.get(type);
		return String.format("%s [%s] %s", name, type, text);
	}

	protected String evalTokens(Set<Integer> indexes, boolean showType) {
		StringBuilder sb = new StringBuilder();
		for (int index : indexes) {
			String name = "";
			switch (index) {
				case -1:
					name = "EOF";
					break;
				case 0:
					name = "";
					break;
				default:
					name = tokenNames.get(index);
			}
			sb.append(name);
			if (showType) sb.append(String.format(" [%s], ", index));
			sb.append(" | ");
		}
		if (sb.length() > 1) sb.setLength(sb.length() - 3);
		return sb.toString();
	}

	protected String evalAncestors(List<Integer> ancestors) {
		int[] rules = ancestors.stream().mapToInt(i -> i >>> 16).toArray();
		StringBuilder sb = new StringBuilder();
		for (int rule : rules) {
			sb.append(ruleNames.get(rule) + " > ");
		}
		sb.setLength(sb.length() - 3);
		return sb.toString();
	}

	protected String evalSide(Spacing spacing, String ws, Set<Integer> tokens, Bias dir) {
		String sp = spacing != Spacing.UNKNOWN ? spacing.toString().toLowerCase() : "";
		String wsp = Utils.escapeWhitespace(ws, true);
		String tok = evalTokens(tokens, false);

		if (dir == Bias.LEFT) {
			return String.format("{%s} %s[%s]", tok, sp, wsp);
		} else {
			return String.format("%s[%s] {%s}", sp, wsp, tok);
		}
	}
}
