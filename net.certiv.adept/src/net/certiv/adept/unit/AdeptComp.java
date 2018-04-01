package net.certiv.adept.unit;

import java.util.Comparator;

import net.certiv.adept.lang.AdeptToken;

public class AdeptComp implements Comparator<AdeptToken> {

	public static final AdeptComp Instance = new AdeptComp();

	@Override
	public int compare(AdeptToken o1, AdeptToken o2) {
		if (o1.getTokenIndex() < o2.getTokenIndex()) return -1;
		if (o1.getTokenIndex() > o2.getTokenIndex()) return 1;
		return 0;
	}
}
