package net.certiv.adept.core;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.parser.Builder;

public class PerfData {

	private ArrayList<DocPerf> docs;
	private DocPerf current;

	public Duration load;		// corpus load time including any rebuild
	public Duration rebuild;	// corpus rebuild time only

	public int corpusFeatureCnt;
	public int corpusFeatureTypeCnt;
	public Map<Integer, String> corpusDocIndex;

	public PerfData() {
		docs = new ArrayList<>();
	}

	public void setSize(int size) {
		docs = new ArrayList<>(size);
	}

	public void addDoc(Builder builder) {
		current = new DocPerf(builder);
		docs.add(current);
	}

	public DocPerf getDoc(int idx) {
		if (idx < docs.size()) {
			return docs.get(idx);
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

	public void addMatchTime(Duration d) {
		if (current.docMatch == null) {
			current.docMatch = d;
		} else {
			current.docMatch.plus(d);
		}
	}

	public void reset() {
		load = Duration.ZERO;
		rebuild = Duration.ZERO;
		if (current != null) {
			current.docMatch = Duration.ZERO;
			current.docFormat = Duration.ZERO;
		}
	}

	public class Record {

		public Feature terminal;
		public Feature best;
		public TreeMap<Double, Feature> selected;

		public Record(Feature terminal, Feature best, TreeMap<Double, Feature> selected) {
			this.terminal = terminal;
			this.best = best;
			this.selected = selected;
		}
	}

	public class DocPerf {

		public String docName;
		public int docFeatureCnt;
		public int docTypeCnt;
		public int docTerminalCnt;

		public Duration docMatch;
		public Duration docFormat;

		public DocPerf() {}

		public DocPerf(Builder builder) {
			docName = builder.getDocument().getPathname();
			docFeatureCnt = builder.getAllFeatures().size();
			docTypeCnt = builder.typeSet.size();
			docTerminalCnt = 0;
			for (Feature feature : builder.contextFeatureIndex.values()) {
				if (feature.isTerminal()) docTerminalCnt++;
			}
		}
	}
}
