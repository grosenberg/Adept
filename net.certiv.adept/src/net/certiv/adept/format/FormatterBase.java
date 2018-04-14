package net.certiv.adept.format;

import net.certiv.adept.Settings;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

public abstract class FormatterBase {

	protected Document doc;
	protected ParseRecord data;
	protected Settings settings;
	protected String tabEquiv;

	public FormatterBase(DocModel model, Settings settings) {
		this.doc = model.getDocument();
		this.data = doc.getParseRecord();
		this.settings = settings;
		this.tabEquiv = Strings.spaces(settings.tabWidth);
	}

	// converts a token index to a known good feature ref token
	protected RefToken srcRefFor(int index) {
		AdeptToken token = index > -1 ? data.tokenIndex.get(index) : null;
		Feature feature = token != null ? data.index.get(token) : null;
		return feature != null ? feature.getRefFor(token.getTokenIndex()) : null;
	}

	protected String calcSpacing(Spacing spacing, String existing, int indents) {
		switch (spacing) {
			case HFLEX:
				String hflex = existing.replace(tabEquiv, "\t");
				return hflex.replaceAll("\\t[ ]+(?=\\t)", "\t");

			case HSPACE:
				return Strings.SPACE;

			case NONE:
				return "";

			case VLINE:
				return Strings.EOL + Strings.createIndent(settings.tabWidth, settings.useTabs, indents);

			case VFLEX:
				String vflex = Strings.getN(vertCount(existing), Strings.EOL);
				return vflex + Strings.createIndent(settings.tabWidth, settings.useTabs, indents);

			default:
				return existing;
		}
	}

	// produce replacement indent string including any leading newlines for BOL
	protected String calcIndent(String existing, String matched, int indents) {
		String indentStr = "";
		if (!settings.removeTrailingWS) {
			indentStr += Strings.leadHWS(existing);
		}
		int eline = vertCount(existing);
		int mline = vertCount(matched);
		int lines = Math.max(eline, mline);
		indentStr += Strings.getN(lines, Strings.EOL);
		indentStr += Strings.createIndent(settings.tabWidth, settings.useTabs, indents);
		return indentStr;
	}

	protected int vertCount(String text) {
		int lines = Strings.countVWS(text);
		if (settings.removeBlankLines) {
			lines = Math.min(lines, settings.keepNumBlankLines + 1);
		}
		return lines;
	}

	protected void showWhere(RefToken prior, RefToken next, RefToken matched) {
		String pname = prior != null ? data.getTokenName(prior.type) : "Null";
		String mname = matched != null ? data.getTokenName(matched.type) : "Null";
		String nname = next != null ? data.getTokenName(next.type) : "Null";
		Log.debug(this, "   %s > %s >%s", pname, mname, nname);
	}
}
