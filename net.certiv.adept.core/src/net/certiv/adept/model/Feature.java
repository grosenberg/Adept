package net.certiv.adept.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Utils;

import com.google.gson.annotations.Expose;

import net.certiv.adept.Tool;
import net.certiv.adept.topo.Form;
import net.certiv.adept.topo.Location;
import net.certiv.adept.topo.Point;
import net.certiv.adept.topo.Size;
import net.certiv.adept.topo.Stats;

public class Feature implements Comparable<Feature> {

	@Expose private int docId;		// unique id of the document containing this feature
	@Expose private int id;			// unique id of this feature

	@Expose private Kind kind;		// feature category
	@Expose private boolean isVar;	// variable content is ignored

	@Expose private String aspect;	// rule or token name
	@Expose private String text;	// rule or token text
	@Expose private int type;		// encoded token type or rule id (ids are << 10)
	@Expose private int format;		// describes this feature's Facet

	@Expose private int start;		// feature token start index
	@Expose private int stop;		// feature token stop index
	@Expose private int begLine;	// feature start token line
	@Expose private int endLine;	// feature stop token line

	@Expose private int x;			// feature locus
	@Expose private int y;
	@Expose private int w;			// feature size
	@Expose private int h;
	@Expose private double weight;
	@Expose private double selfSim;

	// defines connections between this feature and others in a local group
	@Expose Edges edges;

	// key = docId; value = locations of equivalent features
	@Expose Map<Integer, List<Location>> equivalents;

	private Feature matched;
	private boolean update;
	private int aligned;

	public Feature() {
		super();
		edges = new Edges();
		equivalents = new HashMap<>();
	}

	public Feature(String aspect, int type, Token token, Point location, Size size, int format) {
		this(aspect, type, token, token, location, size, format);
	}

	public Feature(String aspect, int type, Token start, Token stop, Point location, Size size, int format) {
		this();
		this.aspect = aspect;
		this.type = type;
		this.start = start.getTokenIndex();
		this.stop = stop.getTokenIndex();
		this.begLine = start.getLine() - 1;
		this.endLine = stop.getLine() - 1;
		this.format = format;

		int begIdx = start.getStartIndex();
		int endIdx = stop.getStopIndex();
		int endIdx2 = endIdx - 10 > begIdx ? begIdx + 7 : endIdx;
		this.text = start.getInputStream().getText(new Interval(begIdx, endIdx2));
		if (endIdx != endIdx2) this.text += "...";
		this.text = Utils.escapeWhitespace(this.text, true);

		x = location.getCol();
		y = location.getLine();
		w = size.getWidth();
		h = size.getHeight();
		weight = 1;
		update = true;
	}

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public boolean isVar() {
		return isVar;
	}

	public void setVar(boolean isVar) {
		this.isVar = isVar;
	}

	public int getDocId() {
		return docId;
	}

	public int getId() {
		return id;
	}

	/** Sets the document and feature IDs */
	public void setId(int docId, int id) {
		this.docId = docId;
		this.id = id;
	}

	public String getAspect() {
		return aspect;
	}

	public String getText() {
		return text;
	}

	/** Returns the combined type of the feature */
	public int getType() {
		return type;
	}

	/** Returns the start token index */
	public int getStart() {
		return start;
	}

	/** Returns the stop token index */
	public int getStop() {
		return stop;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getWeight() {
		return weight;
	}

	public int getFormat() {
		return format;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public boolean isAlignedAbove() {
		return (aligned & Form.ABOVE) == Form.ABOVE;
	}

	public boolean isAlignedBelow() {
		return (aligned & Form.BELOW) == Form.BELOW;
	}

	public void setAligned(int aligned) {
		this.aligned |= aligned;
	}

	public boolean isRule() {
		return kind == Kind.RULE;
	}

	public boolean isNode() {
		return kind == Kind.NODE;
	}

	public Feature getMatched() {
		return matched;
	}

	public void setMatched(Feature matched) {
		this.matched = matched;
	}

	/** Adds an edge from the receiver to the given feature. Does not add duplicate edges. */
	public void addEdge(Feature leaf) {
		Edge edge = new Edge(this, leaf);
		if (!edges.contains(edge)) {
			edges.addEdge(edge);
			update = true;
		}
	}

	public Edges getEdges() {
		return edges;
	}

	public Map<Integer, List<Edge>> getEdgesMap() {
		return edges.getEdges();
	}

	public List<Edge> getEdges(int type) {
		return edges.getEdges(type);
	}

	/** Returns a value representing a mgr distance between the two given features */
	public double distance(Feature other) {
		return simularity() + simularity() - 2 * simularity(other);
	}

	/** Returns the self similarity of this feature */
	public double simularity() {
		if (update) {
			selfSim = simularity(this);
			update = false;
		}
		return selfSim;
	}

	public double simularity(Feature other) {
		Set<Integer> keys = new HashSet<>(getEdgesMap().keySet());
		keys.addAll(other.getEdgesMap().keySet());
		double sim = 0;
		for (int key : keys) {
			List<Edge> e1 = getEdges(key);
			List<Edge> e2 = other.getEdges(key);
			if (e1 == null || e2 == null) continue;
			sim += similarity(e1, e2);
		}
		return sim;
	}

	private double similarity(List<Edge> e1, List<Edge> e2) {
		double sim = 0;
		for (Edge outer : e1) {
			for (Edge inner : e2) {
				sim += similarity(outer, inner);
			}
		}
		return sim;
	}

	// use normed Hamming distance for format similarity calc
	// use normed difference for weight, metric, and size similarity
	private double similarity(Edge outer, Edge inner) {
		double boost = Tool.mgr.typeBoost(outer, inner);
		double sim = 0;
		sim += boost * Integer.bitCount(outer.leaf.format ^ inner.leaf.format) / 32;
		sim += boost * norm(outer.weight, inner.weight);
		sim += boost * norm(outer.metric, inner.metric);
		sim += boost * norm(outer.leaf.w, inner.leaf.w);
		sim += boost * norm(outer.leaf.h, inner.leaf.h);
		return sim;
	}

	private double norm(double a, double b) {
		double sum = a + b;
		double dif = Math.abs(a - b);
		return (sum - dif) / sum;
	}

	/** Returns the number of unique types of feature type edges */
	public int dimentionality() {
		return edges.typeSize();
	}

	public Stats getStats() {
		return new Stats(this);
	}

	public void mergeEquivalent(Feature feature) {
		weight++;
		List<Location> did = equivalents.get(feature.docId);
		if (did == null) {
			did = new ArrayList<>();
			equivalents.put(feature.docId, did);
		}
		did.add(new Location(feature.docId, feature.id, feature.y, feature.x));
	}

	/**
	 * Equivalency is defined by identity of feature type, equality of edge sets, identity of edge
	 * leaf node text, and identity of format.
	 */
	public boolean equivalentTo(Feature o) {
		// same format
		if (format != o.format) return false;
		// same type of feature
		if (type != o.type) return false;
		// same number of edge types
		if (getEdgesMap().size() != o.getEdgesMap().size()) return false;
		// same edge types
		Set<Integer> types = new HashSet<>(getEdgesMap().keySet());
		types.removeAll(o.getEdgesMap().keySet());
		if (!types.isEmpty()) return false;
		// same edges per type
		for (Integer etype : getEdgesMap().keySet()) {
			List<Edge> e1 = getEdges(etype);
			List<Edge> e2 = new ArrayList<>(o.getEdges(etype));
			if (e1.size() != e2.size()) return false;
			for (Edge a : e1) {
				for (Edge b : e2) {
					if (a.equivalentTo(b)) {
						e2.remove(b);
						break;
					}
				}
			}
			if (!e2.isEmpty()) return false;
		}
		return true;
	}

	@Override
	public int compareTo(Feature o) {
		if (equals(o)) return 0;
		if (type < o.type) return -1;
		if (type > o.type) return 1;
		if (w < o.w) return -1;
		if (w > o.w) return 1;
		if (h < o.h) return -1;
		if (h > o.h) return 1;
		if (weight < o.weight) return -1;
		if (weight > o.weight) return 1;
		if (format < o.format) return -1;
		if (format > o.format) return 1;
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		Feature o = (Feature) obj;
		if (docId != o.docId) return false;
		if (id != o.id) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + docId;
		result = prime * result + id;
		result = prime * result + type;
		result = prime * result + format;
		result = prime * result + Double.hashCode(weight);
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + w;
		result = prime * result + h;
		return result;
	}

	public String diff(Feature o) {
		StringBuilder sb = new StringBuilder();
		if (o == null) {
			sb.append("arg is null");
		} else {
			if (docId != o.docId) sb.append("docId [" + docId + ":" + o.docId + "] ");
			if (id != o.id) sb.append("id [" + id + ":" + o.id + "] ");
			if (type != o.type) sb.append("type [" + type + ":" + o.type + "] ");
			if (format != o.format) sb.append("format [" + format + ":" + o.format + "] ");
			if (start != o.start) sb.append("start [" + start + ":" + o.start + "] ");
			if (stop != o.stop) sb.append("stop [" + stop + ":" + o.stop + "] ");
			if (begLine != o.begLine) sb.append("begLine [" + begLine + ":" + o.begLine + "] ");
			if (endLine != o.endLine) sb.append("endLine [" + endLine + ":" + o.endLine + "] ");
			if (x != o.x) sb.append("x [" + x + ":" + o.x + "] ");
			if (y != o.y) sb.append("y [" + y + ":" + o.y + "] ");
			if (w != o.w) sb.append("w [" + w + ":" + o.w + "] ");
			if (h != o.h) sb.append("h [" + h + ":" + o.h + "] ");
			if (weight != o.weight) sb.append("weight [" + weight + ":" + o.weight + "] ");
			if (selfSim != o.selfSim) sb.append("selfSim [" + selfSim + ":" + o.selfSim + "] ");
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return String.format("Feature [@%s:%s %s %s '%s']", y, x, kind, aspect, text);
	}
}
