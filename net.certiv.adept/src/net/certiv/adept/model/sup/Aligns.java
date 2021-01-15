package net.certiv.adept.model.sup;

import java.util.List;

import net.certiv.adept.lang.AdeptToken;

public enum Aligns {
	BLANK,	// no possible alignment
	ABOVE,
	NOT;

	public static Aligns characterize(List<AdeptToken> priors, AdeptToken token) {
		if (priors == null || priors.isEmpty()) return BLANK;

		int vpos = token.getVisPos();
		for (AdeptToken prior : priors) {
			int pvpos = prior.getVisPos();
			if (pvpos < vpos) continue;
			if (pvpos > vpos) break;
			if (vpos == pvpos) {
				if (prior.getType() == token.getType()) return ABOVE;
			}
		}
		return NOT;
	}
}
