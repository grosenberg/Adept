package net.certiv.adept;

import com.google.gson.annotations.Expose;

/** Settings to use globally. */
public class Settings {

	// settings to use when analyzing a source document

	@Expose public Boolean backup = true;
	@Expose public Boolean check;
	@Expose public Boolean format = true;
	@Expose public Boolean learn;
	@Expose public Boolean rebuild;
	@Expose public Boolean save;

	@Expose public String corpusRoot;
	@Expose public String lang;
	@Expose public String output;
	@Expose public String verbose;
	@Expose public Integer tabWidth = 4;

	// settings to use when generating formatted output

	@Expose public boolean useTabs = true;
	@Expose public boolean removeBlankLines = true;		// remove blank lines
	@Expose public int keepBlankLines = 1;				// but keep at least this number
	@Expose public boolean forceLastLineBlank = true;	// file must end with blank line

	@Expose public boolean joinLines = true;			// allow if indicated by feature match

	@Expose public int lineWidth = 120;					// target formatted width of lines
	@Expose public int commentWidth = 100;				// ... that end with a comment

	@Expose public boolean removeTrailingWS = true;
}
