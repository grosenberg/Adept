package net.certiv.adept.core;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.load.parser.FeatureFactory;

public class PerfData {

	private ArrayList<DocPerf> docs;
	private DocPerf current;

	public static class DocPerf {

		public String docName;
		public int docFeatureCnt;
		public int docTypeCnt;
		public int docTerminalCnt;
		public Duration docFormat;

		public DocPerf() {}

		public DocPerf(FeatureFactory featureFactory) {
			docName = featureFactory.getDocument().getPathname();
			docFeatureCnt = featureFactory.getFeatures().size();
			docTypeCnt = featureFactory.typeSet.size();
			docTerminalCnt = featureFactory.terminalIndex.size();
		}
	}

	public static class Record {

		public Feature terminal;
		public Feature best;
		public TreeMap<Double, Feature> selected;

		public Record(Feature terminal, Feature best, TreeMap<Double, Feature> selected) {
			this.terminal = terminal;
			this.best = best;
			this.selected = selected;
		}
	}

	public Duration load;
	public Duration rebuild;
	public int corpusFeatureCnt;
	public int corpusFeatureTypeCnt;
	public Map<Integer, String> corpusDocIndex;

	public PerfData() {
		docs = new ArrayList<>();
	}

	public void setSize(int size) {
		docs = new ArrayList<>(size);
	}

	public void addDoc(FeatureFactory featureFactory) {
		current = new DocPerf(featureFactory);
		docs.add(current);
	}

	public DocPerf getDoc(int i) {
		if (i < docs.size()) {
			return docs.get(i);
		}
		return new DocPerf();
	}

	public void addFormatTime(Duration d) {
		if (current.docFormat == null) {
			current.docFormat = d;
		} else {
			current.docFormat.plus(d);
		}
	}

	public void reset() {
		load = Duration.ZERO;
		rebuild = Duration.ZERO;
		if (current != null) current.docFormat = Duration.ZERO;
	}
}
