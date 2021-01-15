package net.certiv.adept.model.sup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

import net.certiv.adept.format.prep.Place;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.Record;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.store.TreeMultilist;

// tuple characteristics
public class Field {

	private static final Map<Integer, Field> Pool = new HashMap<>();
	private static int nextKey;

	@Expose private int key;			// serial key
	@Expose private int weight;			// count of equivalents

	// defines an equivalent
	@Expose private int docId;			// unique id of document
	@Expose private int ancestorsId;	// characterizing parent path
	@Expose private Spacing spacing;	// characterizing spacing between
	@Expose private Aligns aligns;		// characterizing alignment to above
	@Expose private Place place;		// characterizing placement in line

	// for visualization
	@Expose private List<Integer> ancestors;

	public static Field get(Tuple tuple, AdeptToken left, AdeptToken token, List<Integer> ancestors) {
		int key = hashKey(ancestors, token);
		Field field = Pool.get(key);
		if (field == null) {
			field = new Field(tuple, left, token, ancestors);
			Pool.put(key, field);
			nextKey++;
		} else {
			field.weight++;
		}
		return field;
	}

	public static void clearPool() {
		Pool.clear();
	}

	// defines a unique Field independent of document
	private static int hashKey(List<Integer> ancestors, AdeptToken token) {
		final int prime = 31;
		int result = 1;
		result = prime * result + ancestors.hashCode();
		result = prime * result + token.getType();
		return result;
	}

	// ----

	public Field() {
		super();
		this.weight = 1;
	}

	public Field(Tuple tuple, AdeptToken left, AdeptToken token, List<Integer> ancestors) {
		this();
		this.docId = tuple.doc.getDocId();
		this.key = nextKey;
		this.ancestors = ancestors;
		this.ancestorsId = ancestors.hashCode();

		int tokenIdx = token.getTokenIndex();
		token.setDent(indenter.getDent(tokenIdx));

		Record rec = tuple.doc.getRecord();
		String ws = rec.getTextBetween(left.getTokenIndex(), token.getTokenIndex());
		spacing = Spacing.characterize(ws, tuple.tabWidth);

		int lnum = token.getLinePos();
		TreeMultilist<Integer, AdeptToken> line = rec.lines.get(lnum);
		List<AdeptToken> tokens = line.get(line.firstKey());
		place = Place.characterize(tokens, token);

		TreeMultilist<Integer, AdeptToken> pline = rec.lines.get(lnum - 1);
		List<AdeptToken> priors = !pline.isEmpty() ? line.get(line.firstKey()) : Collections.emptyList();
		aligns = Aligns.characterize(priors, token);
	}
}
