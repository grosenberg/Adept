package net.certiv.adept.model;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Utils;

import com.google.gson.annotations.Expose;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.util.DamerauAlignment;

public class Feature implements Comparable<Feature> {

	private static final int TXTLEN = 32;
	private static final Map<Integer, Feature> Pool = new HashMap<>();

	private static int nextId;

	private CoreMgr mgr;
	private Feature matched;

	// -------------------------------------------------------------------------------

	// description
	@Expose private int id;						// unique feature key
	@Expose private int docId;					// unique id of the document containing this feature
	@Expose private int key;					// document independent, unique feature hash key

	@Expose private List<Integer> ancestors;	// ancestors
	@Expose private Kind kind;					// feature category
	@Expose private int type;					// feature node token type

	@Expose private int line;					// node line (0..n)
	@Expose private int col;					// node column (0..n)
	@Expose private int visCol;					// visual node column

	@Expose private Bias bias;					// primary orientation
	@Expose private Spacing spacingLeft;		// ws categorized
	@Expose private Spacing spacingRight;
	@Expose private String wsLeft;				// ws content (excluding comments)
	@Expose private String wsRight;
	@Expose private Set<Integer> tokensLeft;	// unique neighboring real token types
	@Expose private Set<Integer> tokensRight;

	@Expose private String nodeName;			// node type name
	@Expose private String text;				// text content
	@Expose private boolean isVar;				// is variable identifier
	@Expose private boolean isComment;			// is comment type

	// equivalent tokens
	@Expose private int weight;					// count of equivalent tokens

	// -------------------------------------------------------------------------------

	/** Create a feature. */
	public static Feature create(CoreMgr mgr, int docId, List<Integer> ancestors, AdeptToken token) {

		int key = hashKey(docId, ancestors, token);
		Feature feature = Pool.get(key);
		if (feature == null) {
			feature = new Feature(mgr, docId, ancestors, token);
			Pool.put(key, feature);
		} else {
			nextId++;
			if (token.tokenLeft() != Token.INVALID_TYPE) feature.tokensLeft.add(token.tokenLeft());
			if (token.tokenRight() != Token.INVALID_TYPE) feature.tokensRight.add(token.tokenRight());
			feature.weight++;
		}
		return feature;
	}

	public static void clearPool() {
		Pool.clear();
	}

	public static void clearNextId() {
		nextId = 0;
	}

	private static int hashKey(int docId, List<Integer> ancestors, AdeptToken token) {
		final int prime = 31;
		int result = 1;
		result = prime * result + docId;
		result = prime * result + token.kind().hashCode();
		result = prime * result + ancestors.hashCode();
		result = prime * result + token.getType();
		result = prime * result + token.bias().hashCode();
		result = prime * result + token.spacingLeft().hashCode();
		result = prime * result + token.spacingRight().hashCode();
		return result;
	}

	// -------------------------------------------------------------------------------

	public Feature() {
		super();
		this.tokensLeft = new TreeSet<>();
		this.tokensRight = new TreeSet<>();
		this.weight = 1;
	}

	private Feature(CoreMgr mgr, int docId, List<Integer> ancestors, AdeptToken token) {

		this();
		this.mgr = mgr;
		this.id = nextId++;
		this.key = hashKey(1, ancestors, token);

		this.docId = docId;
		this.ancestors = ancestors;
		this.kind = token.kind();
		this.type = token.getType();
		this.bias = token.bias();

		this.spacingLeft = token.spacingLeft();
		this.spacingRight = token.spacingRight();
		this.wsLeft = token.wsLeft();
		this.wsRight = token.wsRight();
		this.tokensLeft.add(token.tokenLeft());
		this.tokensRight.add(token.tokenRight());

		this.nodeName = token.nodeName();
		this.text = displayText(token);

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

	public int getKey() {
		return key;
	}

	/**
	 * Returns the ancestor rule context path for this feature. The path starts with the context
	 * containing the feature node.
	 */
	public List<Integer> getAncestors() {
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

	public Spacing getSpacingLeft() {
		return spacingLeft;
	}

	public Spacing getSpacingRight() {
		return spacingRight;
	}

	public Set<Integer> getTokensLeft() {
		return tokensLeft;
	}

	public Set<Integer> getTokensRight() {
		return tokensRight;
	}

	public String getWsLeft() {
		return wsLeft;
	}

	public String getWsRight() {
		return wsRight;
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
		List<Integer> opath = other.getAncestors();
		double dist = DamerauAlignment.distance(ancestors, opath);
		return DamerauAlignment.simularity(dist, ancestors.size(), opath.size());
	}

	public int getWeight() {
		return weight;
	}

	public void addWeight(int cnt) {
		weight += cnt;
	}

	// ===============================================================================================

	/** Allow unique feature sorting independent of the source document. */
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
		if (spacingRight.ordinal() < o.spacingRight.ordinal()) return -1;
		if (spacingRight.ordinal() > o.spacingRight.ordinal()) return 1;
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
		result = prime * result + ((spacingRight == null) ? 0 : spacingRight.hashCode());
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
		if (spacingRight != other.spacingRight) return false;
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
		if (name == null) {
			name = "[Unknown DocId]";
		}
		String lineCol = String.format("@%03d:%03d", getLine(), getCol());
		return String.format("%s %-8s %s '%s'", nodeName, lineCol, name, text);
	}
}
