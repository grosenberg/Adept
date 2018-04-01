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

	private static final String ProfileMsg = "%s \t @%d:%d";
	private static final String IndentMsg = "%3d %s %s %s";

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
		Mark.init(profile, data);
	}

	// ---- Access methods --------------------

	/**
	 * Returns a {@code Dent} characterizing the caculated indentation level at the given token index.
	 *
	 * @param index token index
	 * @return indentation level & affect hint
	 */
	public Dent getDent(int index) {
		Mark mark = profile.floorEntry(index).getValue();
		return mark.createDent(index);
	}

	public void clear() {
		profile.clear();
	}

	// ---- Indentation definition -------------

	/**
	 * Define the span of a statement, inclusive of the statement begin and end tokens and exclusive of
	 * any statement body.
	 */
	public void statement(TerminalNode beg, TerminalNode end) {
		if (beg.getSymbol().getTokenIndex() != end.getSymbol().getTokenIndex()) {
			Mark.create(profile, (AdeptToken) beg.getSymbol(), Op.BEG);
			Mark.create(profile, (AdeptToken) end.getSymbol(), Op.END);
		}
	}

	/**
	 * Define the span of an indentation block, typically a statement body -- inclusive of the block
	 * guards. Default is that the indentation change is naively applied {@code Bind.BEFORE} both the
	 * indent and outdent block guards: adjusted during finalize.
	 */
	public void indent(TerminalNode beg, TerminalNode end) {
		if (beg.getSymbol().getTokenIndex() != end.getSymbol().getTokenIndex()) {
			AdeptToken in = (AdeptToken) beg.getSymbol();
			AdeptToken out = (AdeptToken) end.getSymbol();
			Bind[] binds = calcBinds(in, out);

			Mark.create(profile, in, Op.IN, binds[0]);
			Mark.create(profile, out, Op.OUT, binds[1]);
		}
	}

	private Bind[] calcBinds(AdeptToken beg, AdeptToken end) {
		Bind[] binds = new Bind[] { Bind.AFTER, Bind.BEFORE };

		int pLine = beg.getLine();
		if (beg.atBol() && !data.blanklines.get(beg.getLine() - 1)) {
			pLine--;	// key to prior line bol
		}
		AdeptToken first = data.lineTokensIndex.get(pLine).get(0);
		int pOff = first.visCol();

		if (pLine == beg.getLine()) {
			binds[0] = Bind.AFTER;
		} else if (beg.atBol() && beg.visCol() < pOff) {
			binds[0] = Bind.AFTER;
		} else {
			binds[0] = Bind.BEFORE;
		}

		if (beg.getLine() == end.getLine()) {
			binds[1] = Bind.BEFORE;
		} else if (end.atBol() && end.visCol() < beg.visCol()) {
			binds[1] = Bind.BEFORE;
		} else {
			binds[1] = Bind.AFTER;
		}

		return binds;
	}

	// ---- Indentation support -----

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

	// ---- Indentation finalize -----

	public void finalize(ParserRuleContext ctx) {
		// Log.debug(this, "Profile");
		// checkProfile();
		// Log.debug(this, "Indents");
		// checkIndents();
	}

	void checkProfile() {
		for (Entry<Integer, Mark> entry : profile.entrySet()) {
			int index = entry.getKey();
			Mark mark = entry.getValue();
			String dent = mark.createDent(index).toString();
			int line = 0;
			int col = 0;
			if (index > -1) {
				AdeptToken token = data.getToken(index);
				line = token.getLine() + 1;
				col = token.getCharPositionInLine() + 1;
			}
			Log.debug(this, String.format(ProfileMsg, dent, line, col));
		}
	}

	void checkIndents() {
		int len = data.blanklines.size();

		for (int line = 0; line < len; line++) {
			List<AdeptToken> tokens = data.lineTokensIndex.get(line);
			if (tokens != null) {
				int tIndex = tokens.get(0).getTokenIndex();
				int lIndex = tokens.get(tokens.size() - 1).getTokenIndex();
				String range = String.format("<%s..%s>", tIndex, lIndex);
				Dent dent = getDent(tIndex);
				String indents = dent.toString();
				String graphic = Strings.getN(dent.indents, "--");
				Log.debug(this, String.format(IndentMsg, line + 1, indents, range, graphic));
			} else {
				Log.debug(this, String.format(IndentMsg, line + 1, "", "", "Blank"));
			}
		}
	}
}
