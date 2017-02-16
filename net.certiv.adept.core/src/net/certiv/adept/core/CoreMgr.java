package net.certiv.adept.core;

import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.RecognitionException;

import com.google.common.collect.TreeMultimap;

import net.certiv.adept.Tool;
import net.certiv.adept.antlr.parser.AntlrSourceParser;
import net.certiv.adept.java.parser.JavaSourceParser;
import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.parser.Collector;
import net.certiv.adept.parser.ISourceParser;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.topo.Factor;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;
import net.certiv.adept.xvisitor.parser.XVisitorSourceParser;

public class CoreMgr {

	private String lang;
	public PerfData perfData;

	private DocModel model;
	private CorpusModel corpus;
	private List<Document> corpusDocs;
	private Path corpusDir;
	private int tabWidth;

	private OutputBuilder buffer;

	public CoreMgr() {
		this.perfData = new PerfData();
	}

	public boolean initialize(String lang, Path corpusDir, String corpusExt, int tabWidth, boolean rebuild)
			throws Exception {
		this.lang = lang;
		this.corpusDir = corpusDir;
		this.tabWidth = tabWidth;

		Instant start = Time.start();
		corpus = CorpusModel.load(corpusDir, rebuild);
		corpusDocs = corpus.read(corpusDir, corpusExt, tabWidth);
		perfData.load = Time.end(start);

		if (rebuild || !corpus.isValid(corpusDocs)) {
			doRebuild();
			if (!corpus.isConsistent()) return false;
		}
		perfData.corpusFeatureCnt = corpus.getFeatures().size();
		perfData.corpusFeatureTypeCnt = corpus.getFeatureIndex().size();
		perfData.corpusDocIndex = corpus.getPathnames();
		return true;
	}

	private void doRebuild() {
		Log.info(this, "Rebuilding...");

		corpus.clear();
		Instant start = Time.start();
		for (Document doc : corpusDocs) {
			ISourceParser parser = getLanguageParser();
			Collector collector = new Collector(doc);
			try {
				parser.process(collector, doc);
			} catch (RecognitionException e) {
				Log.error(this, ErrorType.PARSE_ERROR.msg + ": " + doc.getPathname());
				Tool.errMgr.toolError(ErrorType.PARSE_ERROR, doc.getPathname());
				continue;
			} catch (Exception e) {
				Log.error(this, ErrorType.PARSE_FAILURE.msg + ": " + doc.getPathname());
				Tool.errMgr.toolError(ErrorType.PARSE_FAILURE, e, doc.getPathname());
				continue;
			}

			try {
				parser.annotateFeatures(collector);
			} catch (Exception e) {
				Log.error(this, ErrorType.VISITOR_FAILURE.msg + ": " + doc.getPathname(), e);
				Tool.errMgr.toolError(ErrorType.VISITOR_FAILURE, e, doc.getPathname());
				continue;
			}

			collector.annotateComments();
			collector.genLocalEdges(tabWidth);
			corpus.merge(collector);
		}
		perfData.rebuild = Time.end(start);

		try {
			corpus.save(corpusDir);
		} catch (Exception e) {
			corpus.setConsistent(false);
			Tool.errMgr.toolError(ErrorType.CANNOT_WRITE_FILE, e.getMessage());
		}
	}

	/** Return the corpus model */
	public CorpusModel getCorpusModel() {
		return corpus;
	}

	public ISourceParser getLanguageParser() {
		switch (lang) {
			case "antlr":
				return new AntlrSourceParser();
			case "java":
				return new JavaSourceParser();
			case "xvisitor":
				return new XVisitorSourceParser();
		}
		return null;
	}

	public Map<Factor, Double> getFactors() {
		return corpus.getLabelBoosts();
	}

	public List<Integer> excludedLangTypes() {
		return getLanguageParser().excludedTypes();
	}

	/** Incorporates and persists the model in the corpus in the given directory. */
	public void update(Path corpusDir, Document doc) {
		corpus.write(corpusDir, doc);
	}

	/**
	 * Find and associate a best matching feature from the corpus model with each node and comment
	 * feature of the document model.
	 */
	public void evaluate() {
		Instant start = Time.start();
		for (Feature feature : model.getFeatures()) {
			if (feature.getKind() == Kind.RULE) continue;

			TreeMultimap<Double, Feature> selected = getMatchSet(feature);
			Confidence.eval(feature, selected);
			if (Confidence.inRange()) {
				Feature best = Confidence.best();
				feature.setMatched(best);
			}
		}
		perfData.addFormatTime(Time.end(start));
	}

	public TreeMultimap<Double, Feature> getMatchSet(Feature node) {
		return corpus.match(node);
	}

	/** Applies the model to format the doc content */
	public void apply() {
		Instant start = Time.start();
		Formatter formatter = new Formatter(model);
		buffer = formatter.process();
		model.getDocument().setModified(buffer.toString());
		perfData.addFormatTime(Time.end(start));
	}

	/** Saves the formatted doc content */
	public void save() {
		model.getDocument().saveModified(true);
	}

	// ----

	public void createDocModel(Collector collector) {
		model = new DocModel(collector);
		perfData.addDoc(collector);
	}

	/** Return the document model */
	public DocModel getDocModel() {
		return model;
	}

	public int getCorpusTabWidth() {
		return tabWidth;
	}
}
