package net.certiv.adept.tool;

/**
 * Abstracts away the definition of Message severity and the text that should
 * display to represent that severity if there is no StringTemplate available
 * to do it.
 *
 * @author Jim Idle - Temporal Wave LLC (jimi@temporal-wave.com)
 */
public enum ErrorSeverity {
    INFO    ("info"),
	WARNING ("warning"),
	WARNING_ONE_OFF ("warning"),
	ERROR   ("error"),
	ERROR_ONE_OFF   ("error"),
    FATAL   ("fatal"),  // TODO: add fatal for which phase? sync with ErrorManager
    ;

    /**
     * The text version of the ENUM value, used for display purposes
     */
    private final String text;

    /**
     * Standard getter method for the text that should be displayed in order to
     * represent the severity to humans and product modelers.
     *
     * @return The human readable string representing the severity level
     */
    public String getText() { return text; }

    /**
     * Standard constructor to build an instance of the Enum entries
     *
     * @param text The human readable string representing the severity level
     */
    private ErrorSeverity(String text) { this.text = text; }
}

