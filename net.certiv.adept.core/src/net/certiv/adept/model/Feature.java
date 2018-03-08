package net.certiv.adept.model;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.misc.Utils;

import com.google.gson.annotations.Expose;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.util.DamerauAlignment;

public class Feature implements Comparable<Feature> {

	private static final int TXTLEN = 16;
	private static final Map<Integer, Feature> Pool = new HashMap<>();

	private static int nextId;

	private CoreMgr mgr;
	private Feature matched;

	// -------------------------------------------------------------------------------

	// description
	@Expose private int id;						// internal value uniquely identifying this feature
	@Expose private int docId;					// unique id of the document containing this feature
	@Expose private int typeKey;				// document independent, unique feature hash key
	@Expose private int compKey;				// document independent, unique feature path hash key

	@Expose private List<Integer> ancestors;	// ancestor path
	@Expose private Kind kind;					// feature category
	@Expose private int type;					// feature token type

	@Expose private int line;					// node line (0..n)
	@Expose private int col;					// node column (0..n)
	@Expose private int visCol;					// visual node column

	@Expose private Bias bias;					// ws orientation
	@Expose private Spacing spacing;			// ws category
	@Expose private String ws;					// ws content (excluding comments)

	@Expose private String nodeName;			// node name
	@Expose private String text;				// text content
	@Expose private boolean isVar;				// is variable identifier
	@Expose private boolean isComment;			// is comment type

	@Expose private List<Integer> equivalents;	// list of equivalent (virtual) features

	// -------------------------------------------------------------------------------

	/** Create a feature. */
	public static Feature create(CoreMgr mgr, int docId, List<Integer> ancestors, AdeptToken token, Bias bias) {

		Spacing spacing = bias == Bias.RIGHT ? token.spacingRight() : token.spacingLeft();
		int key = hashKey(docId, token.kind(), ancestors, token.getType(), bias, spacing);
		Feature feature = Pool.get(key);
		if (feature == null) {
			feature = new Feature(mgr, docId, ancestors, token, bias);
			Pool.put(key, feature);
		} else {
			feature.addEquivalent(docId, nextId);
			nextId++;
		}
		return feature;
	}

	public static void clearNextId() {
		nextId = 0;
	}

	private static int hashKey(int docId, Kind kind, List<Integer> ancestors, int type, Bias bias, Spacing spacing) {
		final int prime = 31;
		int result = 1;
		result = prime * result + docId;
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		result = prime * result + ((ancestors == null) ? 0 : ancestors.hashCode());
		result = prime * result + type;
		result = prime * result + ((bias == null) ? 0 : bias.hashCode());
		result = prime * result + ((spacing == null) ? 0 : spacing.hashCode());
		return result;
	}

	// -------------------------------------------------------------------------------

	public Feature() {
		super();
		this.equivalents = new ArrayList<>();
	}

	private Feature(CoreMgr mgr, int docId, List<Integer> ancestors, AdeptToken token, Bias bias) {

		this();
		this.mgr = mgr;
		this.id = nextId++;

		this.typeKey = hashKey(1, token.kind(), ancestors, token.getType(), bias, spacing);
		this.compKey = hashKey(1, token.kind(), ancestors, token.getType(), bias, Spacing.UNKNOWN);

		this.docId = docId;
		this.ancestors = ancestors;
		this.bias = bias;

		this.kind = token.kind();
		this.type = token.getType();

		this.spacing = bias == Bias.RIGHT ? token.spacingRight() : token.spacingLeft();
		this.ws = bias == Bias.RIGHT ? token.wsRight() : token.wsLeft();

		this.nodeName = token.nodeName();
		this.text = String.format("%s: %s", nodeName, displayText(token));

		this.line = token.getLine() - 1;
		this.col = token.getCharPositionInLine();
		this.visCol = token.visCol();

		this.isVar = kind == Kind.VAR;
		this.isComment = kind == Kind.BLOCKCOMMENT || kind == Kind.LINECOMMENT;
	}

	private String displayText(AdeptToken token) {
		String txt = token.getText().trim();
		if (txt.length() > TXTLEN) {
			txt = txt.substring(0, TXTLEN - 3) + "...";
		}
		return Utils.escapeWhitespace(txt, true);
	}

	// -------------------------------------------------------------------------------

	public CoreMgr getMgr() {
		return mgr;
	}

	public void setMgr(CoreMgr mgr) {
		this.mgr = mgr;
	}

	public int getTypeKey() {
		return typeKey;
	}

	public int getCompareKey() {
		return compKey;
	}

	/**
	 * Returns the ancestor path for this feature. The path starts with the node type and ends with the
	 * parse tree root rule index.
	 */
	public List<Integer> getAncestorPath() {
		return ancestors;
	}

	public int getId() {
		return id;
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

	public String getNodeName() {
		return nodeName;
	}

	public String getText() {
		return text;
	}

	/** Returns the combined (rule/terminal) type of the feature */
	public int getType() {
		return type;
	}

	public Bias getBias() {
		return bias;
	}

	public Spacing getSpacing() {
		return spacing;
	}

	public String getWs() {
		return ws;
	}

	/** Returns the line (0..n) */
	public int getLine() {
		return line;
	}

	/** Returns the line (0..n) */
	public int getCol() {
		return col;
	}

	/** Returns the visual column in the current line (0..n) */
	public int getVisCol() {
		return visCol;
	}

	public Feature getMatched() {
		return matched;
	}

	public void setMatched(Feature matched) {
		this.matched = matched;
	}

	public boolean isTerminal() {
		return kind == Kind.TERMINAL;
	}

	// ===============================================================================================

	public double ancestorSimilarity(Feature other) {
		List<Integer> opath = other.getAncestorPath();
		double dist = DamerauAlignment.distance(ancestors.subList(1, ancestors.size()), opath.subList(1, opath.size()));
		return DamerauAlignment.simularity(dist, ancestors.size(), opath.size());
	}

	public int getWeight() {
		return equivalents.size();
	}

	public void addEquivalent(int docId, int featureId) {
		equivalents.add((docId << 16) + featureId);
	}

	public void addEquivalents(List<Integer> equivs) {
		equivalents.addAll(equivs);
	}

	public List<Integer> getEquivalents() {
		return equivalents;
	}

	// ===============================================================================================

	@Override
	public int compareTo(Feature o) {
		// if (docId < o.docId) return -1;
		// if (docId > o.docId) return 1;
		if (kind.ordinal() < o.kind.ordinal()) return -1;
		if (kind.ordinal() > o.kind.ordinal()) return 1;
		if (ancestors.hashCode() < o.ancestors.hashCode()) return -1;
		if (ancestors.hashCode() > o.ancestors.hashCode()) return 1;
		if (type < o.type) return -1;
		if (type > o.type) return 1;
		if (bias.ordinal() < o.bias.ordinal()) return -1;
		if (bias.ordinal() > o.bias.ordinal()) return 1;
		if (spacing.ordinal() < o.spacing.ordinal()) return -1;
		if (spacing.ordinal() > o.spacing.ordinal()) return 1;
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + docId;
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		result = prime * result + ((ancestors == null) ? 0 : ancestors.hashCode());
		result = prime * result + type;
		result = prime * result + ((bias == null) ? 0 : bias.hashCode());
		result = prime * result + ((spacing == null) ? 0 : spacing.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Feature other = (Feature) obj;
		if (docId != other.docId) return false;
		if (kind != other.kind) return false;
		if (ancestors == null) {
			if (other.ancestors != null) return false;
		} else if (!ancestors.equals(other.ancestors)) return false;
		if (type != other.type) return false;
		if (bias != other.bias) return false;
		if (spacing != other.spacing) return false;
		return true;
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
		String lineCol = String.format("@%03d:%03d", getLine(), getCol());
		return String.format("%s %-8s %s '%s'", nodeName, lineCol, name, text);
	}
}
