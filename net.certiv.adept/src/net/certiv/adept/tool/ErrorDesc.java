/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.tool;

public enum ErrorDesc {

	INFO(-1, "", ErrorKind.INFO),

	// ---

	DIR_NOT_FOUND(1, "directory not found: <arg>", ErrorKind.ERROR),

	DIR_IS_FILE(2, "directory is a file: <arg>", ErrorKind.ERROR),

	CANNOT_OPEN_FILE(3, "cannot find or open file: <arg><if(exception&&verbose)>; reason: <exception><endif>",
			ErrorKind.ERROR),

	CANNOT_READ_FILE(4, "cannot read file <arg>: <arg2>", ErrorKind.ERROR),

	CANNOT_WRITE_FILE(5, "cannot write file <arg>: <arg2>", ErrorKind.ERROR),

	INTERNAL_ERROR(20,
			"internal error: <arg> <arg2><if(exception&&verbose)>: <exception>"
					+ "<stackTrace; separator=\"\\n\"><endif>",
			ErrorKind.ERROR),

	// ---

	SYNTAX_ERROR(50, "syntax error: <arg>", ErrorKind.ERROR),

	// ---

	INVALID_CMDLINE_ARG(100, "unknown command-line option <arg>", ErrorKind.ERROR),
	CONFIG_FAILURE(101, "Configuration failure", ErrorKind.ERROR),
	INVALID_VERBOSE_LEVEL(103, "Invalid verbosity verbose: <arg>", ErrorKind.ERROR),

	// ---

	PARSE_FAILURE(200, "Failed to parse: <arg>", ErrorKind.ERROR),
	PARSE_ERROR(201, "Error in parse: <arg>", ErrorKind.ERROR),
	VISITOR_FAILURE(230, "Failed to walk: <arg>", ErrorKind.ERROR),
	VISITOR_ERROR(231, "Error in visitor: <arg>", ErrorKind.ERROR),

	// ---

	MODEL_BUILD_FAILURE(300, "Failed model build: <arg>", ErrorKind.ERROR),
	MODEL_LOAD_FAILURE(301, "Failed model load: <arg>", ErrorKind.ERROR),
	MODEL_SAVE_FAILURE(302, "Failed model save: <arg>", ErrorKind.ERROR),
	MODEL_VALIDATE_FAILURE(303, "Model validation failed: <arg>", ErrorKind.ERROR),

	// ---

	;

	public final String msg;
	public final int code;
	public final ErrorKind kind;

	ErrorDesc(int code, String msg, ErrorKind kind) {
		this.code = code;
		this.msg = msg;
		this.kind = kind;
	}
}
