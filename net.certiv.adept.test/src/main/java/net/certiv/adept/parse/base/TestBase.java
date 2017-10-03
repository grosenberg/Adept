/* Copyright � 2015 Gerald Rosenberg.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the License.md file.
 */
package net.certiv.adept.parse.base;

import java.io.File;
import java.io.IOException;

public abstract class TestBase {

	/** Platform dependent end-of-line marker */
	public static final String Eol = System.lineSeparator();

	public static final String DataDir = "test.snippets";
	private static final String ResultDir = "test.expected";
	private static final String ResultExt = "Result.txt";

	public TestBase() {
		super();
	}

	public String getDataDir() {
		return DataDir;
	}

	public abstract String getBaseDir();

	public abstract String getSnippetExt();

	public String readSrcString(String name) {
		return readString(DataDir, name, getSnippetExt());
	}

	/**
	 * Reads the expected parser results from disk. If no file is found, writes the given data to
	 * the parser results file.
	 * 
	 * @param name the name of the snippet
	 * @param data the data to write
	 * @return the data read from the snippet parser results file
	 */
	public String readResultsString(String name, String data) {
		return readStringExt(name, data, ResultExt);
	}

	private String readStringExt(String name, String data, String ext) {
		String expecting = readString(ResultDir, name, ext);
		if (expecting.length() == 0) {
			writeString(ResultDir, name, data, ext);
		}
		return expecting;
	}

	private String readString(String dir, String name, String ext) {
		name = convertName(dir, name, ext);
		File f = new File(name);
		String data = "";
		if (f.isFile()) {
			try {
				data = TestUtils.read(f);
			} catch (IOException e) {
				System.err.println("Read failed: " + e.getMessage());
			}
		}
		return data;
	}

	private void writeString(String dir, String name, String data, String ext) {
		name = convertName(dir, name, ext);
		File f = new File(name);
		File p = f.getParentFile();
		if (!p.exists()) {
			if (!p.mkdirs()) {
				System.err.println("Failed to create directory: " + p.getAbsolutePath());
				return;
			}
		} else if (p.isFile()) {
			System.err.println("Cannot make directory: " + p.getAbsolutePath());
			return;
		}

		if (f.exists() && f.isFile()) {
			f.delete();
		}
		try {
			TestUtils.write(f, data, false);
		} catch (IOException e) {
			System.err.println("Write failed: " + e.getMessage());
		}
	}

	protected String convertName(String dir, String name, String ext) {
		int idx = name.lastIndexOf('.');
		if (idx > 0) {
			name = name.substring(0, idx);
		}
		return TestUtils.concat(getBaseDir(), dir, name + ext);
	}
}
