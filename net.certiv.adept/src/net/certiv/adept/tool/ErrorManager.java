/*
 * [The "BSD license"] Copyright (c) 2012 Terence Parr Copyright (c) 2012 Sam Harwell All rights
 * reserved. Redistribution and use in doc and binary forms, with or without modification, are
 * permitted provided that the following conditions are met: 1. Redistributions of doc code must
 * retain the above copyright notice, this list of conditions and the following disclaimer. 2.
 * Redistributions in binary Facet must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution. 3. The name of the author may not be used to endorse or promote products derived
 * from this software without specific prior written permission. THIS SOFTWARE IS PROVIDED BY THE
 * AUTHOR ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */
package net.certiv.adept.tool;

import java.io.File;
import java.net.URL;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.misc.ErrorBuffer;

import net.certiv.adept.tool.Messages.GrammarSemanticsMessage;
import net.certiv.adept.tool.Messages.GrammarSyntaxMessage;
import net.certiv.adept.tool.Messages.ToolMessage;

public class ErrorManager {

	public IToolListener tool;
	public int errors;
	public int warnings;

	/** All errors that have been generated */
	public Set<ErrorType> errorTypes = EnumSet.noneOf(ErrorType.class);

	/** The group of templates that represent the current message format. */
	STGroup format;

	/** Messages should be sensitive to the locale. */
	Locale locale;
	String formatName;

	ErrorBuffer initSTListener = new ErrorBuffer();

	public ErrorManager(IToolListener tool) {
		this.tool = tool;
	}

	public void resetErrorState() {
		errors = 0;
		warnings = 0;
	}

	public ST getMessageTemplate(Messages msg) {
		ST messageST = msg.getMessageTemplate(false);
		ST locationST = getLocationFormat();
		ST reportST = getReportFormat(msg.getErrorType().severity);
		ST messageFormatST = getMessageFormat();

		boolean locationValid = false;
		if (msg.line != -1) {
			locationST.add("line", msg.line);
			locationValid = true;
		}
		if (msg.charPosition != -1) {
			locationST.add("column", msg.charPosition);
			locationValid = true;
		}
		if (msg.fileName != null) {
			File f = new File(msg.fileName);
			// Don't show path to file in messages; too long.
			String displayFileName = msg.fileName;
			if (f.exists()) {
				displayFileName = f.getName();
			}
			locationST.add("file", displayFileName);
			locationValid = true;
		}

		messageFormatST.add("id", msg.getErrorType().code);
		messageFormatST.add("text", messageST);

		if (locationValid) reportST.add("location", locationST);
		reportST.add("message", messageFormatST);
		return reportST;
	}

	/** Return a StringTemplate that refers to the current format used for emitting messages. */
	public ST getLocationFormat() {
		return format.getInstanceOf("location");
	}

	public ST getReportFormat(ErrorSeverity severity) {
		ST st = format.getInstanceOf("report");
		st.add("type", severity.getText());
		return st;
	}

	public ST getMessageFormat() {
		return format.getInstanceOf("message");
	}

	public boolean formatWantsSingleLineMessage() {
		return format.getInstanceOf("wantsSingleLineMessage").render().equals("true");
	}

	public void info(String msg) {
		tool.info(msg);
	}

	public static void fatalInternalError(String error, Throwable e) {
		internalError(error, e);
		throw new RuntimeException(error, e);
	}

	public static void internalError(String error, Throwable e) {
		StackTraceElement location = getLastNonErrorManagerCodeLocation(e);
		internalError("Exception " + e + "@" + location + ": " + error);
	}

	public static void internalError(String error) {
		StackTraceElement location = getLastNonErrorManagerCodeLocation(new Exception());
		String msg = location + ": " + error;
		System.err.println("internal error: " + msg);
	}

	/**
	 * Raise a predefined message with some number of paramters for the StringTemplate but for which
	 * there is no location information possible.
	 * 
	 * @param errorType The Message Descriptor
	 * @param args The arguments to pass to the StringTemplate
	 */
	public void toolError(ErrorType errorType, Object... args) {
		toolError(errorType, null, args);
	}

	public void toolError(ErrorType errorType, Throwable e, Object... args) {
		ToolMessage msg = new ToolMessage(errorType, e, args);
		emit(errorType, msg);
	}

	public void grammarError(ErrorType etype, String fileName, Token token, Object... args) {
		Messages msg = new GrammarSemanticsMessage(etype, fileName, token, args);
		emit(etype, msg);

	}

	public void syntaxError(ErrorType etype, String fileName, Token token, RecognitionException e, Object... args) {
		Messages msg = new GrammarSyntaxMessage(etype, fileName, token, e, args);
		emit(etype, msg);
	}

	public int getNumWarnings() {
		return warnings;
	}

	public int getNumErrors() {
		return errors;
	}

	/** Return first non ErrorManager code location for generating messages */
	private static StackTraceElement getLastNonErrorManagerCodeLocation(Throwable e) {
		StackTraceElement[] stack = e.getStackTrace();
		int i = 0;
		for (; i < stack.length; i++) {
			StackTraceElement t = stack[i];
			if (!t.toString().contains("ErrorManager")) {
				break;
			}
		}
		StackTraceElement location = stack[i];
		return location;
	}

	// S U P P O R T C O D E

	public void emit(ErrorType etype, Messages msg) {
		switch (etype.severity) {
			case WARNING:
				warnings++;
				tool.warn(msg);
				break;
			case ERROR:
				errors++;
				tool.error(msg);
				break;
		}
		errorTypes.add(etype);
	}

	/**
	 * The format gets reset either from the Tool if the user supplied a command line option to that
	 * effect Otherwise we just use the default "adept".
	 */
	public void setFormat(String formatName) {
		this.formatName = formatName;
		String fileName = formatName + STGroup.GROUP_FILE_EXTENSION;
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		URL url = cl.getResource(fileName);
		if (url == null) {
			cl = ErrorManager.class.getClassLoader();
			url = cl.getResource(fileName);
		}
		if (url == null && formatName.equals("adept")) {
			rawError("Adept installation corrupted; cannot find Adept messages format file " + fileName);
			panic();
		} else if (url == null) {
			rawError("no such message format file " + fileName + " retrying with default ANTLR format");
			setFormat("adept"); // recurse on this rule, trying the default message format
			return;
		}

		format = new STGroupFile(fileName, "UTF-8");
		format.load();

		if (!initSTListener.errors.isEmpty()) {
			rawError("ANTLR installation corrupted; can't load messages format file:\n" + initSTListener.toString());
			panic();
		}

		boolean formatOK = verifyFormat();
		if (!formatOK && formatName.equals("antlr")) {
			rawError("ANTLR installation corrupted; ANTLR messages format file " + formatName + ".stg incomplete");
			panic();
		} else if (!formatOK) {
			setFormat("antlr"); // recurse on this rule, trying the default message format
		}
	}

	/** Verify the message format template group */
	protected boolean verifyFormat() {
		boolean ok = true;
		if (!format.isDefined("location")) {
			System.err.println("Format template 'location' not found in " + formatName);
			ok = false;
		}
		if (!format.isDefined("message")) {
			System.err.println("Format template 'message' not found in " + formatName);
			ok = false;
		}
		if (!format.isDefined("report")) {
			System.err.println("Format template 'report' not found in " + formatName);
			ok = false;
		}
		return ok;
	}

	// If there are errors during ErrorManager init, we have no choice but to go to System.err.
	static void rawError(String msg) {
		System.err.println(msg);
	}

	static void rawError(String msg, Throwable e) {
		rawError(msg);
		e.printStackTrace(System.err);
	}

	public void panic(ErrorType errorType, Object... args) {
		ToolMessage msg = new ToolMessage(errorType, args);
		ST msgST = getMessageTemplate(msg);
		String outputMsg = msgST.render();
		if (formatWantsSingleLineMessage()) {
			outputMsg = outputMsg.replace('\n', ' ');
		}
		panic(outputMsg);
	}

	public static void panic(String msg) {
		rawError(msg);
		panic();
	}

	public static void panic() {
		// can't call tool.panic since there may be multiple tools; just one error manager
		throw new Error("ANTLR ErrorManager panic");
	}
}
