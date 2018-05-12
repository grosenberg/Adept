package net.certiv.adept.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.util.Damerau;
import net.certiv.adept.util.Log;

public class Feature implements Comparable<Feature>, Cloneable {

	private static final Map<Integer, Feature> Pool = new HashMap<>();
	private static int nextId;

	private CoreMgr mgr;
	private boolean matched;	// ref tokens have been matched to corpus

	// -------------------------------------------------------------------------------

	@Expose private int id;						// unique feature key
	@Expose private int docId;					// unique id of the document containing this feature
	@Expose private int key;					// document independent, unique feature hash key

	@Expose private List<Integer> ancestors;	// ancestors
	@Expose private Kind kind;					// feature category
	@Expose private int type;					// feature node token type
	@Expose private int weight;					// count of equivalent feature tokens

	@Expose private List<RefToken> refs;		// all token refs specific to this feature

	// for visualization
	@Expose private String docName;				// originating document name
	@Expose private String nodeName;

	// -------------------------------------------------------------------------------

	/** Create a feature. */
	public static Feature create(CoreMgr mgr, Document doc, List<Integer> ancestors, AdeptToken token) {

		int key = hashKey(ancestors, token);
		Feature feature = Pool.get(key);
		if (feature == null) {
			feature = new Feature(mgr, doc, ancestors, token);
			Pool.put(key, feature);
			nextId++;
		} else {
			feature.refs.add(token.refToken());
			feature.weight++;
		}
		return feature;
	}

	public static void clearPool() {
		Pool.clear();
	}

	// defines a unique Feature independent of document
	private static int hashKey(List<Integer> ancestors, AdeptToken token) {
		final int prime = 31;
		int result = 1;
		result = prime * result + ancestors.hashCode();
		result = prime * result + token.getType();
		return result;
	}

	// -------------------------------------------------------------------------------

	public Feature() {
		super();
		this.refs = new ArrayList<>();
		this.weight = 1;
	}

	private Feature(CoreMgr mgr, Document doc, List<Integer> ancestors, AdeptToken token) {
		this();
		this.mgr = mgr;
		this.id = nextId;
		this.key = hashKey(ancestors, token);

		this.docId = doc.getDocId();
		this.ancestors = ancestors;
		this.kind = token.kind();
		this.type = token.getType();
		this.refs.add(token.refToken());

		// for analysis
		this.docName = doc.getFilename();
		this.nodeName = token.getText();
	}

	// -------------------------------------------------------------------------------

	public CoreMgr getMgr() {
		return mgr;
	}

	public void setMgr(CoreMgr mgr) {
		this.mgr = mgr;
	}

	public int getId() {
		return id;
	}

	public int getKey() {
		return key;
	}

	public int getDocId() {
		return docId;
	}

	public Kind getKind() {
		return kind;
	}

	/**
	 * Returns the ancestor rule context path for this feature. The path starts with the context
	 * containing the feature node.
	 */
	public List<Integer> getAncestors() {
		return ancestors;
	}

	/** Returns the type of the feature */
	public int getType() {
		return type;
	}

	public String getNodeName() {
		return nodeName;
	}

	/** Returns the RefToken having the given token index. */
	public RefToken getRefFor(int index) {
		for (RefToken ref : refs) {
			if (ref.index == index) return ref;
		}
		Log.error(this, "Problem: ref is null.");
		return null;
	}

	/** Get all ref tokens associated with this feature. */
	public List<RefToken> getRefs() {
		return refs;
	}

	public void setRefs(List<RefToken> refs) {
		this.refs = refs;
	}

	public RefToken getEquiv(RefToken other) {
		for (RefToken ref : refs) {
			if (ref.equivalentTo(other)) return ref;
		}
		return null;
	}

	public double[] maxRank() {
		double[] max = { 0, 0 };
		for (RefToken ref : refs) {
			max[0] = Math.max(max[0], ref.rank);
			for (Context context : ref.contexts) {
				max[1] = Math.max(max[1], context.rank);
			}
		}
		return max;
	}

	public int getWeight() {
		return weight;
	}

	public void addWeight(int cnt) {
		weight += cnt;
	}

	public boolean isTerminal() {
		return kind == Kind.TERMINAL;
	}

	public boolean isComment() {
		return kind == Kind.BLOCKCOMMENT || kind == Kind.LINECOMMENT;
	}

	public boolean isMatched() {
		return matched;
	}

	public void setMatched(boolean done) {
		this.matched = done;
	}

	public String getDocName() {
		return docName;
	}

	public double ancestorSimilarity(Feature other) {
		List<Integer> opath = other.getAncestors();
		double dist = Damerau.distance(ancestors, opath);
		return Damerau.simularity(dist, ancestors.size(), opath.size());
	}

	// ===============================================================================================

	/** Deep clone. */
	public Feature copy() {
		try {
			Feature copy = (Feature) super.clone();
			copy.refs = new ArrayList<>();
			for (RefToken ref : refs) {
				copy.refs.add(ref.copy());
			}
			return copy;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Feature copy failed!");
		}
	}

	/** Allow feature sorting independent of the source document. */
	@Override
	public int compareTo(Feature o) {
		if (ancestors.hashCode() < o.ancestors.hashCode()) return -1;
		if (ancestors.hashCode() > o.ancestors.hashCode()) return 1;
		if (type < o.type) return -1;
		if (type > o.type) return 1;
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + docId;
		result = prime * result + ((ancestors == null) ? 0 : ancestors.hashCode());
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Feature other = (Feature) obj;
		if (docId != other.docId) return false;
		if (ancestors == null) {
			if (other.ancestors != null) return false;
		} else if (!ancestors.equals(other.ancestors)) return false;
		if (type != other.type) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s %s ", nodeName, docName);
	}
}
