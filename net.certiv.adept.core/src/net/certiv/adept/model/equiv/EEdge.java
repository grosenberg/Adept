package net.certiv.adept.model.equiv;

import net.certiv.adept.model.Edge;
import net.certiv.adept.model.EdgeKey;

/** Wrapper class to provide an 'equivalent' equals function */
public class EEdge implements Comparable<EEdge> {

	private Edge edge;

	public EEdge(Edge edge) {
		this.edge = edge;
	}

	public EdgeKey getEdgeKey() {
		return edge.getEdgeKey();
	}

	@Override
	public int compareTo(EEdge ee) {
		if (this == ee) return 0;
		return getEdgeKey().compareTo(ee.getEdgeKey());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		EEdge other = (EEdge) obj;
		if (edge == null || other.edge == null) return false;
		if (!getEdgeKey().equals(other.getEdgeKey())) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edge == null) ? 0 : edge.hashCode());
		return result;
	}
}
