package net.certiv.adept.format.align;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.unit.AdeptComp;
import net.certiv.adept.unit.TableMultilist;
import net.certiv.adept.unit.TreeMultilist;

public class Group {

	// key=align type; index=line number; value=set of tokens in align group
	private final TableMultilist<Align, Integer, AdeptToken> group = new TableMultilist<>();

	public Group() {
		group.setValueComp(AdeptComp.Instance);
	}

	public Group(Align align, int line, AdeptToken token) {
		this();
		addGroupMembers(align, line, token);
	}

	public void addGroupMembers(Align align, int line, AdeptToken... tokens) {
		List<AdeptToken> tmp = new ArrayList<>();
		Collections.addAll(tmp, tokens);
		group.put(align, line, tmp);
	}

	public void addGroupMembers(Align align, int line, List<AdeptToken> tokens) {
		group.put(align, line, tokens);
	}

	public TableMultilist<Align, Integer, AdeptToken> getMembers() {
		return group;
	}

	// --------------------------------------------------

	public TreeMultilist<Integer, AdeptToken> get(Align align) {
		return group.get(align);
	}

	public int lastLine(Align align) {
		TreeMultilist<Integer, AdeptToken> sub = group.get(align);
		return sub.lastKey();
	}

	public boolean contiguous(Align align, int line) {
		TreeMultilist<Integer, AdeptToken> sub = group.get(align);
		return sub.firstKey() - 1 == line || sub.lastKey() + 1 == line;
	}
}
