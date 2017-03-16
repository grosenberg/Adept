package net.certiv.adept.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ComputationException;
import com.google.gson.annotations.Expose;

import net.certiv.adept.Tool;
import net.certiv.adept.parser.AdeptToken;
import net.certiv.adept.topo.Facet;
import net.certiv.adept.topo.Factor;
import net.certiv.adept.topo.Location;
import net.certiv.adept.topo.Point;
import net.certiv.adept.topo.Stats;
import net.certiv.adept.util.Norm;

public class Feature implements Comparable<Feature> {

	private static final String ErrInvalidDist = "Invalid distance computed (%s) for %s -> %s";
	private static final int TXTLEN = 16;

	private static Map<Feature, Feature> pool = new LinkedHashMap<>();

	@Expose private int id;			// internal value uniquely identifying this feature

	@Expose private int docId;		// unique id of the document containing this feature
	@Expose private long type;		// encoded token type or rule id (ids are << 32)
	@Expose private int start;		// feature token start index
	@Expose private int stop;		// feature token stop index

	@Expose private Kind kind;		// feature category
	@Expose private String aspect;	// rule or token name
	@Expose private boolean isVar;	// variable content is ignored
	@Expose private String text;	// rule or token text
	@Expose private int format;		// describes this feature's Facets

	@Expose private int col;		// feature locus
	@Expose private int line;

	@Expose private int begLine;	// feature start token line
	@Expose private int endLine;	// feature stop token line

	@Expose private int weight;		// number of equivalents
	@Expose private boolean equiv;	// is weighted to another corpus feature

	// defines connections between this feature and others in a local group
	@Expose EdgeSet edgeSet;

	// key = docId; value = locations of equivalent features
	@Expose ArrayListMultimap<Integer, Location> equivalents;

	private boolean reCalc;
	private double selfSim;
	private Feature matched;

	// comments
	public static Feature create(Kind kind, String aspect, int docId, long type, Token token, int format) {
		return create(kind, aspect, docId, type, token, format, false);
	}

	// nodes
	public static Feature create(Kind kind, String aspect, int docId, long type, Token token, int format,
			boolean isVar) {
		return create(kind, aspect, docId, type, token, token, format, isVar);
	}

	// rules
	public static Feature create(Kind kind, String aspect, int docId, long type, Token start, Token stop, int format,
			boolean isVar) {

		Feature feature = new Feature(docId, type, start, stop);
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

	Feature(int docId, long type, Token start, Token stop) {
		this();
		this.docId = docId;
		this.type = type;
		this.start = start.getTokenIndex();
		this.stop = stop.getTokenIndex();
	}

	void update(int id, Kind kind, String aspect, int format, boolean isVar, Token start, Token stop) {
		this.id = id;
		this.kind = kind;
		this.aspect = aspect;
		this.format = format;
		this.isVar = isVar;
		this.text = genText(start.getInputStream(), start.getStartIndex(), stop.getStopIndex());
		this.begLine = start.getLine() - 1;
		this.endLine = stop.getLine() - 1;

		Point coords = getCoords(start);
		col = coords.getCol();
		line = coords.getLine();

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

	/**
	 * Adds an edge from the receiver, as root, to the given feature. Does not add duplicates as
	 * defined by root id and leaf id pairs.
	 */
	public void addEdge(Feature leaf) {
		if (leaf != this) {
			Edge edge = Edge.create(this, leaf);
			if (edgeSet.addEdge(edge)) {
				reCalc = true;
			}
		}
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

	public boolean isBefore(Feature root) {
		if (getLine() < root.getLine()) return true;
		if (getLine() > root.getLine()) return false;
		if (getCol() < root.getCol()) return true;
		return false;
	}

	public Location getLocation() {
		return new Location(getDocId(), getId(), getLine(), getCol());
	}

	/**
	 * Returns a positive value representing a kernel-based distance between this and the given
	 * corpus feature. A distance value of <code>zero</code> indicates that the two features are
	 * identical.
	 */
	public double distance(Feature corp) {
		// Log.debug(this, "Type " + aspect);
		double dist = selfSimularity() + corp.selfSimularity() - (2 * similarity(corp));
		// Log.debug(this, "Distance: " + String.valueOf(dist));
		// Log.debug(this, "----------------");
		if (dist < 0) {
			String msg = String.format(ErrInvalidDist, dist, this.toString(), corp.toString());
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
	public double similarity(Feature corp) {
		double featSim = featLabelSimilarity(corp);
		double edgeSim = getEdgeSet().similarity(corp.getEdgeSet());
		// Log.debug(this, "Feature label sim: " + String.valueOf(featSim));
		// Log.debug(this, "Edge set sim : " + String.valueOf(edgeSim));
		return featSim + edgeSim;
	}

	// type and text (if applicable) have to be identical
	public double featLabelSimilarity(Feature corp) {
		Map<Factor, Double> boosts = Tool.mgr.getFactors();
		double[] vals = new double[6];
		vals[0] = boosts.get(Factor.FORMAT) * Facet.similarity(format, corp.format);
		vals[1] = boosts.get(Factor.DENTATION) * Facet.simDentation(format, corp.format);
		vals[2] = boosts.get(Factor.TEXT) * (text.equals(corp.text) ? 1 : 0);
		vals[3] = boosts.get(Factor.WEIGHT) * Norm.delta(weight, corp.weight);
		vals[4] = boosts.get(Factor.EDGE_TYPES) * Norm.delta(dimensionality(), corp.dimensionality());
		vals[5] = boosts.get(Factor.EDGE_CNT) * Norm.delta(edgeSet.getEdgeCount(), corp.edgeSet.getEdgeCount());
		double sum = Norm.sum(vals);
		return sum;
	}

	/** Returns the number of unique types of feature type edgeSet */
	public int dimensionality() {
		return edgeSet.getTypeCount();
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

	/** Returns the start token index */
	public int getStart() {
		return start;
	}

	/** Returns the stop token index */
	public int getStop() {
		return stop;
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
		if (edgeSet.disjointCount(o.getEdgeSet()) != 0) return false;
		return true;
	}

	@Override
	public int compareTo(Feature o) {
		if (docId < o.docId) return -1;
		if (docId > o.docId) return 1;
		if (type < o.type) return -1;
		if (type > o.type) return 1;
		if (start < o.start) return -1;
		if (start > o.start) return 1;
		if (stop < o.stop) return -1;
		if (stop > o.stop) return 1;
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
		if (start != o.start) return false;
		if (stop != o.stop) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + docId;
		result = prime * result + (int) (type >>> 32);
		result = prime * result + (int) type;
		result = prime * result + start;
		result = prime * result + stop;
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
			if (start != o.start) sb.append("start [" + start + ":" + o.start + "] ");
			if (stop != o.stop) sb.append("stop [" + stop + ":" + o.stop + "] ");
			if (format != o.format) sb.append("format [" + format + ":" + o.format + "] ");
			if (begLine != o.begLine) sb.append("begLine [" + begLine + ":" + o.begLine + "] ");
			if (endLine != o.endLine) sb.append("endLine [" + endLine + ":" + o.endLine + "] ");
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
