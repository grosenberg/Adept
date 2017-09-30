package net.certiv.adept.core;

import net.certiv.adept.Settings;
import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.util.Chunk;
import net.certiv.adept.output.Formatter;
import net.certiv.adept.output.OutputBuilder;
import net.certiv.adept.util.TreeMultimap;

public class DocProcessor extends ParseProcessor {

	private Document doc;
	private DocModel docModel;

	public DocProcessor(ProcessMgr mgr, Document doc, Settings settings) {
		super(mgr, settings);
		this.doc = doc;
	}

	public DocModel createDocModel() {
		docModel = new DocModel(mgr, builder);
		mgr.addPerfDataDoc(builder);
		return docModel;
	}

	/**
	 * Match each feature in the current document model with those in the corpus model to find
	 * corresponding 'best' matched features.
	 */
	public void match(CorpusModel corModel) {
		for (Feature feature : docModel.getFeatures()) {
			switch (feature.getKind()) {
				case RULE:
					continue;
				case BLOCKCOMMENT:
				case LINECOMMENT:
					continue;
				case TERMINAL:
					TreeMultimap<Double, Feature> matches = corModel.match(feature);
					if (!matches.isEmpty()) {
						Chunk.eval(matches);
						Feature matched = Chunk.bestMatch();
						if (matched != null) {
							feature.setMatched(matched);
						}
					}
			}
		}
	}

	/** Applies the docModel to format the doc content. */
	public void formatDocument() {
		if (settings.format) {
			Formatter formatter = new Formatter(docModel);
			OutputBuilder buffer = formatter.process();
			doc.setModified(buffer.toString());
			if (settings.save) doc.saveModified(settings.backup);
		}
	}

	public DocModel getDocModel() {
		return docModel;
	}
}
