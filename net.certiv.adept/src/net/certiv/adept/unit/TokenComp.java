package net.certiv.adept.unit;

import java.util.Comparator;

import net.certiv.adept.lang.AdeptToken;

public class TokenComp implements Comparator<AdeptToken> {

	public static final TokenComp Instance = new TokenComp();

	@Override
	public int compare(AdeptToken o1, AdeptToken o2) {
		if (o1.getTokenIndex() < o2.getTokenIndex()) return -1;
		if (o1.getTokenIndex() > o2.getTokenIndex()) return 1;
		return 0;
	}
}
