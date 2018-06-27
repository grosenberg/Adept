package net.certiv.adept.lang.chunk;

import java.util.ArrayList;
import java.util.List;

public class Chunk {

	int rule;	// rule number
	int begIdx; // first token index
	int endIdx; // last token index

	Tract x;

	// included chunks
	List<Chunk> elements = new ArrayList<>();

	// description: branch length sums and
	// other tree derivedd metrics
	double density;

	public Chunk() {}

	void density() {

	}
}
