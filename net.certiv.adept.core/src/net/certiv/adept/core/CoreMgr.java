package net.certiv.adept.core;

import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

import org.antlr.v4.runtime.RecognitionException;

import com.google.common.collect.TreeMultimap;

import net.certiv.adept.Tool;
import net.certiv.adept.lang.antlr.parser.AntlrSourceParser;
import net.certiv.adept.lang.java.parser.JavaSourceParser;
import net.certiv.adept.lang.xvisitor.parser.XVisitorSourceParser;
import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.model.load.Corpus;
import net.certiv.adept.model.load.Store;
import net.certiv.adept.model.load.parser.FeatureFactory;
import net.certiv.adept.model.load.parser.ISourceParser;
import net.certiv.adept.model.topo.Factor;
import net.certiv.adept.model.tune.Boosts;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;

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
		cpsModel = Store.loadModel(this, corpusDir, rebuild, pathnames);
		corpusDocs = Corpus.readDocuments(corpusDir, corpusExt, tabWidth);
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
			FeatureFactory featureFactory = collect(doc, false);
			cpsModel.merge(featureFactory);
		}

		cpsModel.finalizeBuild();	// post-build operations
		perfData.rebuild = Time.end(start);

		try {
			cpsModel.save(corpusDir);
		} catch (Exception e) {
			cpsModel.setConsistent(false);
			Tool.errMgr.toolError(ErrorType.CANNOT_WRITE_FILE, e.getMessage());
			Log.error(this, "Cannot write file(s): ", e);
		}
	}

	/** Returns a new feature collector populated from the given document. */
	public FeatureFactory collect(Document doc, boolean checkOnly) {
		ISourceParser parser = getLanguageParser();
		FeatureFactory featureFactory = new FeatureFactory(this, doc);
		try {
			parser.process(featureFactory, doc);
		} catch (RecognitionException e) {
			Log.error(this, ErrorType.PARSE_ERROR.msg + ": " + doc.getPathname());
			Tool.errMgr.toolError(ErrorType.PARSE_ERROR, doc.getPathname());
			return featureFactory;
		} catch (Exception e) {
			Log.error(this, ErrorType.PARSE_FAILURE.msg + ": " + doc.getPathname());
			Tool.errMgr.toolError(ErrorType.PARSE_FAILURE, e, doc.getPathname());
			return featureFactory;
		}

		if (checkOnly) return featureFactory;

		featureFactory.index();
		try {
			parser.extractFeatures(featureFactory);
		} catch (Exception e) {
			Log.error(this, ErrorType.VISITOR_FAILURE.msg + ": " + doc.getPathname(), e);
			Tool.errMgr.toolError(ErrorType.VISITOR_FAILURE, e, doc.getPathname());
			return featureFactory;
		}

		featureFactory.annotateComments();
		featureFactory.genLocalEdges();
		return featureFactory;
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
		Instant mark = Time.start();
		// Log.debug(this, String.format("Using %s", getBoosts()));

		for (Feature feature : docModel.getFeatures()) {
			if (feature.getKind() == Kind.RULE) continue;

			TreeMultimap<Double, Feature> mset = getMatchSet(feature);
			if (mset.isEmpty()) {
				Log.error(this, "No matches for: " + feature);
				return;
			}

			Confidence.eval(mset);
			feature.setMatched(Confidence.best());
		}
		perfData.addFormatTime(Time.end(mark));
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

	public void createDocModel(FeatureFactory featureFactory) {
		docModel = new DocModel(this, featureFactory);
		perfData.addDoc(featureFactory);
	}

	/** Return the document docModel */
	public DocModel getDocModel() {
		return docModel;
	}

	public int getCorpusTabWidth() {
		return tabWidth;
	}
}
