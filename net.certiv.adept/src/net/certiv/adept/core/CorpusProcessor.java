package net.certiv.adept.core;

import java.nio.file.Path;
import java.util.List;

import net.certiv.adept.Settings;
import net.certiv.adept.Tool;
import net.certiv.adept.core.util.Facet;
import net.certiv.adept.core.util.Form;
import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.load.CorpusData;
import net.certiv.adept.model.load.CorpusDocs;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.util.Calc;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;

public class CorpusProcessor extends BaseProcessor {

	private List<String> pathnames;
	private boolean forceBuild;

	private CorpusModel corModel;

	public CorpusProcessor(CoreMgr mgr, Settings settings, List<String> pathnames) {
		super(mgr, settings);

		if (pathnames == null || pathnames.isEmpty()) {
			this.pathnames = CorpusDocs.readPathnames(settings.corpusDir, settings.corpusExt);
			this.forceBuild = false;
		} else {
			this.pathnames = pathnames;
			this.forceBuild = true;
		}
	}

	public void loadModel() {
		Time.start(Facet.LOAD);
		if (settings.rebuild || forceBuild) {
			buildCorpusModel();
		} else {
			try {
				corModel = CorpusData.loadModel(mgr, settings.corpusDir, pathnames);
			} catch (Exception e) {
				corModel.setConsistent(false);
				Tool.errMgr.toolError(ErrorType.MODEL_LOAD_FAILURE, e.getMessage());
				Log.error(this, "Corpus model load failure: ", e);
			}
			if (!corModel.isConsistent()) {
				buildCorpusModel();
			}
		}
		Time.stop(Facet.LOAD);
	}

	public void buildCorpusModel() {
		Time.clear();
		Calc.clear();

		Time.start(Facet.BUILD);
		Log.info(this, "Building corpus model ...");

		CorpusData.removeDataFiles(settings.corpusDir);
		corModel = new CorpusModel(settings.corpusDir);

		List<Document> documents = CorpusDocs.readDocuments(settings.corpusDir, settings.corpusExt, settings.tabWidth);
		// documents.subList(3, documents.size()).clear();

		for (Document doc : documents) {
			if (pathnames.contains(doc.getPathname())) {
				Calc.inc(Form.DOCS);
				Feature.clearPool();
				processDocument(doc, false);
				Calc.delta(Form.TOKS, doc.getParseRecord().tokenStream.size());
				corModel.merge(builder);	// merge document features into the corpus
			}
		}
		corModel.postBuild(builder);
		Thread t = new Thread(mgr.getThreadGroup(), new SaveOp(corModel, settings.corpusDir));
		t.start();
		Time.stop(Facet.BUILD);
	}

	public CorpusModel getCorpusModel() {
		return corModel;
	}

	public boolean isConsistent() {
		return corModel.isConsistent();
	}

	private class SaveOp implements Runnable {

		private CorpusModel model;
		private Path dir;

		public SaveOp(CorpusModel model, Path dir) {
			super();
			this.model = model;
			this.dir = dir;
		}

		@Override
		public void run() {
			try {
				model.save(dir);
			} catch (Exception e) {
				corModel.setConsistent(false);
				Tool.errMgr.toolError(ErrorType.MODEL_SAVE_FAILURE, e.getMessage());
				Log.error(this, "Cannot write file(s): ", e);
			}
		}
	}
}