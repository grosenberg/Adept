package net.certiv.adept.model.topo;

import java.util.Set;

public class Eval {

	private boolean result = true;
	private Set<Facet> facets;

	public Eval(int format) {
		facets = Facet.get(format);
	}

	/** Contains all of the given elements. */
	public Eval all(Facet... elements) {
		for (Facet element : elements) {
			result &= facets.contains(element);
		}
		return this;
	}

	/** Contains any of the given elements. */
	public Eval any(Facet... elements) {
		boolean any = false;
		for (Facet element : elements) {
			any |= facets.contains(element);
		}
		result &= any;
		return this;
	}

	/** Contains none of the given elements. */
	public Eval none(Facet... elements) {
		boolean none = true;
		for (Facet element : elements) {
			none &= !facets.contains(element);
		}
		result &= none;
		return this;
	}

	public Eval and(Eval o) {
		result &= o.result();
		return this;
	}

	public Eval or(Eval o) {
		result |= o.result();
		return this;
	}

	/** Returns the current result value. The internal result value of this object is reset. */
	public boolean result() {
		boolean r = result;
		result = true;
		return r;
	}
}
