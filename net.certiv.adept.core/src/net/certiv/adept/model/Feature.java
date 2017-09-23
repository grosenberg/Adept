package net.certiv.adept.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.gson.annotations.Expose;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.model.topo.Facet;
import net.certiv.adept.model.topo.Factor;
import net.certiv.adept.model.topo.Location;
import net.certiv.adept.model.topo.Stats;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Maths;

public class Feature implements Comparable<Feature> {

	private static final String ErrInvalidDist = "Invalid distance computed (%s) for %s -> %s";
	private static final int TXTLEN = 16;

	private static Map<Feature, Feature> pool = new LinkedHashMap<>();

	@Expose private int id;			// internal value uniquely identifying this feature

	@Expose private int docId;		// unique id of the document containing this feature
	@Expose private long type;		// encoded token type or rule id (ids are << 32)

	@Expose private Kind kind;		// feature category
	@Expose private String aspect;	// rule or token name
	@Expose private boolean isVar;	// variable content is ignored
	@Expose private String text;	// rule or token text
	@Expose private int format;		// describes this feature's Facets

	@Expose private int col;		// feature locus
	@Expose private int line;

	@Expose private int begIdx;		// feature token begIdx index
	@Expose private int endIdx;		// feature token endIdx index
	@Expose private int begOffset;	// feature beg character offset
	@Expose private int endOffset;	// feature end character offset

	@Expose private int weight;		// number of equivalents
	@Expose private boolean equiv;	// is weighted to another corpus feature

	// defines connections between this feature and others in a local group
	@Expose EdgeSet edgeSet;

	// key = docId; value = locations of equivalent features
	@Expose ArrayListMultimap<Integer, Location> equivalents;

	private CoreMgr mgr;

	private boolean reCalc;
	private double selfSim;
	private Feature matched;

	// comments
	public static Feature create(CoreMgr mgr, Kind kind, String aspect, int docId, long type, Token token, int format) {
		return create(mgr, kind, aspect, docId, type, token, format, false);
	}

	// nodes
	public static Feature create(CoreMgr mgr, Kind kind, String aspect, int docId, long type, Token token, int format,
			boolean isVar) {
		return create(mgr, kind, aspect, docId, type, token, token, format, isVar);
	}

	// rules
	public static Feature create(CoreMgr mgr, Kind kind, String aspect, int docId, long type, Token start, Token stop,
			int format, boolean isVar) {

		Feature feature = new Feature(mgr, docId, type, start, stop);
		Feature existing = pool.get(feature);
		if (existing != null) return existing;

		int id = pool.size();
		feature.update(id, kind, aspect, format, isVar, start, stop);
		pool.put(feature, feature);
		return feature;
	}

	Feature() {
		super();
		edgeSet = new EdgeSet();
		equivalents = ArrayListMultimap.create();
	}

	Feature(CoreMgr mgr, int docId, long type, Token start, Token stop) {
		this();
		this.mgr = mgr;
		this.docId = docId;
		this.type = type;
		this.begIdx = start.getTokenIndex();
		this.endIdx = stop.getTokenIndex();
	}

	void update(int id, Kind kind, String aspect, int format, boolean isVar, Token start, Token stop) {
		this.id = id;
		this.kind = kind;
		this.aspect = aspect;
		this.format = format;
		this.isVar = isVar;

		this.col = start.getCharPositionInLine();
		this.line = start.getLine() - 1;
		this.begOffset = start.getStartIndex();
		this.endOffset = stop.getStopIndex();

		this.text = genText(start.getInputStream(), begOffset, endOffset);

		weight = 1;
		reCalc = true;
	}

	private String genText(CharStream is, int startIndex, int stopIndex) {
		int end = stopIndex < (startIndex + TXTLEN) ? stopIndex : startIndex + TXTLEN - 3;
		String text = is.getText(new Interval(startIndex, end)).trim();
		if (end != stopIndex) text += "...";
		return Utils.escapeWhitespace(text, true);
	}

	/**
	 * Adds an edge from the receiver, as root, to the given feature. Does not add duplicates as
	 * defined by root id and leaf id pairs.
	 */
	public void addEdge(Feature leaf, EdgeType kind) {
		if (leaf != this) {
			Edge edge = Edge.create(this, leaf, kind);
			if (edgeSet.addEdge(edge)) {
				reCalc = true;
			}
		}
	}

	public CoreMgr getMgr() {
		return mgr;
	}

	public EdgeSet getEdgeSet() {
		return edgeSet;
	}

	public List<Edge> getEdges(int leafType) {
		return edgeSet.getEdges(leafType);
	}

	/** Functionally merge an equivalent feature with this unique root feature. */
	public void mergeEquivalent(Feature other) {
		other.setEquivalent(true);
		equivalents.put(other.getDocId(), other.getLocation());
		weight++;
	}

	public boolean isAfter(Feature root) {
		if (getLine() < root.getLine()) return false;
		if (getLine() > root.getLine()) return true;
		if (getCol() < root.getCol()) return false;
		return true;
	}

	public Location getLocation() {
		return new Location(getDocId(), getId(), getLine(), getCol());
	}

	/**
	 * Returns a positive value representing a kernel-based distance between this and the given
	 * corpus feature. A distance value of <code>zero</code> indicates that the two features are
	 * identical.
	 */
	public double distance(Feature cps) {
		double dist = selfSimularity() + cps.selfSimularity() - (2 * similarity(cps));
		if (dist < 0) {
			String msg = String.format(ErrInvalidDist, dist, this.toString(), cps.toString());
			// throw new ComputationException(new Throwable(msg));
			Log.error(this, msg);
			dist = Double.POSITIVE_INFINITY;
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
	public double similarity(Feature cps) {
		double featSim = featLabelSimilarity(cps);
		double edgeSim = getEdgeSet().similarity(cps.getEdgeSet());
		return featSim + edgeSim;
	}

	// type and text (if applicable) have to be identical
	public double featLabelSimilarity(Feature cps) {
		double[] vals = new double[6];
		vals[0] = mgr.getBoost(Factor.FORMAT) * Facet.similarity(format, cps.format);
		vals[1] = mgr.getBoost(Factor.DENTATION) * Facet.dentSimilarity(format, cps.format);
		vals[2] = mgr.getBoost(Factor.TEXT) * (text.equals(cps.text) ? 1 : 0);
		vals[3] = mgr.getBoost(Factor.WEIGHT) * Maths.invDelta(weight, cps.weight);
		vals[4] = mgr.getBoost(Factor.EDGE_TYPES) * Maths.invDelta(dimensionality(), cps.dimensionality());
		vals[5] = mgr.getBoost(Factor.EDGE_CNT) * Maths.invDelta(edgeSet.getEdgeCount(), cps.edgeSet.getEdgeCount());
		double sum = Maths.sum(vals);
		return sum;
	}

	/** Returns the number of unique types of feature type edgeSet */
	public int dimensionality() {
		return edgeSet.getTypeCount();
	}

	/** Returns the character stream distance between the this and the given feature. */
	public int offsetDistance(Feature o) {
		return Math.abs(begOffset - o.begOffset);
	}

	public long getId() {
		return id;
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

	public String getAspect() {
		return aspect;
	}

	public String getText() {
		return text;
	}

	/** Returns the combined type of the feature */
	public long getType() {
		return type;
	}

	/** Returns the begIdx token index */
	public int getStart() {
		return begIdx;
	}

	/** Returns the endIdx token index */
	public int getStop() {
		return endIdx;
	}

	public int getCol() {
		return col;
	}

	public int getLine() {
		return line;
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

	public boolean isEquivalent() {
		return equiv;
	}

	/** Sets this feature as being not a unique root feature. */
	public void setEquivalent(boolean equiv) {
		this.equiv = equiv;
	}

	public boolean isAligned() {
		return (format & Facet.ALIGNED.value()) > 0;
	}

	public boolean isAlignedSame() {
		return (format & Facet.ALIGNED_SAME.value()) > 0;
	}

	public void setAligned(Facet aligned) {
		format |= aligned.value();
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

	public Stats getStats() {
		return new Stats(this);
	}

	public Stats getStats(Feature matched) {
		return new Stats(this, matched);
	}

	public void setFormat(int format) {
		this.format = format;
	}

	/**
	 * Returns whether this feature is equivalent to the given feature. Equivalency is defined by
	 * identity of feature type, equality of edge sets (including identity of edge leaf node text),
	 * and identity of format.
	 */
	public boolean equivalentTo(Feature o) {
		if (type != o.type) return false;
		if (format != o.format) return false;
		return true;
	}

	@Override
	public int compareTo(Feature o) {
		if (docId < o.docId) return -1;
		if (docId > o.docId) return 1;
		if (type < o.type) return -1;
		if (type > o.type) return 1;
		if (begIdx < o.begIdx) return -1;
		if (begIdx > o.begIdx) return 1;
		if (endIdx < o.endIdx) return -1;
		if (endIdx > o.endIdx) return 1;
		if (weight < o.weight) return -1;
		if (weight > o.weight) return 1;
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		Feature o = (Feature) obj;
		if (docId != o.docId) return false;
		if (type != o.type) return false;
		if (begIdx != o.begIdx) return false;
		if (endIdx != o.endIdx) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + docId;
		result = prime * result + (int) (type >>> 32);
		result = prime * result + (int) type;
		result = prime * result + begIdx;
		result = prime * result + endIdx;
		return result;
	}

	public String diff(Feature o) {
		StringBuilder sb = new StringBuilder();
		if (o == null) {
			sb.append("arg is null");
		} else {
			if (id != o.id) sb.append("id [" + id + ":" + o.id + "] ");
			if (docId != o.docId) sb.append("docId [" + docId + ":" + o.docId + "] ");
			if (type != o.type) sb.append("type [" + type + ":" + o.type + "] ");
			if (format != o.format) sb.append("format [" + format + ":" + o.format + "] ");
			if (begIdx != o.begIdx) sb.append("begIdx [" + begIdx + ":" + o.begIdx + "] ");
			if (endIdx != o.endIdx) sb.append("endIdx [" + endIdx + ":" + o.endIdx + "] ");
			if (begOffset != o.begOffset) sb.append("begOffset [" + begOffset + ":" + o.begOffset + "] ");
			if (endOffset != o.endOffset) sb.append("endOffset [" + endOffset + ":" + o.endOffset + "] ");
			if (col != o.col) sb.append("col [" + col + ":" + o.col + "] ");
			if (line != o.line) sb.append("line [" + line + ":" + o.line + "] ");
			if (weight != o.weight) sb.append("rarity [" + weight + ":" + o.weight + "] ");
			if (selfSim != o.selfSim) sb.append("selfSim [" + selfSim + ":" + o.selfSim + "] ");
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return String.format("Feature @%s:%s %s %s 0x%08x '%s'", line, col, kind, aspect, format, text);
	}
}
