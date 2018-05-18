/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.model.load;

import java.nio.file.Paths;
import java.util.List;

import com.google.gson.annotations.Expose;

import net.certiv.adept.model.Feature;

/**
 * Functions only as the persistance root for the storage of a feature set. Not used as part of the
 * operational model.
 */
public class FeatureSet {

	@Expose private int docId;
	@Expose private String pathname;
	@Expose private List<Feature> features;

	/**
	 * Collection of features.
	 *
	 * @param docId the id of the document that originated these features
	 * @param pathname the pathname of the document that originated these features
	 * @param features the list of features identified in the document
	 */
	public FeatureSet(int docId, String pathname, List<Feature> features) {
		this.docId = docId;
		this.pathname = pathname;
		this.features = features;
	}

	public int getDocId() {
		return docId;
	}

	public String getPathname() {
		return pathname;
	}

	public String getFilename() {
		return Paths.get(pathname).getFileName().toString();
	}

	public List<Feature> getFeatures() {
		return features;
	}
}
