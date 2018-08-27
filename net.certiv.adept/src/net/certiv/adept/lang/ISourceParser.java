/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang;

import java.util.List;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.adept.Tool;
import net.certiv.adept.format.prep.Aligner;
import net.certiv.adept.format.prep.Indenter;
import net.certiv.adept.model.Document;

public interface ISourceParser extends IParseErrorReporter {

	/**
	 * Fills in the given builder by parsing the given document.
	 *
	 * @throws RecognitionException source parsing failure
	 * @throws Exception everything else
	 */
	void process(Tool tool, Builder builder, Document doc) throws RecognitionException, Exception;

	/** Tree walker: build dentation profile and annotate the given builder. */
	void defineIndentation(Indenter indenter);

	/** Tree walker: identify alignable fields and annotate the given builder. */
	void locateAlignables(Aligner aligner);

	/** Tree walker: extract features and annotate the given builder. */
	void extractFeatures(Builder builder);

	/** Returns the language specific list of feature types that are to be excluded. */
	List<Integer> excludedTypes();

	TokenFactory<?> getTokenFactory();

	void setup(TokenFactory<?> factory, String content);

	ParseTree getParseTree();

	List<String> getRuleNames();

	List<String> getTokenNames();

	Record getParseData();

	List<AdeptToken> lex(String content) throws RecognitionException;
}
