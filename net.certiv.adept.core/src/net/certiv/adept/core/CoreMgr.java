package net.certiv.adept.core;

import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

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
import net.certiv.adept.model.ModelIO;
import net.certiv.adept.parser.Collector;
import net.certiv.adept.parser.ISourceParser;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.topo.Factor;
import net.certiv.adept.tune.Boosts;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;
import net.certiv.adept.xvisitor.parser.XVisitorSourceParser;

public class CoreMgr {

	private String lang;
	public PerfData perfData;

	private DocModel docModel;
	private CorpusModel cpsModel;
	private List<Document> corpusDocs;
	private Path corpusDir;
	private int tabWidth;

	private OutputBuilder buffer;

	public CoreMgr() {
		this.perfData = new PerfData();
	}

	public boolean initialize(String lang, Path corpusDir, String corpusExt, int tabWidth, boolean rebuild,
			List<String> pathnames) throws Exception {
		
		this.lang = lang;
		this.corpusDir = corpusDir;
		this.tabWidth = tabWidth;

		Instant start = Time.start();
		cpsModel = ModelIO.loadModel(this, corpusDir, rebuild, pathnames);
		corpusDocs = ModelIO.readDocuments(corpusDir, corpusExt, tabWidth);
		perfData.load = Time.end(start);

		if (rebuild || !cpsModel.isValid(corpusDocs)) {
			doRebuild();
			if (!cpsModel.isConsistent()) return false;
		}
		perfData.corpusFeatureCnt = cpsModel.getFeatures().size();
		perfData.corpusFeatureTypeCnt = cpsModel.getFeatureIndex().size();
		perfData.corpusDocIndex = cpsModel.getPathnames();
		return true;
	}

	private void doRebuild() {
		Log.info(this, "Rebuilding...");

		cpsModel.clear();
		Instant start = Time.start();
		for (Document doc : corpusDocs) {
			Collector collector = collect(doc, false);
			cpsModel.merge(collector);
		}

		cpsModel.finalizeBuild();	// post-build operations
		perfData.rebuild = Time.end(start);

		try {
			cpsModel.save(corpusDir);
		} catch (Exception e) {
			cpsModel.setConsistent(false);
			Tool.errMgr.toolError(ErrorType.CANNOT_WRITE_FILE, e.getMessage());
		}
	}

	/** Returns a new feature collector populated from the given document. */
	public Collector collect(Document doc, boolean checkOnly) {
		ISourceParser parser = getLanguageParser();
		Collector collector = new Collector(this, doc);
		try {
			parser.process(collector, doc);
		} catch (RecognitionException e) {
			Log.error(this, ErrorType.PARSE_ERROR.msg + ": " + doc.getPathname());
			Tool.errMgr.toolError(ErrorType.PARSE_ERROR, doc.getPathname());
			return collector;
		} catch (Exception e) {
			Log.error(this, ErrorType.PARSE_FAILURE.msg + ": " + doc.getPathname());
			Tool.errMgr.toolError(ErrorType.PARSE_FAILURE, e, doc.getPathname());
			return collector;
		}

		if (checkOnly) return collector;

		collector.index();
		try {
			parser.annotateFeatures(collector);
		} catch (Exception e) {
			Log.error(this, ErrorType.VISITOR_FAILURE.msg + ": " + doc.getPathname(), e);
			Tool.errMgr.toolError(ErrorType.VISITOR_FAILURE, e, doc.getPathname());
			return collector;
		}

		collector.annotateComments();
		collector.genLocalEdges();
		return collector;
	}

	/** Return the cpsModel docModel */
	public CorpusModel getCorpusModel() {
		return cpsModel;
	}

	public void setCorpusModel(CorpusModel corpus) {
		this.cpsModel = corpus;
	}

	public List<Document> getCorpusDocs() {
		return corpusDocs;
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

	public Boosts getBoosts() {
		return cpsModel.getBoosts();
	}

	public double getBoost(Factor factor) {
		return cpsModel.getBoost(factor);
	}

	public void setBoosts(Boosts boosts) {
		cpsModel.setBoosts(boosts);
	}

	public List<Integer> excludedLangTypes() {
		return getLanguageParser().excludedTypes();
	}

	/** Incorporates and persists the docModel in the cpsModel in the given directory. */
	public void update(Path corpusDir, Document doc) {
		cpsModel.writeDocument(corpusDir, doc);
	}

	/**
	 * Find and associate a best matching feature from the cpsModel docModel with each node and
	 * comment feature of the document docModel.
	 */
	public void compare() {
		Instant start = Time.start();
		// Log.debug(this, String.format("Using %s", getBoosts()));

		for (Feature feature : docModel.getFeatures()) {
			if (feature.getKind() == Kind.RULE) continue;

			Confidence.eval(feature, getMatchSet(feature));
			feature.setMatched(Confidence.best());
		}
		perfData.addFormatTime(Time.end(start));
	}

	public TreeMultimap<Double, Feature> getMatchSet(Feature node) {
		return cpsModel.match(node);
	}

	/** Applies the docModel to format the doc content */
	public void apply() {
		Instant start = Time.start();
		Formatter formatter = new Formatter(docModel);
		buffer = formatter.process();
		docModel.getDocument().setModified(buffer.toString());
		perfData.addFormatTime(Time.end(start));
	}

	/** Saves the formatted doc content */
	public void save() {
		docModel.getDocument().saveModified(true);
	}

	// ----

	public void createDocModel(Collector collector) {
		docModel = new DocModel(this, collector);
		perfData.addDoc(collector);
	}

	/** Return the document docModel */
	public DocModel getDocModel() {
		return docModel;
	}

	public int getCorpusTabWidth() {
		return tabWidth;
	}
}
