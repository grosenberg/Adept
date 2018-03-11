package net.certiv.adept.lang;

import java.util.List;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.adept.model.Document;

public interface ISourceParser extends IParseErrorReporter {

	/**
	 * Fills in the given builder by parsing the given document.
	 *
	 * @throws Exception
	 * @throws RecognitionException
	 */
	void process(Builder builder, Document doc) throws RecognitionException, Exception;

	/** Walk the parse tree to extract features and annotate the given builder. */
	void extractFeatures(Builder builder);

	/** Returns the language specific list of feature types that are to be excluded. */
	List<Integer> excludedTypes();

	ParseTree getParseTree();

	List<String> getRuleNames();

	List<String> getTokenNames();

	ParseRecord getParseData();
}
