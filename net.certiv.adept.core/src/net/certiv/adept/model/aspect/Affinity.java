package net.certiv.adept.model.aspect;

import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.Expose;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.EdgeType;
import net.certiv.adept.model.aspect.Affinity.AffinityPart;

/**
 * The Affinity aspect is used to describe a given feature relative to other features located within
 * a defined local scope centered on the given feature.
 */
public class Affinity extends Aspect<Affinity, AffinityPart> {

	@Expose private long leafId;	// key uniquely identifying associated feature
	@Expose private EdgeType kind;	// connection/relation type

	@Expose private int spanOffset; /* num of stream tokens between leaf and root features, ignoring non-visible and
									 * comment tokens; positive if leaf start token index is before that of this
									 * feature */

	@Expose private int horzOffset; /* relative line token offset between leaf and root features; positive if leaf token
									 * offset is before that of this feature */

	@Expose private int vertOffset; /* relative num of lines between leaf and root features; positive if leaf line num
									 * is before that of this feature */

	public static enum AffinityPart {
		SPAN_OFFSET,
		HORZ_OFFSET,
		VERT_OFFSET,
		METRIC_DIST;
	}

	@Override
	public List<AffinityPart> getAspectParts() {
		return Arrays.asList(AffinityPart.values());
	}

	@Override
	public double similarity(Feature other, AffinityPart part) {
		return 0;
	}

}
