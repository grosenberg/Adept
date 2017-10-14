package net.certiv.adept.model;

import java.nio.file.Paths;
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
import net.certiv.adept.model.util.DamerauAlignment;
import net.certiv.adept.model.util.EdgeType;
import net.certiv.adept.model.util.Factor;
import net.certiv.adept.model.util.Kind;
import net.certiv.adept.model.util.Location;
import net.certiv.adept.model.util.MatchData;

public class Feature implements Comparable<Feature> {

	private static final int TXTLEN = 16;
	//	private static final String ErrInvalidDist = "Invalid distance (%s) for %s -> %s";

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
	@Expose private int col;			// document locus 
	@Expose private int line;
	@Expose private int visCol;
	@Expose private int begIdx;			// token stream index
	@Expose private int endIdx;
	@Expose private int begOffset;		// character stream offset
	@Expose private int endOffset;
	@Expose private int length;			// in real tokens

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
	public static Feature create(ProcessMgr mgr, Kind kind, String aspect, int docId, int type, Token token, int visCol,
			Format format) {
		return create(mgr, kind, aspect, docId, type, token, token, 1, visCol, format, false, true);
	}

	// terminal nodes
	public static Feature create(ProcessMgr mgr, Kind kind, String aspect, int docId, int type, Token token, int visCol,
			Format format, boolean isVar) {
		return create(mgr, kind, aspect, docId, type, token, token, 1, visCol, format, isVar, false);
	}

	// rules
	public static Feature create(ProcessMgr mgr, Kind kind, String aspect, int docId, int type, Token start, Token stop,
			int length, int visCol, Format format, boolean isVar, boolean isComment) {

		Feature feature = new Feature(mgr, kind, aspect, docId, type, start, stop, length, visCol, format, isVar,
				isComment);
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

	public Feature(ProcessMgr mgr, Kind kind, String aspect, int docId, int type, Token start, Token stop, int length,
			int visCol, Format format, boolean isVar, boolean isComment) {

		this();
		this.mgr = mgr;
		this.docId = docId;
		this.type = type;
		this.kind = kind;
		this.aspect = aspect;
		this.length = length;
		this.visCol = visCol;
		this.format = format;
		this.isVar = isVar;
		this.isComment = isComment;

		this.col = start.getCharPositionInLine();
		this.line = start.getLine() - 1;
		this.begIdx = start.getTokenIndex();
		this.endIdx = stop.getTokenIndex();
		this.begOffset = start.getStartIndex();
		this.endOffset = stop.getStopIndex();
		this.text = genText(start.getInputStream(), begOffset, endOffset);
	}

	private String genText(CharStream is, int startIndex, int stopIndex) {
		int end = stopIndex < (startIndex + TXTLEN) ? stopIndex : startIndex + TXTLEN - 3;
		String text = is.getText(new Interval(startIndex, end)).trim();
		if (end != stopIndex) text += "...";
		return Utils.escapeWhitespace(text, true);
	}

	/**
	 * Adds an edge from the receiver, as root, to the given feature, as leaf. EdgeSet will not add
	 * duplicates as defined by root and leaf id pairs.
	 * 
	 * @param type the defined type of edge
	 * @param leaf the connected leaf
	 * @param len the real token length of the edge
	 */
	public void addEdge(EdgeType type, Feature leaf, int len) {
		if (leaf != null && id != leaf.id) {
			Edge edge = Edge.create(this, leaf, type, len);
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

	/** Returns the signed raw character stream distance between the this and the given feature. */
	public int charOffset(Feature o) {
		return begOffset - o.begOffset;
	}

	/** Returns the signed raw token stream distance between the this and the given feature. */
	public int tokenOffset(Feature o) {
		return begIdx - o.begIdx;
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

	public int getVisCol() {
		return visCol;
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

	public int getEquivalentWeight() {
		return equivalents.size() + 1;
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

	public MatchData getStats() {
		return new MatchData(this);
	}

	public MatchData getStats(Feature matched) {
		return new MatchData(this, matched);
	}

	// ===============================================================================================

	/**
	 * Returns a positive value in the range [0-1] representing the similarity between this and the
	 * given feature. A kernel-based method is used to normalize between the two features being
	 * evaluated. A value of {@code 1} indicates the two features are identical.
	 */
	public double similarity(Feature other) {
		double self = selfSimularity();
		double them = other.selfSimularity();
		double pair = mutualSimilarity(other);
		double dist = self + them - (2 * pair);
		if (dist > 1) dist = 1; // happens due to asymmetries of boosts
		if (dist < 0) dist = 0;
		return 1 - dist;
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
		sim += mgr.getBoost(Factor.EDGE_ASPECTS) * edgeSetAspectsSimilarity(other);
		sim += mgr.getBoost(Factor.EDGE_TEXTS) * edgeSetTextSimilarity(other);
		sim += mgr.getBoost(Factor.FORMAT_LINE) * formatLineSimilarity(other);
		sim += mgr.getBoost(Factor.FORMAT_WS) * formatWsSimilarity(other);
		sim += mgr.getBoost(Factor.FORMAT_STYLE) * formatStyleSimilarity(other);
		sim += mgr.getBoost(Factor.WEIGHT) * weightSimilarity(other);
		return sim / (mgr.getBoosts().getTotal() + 0.5); // XXX: fudged
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

	public double edgeSetAspectsSimilarity(Feature other) {
		return getEdgeSet().aspectsSimilarity(other.getEdgeSet());
	}

	public double formatLineSimilarity(Feature other) {
		return format.similarityLine(other.format);
	}

	public double formatWsSimilarity(Feature other) {
		return format.similarityWs(other.format);
	}

	public double formatStyleSimilarity(Feature other) {
		return format.similarityStyle(other.format);
	}

	public double weightSimilarity(Feature other) {
		return Math.max(getEquivalentWeight(), other.getEquivalentWeight())
				/ (double) mgr.getCorpusModel().getMaxEquivs();
	}

	// ===============================================================================================

	/**
	 * Returns whether this feature is equivalent to the given feature. Equivalency is defined by
	 * identity of feature type, identity of format, and equality of edge set leaf type sequences.
	 */
	public boolean equivalentTo(Feature o) {
		if (id == o.id) return false;			// exclude identity
		if (type != o.type) return false;
		if (!format.equivalentTo(o.format)) return false;
		if (!edgeSet.equivalentTo(o.getEdgeSet())) return false;
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
			if (getEquivalentWeight() != o.getEquivalentWeight())
				sb.append("equivalents [" + getEquivalentWeight() + ":" + o.getEquivalentWeight() + "] ");
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
		if (getEquivalentWeight() < o.getEquivalentWeight()) return -1;
		if (getEquivalentWeight() > o.getEquivalentWeight()) return 1;
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
		String name = mgr.getSourceDocname(docId);
		if (name != null) {
			name = "[S] " + Paths.get(name).getFileName().toString();
		} else {
			name = mgr.getCorpusDocname(docId);
			if (name != null) name = "[C] " + Paths.get(name).getFileName().toString();
		}
		if (name == null) name = "[Unknown DocId]";
		String lineCol = String.format("@%03d:%03d", line, col);
		return String.format("%s %-8s %s '%s'", aspect, lineCol, name, text);
	}
}
