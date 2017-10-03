package net.certiv.adept.core;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.certiv.adept.Settings;
import net.certiv.adept.Tool;
import net.certiv.adept.lang.antlr.parser.AntlrSourceParser;
import net.certiv.adept.lang.java.parser.JavaSourceParser;
import net.certiv.adept.lang.xvisitor.parser.XVisitorSourceParser;
import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.load.CorpusDocs;
import net.certiv.adept.model.parser.Builder;
import net.certiv.adept.model.parser.ISourceParser;
import net.certiv.adept.model.tune.Boosts;
import net.certiv.adept.model.util.Factor;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;
import net.certiv.adept.util.TreeMultimap;

public class ProcessMgr {

	public PerfData perfData;

	private Settings settings;
	private DocModel docModel;
	private CorpusModel corModel;

	// key=docId; value=document
	private Map<Integer, Document> documents;

	public ProcessMgr() {
		this.perfData = new PerfData();
	}

	/** Loads the corpus model. Builds the model if requested or required. */
	public boolean loadCorpusModel(Settings settings, List<String> pathnames) {
		this.settings = settings;

		Instant start = Time.start();

		CorpusProcessor cp = new CorpusProcessor(this, settings, pathnames);
		cp.loadModel();
		corModel = cp.getCorpusModel();

		perfData.load = Time.end(start);
		perfData.corpusFeatureCnt = corModel.getFeatures().size();
		perfData.corpusFeatureTypeCnt = corModel.getFeatureIndex().size();
		perfData.corpusDocIndex = corModel.getPathnames();

		return cp.isConsistent() ? true : false;
	}

	public void execute(Settings settings, List<String> pathnames) {
		documents = loadDocuments(pathnames);
		Log.info(this, documents.size() + " source documents to process.");
		for (Document doc : documents.values()) {
			if (settings.learn) {			// add document to corpus repo
				CorpusDocs.writeDocument(settings.corpusDir, doc);
			} else {
				Instant start = Time.start();

				DocProcessor dp = new DocProcessor(this, doc, settings);
				if (dp.parseDocument(doc, settings.check)) {
					docModel = dp.createDocModel();
					dp.match(corModel);
					dp.formatDocument();
				}

				perfData.addFormatTime(Time.end(start));
			}
		}
	}

	private Map<Integer, Document> loadDocuments(List<String> pathnames) {
		Map<Integer, Document> documents = new HashMap<>();
		for (String pathname : pathnames) {
			try {
				Document doc = loadDocument(pathname);
				documents.put(doc.getDocId(), doc);
			} catch (IOException e) {}
		}
		perfData.setSize(documents.size());
		return documents;
	}

	private Document loadDocument(String pathname) throws IOException {
		File file = new File(pathname);
		if (!file.exists()) {
			Tool.errMgr.toolError(ErrorType.CANNOT_OPEN_FILE, pathname);
			throw new IOException("Source file does not exist: " + pathname);
		}

		byte[] content;
		try {
			content = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			Tool.errMgr.toolError(ErrorType.CANNOT_READ_FILE, pathname);
			throw e;
		}
		return new Document(pathname, settings.tabWidth, new String(content, StandardCharsets.UTF_8));
	}

	// ----

	public void addPerfDataDoc(Builder builder) {
		perfData.addDoc(builder);
	}

	// ----

	/** Returns the document identified by the given document id or {@code null}. */
	public Document getDocument(int docId) {
		return documents.get(docId);
	}

	/** Return the document model */
	public DocModel getDocModel() {
		return docModel;
	}

	/** Return the corpus model */
	public CorpusModel getCorpusModel() {
		return corModel;
	}

	public int getCorpusTabWidth() {
		return settings.tabWidth;
	}

	public TreeMultimap<Double, Feature> getMatchSet(Feature source) {
		return corModel.match(source);
	}

	// ----

	public ISourceParser getLanguageParser() {
		switch (settings.lang) {
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

	// ----

	public Boosts getBoosts() {
		return corModel.getBoosts();
	}

	public double getBoost(Factor factor) {
		return corModel.getBoost(factor);
	}

	public void setBoosts(Boosts boosts) {
		corModel.setBoosts(boosts);
	}
}
