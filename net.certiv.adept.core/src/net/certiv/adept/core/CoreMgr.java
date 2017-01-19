package net.certiv.adept.core;

import java.nio.file.Path;
import java.util.List;
import java.util.TreeMap;

import org.antlr.v4.runtime.RecognitionException;

import net.certiv.adept.Tool;
import net.certiv.adept.antlr.parser.AntlrSourceParser;
import net.certiv.adept.java.parser.JavaSourceParser;
import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Edge;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.parser.Collector;
import net.certiv.adept.parser.ISourceParser;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.util.Log;
import net.certiv.adept.xvisitor.parser.XVisitorSourceParser;

public class CoreMgr {

	private String lang;

	private DocModel model;
	private CorpusModel corpus;
	private List<Document> corpusDocs;
	private Path corpusDir;

	private OutputBuilder buffer;

	public CoreMgr(String lang) {
		this.lang = lang;
	}

	public void initialize(Path corpusDir, String corpusExt, int tabWidth, boolean rebuild) throws Exception {
		this.corpusDir = corpusDir;
		corpus = CorpusModel.load(corpusDir, rebuild);
		corpusDocs = corpus.read(corpusDir, corpusExt, tabWidth);
		if (rebuild || !corpus.isValid(corpusDocs)) {
			doRebuild();
		}
	}

	private void doRebuild() {
		Log.info(this, "Rebuilding...");

		corpus.clear();
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
				parser.annotate(collector);
			} catch (Exception e) {
				Log.error(this, ErrorType.VISITOR_FAILURE.msg + ": " + doc.getPathname());
				Tool.errMgr.toolError(ErrorType.VISITOR_FAILURE, e, doc.getPathname());
				continue;
			}

			collector.annotateComments();
			collector.index();
			corpus.include(collector);
		}

		try {
			corpus.save(corpusDir);
		} catch (Exception e) {
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
		for (Feature feature : model.getFeatures()) {
			if (feature.getKind() == Kind.RULE) continue;

			TreeMap<Double, Feature> selected = corpus.match(feature);
			Confidence.eval(feature, selected);
			if (Confidence.inRange()) {
				feature.setMatched(Confidence.best());
			}
		}
	}

	/** Applies the model to format the doc content */
	public void apply() {
		Formatter formatter = new Formatter(model);
		buffer = formatter.process();
		model.getDocument().setModified(buffer.toString());
	}

	/** Saves the formatted doc content */
	public void save() {
		model.getDocument().saveModified(true);
	}

	public double typeBoost(Edge outer, Edge inner) {
		return 1;
	}

	// ----

	public void createDocModel(Collector collector) {
		model = new DocModel(collector);
	}

	/** Return the document model */
	public DocModel getDocModel() {
		return model;
	}
}
