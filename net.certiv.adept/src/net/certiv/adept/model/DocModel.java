/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.model;

import java.util.List;
import java.util.TreeMap;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.Builder;
import net.certiv.adept.lang.Record;

public class DocModel {

	private CoreMgr mgr;
	private Builder builder;
	private Document doc;
	private List<Feature> features;

	// key=token; value=unique feature
	private TreeMap<AdeptToken, Feature> index;

	/** Creates a nascent model for the given document. */
	public DocModel(CoreMgr mgr, Builder builder) {
		this.mgr = mgr;
		this.builder = builder;

		this.features = builder.getFeatures();
		this.index = builder.getIndex();
		this.doc = builder.getDocument();
		doc.setModel(this);
	}

	public CoreMgr getMgr() {
		return mgr;
	}

	public void setMgr(CoreMgr mgr) {
		this.mgr = mgr;
	}

	public Document getDocument() {
		return doc;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public Record getParseRecord() {
		return builder;
	}

	/** Returns an orderd map, keyed by token/index, of the features in the document model. */
	public TreeMap<AdeptToken, Feature> getIndex() {
		return index;
	}

	public int getFeaturesCount() {
		return features.size();
	}

	public int getTokenRefsCount() {
		return index.size();
	}

	public void dispose() {
		doc.setModel(null);
		doc.setBuilder(null);
		doc = null;
		index = null;
		features = null;
		builder.dispose();
		builder = null;
		mgr = null;
	}
}
