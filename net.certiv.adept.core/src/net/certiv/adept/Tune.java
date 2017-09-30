package net.certiv.adept;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.load.Config;
import net.certiv.adept.model.tune.Boosts;
import net.certiv.adept.model.tune.Fitter;
import net.certiv.adept.model.util.Align;
import net.certiv.adept.model.util.Factor;
import net.certiv.adept.tool.ErrorManager;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.tool.ITool;
import net.certiv.adept.tool.LangDescriptor;
import net.certiv.adept.tool.Level;
import net.certiv.adept.tool.Options;
import net.certiv.adept.tool.Options.OptionType;
import net.certiv.adept.util.Log;

/**
 * Performs hyperparameter optimization of the boost factor parameters using latin hypercube
 * parameter set generation. Characterization of the candidate boost factor sets is performed using
 * a leave-one-out cross-validation strategy utilizing the documents of the existing corpus.
 */
public class Tune implements ITool {

	public static final int DIMS = Factor.values().length;

	private Tool tool;
	private ProcessMgr mgr;

	private static Options[] optionDefs = { //
			new Options("corpusRoot", "-d", OptionType.STRING, "root corpus directory"),
			new Options("lang", "-g", OptionType.STRING, "language type"),
			new Options("samples", "-s", OptionType.INT, "number of samples per factor"), //
	};

	private String corpusRoot;
	private String lang;
	private Integer samples;

	private List<LangDescriptor> languages;
	private Path corpusDir;
	private String corpusExt;
	private Integer tabWidth;

	public static void main(String[] args) {
		new Tune(args);
	}

	public Tune(String[] args) {
		tool = new Tool();
		if (args.length == 0 || !Options.process(this, optionDefs, args) || !validate()) {
			help();
			exit(-1);
		}

		tune();
		exit(0);
	}

	private boolean validate() {
		if (corpusRoot == null) return false;
		if (lang == null) return false;
		corpusDir = Paths.get(corpusRoot, lang);

		languages = Config.loadLanguages();
		if (languages == null) return false;

		boolean found = false;
		for (LangDescriptor language : languages) {
			if (language.name.equals(lang)) {
				found = true;
				corpusExt = language.ext;
				if (tabWidth == null) tabWidth = language.tabWidth;
				break;
			}
		}
		if (!found) {
			tool.getErrMgr().toolError(ErrorType.INVALID_CMDLINE_ARG, "Unrecognized language type: " + lang);
			return false;
		}

		if (samples == null || samples < 1 || samples > 100) {
			samples = 20;
		}

		return true;
	}

	void tune() {
		List<String> names = null;
		try {
			names = Config.getContainedFilenames(corpusDir, corpusExt);
		} catch (IOException e) {
			tool.getErrMgr().toolError(ErrorType.CANNOT_READ_FILE, e.getMessage());
			exit(-1);
		}

		Fitter fitter = new Fitter(samples);
		for (String name : names) {
			ArrayList<String> remainder = new ArrayList<>(names);
			remainder.remove(name);
			tune(fitter, name, remainder);
		}
		Boosts fit = fitter.bestFit();
		double ftn = fitter.bestFittness();
		Log.debug(this, String.format("Best Fit: %01.4f : %s", ftn, fit));

		mgr = tool.getMgr();
		CorpusModel model = mgr.getCorpusModel();

		// now, preserve results
		model.setBoosts(fit);
		try {
			model.save(corpusDir); // only save the model root
		} catch (Exception e) {
			tool.getErrMgr().toolError(ErrorType.CANNOT_WRITE_FILE, e.getMessage());
			exit(-1);
		}
	}

	// execute for each sample configuration
	private void tune(Fitter fitter, String name, List<String> remainder) {
		for (int idx = 0; idx < samples; idx++) {
			Boosts boosts = fitter.getBoosts(idx);
			Tool tool = createTool(name, remainder, boosts, true/* idx == 0 */);
			tool.execute();

			String source = tool.getMgr().getDocModel().getDocument().getContent();
			double fitness = Align.similarity(source, tool.getFormatted());
			fitter.put(fitness, boosts);
			Log.debug(this, String.format("%2d %01.9f : %s", idx, fitness, boosts));
		}
	}

	private Tool createTool(String srcName, List<String> corpusNames, Boosts boosts, boolean force) {
		Tool tool = new Tool();
		tool.setCorpusRoot(corpusRoot);
		tool.setLang(lang);
		tool.setTabWidth(tabWidth);
		tool.setSave(false);
		tool.setRebuild(force);
		tool.setVerbose(Level.QUIET);

		tool.setSourceFiles(srcName);
		tool.setCorpusFiles(corpusNames);

		boolean ok = tool.loadResources();
		ok = ok && tool.validateOptions();

		if (!ok) {
			Log.error(this, "Failed to initialize test tool.");
			return null;
		}

		tool.getMgr().setBoosts(boosts);
		return tool;
	}

	@Override
	public ErrorManager getErrMgr() {
		return tool.getErrMgr();
	}

	public void help() {
		for (Options o : optionDefs) {
			String name = o.name + (o.argType != OptionType.BOOL ? " ___" : "");
			String s = String.format(" %-19s %s", name, o.description);
			tool.info(s);
		}
	}

	public void exit(int e) {
		System.exit(e);
	}
}
