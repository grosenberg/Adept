package net.certiv.adept.view.models;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import org.antlr.v4.runtime.Token;

import net.certiv.adept.format.align.Align;
import net.certiv.adept.model.RefToken;

public abstract class BaseTableModel extends AbstractTableModel {

	protected static final Comparator<Number> NumComp = new Comparator<Number>() {

		@Override
		public int compare(Number o1, Number o2) {
			if (o1.doubleValue() < o2.doubleValue()) return -1;
			if (o1.doubleValue() > o2.doubleValue()) return 1;
			return 0;
		}
	};

	private static List<String> ruleNames;
	private static List<String> tokenNames;

	public BaseTableModel(List<String> ruleNames, List<String> tokenNames) {
		super();
		BaseTableModel.ruleNames = ruleNames;
		BaseTableModel.tokenNames = tokenNames;
	}

	protected static String tPlace(RefToken ref) {
		String ret = ref.place.toString();
		if (ref.place.atBOL()) ret += " (" + ref.indents + ")";
		return ret;
	}

	protected static String tAlign(RefToken ref) {
		if (ref.align == Align.NONE) return "None --";

		String alMsg = "%s {%s}  %s:%s (%s)";
		return String.format(alMsg, ref.align, ref.gap, ref.inGroup, ref.inLine, ref.grpTotal);
	}

	protected static String tSpace(RefToken ref) {
		String spMsg = "%s  %s  <  %s  >  %s  %s";
		return String.format(spMsg, fType(ref.lType), ref.lSpacing, fType(ref.type), ref.rSpacing, fType(ref.rType));
	}

	protected static String tLocation(RefToken ref) {
		String locMsg = "@%s:%s <%s>";
		return String.format(locMsg, ref.line, ref.col, ref.visCol);
	}

	protected static String tText(int type, String text) {
		return fType(type) + " " + text;
	}

	protected static String fType(int type) {
		String name = type == Token.EOF ? "EOF" : tokenNames.get(type);
		return String.format("%s (%s)", name, type);
	}

	protected static String sType(int type) {
		String name = type == Token.EOF ? "EOF" : tokenNames.get(type);
		return String.format("%s", name);
	}

	protected static String evalAncestors(List<Integer> ancestors) {
		int[] rules = ancestors.stream().mapToInt(i -> i).toArray();
		StringBuilder sb = new StringBuilder();
		for (int rule : rules) {
			sb.append(ruleNames.get(rule) + " > ");
		}
		sb.setLength(sb.length() - 3);
		return sb.toString();
	}

	protected static String evalTokens(Set<Integer> indexes, boolean showType) {
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
}
