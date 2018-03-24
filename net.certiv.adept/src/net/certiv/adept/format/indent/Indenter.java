package net.certiv.adept.format.indent;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.util.Collect;

public class Indenter {

	// key=dent token index; value=dent
	private final TreeMap<Integer, Dent> profile;

	// operation
	private enum Op {
		ROOT,	// 0 indent level
		IN,		// indent
		OUT,	// outdent
		BEG,	// begin wrappable statement
		END;	// end same
	}

	// binding relative to index
	private enum Bind {
		BEFORE,
		AFTER;
	}

	private class Dent implements Comparator<Dent> {

		Op op;
		Bind bind;
		int indents;	// indent level
		int index;

		public Dent(AdeptToken token, Op op) {
			this.op = op;
			bind = op == Op.IN ? Bind.AFTER : Bind.BEFORE;
			indents = 0;
			index = token != null ? token.getTokenIndex() : -1;

			profile.put(index, this);

			if (index > -1) update(index);
		}

		private void update(int index) {
			Entry<Integer, Dent> entry = profile.lowerEntry(index);
			if (entry == null) throw new IllegalStateException("Invalid token index for indenter.");

			int priorIndents = entry.getValue().indents;

			while (true) {
				entry = profile.higherEntry(entry.getValue().index);
				if (entry == null) break;

				Dent cur = entry.getValue();
				switch (cur.op) {
					case ROOT:
						priorIndents = 0;
						break;
					case IN:
						priorIndents++;
						break;
					case OUT:
						priorIndents = Math.max(0, priorIndents - 1);
						break;
					case BEG:
					case END:
				}
				cur.indents = priorIndents;
			}
		}

		@Override
		public int compare(Dent o1, Dent o2) {
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
			Dent other = (Dent) obj;
			if (op != other.op) return false;
			if (bind != other.bind) return false;
			if (index != other.index) return false;
			return true;
		}

		@Override
		public String toString() {
			return "Dent [op=" + op + ", bind=" + bind + ", indents=" + indents + ", index=" + index + "]";
		}
	}

	public Indenter(ParseRecord data) {
		// this.data = data;

		profile = new TreeMap<>();
		new Dent(null, Op.ROOT); // initialize
	}

	// ----------------------------------------------------

	/**
	 * Returns the indentation level for the given token.
	 *
	 * @param token token
	 * @return indentation level
	 */
	public int getIndents(AdeptToken token) {
		return getIndents(token.getTokenIndex());
	}

	/**
	 * Returns the indentation level at the given token index.
	 *
	 * @param index token index
	 * @return indentation level
	 */
	public int getIndents(int index) {
		Dent dent = profile.floorEntry(index).getValue();
		if (index > dent.index) {
			switch (dent.op) {
				case BEG:
					return dent.indents + 2;
				default:
					return dent.indents;
			}
		}

		if (index == dent.index) {
			switch (dent.op) {
				case ROOT:
					return 0;
				case IN:
					return dent.bind == Bind.BEFORE ? dent.indents : dent.indents - 1;
				case OUT:
					return dent.bind == Bind.BEFORE ? dent.indents - 1 : dent.indents;
				case BEG:
				case END:
					return dent.indents;
			}
		}
		throw new IllegalArgumentException("Indent token index invalid.");
	}

	// ---- Indentation definition operations -------------

	/**
	 * Define the span of a statement, exclusive of any statement body -- inclusive of the statement end
	 * points.
	 */
	public void statement(TerminalNode beg, TerminalNode end) {
		new Dent((AdeptToken) beg.getSymbol(), Op.BEG);
		new Dent((AdeptToken) end.getSymbol(), Op.END);
	}

	/**
	 * Define the span of an indentation block, typically a statement body -- inclusive of the block
	 * guards.
	 */
	public void indent(TerminalNode beg, TerminalNode end) {
		new Dent((AdeptToken) beg.getSymbol(), Op.IN);
		new Dent((AdeptToken) end.getSymbol(), Op.OUT);
	}

	// ---- Indentation support operations -----

	/** Returns the first non-null Terminal node in the given ordered array of elements. */
	@SuppressWarnings("unchecked")
	public TerminalNode first(Object... elems) {
		for (Object elem : elems) {
			if (elem != null) {
				if (elem instanceof TerminalNode) return (TerminalNode) elem;
				if (elem instanceof ParserRuleContext) {
					return findFirstTerminal((ParserRuleContext) elem);
				}
				if (elem instanceof List<?> && Collect.notEmpty((List<? extends Object>) elem)) {
					return first(((List<? extends Object>) elem).get(0));
				}
			}
		}
		throw new IllegalArgumentException("No TerminalNode in args.");
	}

	/** Returns the non-null Terminal node prior to the given context. */
	public TerminalNode before(ParserRuleContext ctx) {
		ParserRuleContext parent = ctx.getParent();
		for (int idx = parent.getChildCount() - 1; idx > -1; idx--) {
			if (parent.getChild(idx) == ctx) {
				return last(parent.getChild(idx - 1));
			}
		}
		throw new IllegalArgumentException("No TerminalNode in args.");
	}

	/** Returns the last non-null Terminal node in the given ordered array of elements. */
	@SuppressWarnings("unchecked")
	public TerminalNode last(Object... elems) {
		for (int idx = elems.length - 1; idx > -1; idx--) {
			if (elems[idx] != null) {
				if (elems[idx] instanceof TerminalNode) return (TerminalNode) elems[idx];
				if (elems[idx] instanceof ParserRuleContext) {
					return findLastTerminal((ParserRuleContext) elems[idx]);
				}
				if (elems[idx] instanceof List<?> && Collect.notEmpty((List<? extends Object>) elems[idx])) {
					return first(((List<? extends Object>) elems[idx]).get(0));
				}
			}
		}
		throw new IllegalArgumentException("No TerminalNode in args.");
	}

	// -------------------------------------------------------

	private TerminalNode findFirstTerminal(ParserRuleContext elem) {
		ParseTree child = elem.getChild(0);
		while (child != null && child instanceof RuleNode) {
			child = child.getChild(0);
		}
		if (child == null) throw new IllegalStateException("Terminal not found.");
		return (TerminalNode) child;
	}

	private TerminalNode findLastTerminal(ParserRuleContext elem) {
		ParseTree child = elem.getChild(elem.getChildCount() - 1);
		while (child != null && child instanceof RuleNode) {
			child = child.getChild(child.getChildCount() - 1);
		}
		if (child == null) throw new IllegalStateException("Terminal not found.");
		return (TerminalNode) child;
	}

	public void clear() {
		profile.clear();
	}
}
