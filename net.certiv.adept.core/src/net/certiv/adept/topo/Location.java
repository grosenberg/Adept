package net.certiv.adept.topo;

import com.google.gson.annotations.Expose;

/** Identifies the location of a feature uniquely within the corpus model. */
public class Location {

	@Expose public int docId;
	@Expose public int featureId;
	@Expose public int line;
	@Expose public int col;

	public Location(int docId, int id, int line, int col) {
		this.docId = docId;
		this.featureId = id;
		this.line = line;
		this.col = col;
	}
}
