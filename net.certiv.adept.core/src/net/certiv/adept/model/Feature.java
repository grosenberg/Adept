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

import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.parser.ParseData;
import net.certiv.adept.model.util.DamerauAlignment;
import net.certiv.adept.model.util.Factor;
import net.certiv.adept.model.util.Location;
import net.certiv.adept.model.util.Stats;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Maths;

public class Feature implements Comparable<Feature> {

	private static final int TXTLEN = 16;
	private static final String ErrInvalidDist = "Invalid distance computed (%s) for %s -> %s";

	// -------------------------------------------------------------------------------

	// description 
	@Expose private int id;					// internal value uniquely identifying this feature
	@Expose private int docId;				// unique id of the document containing this feature
	@Expose private int type;				// encoded token type or rule idx (rules are << 16)
	@Expose private Kind kind;				// feature category
	@Expose private String aspect;			// node name
	@Expose private String text;			// text content
	@Expose private boolean isVar;			// variable identifier
	@Expose private boolean isComment;		// comment type

	// ancestor path
	@Expose private List<Integer> ancestors;

	// connections to other features in a local group
	@Expose private EdgeSet edgeSet;

	// formatting
	@Expose private Format format;

	// position
	@Expose private int col;			// feature locus
	@Expose private int line;
	@Expose private int begIdx;			// feature token begIdx index
	@Expose private int endIdx;			// feature token endIdx index
	@Expose private int begOffset;		// feature beg character offset
	@Expose private int endOffset;		// feature end character offset
	@Expose private int length;			// feature real token length

	// key = docId; value = locations of features equivalent to this
	@Expose private ArrayListMultimap<Integer, Location> equivalents;
	@Expose private boolean equivalent;		// equivalent to another

	// -------------------------------------------------------------------------------

	private static final Map<Feature, Feature> pool = new LinkedHashMap<>();

	private ProcessMgr mgr;
	private boolean reCalc;
	private double selfSim;
	private Feature matched;

	// comments
	public static Feature create(ProcessMgr mgr, Kind kind, String aspect, int docId, int type, Token token,
			Format format) {
		return create(mgr, kind, aspect, docId, type, token, token, format, false, true);
	}

	// terminal nodes
	public static Feature create(ProcessMgr mgr, Kind kind, String aspect, int docId, int type, Token token,
			Format format, boolean isVar) {
		return create(mgr, kind, aspect, docId, type, token, token, format, isVar, false);
	}

	// rules
	public static Feature create(ProcessMgr mgr, Kind kind, String aspect, int docId, int type, Token start, Token stop,
			Format format, boolean isVar, boolean isComment) {

		Feature feature = new Feature(mgr, kind, aspect, docId, type, start, stop, format, isVar, isComment);
		Feature existing = pool.get(feature);
		if (existing != null) return existing;

		int id = pool.size();
		feature.setId(id);
		pool.put(feature, feature);
		return feature;
	}

	public Feature() {
		edgeSet = new EdgeSet();
		equivalents = ArrayListMultimap.create();
		this.reCalc = true;
	}

	public Feature(ProcessMgr mgr, Kind kind, String aspect, int docId, int type, Token start, Token stop,
			Format format, boolean isVar, boolean isComment) {

		this();
		this.mgr = mgr;
		this.docId = docId;
		this.type = type;
		this.kind = kind;
		this.aspect = aspect;
		this.format = format;
		this.isVar = isVar;
		this.isComment = isComment;

		this.begIdx = start.getTokenIndex();
		this.endIdx = stop.getTokenIndex();
		this.begOffset = start.getStartIndex();
		this.endOffset = stop.getStopIndex();
		this.text = genText(start.getInputStream(), begOffset, endOffset);
		this.col = start.getCharPositionInLine();
		this.line = start.getLine() - 1;
		this.length = countLength(start, stop);
	}

	private int countLength(Token start, Token stop) {
		int beg = start.getTokenIndex();
		int end = stop.getTokenIndex();
		if (beg == -1 || end == -1) return 0;

		Document doc = mgr.getDocument(docId);
		ParseData data = doc.getParseData();
		int len = 0;
		for (Token token : data.getTokenStream().get(beg, end)) {
			if (data.isWsOrComment(token.getType())) continue;
			len++;
		}
		return len;
	}

	private String genText(CharStream is, int startIndex, int stopIndex) {
		int end = stopIndex < (startIndex + TXTLEN) ? stopIndex : startIndex + TXTLEN - 3;
		String text = is.getText(new Interval(startIndex, end)).trim();
		if (end != stopIndex) text += "...";
		return Utils.escapeWhitespace(text, true);
	}

	/**
	 * Adds an edge from the receiver, as root, to the given feature. Does not add duplicates as defined
	 * by root id and leaf id pairs.
	 */
	public void addEdge(Feature leaf, EdgeType kind) {
		if (leaf != this) {
			Edge edge = Edge.create((Feature) this, leaf, kind);
			if (edgeSet.addEdge(edge)) {
				reCalc = true;
			}
		}
	}

	public ProcessMgr getMgr() {
		return mgr;
	}

	public void setMgr(ProcessMgr mgr) {
		this.mgr = mgr;
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
	 * Returns the ancestor path for this feature. The path starts with the node type and ends with the
	 * parse tree root rule index.
	 */
	public List<Integer> getAncestorPath() {
		return ancestors;
	}

	public void setAncestorPath(List<Integer> ancestors) {
		this.ancestors = ancestors;
	}

	/** Returns the number of unique types of feature type edgeSet */
	public int dimensionality() {
		return edgeSet.getEdgeTypes().size();
	}

	/** Returns the character stream distance between the this and the given feature. */
	public int offsetDistance(Feature o) {
		return Math.abs(begOffset - o.begOffset);
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Kind getKind() {
		return kind;
	}

	public boolean isVar() {
		return isVar;
	}

	public boolean isComment() {
		return isComment;
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
	public int getType() {
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

	/** Length, in tokens, of the feature. */
	public int getLength() {
		return length;
	}

	public int getCol() {
		return col;
	}

	public int getLine() {
		return line;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
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

	public int numEquivalent() {
		return equivalents.size();
	}

	public ArrayListMultimap<Integer, Location> getEquivalents() {
		return equivalents;
	}

	public boolean isEquivalent() {
		return equivalent;
	}

	public void setEquivalent(boolean equivalent) {
		this.equivalent = equivalent;
	}

	public boolean isAligned() {
		return format.alignAbove || format.alignBelow;
	}

	public boolean isRule() {
		return kind == Kind.RULE;
	}

	public boolean isTerminal() {
		return kind == Kind.TERMINAL;
	}

	public Stats getStats() {
		return new Stats(this);
	}

	public Stats getStats(Feature matched) {
		return new Stats(this, matched);
	}

	// ===============================================================================================

	/**
	 * Returns a positive value in the range [0-1] representing the similarity between this and the
	 * given feature. A kernel-based method is used to normalize between the two features being
	 * evaluated. A value of <code>1</code> indicates that the two features are identical.
	 */
	public double similarity(Feature other) {
		double sim = selfSimularity() + other.selfSimularity() - (2 * mutualSimilarity(other));
		if (sim < 0) {
			String msg = String.format(ErrInvalidDist, sim, this.toString(), other.toString());
			Log.error(this, msg);
			sim = 0;
		}
		return sim;
	}

	public double selfSimularity() {
		if (reCalc) {
			selfSim = mutualSimilarity(this);
			reCalc = false;
		}
		return selfSim;
	}

	// normalized sum of the labeled feature similarities 
	public double mutualSimilarity(Feature other) {
		double sim = 0;
		sim += mgr.getBoost(Factor.ANCESTORS) * ancestorSimilarity(other);
		sim += mgr.getBoost(Factor.EDGE_TYPES) * edgeSetTypeSimilarity(other);
		sim += mgr.getBoost(Factor.EDGE_TEXTS) * edgeSetTextSimilarity(other);
		sim += mgr.getBoost(Factor.FORMAT) * formatSimilarity(other);
		sim += mgr.getBoost(Factor.WEIGHT) * weightSimilarity(other);
		return sim / 5;
	}

	public double ancestorSimilarity(Feature other) {
		List<Integer> opath = other.getAncestorPath();
		double dist = DamerauAlignment.distance(ancestors.subList(1, ancestors.size()), opath.subList(1, opath.size()));
		return DamerauAlignment.simularity(dist, ancestors.size(), opath.size());
	}

	public double edgeSetTypeSimilarity(Feature other) {
		return getEdgeSet().typeSimilarity(other.getEdgeSet());
	}

	public double edgeSetTextSimilarity(Feature other) {
		return getEdgeSet().textSimilarity(other.getEdgeSet());
	}

	public double formatSimilarity(Feature other) {
		return format.similarity(other.format);
	}

	public double weightSimilarity(Feature other) {
		return Maths.invDelta(numEquivalent(), other.numEquivalent());
	}

	// ===============================================================================================

	/**
	 * Returns whether this feature is equivalent to the given feature. Equivalency is defined by
	 * identity of feature type, identity of format, and equality of edge set leaf type sequences.
	 */
	public boolean equivalentTo(Feature o) {
		if (type != o.type) return false;
		if (format != o.format) return false;
		if (!edgeSet.equals(o.getEdgeSet())) return false;
		return true;
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
			if (numEquivalent() != o.numEquivalent())
				sb.append("equivalents [" + numEquivalent() + ":" + o.numEquivalent() + "] ");
		}
		return sb.toString();
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
		if (numEquivalent() < o.numEquivalent()) return -1;
		if (numEquivalent() > o.numEquivalent()) return 1;
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
		result = prime * result + type;
		result = prime * result + begIdx;
		result = prime * result + endIdx;
		return result;
	}

	@Override
	public String toString() {
		return String.format("Feature @%s:%s %s %s 0x%08x '%s'", line, col, kind, aspect, format, text);
	}
}
