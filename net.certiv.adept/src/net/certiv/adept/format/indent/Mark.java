package net.certiv.adept.format.indent;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;

/**
 * Description of the indentation at the specific token index of a change. The indentation level
 * value is naive: presumes that the value change occurs {@code Bind.BEFORE} the {@code Mark} index.
 */
public class Mark implements Comparator<Mark> {

	private static ParseRecord _data;
	int index;
	int indents;	// nominal indent level at a token index
	Op op;			// indent direction
	Bind bind;		// indenting hint

	public static void init(TreeMap<Integer, Mark> profile, ParseRecord data) {
		_data = data;
		create(profile, null, Op.ROOT);
	}

	public static Mark create(TreeMap<Integer, Mark> profile, AdeptToken token, Op op) {
		return new Mark(profile, token, op, Bind.BEFORE);
	}

	public static Mark create(TreeMap<Integer, Mark> profile, AdeptToken token, Op op, Bind bind) {
		return new Mark(profile, token, op, bind);
	}

	private Mark(TreeMap<Integer, Mark> profile, AdeptToken token, Op op, Bind bind) {
		this.op = op;
		this.bind = bind;
		index = token != null ? token.getTokenIndex() : -1;

		// add mark to profile
		profile.put(index, this);

		// dynamically update indent levels
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

	/** Create a Dent that reflects the values of this Mark at the given token index. */
	public Dent createDent(int index) {
		Dent dent = new Dent(this);
		if (this.index == index) {					// on mark
			if (bind == Bind.AFTER) {
				if (op == Op.IN) {
					dent.indents--;
				} else if (op == Op.OUT) {
					dent.indents++;
				}
			}

		} else if (index > this.index) {			// after the mark
			if (op == Op.BEG && bind == Bind.WRAP) {
				dent.indents += dent.wrap;
			}
		}

		return dent;
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
			text = _data.getTokenInterval(index, index).get(0).getText();
		}
		return String.format("%s %s:%s '%s'", super.toString(), op, index, text);
	}
}
