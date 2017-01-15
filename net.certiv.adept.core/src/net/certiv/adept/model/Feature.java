package net.certiv.adept.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.Token;

import com.google.gson.annotations.Expose;

import net.certiv.adept.Tool;
import net.certiv.adept.topo.Point;
import net.certiv.adept.topo.Size;

public class Feature implements Comparable<Feature> {

	@Expose private int docId;	// unique id of the document containing this feature
	@Expose private int id;		// unique id of this feature

	@Expose private FeatureType featureType; // feature category

	@Expose private String aspect;	// rule or token name
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
	@Expose Edges edges = new Edges();

	private Feature matched;
	private boolean update;

	public Feature(String aspect, int type, Token token, Point location, Size size, int format) {
		this(aspect, type, token, token, location, size, format);
	}

	public Feature(String aspect, int type, Token start, Token stop, Point location, Size size, int format) {
		this.aspect = aspect;
		this.type = type;
		this.start = start.getTokenIndex();
		this.stop = stop.getTokenIndex();
		this.begLine = start.getLine() - 1;
		this.endLine = stop.getLine() - 1;
		this.format = format;

		x = location.getCol();
		y = location.getLine();
		w = size.getWidth();
		h = size.getHeight();
		weight = 1;
		update = true;
	}

	public FeatureType getFeatureType() {
		return featureType;
	}

	public void setFeatureType(FeatureType type) {
		this.featureType = type;
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

	public boolean isRule() {
		return featureType == FeatureType.RULE;
	}

	public boolean isNode() {
		return featureType == FeatureType.NODE;
	}

	public Feature getMatched() {
		return matched;
	}

	public void setMatched(Feature matched) {
		this.matched = matched;
	}

	public void addEdge(Feature leaf) {
		edges.addEdge(new Edge(this, leaf));
		update = true;
	}

	public Map<Integer, List<Edge>> getEdges() {
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
		Set<Integer> keys = new HashSet<>(getEdges().keySet());
		keys.addAll(other.getEdges().keySet());
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
		Feature other = (Feature) obj;
		if (type != other.type) return false;
		if (format != other.format) return false;
		if (weight != other.weight) return false;
		if (x != other.x) return false;
		if (y != other.y) return false;
		if (w != other.w) return false;
		if (h != other.h) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		return String.format("Feature [id=%s, x=%s, edges=%s]", id, x, edges);
	}
}
