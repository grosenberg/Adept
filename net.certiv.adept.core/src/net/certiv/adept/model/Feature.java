package net.certiv.adept.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Utils;

import com.google.common.collect.ComputationException;
import com.google.gson.annotations.Expose;

import net.certiv.adept.Tool;
import net.certiv.adept.model.equiv.EFeature;
import net.certiv.adept.parser.AdeptToken;
import net.certiv.adept.topo.Facet;
import net.certiv.adept.topo.Factor;
import net.certiv.adept.topo.Form;
import net.certiv.adept.topo.Point;
import net.certiv.adept.topo.Span;
import net.certiv.adept.topo.Stats;

public class Feature implements Comparable<Feature> {

	private static final String ErrInvalidDist = "Invalid distance computed (%s) for %s -> %s";
	private static final int TXTLEN = 16;
	private static Map<FeatKey, Feature> pool = new HashMap<>();

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
	@Expose private int lines;		// feature vertical span
	@Expose private int size;		// feature size
	@Expose private double weight;

	// defines connections between this feature and others in a local group
	@Expose EdgeSet edgeSet;

	// // key = docId; value = locations of equivalent features
	// @Expose Map<Integer, List<Location>> equivalents;

	private boolean reCalc;
	private int aligned;
	private double selfSim;
	private Feature matched;
	private EFeature equiv;

	// comments
	public static Feature create(Kind kind, String aspect, int docId, int id, int type, Token token, int format) {
		return create(kind, aspect, docId, id, type, token, format, false);
	}

	// nodes
	public static Feature create(Kind kind, String aspect, int docId, int id, int type, Token token, int format,
			boolean isVar) {
		return create(kind, aspect, docId, id, type, token, token, format, isVar);
	}

	// rules
	public static Feature create(Kind kind, String aspect, int docId, int id, int type, Token start, Token stop,
			int format, boolean isVar) {

		FeatKey key = FeatKey.create(docId, type, start, stop);
		Feature feature = pool.get(key);
		if (feature == null) {
			feature = new Feature(kind, aspect, docId, id, type, start, stop, format, isVar);
			pool.put(key, feature);
		}
		return feature;
	}

	Feature() {
		super();
		edgeSet = new EdgeSet();
		// equivalents = new HashMap<>();
	}

	Feature(Kind kind, String aspect, int docId, int id, int type, Token start, Token stop, int format, boolean isVar) {
		this();
		this.kind = kind;
		this.aspect = aspect;
		this.docId = docId;
		this.id = id;
		this.type = type;
		this.format = format;
		this.isVar = isVar;

		this.start = start.getTokenIndex();
		this.stop = stop.getTokenIndex();
		this.begLine = start.getLine() - 1;
		this.endLine = stop.getLine() - 1;

		this.text = genText(start.getInputStream(), start.getStartIndex(), stop.getStopIndex());

		Point coords = getCoords(start);
		x = coords.getCol();
		y = coords.getLine();

		Span span = getSize(start, stop);
		size = span.getWidth();
		lines = span.getHeight();

		weight = 1;
		reCalc = true;
	}

	private String genText(CharStream is, int startIndex, int stopIndex) {
		int end = stopIndex < (startIndex + TXTLEN) ? stopIndex : startIndex + TXTLEN - 3;
		String text = is.getText(new Interval(startIndex, end));
		if (end != stopIndex) text += "...";
		return Utils.escapeWhitespace(text, true);
	}

	private Point getCoords(Token start) {
		return ((AdeptToken) start).coords();
	}

	private Span getSize(Token start, Token stop) {
		if (start == stop) return new Span(start.getText().length(), 1);

		int height = stop.getLine() - start.getLine() + 1;
		int width = stop.getStopIndex() - start.getStartIndex() + 1;
		return new Span(width, height);
	}

	/** Adds an edge from the receiver, as root, to the given feature. Does not add duplicates. */
	public void addEdge(Feature leaf) {
		Edge edge = new Edge(this, leaf);
		if (edgeSet.addEdge(edge)) {
			reCalc = true;
		}
	}

	public EdgeSet getEdgeSet() {
		return edgeSet;
	}

	public List<Edge> getEdges(EdgeKey key) {
		return edgeSet.getEdges(key);
	}

	/**
	 * Returns a positive value representing a kernel-based distance between this and the given
	 * feature. A distance value of <code>zero</code> indicates that the two features are identical.
	 */
	public double distance(Feature other) {
		double dist = selfSimularity() + other.selfSimularity() - (2 * similarity(other));
		// Log.debug(this, "Distance: " + String.valueOf(dist));
		// Log.debug(this, "----------------");
		if (dist < 0) {
			String msg = String.format(ErrInvalidDist, dist, this.toString(), other.toString());
			throw new ComputationException(new Throwable(msg));
		}
		return dist;
	}

	public double selfSimularity() {
		if (reCalc) {
			selfSim = similarity(this);
			reCalc = false;
		}
		return selfSim;
	}

	// sum of feature label similarities and the edge set similarity
	public double similarity(Feature o) {
		double featSim = featLabelSimilarity(o);
		double edgeSim = getEdgeSet().similarity(o.getEdgeSet());
		// Log.debug(this, "Feature label sim: " + String.valueOf(featSim));
		// Log.debug(this, "Edge set sim : " + String.valueOf(edgeSim));
		return featSim + edgeSim;
	}

	// type and text (if applicable) have to be identical
	public double featLabelSimilarity(Feature other) {
		double sim = 0;
		Map<Factor, Double> boosts = Tool.mgr.getFactors();
		sim += boosts.get(Factor.SIZE) * norm(size, other.size);
		sim += boosts.get(Factor.WEIGHT) * norm(weight, other.weight);
		sim += boosts.get(Factor.EDGES) * norm(edgeSet.getEdgeCount(), other.edgeSet.getEdgeCount());
		sim += boosts.get(Factor.EDGE_TYPES) * norm(edgeSet.getTypeCount(), other.edgeSet.getTypeCount());
		sim += boosts.get(Factor.FORMAT) * Facet.similarity(format, other.format);
		return sim;
	}

	// TODO: normalize against total value of feature labels
	public static double norm(double a, double b) {
		if (a == 0 && b == 0) return 0;  // 0.0/0.0 == NaN
		double ave = (a + b) / 2;
		double max = Math.max(a, b);
		return ave / max;
	}

	/** Returns the number of unique types of feature type edgeSet */
	public int dimensionality() {
		return edgeSet.getTypeCount();
	}

	public Kind getKind() {
		return kind;
	}

	public boolean isVar() {
		return isVar;
	}

	public int getDocId() {
		return docId;
	}

	public int getId() {
		return id;
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

	public double getSelfSim() {
		return selfSim;
	}

	public int getAligned() {
		return aligned;
	}

	public Stats getStats() {
		return new Stats(this);
	}

	public Stats getStats(Feature matched) {
		return new Stats(this, matched);
	}

	// /**
	// * Equivalency is defined by identity of feature type, equality of edge sets, identity of edge
	// * leaf node text, and identity of format.
	// */
	// public boolean equivalentTo(Feature o) {
	// // same format
	// if (format != o.format) return false;
	// // same type of feature
	// if (type != o.type) return false;
	// // same number of edge types
	// if (edgeSet.getTypeCount() != o.edgeSet.getTypeCount()) return false;
	// // same edge type/text
	// Set<EdgeKey> keys = new HashSet<>(getEdgesMap().keySet());
	// keys.removeAll(o.getEdgesMap().keySet());
	// if (!keys.isEmpty()) return false;
	// // same edgeSet per key
	// for (EdgeKey key : getEdgesMap().keySet()) {
	// List<Edge> e1 = new ArrayList<>(getEdges(key));
	// List<Edge> e2 = new ArrayList<>(o.getEdges(key));
	// if (e1.size() != e2.size()) return false;
	// for (int idx = 0; idx < e1.size(); idx++) {
	// if (!e1.get(idx).equivalentTo(e2.get(idx))) return false;
	// }
	// }
	// return true;
	// }

	@Override
	public int compareTo(Feature o) {
		if (equals(o)) return 0;
		if (type < o.type) return -1;
		if (type > o.type) return 1;
		if (size < o.size) return -1;
		if (size > o.size) return 1;
		if (lines < o.lines) return -1;
		if (lines > o.lines) return 1;
		if (weight < o.weight) return -1;
		if (weight > o.weight) return 1;
		if (format < o.format) return -1;
		if (format > o.format) return 1;
		return 0;
	}

	public EFeature equiv() {
		if (equiv == null) {
			equiv = new EFeature(this);
		}
		return equiv;
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
		result = prime * result + size;
		result = prime * result + lines;
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
			if (size != o.size) sb.append("size [" + size + ":" + o.size + "] ");
			if (lines != o.lines) sb.append("lines [" + lines + ":" + o.lines + "] ");
			if (weight != o.weight) sb.append("rarity [" + weight + ":" + o.weight + "] ");
			if (selfSim != o.selfSim) sb.append("selfSim [" + selfSim + ":" + o.selfSim + "] ");
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return String.format("Feature [@%s:%s %s %s '%s']", y, x, kind, aspect, text);
	}
}
