package net.certiv.adept.model.util;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + docId;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + line;
		return result;
	}

	public boolean coequals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		Location other = (Location) obj;
		if (docId != other.docId) return false;
		if (line != other.line) return false;
		if (col != other.col) return false;

		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		Location other = (Location) obj;
		if (docId != other.docId) return false;
		if (id != other.id) return false;
		if (line != other.line) return false;
		if (col != other.col) return false;

		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("doc ");
		builder.append(docId);
		builder.append(" @");
		builder.append(line);
		builder.append(":");
		builder.append(col);
		return builder.toString();
	}
}
