package net.certiv.adept.model.aspect;

import java.util.List;

import com.google.gson.annotations.Expose;

import net.certiv.adept.model.Feature;

public abstract class Aspect<T, E> {

	@Expose private long featId;			// key uniquely identifying associated feature

	private SimilarityCalc comp;

	public Aspect() {
		super();
		this.comp = new DefaultSimilarityCalc();
	}

	public long getFeatId() {
		return featId;
	}

	public SimilarityCalc getDefault() {
		return comp;
	}

	public void setDefault(SimilarityCalc comp) {
		this.comp = comp;
	}

	public abstract List<E> getAspectParts();

	public abstract double similarity(Feature other, E part);

}
