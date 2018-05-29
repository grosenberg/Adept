/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.tool;

import java.io.File;
import java.net.URL;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.misc.ErrorBuffer;

import net.certiv.adept.IToolListener;
import net.certiv.adept.Tool;
import net.certiv.adept.tool.Messages.GrammarSemanticsMessage;
import net.certiv.adept.tool.Messages.GrammarSyntaxMessage;
import net.certiv.adept.tool.Messages.ToolMessage;

public abstract class ToolBase {

	public static final String MSG_FORMAT = "adept";
	public static final Token INVALID_TOKEN = new CommonToken(Token.INVALID_TYPE);

	private final List<IToolListener> listeners = new CopyOnWriteArrayList<>();
	protected final DefaultToolListener defaultListener = new DefaultToolListener();

	public int errors;
	public int warnings;

	/** All errors that have been generated */
	public Set<ErrorDesc> errorDescs = EnumSet.noneOf(ErrorDesc.class);

	/** The group of templates that represent the current message format. */
	private STGroup format;
	private ErrorBuffer initSTListener = new ErrorBuffer();
	private String formatName;

	/** Create an instance of the tool, pending configuration and use. */
	public ToolBase() {
		super();
		setFormat(MSG_FORMAT);
	}

	public abstract void version();

	/** Add listener for embedded use. */
	public void addListener(IToolListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IToolListener listener) {
		listeners.remove(listener);
	}

	public List<IToolListener> getListeners() {
		return listeners;
	}

	public void removeListeners() {
		listeners.clear();
	}

	public void resetErrorState() {
		errors = 0;
		warnings = 0;
	}

	public int getNumWarnings() {
		return warnings;
	}

	public int getNumErrors() {
		return errors;
	}

	public void toolInfo(String text) {
		toolInfo(null, text, null);
	}

	public void toolInfo(Object source, String text) {
		toolInfo(source, text, null);
	}

	public void toolInfo(Object source, String text, Throwable e) {
		emitMessage(source, ErrorDesc.INFO, text, e);
	}

	public void toolError(Object source, ErrorDesc err, String text) {
		toolError(source, err, null, text);
	}

	public void toolError(Object source, ErrorDesc err, Throwable e, Object... args) {
		ToolMessage msg = new ToolMessage(err, e, args);
		String text = describe(msg);
		emitMessage(source, err, text, e);
	}

	public void grammarError(Object source, ErrorDesc err, String fileName, Token token, Object... args) {
		Messages msg = new GrammarSemanticsMessage(err, fileName, token, args);
		String text = describe(msg);
		emitMessage(source, err, text, null);
	}

	public void syntaxError(Object source, ErrorDesc err, String fileName, Token token, RecognitionException e,
			Object... args) {
		Messages msg = new GrammarSyntaxMessage(err, fileName, token, e, args);
		String text = describe(msg);
		emitMessage(source, err, text, e);
	}

	private String describe(Messages msg) {
		ST msgST = getMessageTemplate(msg);
		String text = msgST.render();
		if (formatWantsSingleLineMessage()) {
			text = text.replace('\n', ' ');
		}
		return text;
	}

	private void emitMessage(Object source, ErrorDesc err, String text, Throwable e) {
		switch (err.kind) {
			case INFO:
				if (listeners.isEmpty()) {
					defaultListener.info(source, text, e);
				} else {
					for (IToolListener l : listeners) {
						l.info(source, text, e);
					}
				}
				break;
			case WARNING:
				warnings++;
				if (listeners.isEmpty()) {
					defaultListener.warn(source, text, e);
				} else {
					for (IToolListener l : listeners) {
						l.warn(source, text, e);
					}
				}
				break;
			case ERROR:
				errors++;
				if (listeners.isEmpty()) {
					defaultListener.error(source, text, e);
				} else {
					for (IToolListener l : listeners) {
						l.error(source, text, e);
					}
				}
				break;
		}
		errorDescs.add(err);
	}

	protected boolean formatWantsSingleLineMessage() {
		return format.getInstanceOf("wantsSingleLineMessage").render().equals("true");
	}

	/** Return a StringTemplate that refers to the current format used for emitting messages. */
	protected ST getLocationFormat() {
		return format.getInstanceOf("location");
	}

	protected ST getMessageFormat() {
		return format.getInstanceOf("message");
	}

	protected ST getMessageTemplate(Messages msg) {
		ST messageST = msg.getMessageTemplate(false);
		ST locationST = getLocationFormat();
		ST reportST = getReportFormat(msg.getErrorType().kind);
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

	protected ST getReportFormat(ErrorKind kind) {
		ST st = format.getInstanceOf("report");
		st.add("type", kind.text);
		return st;
	}

	/**
	 * The format gets reset either from the Tool if the user supplied a command line option to that
	 * effect Otherwise we just use the default "adept".
	 */
	private void setFormat(String formatName) {
		this.formatName = formatName;
		String fileName = formatName + STGroup.GROUP_FILE_EXTENSION;
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		URL url = cl.getResource(fileName);
		if (url == null) {
			cl = Tool.class.getClassLoader();
			url = cl.getResource(fileName);
		}
		if (url == null && formatName.equals("adept")) {
			toolInfo("Adept installation corrupted; cannot find Adept messages format file " + fileName);
			panic();
		} else if (url == null) {
			toolInfo("no such message format file " + fileName + " retrying with default ANTLR format");
			setFormat("adept"); // recurse on this rule, trying the default message format
			return;
		}

		format = new STGroupFile(fileName, "UTF-8");
		format.load();

		if (!initSTListener.errors.isEmpty()) {
			toolInfo("ANTLR installation corrupted; can't load messages format file:\n" + initSTListener.toString());
			panic();
		}

		boolean formatOK = verifyFormat();
		if (!formatOK && formatName.equals("antlr")) {
			toolInfo("ANTLR installation corrupted; ANTLR messages format file " + formatName + ".stg incomplete");
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

	protected static void panic() {
		throw new Error("Adept panic");
	}

	// // If there are errors during ErrorManager init, we have no choice but to go to System.err.
	// static void rawError(String msg) {
	// System.err.println(msg);
	// }
	//
	// static void rawError(String msg, Throwable e) {
	// rawError(msg);
	// e.printStackTrace(System.err);
	// }
	//
	// protected void fatalInternalError(String error, Throwable e) {
	// internalError(error, e);
	// throw new RuntimeException(error, e);
	// }
	//
	// protected void internalError(String error, Throwable e) {
	// StackTraceElement location = getLastNonErrorManagerCodeLocation(e);
	// internalError("Exception " + e + "@" + location + ": " + error);
	// }
	//
	// protected void internalError(String error) {
	// StackTraceElement location = getLastNonErrorManagerCodeLocation(new Exception());
	// String msg = location + ": " + error;
	// System.err.println("internal error: " + msg);
	// }
	//
	// /** Return first non ErrorManager code location for generating messages */
	// private StackTraceElement getLastNonErrorManagerCodeLocation(Object, source, Throwable e) {
	// if (source == null || e == null) return null;
	// StackTraceElement[] stack = e.getStackTrace();
	// int idx = 0;
	// for (; idx < stack.length; idx++) {
	// StackTraceElement elem = stack[idx];
	// if (elem.getClassName().contains(name)) break;
	// }
	// StackTraceElement location = stack[idx];
	// return location;
	// }
	//
	// protected void panic(ErrorDesc errorDesc, Object... args) {
	// ToolMessage msg = new ToolMessage(errorDesc, args);
	// ST msgST = getMessageTemplate(msg);
	// String outputMsg = msgST.render();
	// if (formatWantsSingleLineMessage()) {
	// outputMsg = outputMsg.replace('\n', ' ');
	// }
	// panic(outputMsg);
	// }
	//
	// protected static void panic(String msg) {
	// rawError(msg);
	// panic();
	// }
}
