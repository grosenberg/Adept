package net.certiv.adept.core;

import net.certiv.adept.Settings;
import net.certiv.adept.core.util.Facet;
import net.certiv.adept.format.Formatter;
import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.util.Time;

public class DocProcessor extends BaseProcessor {

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
	 * Match each feature in the current document model with those in the given corpus model to set the
	 * corresponding 'best' matched feature ref token. Assigns the best match from model to the document
	 * feature.
	 */
	public void match(CorpusModel corModel) {
		Time.start(Facet.MATCH);
		for (Feature feature : docModel.getFeatures()) {
			switch (feature.getKind()) {
				case BLOCKCOMMENT:
				case LINECOMMENT:
				case TERMINAL:
					corModel.match(feature);
			}
		}
		Time.stop(Facet.MATCH);
	}

	/** Applies the docModel to format the doc content. */
	public void formatDocument() {
		Time.start(Facet.FORMAT);
		if (settings.format) {
			Formatter formatter = new Formatter(docModel, settings);
			if (formatter.execute()) {
				doc.setEdits(formatter.getTextEdits());
				doc.setModified(formatter.getFormattedContents());
				if (settings.save) doc.saveModified(settings.backup);
			}
		}
		Time.stop(Facet.FORMAT);
	}
}
