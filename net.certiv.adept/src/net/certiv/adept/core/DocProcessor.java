/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.core;

import java.util.List;

import net.certiv.adept.Settings;
import net.certiv.adept.format.Formatter;
import net.certiv.adept.format.prep.Group;
import net.certiv.adept.format.prep.Scheme;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.util.Function;
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
		Time.start(Function.MATCH);
		for (Feature feature : docModel.getFeatures()) {
			switch (feature.getKind()) {
				case BLOCKCOMMENT:
				case LINECOMMENT:
				case TERMINAL:
					corModel.match(feature);
					break;
				case WHITESPACE:
					break;
			}
		}
		normalizeMatches(corModel);
		Time.stop(Function.MATCH);
	}

	private void normalizeMatches(CorpusModel corModel) {
		List<Group> groups = docModel.getParseRecord().groupIndex;
		for (Group group : groups) {
			for (Scheme scheme : group.getSchemes()) {
				int docId = group.findPrimaryDocId(scheme);
				List<AdeptToken> tokens = group.getAll(scheme);
				for (AdeptToken token : tokens) {
					if (token.refToken().isMatched()) {
						token.refToken().chooseBest(docId);
					}
				}
			}
		}

		// String ancs = Refs.evalAncestors(features.get(0).getAncestors());
		// String docName = corModel.getFilename(docId);
		// String msg = String.format("All %s elements come from %s", ancs, docName);
		// getMgr().getTool().toolInfo(this, msg);
	}

	/** Applies the docModel to format the doc content. */
	public void formatDocument() {
		Time.start(Function.FORMAT);
		Formatter formatter = new Formatter(docModel, settings);
		if (formatter.execute()) {
			doc.setEdits(formatter.getTextEdits());
			doc.setModified(formatter.getFormatted());
			if (settings.save) doc.saveModified(mgr.getTool(), settings.backup);
		}
		formatter.dispose();
		Time.stop(Function.FORMAT);
	}

	public void dispose() {
		doc = null;
		docModel = null;
	}
}
