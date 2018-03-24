package net.certiv.adept.test;

import static org.testng.Assert.*;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import net.certiv.adept.Tool;
import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.ISourceParser;
import net.certiv.adept.model.Feature;
import net.certiv.adept.util.Log;

public class CorpusTest {

	private static final String corpusRoot = "corpus";
	private CoreMgr mgr;
	@SuppressWarnings("unused") private ISourceParser lang;
	@SuppressWarnings("unused") private List<Feature> features;

	@BeforeClass
	public void setup() {
		Tool tool = new Tool();
		tool.setCorpusRoot(corpusRoot);
		tool.setLang("antlr");
		tool.setTabWidth(4);
		tool.setRebuild(true);

		boolean ok = tool.loadResources();
		ok = ok && tool.validateOptions();

		if (!ok) {
			Log.error(this, "Failed to initialize model");
			return;
		}

		mgr = tool.getMgr();
		lang = mgr.getLanguageParser();
		features = mgr.getCorpusModel().getCorpusFeatures();
	}

	@Test
	public void testCoord() {
		assertEquals(false, false);
	}
}
