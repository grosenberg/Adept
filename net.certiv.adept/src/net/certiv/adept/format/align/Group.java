package net.certiv.adept.format.align;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.unit.TableMultilist;

public class Group {

	private static final Comparator<AdeptToken> NodeComp = new Comparator<AdeptToken>() {

		@Override
		public int compare(AdeptToken s1, AdeptToken s2) {
			if (s1.getTokenIndex() < s2.getTokenIndex()) return -1;
			if (s1.getTokenIndex() > s2.getTokenIndex()) return 1;
			return 0;
		}
	};

	// key=align type; index=line number; value=set of tokens in align group
	private final TableMultilist<Align, Integer, AdeptToken> group = new TableMultilist<>();

	private ParserRuleContext ctx;

	public Group(ParserRuleContext ctx) {
		this.ctx = ctx;
		group.setValueComp(NodeComp);
	}

	public ParserRuleContext getContext() {
		return ctx;
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
}
