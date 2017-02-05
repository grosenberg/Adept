package net.certiv.adept.model.equiv;

import net.certiv.adept.model.Feature;

/** Wrapper class to provide an 'equivalent' equals function */
public class EFeature implements Comparable<EFeature> {

	private Feature feature;

	public EFeature(Feature feature) {
		this.feature = feature;
	}

	public String getText() {
		return feature.getText();
	}

	public int getType() {
		return feature.getType();
	}

	public boolean isVar() {
		return feature.isVar();
	}

	@Override
	public int compareTo(EFeature o) {
		if (equals(o)) return 0;
		if (getType() < o.getType()) return -1;
		if (getType() > o.getType()) return 1;
		if (!isVar()) return getText().compareTo(o.getText());
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		EFeature other = (EFeature) obj;
		if (feature == null || other.feature == null) return false;
		if (getType() != other.getType()) return false;
		if (!isVar()) {
			if (!getText().equals(other.getText())) return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((feature == null) ? 0 : feature.hashCode());
		return result;
	}
}
