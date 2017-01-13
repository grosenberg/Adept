package net.certiv.adept.core;

import java.util.ArrayList;
import java.util.List;

public class OutputBuilder {

	class LineBuffer {

		TokenLine tokens;
		StringBuilder sb = new StringBuilder();
	}

	private final List<LineBuffer> lines = new ArrayList<>();

	public OutputBuilder() {
		super();
	}

	public boolean add(TokenLine line) {
		LineBuffer lb = new LineBuffer();
		lb.tokens = line;
		return lines.add(lb);
	}

	public void add(String text) {
		LineBuffer lb = getLast();
		lb.sb.append(text);
	}

	public LineBuffer get(int index) {
		return lines.get(index);
	}

	public LineBuffer getLast() {
		if (!isEmpty()) {
			return lines.get(lines.size() - 1);
		}
		return null;
	}

	public int size() {
		return lines.size();
	}

	public boolean isEmpty() {
		return lines.isEmpty();
	}

	public void clear() {
		lines.clear();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (LineBuffer line : lines) {
			result.append(line.sb.toString());
		}
		return result.toString();
	}
}
