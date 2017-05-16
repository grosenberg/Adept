package net.certiv.adept.model;

/** Wrapper class to provide an 'equivalent' equals function */
public class EqEdge implements Comparable<EqEdge> {

	private Edge edge;

	private long leafType;
	private long rootType;
	private String leafText;
	private String rootText;

	public EqEdge(Edge edge) {
		this.edge = edge;

		leafType = edge.leaf.getType();
		rootType = edge.root.getType();
		leafText = getText(edge.leaf);
		rootText = getText(edge.root);
	}

	private String getText(Feature feature) {
		return feature.isVar() || feature.isRule() ? "" : feature.getText();
	}

	@Override
	public int compareTo(EqEdge o) {
		if (leafType < o.leafType) return -1;
		if (leafType > o.leafType) return 1;
		return leafText.compareTo(o.leafText);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		EqEdge o = (EqEdge) obj;
		if (edge == null || o.edge == null) return false;
		if (leafType != o.leafType) return false;
		if (rootType != o.rootType) return false;
		if (!leafText.equals(o.leafText)) return false;
		if (!rootText.equals(o.rootText)) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + leafText.hashCode();
		result = prime * result + (int) (leafType >>> 32);
		result = prime * result + (int) (leafType);
		result = prime * result + rootText.hashCode();
		result = prime * result + (int) (rootType >>> 32);
		result = prime * result + (int) (rootType);
		return result;
	}
}
