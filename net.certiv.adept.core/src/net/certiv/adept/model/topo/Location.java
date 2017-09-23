package net.certiv.adept.model.topo;

import com.google.gson.annotations.Expose;

/** Identifies the location of a feature uniquely within the corpus model. */
public class Location {

	@Expose public int docId;
	@Expose public long id;
	@Expose public int line;
	@Expose public int col;

	public Location(int docId, long id, int line, int col) {
		this.docId = docId;
		this.id = id;
		this.line = line;
		this.col = col;
	}
}
