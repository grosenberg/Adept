package net.certiv.adept.core;

import org.antlr.v4.runtime.RecognitionException;

import net.certiv.adept.Settings;
import net.certiv.adept.Tool;
import net.certiv.adept.lang.Builder;
import net.certiv.adept.lang.ISourceParser;
import net.certiv.adept.model.Document;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.util.Log;

public abstract class ParseProcessor {

	protected CoreMgr mgr;
	protected Settings settings;
	protected Builder builder;

	public ParseProcessor(CoreMgr mgr, Settings settings) {
		this.mgr = mgr;
		this.settings = settings;
	}

	/**
	 * Parses the given document. If {@code check} is {@code false}, also generate the corresponding
	 * feature set (collected in the builder).
	 */
	public boolean parseDocument(Document doc, boolean check) {
		ISourceParser parser = mgr.getLanguageParser();
		builder = new Builder(mgr, doc);
		try {
			parser.process(builder, doc);
		} catch (RecognitionException e) {
			Log.error(this, ErrorType.PARSE_ERROR.msg + ": " + doc.getPathname());
			Tool.errMgr.toolError(ErrorType.PARSE_ERROR, doc.getPathname());
			return false;
		} catch (Exception e) {
			Log.error(this, ErrorType.PARSE_FAILURE.msg + ": " + doc.getPathname());
			Tool.errMgr.toolError(ErrorType.PARSE_FAILURE, e, doc.getPathname());
			return false;
		}

		if (check) return true;

		builder.update();
		builder.index();
		try {
			parser.extractFeatures(builder);
		} catch (Exception e) {
			Log.error(this, ErrorType.VISITOR_FAILURE.msg + ": " + doc.getPathname(), e);
			Tool.errMgr.toolError(ErrorType.VISITOR_FAILURE, e, doc.getPathname());
			return false;
		}

		// builder.annotateComments();
		return true;
	}

	public CoreMgr getMgr() {
		return mgr;
	}

	public Settings getSettings() {
		return settings;
	}

	public Builder getBuilder() {
		return builder;
	}
}
