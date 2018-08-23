/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.format.plan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.unit.TableMultilist;
import net.certiv.adept.unit.TreeMultilist;

public class Group {

	// key=scheme type; index=line number; value=scheme member tokens
	private final TableMultilist<Scheme, Integer, AdeptToken> members = new TableMultilist<>();

	public Group() {
		// members.setValueComp(AdeptComp.Instance);
	}

	public Group(Scheme scheme, int line, AdeptToken token) {
		this();
		addMembers(scheme, line, token);
	}

	public void addMembers(Scheme scheme, int line, AdeptToken... tokens) {
		List<AdeptToken> list = new ArrayList<>();
		Collections.addAll(list, tokens);
		addMembers(scheme, line, list);
	}

	public void addMembers(Scheme scheme, int line, List<AdeptToken> tokens) {
		members.put(scheme, line, tokens);
	}

	// --------------------------------------------------

	public TableMultilist<Scheme, Integer, AdeptToken> getMembers() {
		return members;
	}

	public TreeMultilist<Integer, AdeptToken> get(Scheme scheme) {
		return members.get(scheme);
	}

	public List<AdeptToken> getAll(Scheme scheme) {
		List<AdeptToken> tokens = new ArrayList<>();
		for (Entry<Integer, List<AdeptToken>> group : members.get(scheme).entrySet()) {
			tokens.addAll(group.getValue());
		}
		return tokens;
	}

	public Set<Scheme> getSchemes() {
		return members.keySet();
	}

	public int lastLine(Scheme scheme) {
		TreeMultilist<Integer, AdeptToken> sub = members.get(scheme);
		return sub.lastKey();
	}

	public boolean contiguous(Scheme scheme, int line) {
		TreeMultilist<Integer, AdeptToken> sub = members.get(scheme);
		return sub.firstKey() - 1 == line || sub.lastKey() + 1 == line;
	}

	/**
	 * Returns {@code true} if all members of the given {@code scheme} are present on the same source
	 * line.
	 */
	public boolean colinear(Scheme scheme) {
		return members.get(scheme).size() == 1;
	}

	public boolean isEmpty() {
		return members.isEmpty();
	}

	public int findPrimaryDocId(Scheme scheme) {
		Map<Integer, Integer> cntr = new HashMap<>();
		for (AdeptToken token : members.get(scheme).valuesAll()) {
			if (token.refToken().matched != null) {
				for (Integer docId : token.refToken().matched.docIds) {
					Integer cnt = cntr.get(docId);
					if (cnt == null) {
						cntr.put(docId, 1);
					} else {
						cntr.put(docId, cnt + 1);
					}
				}
			}
		}

		int docId = -1;
		int max = -1;
		for (Entry<Integer, Integer> entry : cntr.entrySet()) {
			if (entry.getValue() > max) {
				docId = entry.getKey();
				max = entry.getValue();
			}
		}
		return docId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Scheme scheme : members.keySet()) {
			for (Entry<Integer, List<AdeptToken>> lines : members.entrySet(scheme)) {
				List<String> values = new ArrayList<>();
				for (AdeptToken token : lines.getValue()) {
					values.add(token.getText());
				}
				sb.append(String.format("%s: %2s %s\n", scheme, lines.getKey(), values));
			}
		}
		return sb.toString();
	}
}
