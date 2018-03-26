package net.certiv.adept.format.indent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

public class Indenter {

	private static final String IndMsg = "%3d %s %s %s";
	private static final Comparator<ParseTree> PtComp = new Comparator<ParseTree>() {

		@Override
		public int compare(ParseTree p1, ParseTree p2) {
			int p1beg;
			int p1end;
			int p2beg;
			int p2end;

			if (p1 instanceof TerminalNode) {
				p1beg = p1end = ((TerminalNode) p1).getSymbol().getTokenIndex();
			} else {
				p1beg = ((ParserRuleContext) p1).start.getTokenIndex();
				p1end = ((ParserRuleContext) p1).stop.getTokenIndex();
			}

			if (p2 instanceof TerminalNode) {
				p2beg = p2end = ((TerminalNode) p2).getSymbol().getTokenIndex();
			} else {
				p2beg = ((ParserRuleContext) p2).start.getTokenIndex();
				p2end = ((ParserRuleContext) p2).stop.getTokenIndex();
			}

			if (p1end < p2beg) return -1;
			if (p1beg > p2end) return 1;
			return 0;
		}
	};

	// key=token index; value=dent
	private final TreeMap<Integer, Mark> profile = new TreeMap<>();
	private ParseRecord data;

	public Indenter(ParseRecord data) {
		this.data = data;

		// initialize profile
		new Mark(null, Op.ROOT);
	}

	// ----------------------------------------------------

	/**
	 * Returns the default caculated indentation level at the given token index.
	 *
	 * @param index token index
	 * @return indentation level & affect hint
	 */
	public Dent getDent(int index) {
		Mark mark = profile.floorEntry(index).getValue();
		if (mark.index < index && mark.op == Op.BEG) {
			return new Dent(mark.indents, Bind.WRAP);	// in wrappable statement
		}
		return mark;
	}

	public void clear() {
		profile.clear();
	}

	// ---- Indentation definition operations -------------

	/**
	 * Define the span of a statement, inclusive of the statement begin and end tokens and exclusive of
	 * any statement body.
	 */
	public void statement(TerminalNode beg, TerminalNode end) {
		if (beg.getSymbol().getTokenIndex() != end.getSymbol().getTokenIndex()) {
			new Mark((AdeptToken) beg.getSymbol(), Op.BEG);
			new Mark((AdeptToken) end.getSymbol(), Op.END);
		}
	}

	/**
	 * Define the span of an indentation block, typically a statement body -- inclusive of the block
	 * guards. Default is set for the indentation change to be applied {@code Bind.AFTER} the indent
	 * block guard and {@code Bind.BEFORE} the outdent block guard.
	 */
	public void indent(TerminalNode beg, TerminalNode end) {
		if (beg.getSymbol().getTokenIndex() != end.getSymbol().getTokenIndex()) {
			new Mark((AdeptToken) beg.getSymbol(), Op.IN);
			new Mark((AdeptToken) end.getSymbol(), Op.OUT);
		}
	}

	// ---- Indentation support operations -----

	/** Returns the non-null TerminalNode prior to the given context. */
	public TerminalNode before(ParserRuleContext ctx) {
		ParserRuleContext parent = ctx.getParent();
		int ndx = parent.children.indexOf(ctx);
		if (ndx > 0) return last(parent.getChild(ndx - 1));

		throw new IllegalArgumentException("No TerminalNode before arg.");
	}

	/** Returns the first non-null TerminalNode in the given un-ordered array of nodes. */
	public TerminalNode first(Object... nodes) {
		return first(flatten(nodes));
	}

	private TerminalNode first(List<ParseTree> nodes) {
		TreeSet<ParseTree> ordered = new TreeSet<>(PtComp);
		ordered.addAll(nodes);
		ParseTree node = ordered.first();
		if (node instanceof TerminalNode) return (TerminalNode) node;
		return first(((ParserRuleContext) node).children);
	}

	/** Returns the last non-null TerminalNode in the given un-ordered array of ParseTree nodes. */
	public TerminalNode last(Object... nodes) {
		return last(flatten(nodes));
	}

	private TerminalNode last(List<ParseTree> nodes) {
		TreeSet<ParseTree> ordered = new TreeSet<>(PtComp);
		ordered.addAll(nodes);
		ParseTree node = ordered.last();
		if (node instanceof TerminalNode) return (TerminalNode) node;
		return last(((ParserRuleContext) node).children);
	}

	@SuppressWarnings("unchecked")
	private List<ParseTree> flatten(Object[] nodes) {
		List<ParseTree> flat = new ArrayList<>();
		for (Object node : nodes) {
			if (node != null) {
				if (node instanceof List<?>) {
					flat.addAll((List<? extends ParseTree>) node);
				} else {
					flat.add((ParseTree) node);
				}
			}
		}
		return flat;
	}

	// ---- Indentation finalize operations -----

	public void finalize(ParserRuleContext ctx) {
		// checkProfile();
		// checkIndents() ;
	}

	private static final String ProMsg = "@%d:%d <%d> %s";

	@SuppressWarnings("unused")
	private void checkProfile() {
		for (Entry<Integer, Mark> entry : profile.entrySet()) {
			int index = entry.getKey();
			String dent = entry.getValue().toString();
			int line = 0;
			int col = 0;
			if (index > -1) {
				AdeptToken token = data.getToken(index);
				line = token.getLine() + 1;
				col = token.getCharPositionInLine() + 1;
			}
			Log.debug(this, String.format(ProMsg, line, col, index, dent));
		}
	}

	@SuppressWarnings("unused")
	private void checkIndents() {
		int len = data.blanklines.size();

		for (int line = 0; line < len; line++) {
			List<AdeptToken> tokens = data.lineTokensIndex.get(line);
			if (tokens != null) {
				int tIndex = tokens.get(0).getTokenIndex();
				int lIndex = tokens.get(tokens.size() - 1).getTokenIndex();
				String range = String.format("<%s..%s>", tIndex, lIndex);
				Dent dent = getDent(tIndex);
				String indents = dent.toString();
				String graphic = Strings.getN(dent.getIndents(), "--");
				Log.debug(this, String.format(IndMsg, line + 1, indents, range, graphic));
			} else {
				Log.debug(this, String.format(IndMsg, line + 1, "", "", "Blank"));
			}
		}
	}

	// -------------------------------------------------------

	private class Mark extends Dent implements Comparator<Mark> {

		Op op;
		int index;

		public Mark(AdeptToken token, Op op) {
			super(0, Bind.BEFORE);
			this.op = op;
			index = token != null ? token.getTokenIndex() : -1;
			profile.put(index, this);

			if (token != null) {
				Entry<Integer, Mark> pEntry = profile.lowerEntry(index);
				if (pEntry == null) throw new IllegalStateException("Invalid token index for indenter.");

				Mark prior = pEntry.getValue();
				for (Mark current : profile.tailMap(index).values()) {
					switch (current.op) {
						case ROOT:
							current.indents = 0;
							break;
						case IN:
							current.indents = prior.indents + 1;
							current.bind = Bind.AFTER;
							break;
						case OUT:
							current.indents = Math.max(0, prior.indents - 1);
							break;
						default:
							current.indents = prior.indents;
					}
					prior = current;
				}
			}
		}

		@Override
		public int compare(Mark o1, Mark o2) {
			if (o1.index < o2.index) return -1;
			if (o1.index > o2.index) return 1;
			return 0;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((op == null) ? 0 : op.hashCode());
			result = prime * result + ((bind == null) ? 0 : bind.hashCode());
			result = prime * result + index;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			Mark other = (Mark) obj;
			if (op != other.op) return false;
			if (bind != other.bind) return false;
			if (index != other.index) return false;
			return true;
		}

		@Override
		public String toString() {
			String text = "ROOT";
			if (index > -1) {
				text = data.getTokenInterval(index, index).get(0).getText();
			}
			return String.format("%s %s:%s '%s'", super.toString(), op, index, text);
		}
	}

	// Mark operations
	private enum Op {
		ROOT,	// 0 indent level (force)

		IN,		// indent
		OUT,	// dedent

		BEG,	// wrappable statement
		END;
	}
}
