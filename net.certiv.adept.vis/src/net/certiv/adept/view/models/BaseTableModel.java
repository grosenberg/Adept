package net.certiv.adept.view.models;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import org.antlr.v4.runtime.Token;

import net.certiv.adept.format.align.Align;
import net.certiv.adept.model.RefToken;

public abstract class BaseTableModel extends AbstractTableModel {

	private static final String DentMsg = "%s ($s) %s";
	private static final String AlignMsg = "%s {%s}  %s:%s (%s)";
	private static final String SpaceMsg = "%s  %s  <  %s  >  %s  %s";
	private static final String LocMsg = "@%s:%s <%s>";

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
		return ref.place.toString();
	}

	protected static String tIndent(RefToken ref) {
		return String.format(DentMsg, ref.dent.indents, ref.dent.bind, ref.dent.getIndents());
	}

	protected static String tAlign(RefToken ref) {
		if (ref.align == Align.NONE) return "None --";
		return String.format(AlignMsg, ref.align, ref.gap, ref.inGroup, ref.inLine, ref.grpTotal);
	}

	protected static String tSpace(RefToken ref) {
		return String.format(SpaceMsg, fType(ref.lType), ref.lSpacing, fType(ref.type), ref.rSpacing, fType(ref.rType));
	}

	protected static String tLocation(RefToken ref) {
		return String.format(LocMsg, ref.line, ref.col, ref.visCol);
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
