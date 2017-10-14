package net.certiv.adept.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.certiv.adept.model.util.Aspect;
import net.certiv.adept.model.util.Sim;
import net.certiv.adept.model.util.IntsComparator;
import net.certiv.adept.util.ArrayMultilist;
import net.certiv.adept.util.diff.CommandVisitor;
import net.certiv.adept.util.diff.EditScript;

public class EdgeAspects {

	private static final int NOP = -3;

	private final ArrayMultilist<Integer, Aspect> aspects = new ArrayMultilist<>();

	public EdgeAspects() {
		super();
	}

	// --------------------------------------------------------------

	public Map<Sim, Double> similarities(EdgeAspects others) {
		Map<Sim, Double> sims = new HashMap<>();

		List<Integer> baseTypes = aspects.keys();
		List<List<Aspect>> baseLists = aspects.values();
		List<Integer> cmplTypes = others.keys();
		List<List<Aspect>> cmplLists = others.values();

		if (aspects.keySize() > others.keySize()) {
			baseTypes = others.keys();
			baseLists = others.values();
			cmplTypes = aspects.keys();
			cmplLists = aspects.values();
		}

		sims.put(Sim.MIN, (double) baseTypes.size());
		sims.put(Sim.MAX, (double) cmplTypes.size());

		int[] pairs = typeAlign(baseTypes, cmplTypes);
		sims.put(Sim.ARITY, (double) arity(pairs));
		sims.put(Sim.DISPARITY, (double) disparity(pairs));

		sims.put(Sim.SIM, matched(pairs, baseLists, cmplLists));

		return sims;
	}

	private int[] typeAlign(List<Integer> befTypes, List<Integer> aftTypes) {
		IntsComparator intComp = new IntsComparator(befTypes, aftTypes);
		EditScript<Integer> script = intComp.getScript();
		int[] pairs = new int[befTypes.size()];
		script.visit(new IntsVisitor(pairs));
		return pairs;
	}

	private double arity(int[] pairs) {
		int cnt = 0;
		for (int cmpl : pairs) {
			if (cmpl != NOP) cnt++;
		}
		return cnt;
	}

	private double disparity(int[] pairs) {
		double dist = 0;
		int cnt = 0;

		for (int idx = 0; idx < pairs.length; idx++) {
			int jdx = pairs[idx];
			if (jdx != NOP) {
				dist += jdx - idx;
				cnt++;
			}
		}
		return cnt > 0 ? dist / cnt : 0;
	}

	private double matched(int[] pairs, List<List<Aspect>> baseLists, List<List<Aspect>> complLists) {
		double sim = 0;
		int cnt = 0;
		for (int idx = 0; idx < pairs.length; idx++) {
			int jdx = pairs[idx];
			if (jdx != NOP) {
				sim += intersect(baseLists.get(idx), complLists.get(jdx));
				cnt++;
			}
		}
		return cnt > 0 ? sim / cnt : 0;
	}

	private double intersect(List<Aspect> base, List<Aspect> cmpl) {
		double cnt = 0;
		for (Aspect aspect : base) {
			if (cmpl.contains(aspect)) cnt++;
		}
		return cnt / Math.max(base.size(), cmpl.size());
	}

	// --------------------------------------------------------------

	private class IntsVisitor implements CommandVisitor<Integer> {

		private int bdx = 0;
		private int adx = 0;

		private int[] pairs;

		public IntsVisitor(int[] pairs) {
			super();
			this.pairs = pairs;
		}

		@Override
		public void visitKeepCommand(Integer object) {
			pairs[bdx] = adx;
			bdx++;
			adx++;
		}

		@Override
		public void visitDeleteCommand(Integer object) {
			pairs[bdx] = NOP;
			bdx++;
		}

		@Override
		public void visitInsertCommand(Integer object) {
			adx++;
		}
	}

	// -------------------------------------------------------------------

	public boolean add(Integer key, List<Aspect> value) {
		return aspects.add(key, value);
	}

	public List<Aspect> get(int idx) {
		return aspects.get(idx);
	}

	public List<List<Aspect>> get(Integer key) {
		return aspects.get(key);
	}

	public boolean containsKey(Integer key) {
		return aspects.containsKey(key);
	}

	public List<Integer> keys() {
		return aspects.keys();
	}

	public List<List<Aspect>> values() {
		return aspects.values();
	}

	public boolean isEmpty() {
		return aspects.isEmpty();
	}

	public int keySize() {
		return aspects.keySize();
	}
}
