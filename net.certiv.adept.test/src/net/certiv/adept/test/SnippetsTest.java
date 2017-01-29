/* Copyright � 2015-2016 Gerald Rosenberg.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the License.md file.
 */
package net.certiv.adept.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import net.certiv.adept.Tool;
import net.certiv.adept.test.base.TestBase;
import net.certiv.adept.test.base.TestUtils;
import net.certiv.adept.tool.Level;

public class SnippetsTest extends TestBase {

	// ------------------------------------------------------------------------
	// Custom configuration area - change as needed ---------------------------

	/** Base directory for snippet data files and results */
	public static final String BasesDir = "D:/DevFiles/Eclipse/Adept/net.certiv.adept.test";
	/** File extension for snippet data */
	public static final String Ext = ".g4";
	/** If true, enables echoing to console */
	public static final boolean Echo = true;
	/** If true, echoes a system result listing */
	public static final boolean SysOut = true;

	// ------------------------------------------------------------------------
	// Standard per-method configuration - change as desired ------------------

	@BeforeMethod
	public void setUp() throws Exception {
		// Log.setTestMode(true); // stop logger noise
	}

	@AfterMethod
	public void teadDown() throws Exception {}

	// ------------------------------------------------------------------------
	// Standard Data Provider Method - do not change --------------------------

	@DataProvider(name = "srcFilenames")
	public Object[][] listFilenames() {
		Object[][] data = new Object[][] {};
		String dir = TestUtils.concat(getBaseDir(), getDataDir());
		File d = new File(dir);
		if (d.isDirectory()) {
			Collection<File> files = TestUtils.listFiles(d, new String[] { Ext }, 1);
			if (files != null) {
				data = new Object[files.size()][1];
				for (int idx = 0; idx < files.size(); idx++) {
					File f = ((List<File>) files).get(idx);
					String rel = TestUtils.relative(d, f);
					data[idx][0] = TestUtils.changeExtent(rel, "");
				}
			}
		}
		return data;
	}

	// ------------------------------------------------------------------------
	// Actual Snippet Test Method ---------------------------------------------

	@Test(dataProvider = "srcFilenames")
	public void testResult(String name) throws Exception {
		List<String> srcFiles = new ArrayList<>();
		String pName = convertName(DataDir, name, Ext);
		srcFiles.add(pName);

		Tool tool = new Tool();
		tool.setSourceFiles(srcFiles);
		tool.setLang("antlr");
		tool.setCorpusRoot("D:/DevFiles/Eclipse/Adept/net.certiv.adept.core/corpus");
		tool.setTabWidth(4);
		tool.setVerbose(Level.QUIET);

		tool.setBackup(false);
		tool.setFormat(true);
		tool.setSave(false);

		tool.loadResources();
		tool.validateOptions();
		tool.execute();

		String found = tool.getFormatted();
		String expecting = readResultsString(name, found);
		Assert.assertEquals(found, expecting);
	}

	// ------------------------------------------------------------------------
	// Required Utility Methods - do not change -------------------------------

	@Override
	public String getBaseDir() {
		return BasesDir;
	}

	@Override
	public String getSnippetExt() {
		return Ext;
	}
}
