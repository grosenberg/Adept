package net.certiv.adept.format.plan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.unit.AdeptComp;
import net.certiv.adept.unit.TableMultilist;
import net.certiv.adept.unit.TreeMultilist;

public class Group {

	// key=align type; index=line number; value=set of tokens in align members
	private final TableMultilist<Scheme, Integer, AdeptToken> members = new TableMultilist<>();

	public Group() {
		members.setValueComp(AdeptComp.Instance);
	}

	public Group(Scheme align, int line, AdeptToken token) {
		this();
		addGroupMembers(align, line, token);
	}

	public void addGroupMembers(Scheme align, int line, AdeptToken... tokens) {
		List<AdeptToken> tmp = new ArrayList<>();
		Collections.addAll(tmp, tokens);
		members.put(align, line, tmp);
	}

	public void addGroupMembers(Scheme align, int line, List<AdeptToken> tokens) {
		members.put(align, line, tokens);
	}

	public TableMultilist<Scheme, Integer, AdeptToken> getMembers() {
		return members;
	}

	// --------------------------------------------------

	public TreeMultilist<Integer, AdeptToken> get(Scheme align) {
		return members.get(align);
	}

	public int lastLine(Scheme align) {
		TreeMultilist<Integer, AdeptToken> sub = members.get(align);
		return sub.lastKey();
	}

	public boolean contiguous(Scheme align, int line) {
		TreeMultilist<Integer, AdeptToken> sub = members.get(align);
		return sub.firstKey() - 1 == line || sub.lastKey() + 1 == line;
	}
}
