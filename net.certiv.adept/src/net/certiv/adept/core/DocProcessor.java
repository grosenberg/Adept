package net.certiv.adept.core;

import net.certiv.adept.Settings;
import net.certiv.adept.format.Formatter;
import net.certiv.adept.format.OutputBuilder;
import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;

public class DocProcessor extends ParseProcessor {

	private Document doc;
	private DocModel docModel;

	public DocProcessor(CoreMgr mgr, Document doc, Settings settings) {
		super(mgr, settings);
		this.doc = doc;
	}

	public DocModel createDocModel() {
		docModel = new DocModel(mgr, builder);
		return docModel;
	}

	public DocModel getDocModel() {
		return docModel;
	}

	/**
	 * Match each feature in the current document model with those in the corpus model to find
	 * corresponding 'best' matched features. Assigns the best match from model to the feature.
	 */
	public void match(CorpusModel corModel) {
		for (Feature feature : docModel.getFeatures()) {
			switch (feature.getKind()) {
				case BLOCKCOMMENT:
				case LINECOMMENT:
				case TERMINAL:
				case VAR:
					Feature matched = corModel.match(feature);
					feature.setMatched(matched);
			}
		}
	}

	/** Applies the docModel to format the doc content. */
	public void formatDocument() {
		if (settings.format) {
			Formatter formatter = new Formatter(docModel, settings);
			OutputBuilder buffer = formatter.process();
			doc.setModified(buffer.toString());
			if (settings.save) doc.saveModified(settings.backup);
		}
	}
}
