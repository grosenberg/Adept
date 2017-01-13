package net.certiv.adept;

import com.google.gson.annotations.Expose;

/** Settings to use when generating formatted output */
public class Settings {

	@Expose public int tabWidth = 4;
	@Expose public boolean useTabs = true;
	@Expose public boolean removeBlankLines = true;	// remove blank lines
	@Expose public int keepBlankLines = 1;			// but keep at least this number

	@Expose public boolean joinLines = true;		// if indicated by feature match

	@Expose public int lineWidth = 120;				// target formatted width of lines
	@Expose public int commentWidth = 100;			// ... that end with a comment

	@Expose public boolean removeTrailingWS = true;
}
