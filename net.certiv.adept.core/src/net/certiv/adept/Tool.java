package net.certiv.adept;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.antlr.v4.runtime.RecognitionException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.core.PerfData;
import net.certiv.adept.model.Document;
import net.certiv.adept.parser.Collector;
import net.certiv.adept.parser.ISourceParser;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.tool.LangDescriptor;
import net.certiv.adept.tool.Level;
import net.certiv.adept.tool.Options;
import net.certiv.adept.tool.Options.OptionType;
import net.certiv.adept.tool.ToolBase;
import net.certiv.adept.util.Log;

public class Tool extends ToolBase {

	private static Options[] optionDefs = { //
			new Options("backup", "-b", OptionType.BOOL, "create doc backup"),
			new Options("check", "-c", OptionType.BOOL, "check doc for suitability to format"),
			new Options("format", "-f", OptionType.BOOL, "format doc (default)"),
			new Options("learn", "-l", OptionType.BOOL, "add doc to corpus training"),
			new Options("rebuild", "-r", OptionType.BOOL, "force rebuild of the corpus model"),
			new Options("save", "-s", OptionType.BOOL, "save formatted doc to file"),

			new Options("corpusRoot", "-d", OptionType.STRING, "root corpus directory"),
			new Options("lang", "-g", OptionType.STRING, "language type"),
			new Options("output", "-e", OptionType.STRING, "formatted output settings"),
			new Options("verbose", "-v", OptionType.STRING, "verbosity (one of 'quiet', 'info', 'warn', 'error')"),
			new Options("tabWidth", "-w", OptionType.INT, "width of a tab"),
			//
			// new Options("trust", "-t", OptionType.INT, "formatter confidence threshold (1-6;
			// min=1; default=3"),
			//
	};

	// fields set by option manager
	private Boolean backup;
	private Boolean check;
	private Boolean format;
	private Boolean learn;
	private Boolean rebuild;
	private Boolean save;

	private String corpusRoot;
	private String lang;
	private String output;
	private String verbose;
	private Integer tabWidth;

	// fields set by init/validate
	public static Settings settings;
	public static CoreMgr mgr; 	// holds the corpus model and doc models

	private String version;
	private List<String> sourceFiles;
	private List<LangDescriptor> languages;
	private List<Document> documents;
	private int corpusTabWidth;
	private Path corpusDir;		// root dir + lang
	private String corpusExt;	// lang extention type

	public static void main(String[] args) {
		Tool adept = new Tool();
		if (args.length == 0) {
			adept.help();
			adept.exit(0);
		}
		boolean ok = adept.processFlags(args);
		ok = ok && adept.validateOptions();
		if (!ok) {
			adept.exit(1);
		}
		adept.execute();
		adept.exit(0);
	}

	/**
	 * Creates an embedded instance of the tool configured using command-line styled arguments. The
	 * options are validated and, if valid, the tool is executed.
	 * 
	 * @param args command-line styled arguments
	 */
	public Tool(String[] args) {
		this();
		boolean ok = processFlags(args);
		ok = ok && validateOptions();
		if (ok) execute();
	}

	/**
	 * Creates an embedded instance of the tool, pending configuration. To use
	 * <ul>
	 * <li>call setters to configure
	 * <li>call loadResources()
	 * <li>call validateOptions()
	 * <li>call execute()
	 * <li>call getSources() to retrieve the results, if generated.
	 * </ul>
	 */
	public Tool() {
		super();
		mgr = new CoreMgr();
	}

	/**
	 * Configure the tool using command-line styled arguments.
	 * 
	 * @param args command-line styled arguments
	 * @return true iff all of the command-line styled arguments are recognized
	 */
	public boolean processFlags(String[] args) {
		boolean ok = loadResources();
		ok = ok && Options.process(this, optionDefs, args);
		if (ok) sourceFiles = Options.getRemainder();
		return ok;
	}

	public boolean loadResources() {
		ClassLoader cl = this.getClass().getClassLoader();

		try (InputStream in = cl.getResourceAsStream("adept.properties")) {
			Properties prop = new Properties();
			prop.load(in);
			version = (String) prop.get("version");
		} catch (IOException e) {
			errMgr.toolError(ErrorType.CONFIG_FAILURE, "Failed reading version property (" + e.getMessage() + ")");
			return false;
		}

		try (Reader reader = new InputStreamReader(cl.getResourceAsStream("languages.json"), "UTF-8")) {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			Type collection = new TypeToken<Collection<LangDescriptor>>() {}.getType();
			languages = gson.fromJson(reader, collection);
		} catch (Exception e) {
			errMgr.toolError(ErrorType.CONFIG_FAILURE, "Failed reading lang descriptors (" + e.getMessage() + ")");
			return false;
		}
		return true;
	}

	public boolean validateOptions() {
		// ---- settings & output ----

		if (!chkSettings()) return false;

		// ---- lang & corpusRoot ----

		if (corpusRoot != null) {
			settings.corpusRoot = corpusRoot;
		}

		if (lang == null && settings.lang == null) {
			errMgr.toolError(ErrorType.INVALID_CMDLINE_ARG, "Must specify the language type");
			return false;
		} else if (lang != null) {
			settings.lang = lang;
		}

		boolean found = false;
		for (LangDescriptor language : languages) {
			if (language.name.equals(settings.lang)) {
				found = true;
				corpusExt = language.ext;
				if (settings.corpusRoot == null) settings.corpusRoot = language.corpusRoot;
				corpusTabWidth = language.tabWidth;
				break;
			}
		}

		if (!found) {
			errMgr.toolError(ErrorType.INVALID_CMDLINE_ARG, "Unrecognized language type: " + settings.lang);
			return false;
		}

		if (settings.corpusRoot == null) {
			errMgr.toolError(ErrorType.INVALID_CMDLINE_ARG, "Must specify a corpus root directory.");
			return false;
		}

		corpusDir = Paths.get(settings.corpusRoot, settings.lang);
		File corpusFile = corpusDir.toFile();
		if (!corpusFile.exists()) {
			corpusFile.mkdirs();
		}
		if (corpusFile.isFile()) {
			errMgr.toolError(ErrorType.INVALID_CMDLINE_ARG, "Model directory cannot be a file");
			return false;
		}

		// ---- backup ----

		if (backup == null && settings.backup == null) {
			settings.backup = true;
		} else if (backup != null) {
			settings.backup = backup;
		}

		// ---- check ----

		if (check == null && settings.check == null) {
			settings.check = false;
		} else if (check != null) {
			settings.check = check;
		}

		// ---- format ----

		if (format == null && settings.format == null) {
			settings.format = true;
		} else if (format != null) {
			settings.format = format;
		}

		// ---- learn ----

		if (learn == null && settings.learn == null) {
			settings.learn = false;
		} else if (learn != null) {
			settings.learn = learn;
		}

		// ---- rebuild ----

		if (rebuild == null && settings.rebuild == null) {
			settings.rebuild = false;
		} else if (rebuild != null) {
			settings.rebuild = rebuild;
		}

		// ---- save ----

		if (save == null && settings.save == null) {
			settings.save = false;
		} else if (save != null) {
			settings.save = save;
		}

		// ---- tabWidth ----

		if (tabWidth == null && settings.tabWidth == null) {
			settings.tabWidth = 4;
		} else if (tabWidth != null) {
			settings.tabWidth = tabWidth;
		}
		if (settings.tabWidth < 1 || settings.tabWidth > 10) settings.tabWidth = 4;

		// ---- verbose ----

		if (verbose == null && settings.verbose == null) {
			settings.verbose = "warn";
		} else if (verbose != null) {
			settings.verbose = verbose;
		}

		try {
			getDefaultListener().setLevel(Level.valueOf(settings.verbose.trim().toUpperCase()));
		} catch (IllegalArgumentException e) {
			Log.error(this, ErrorType.INVALID_VERBOSE_LEVEL.msg + ": " + settings.verbose);
			errMgr.toolError(ErrorType.INVALID_VERBOSE_LEVEL, settings.verbose);
		}

		// ---- init core manager ----

		try {
			boolean ok = mgr.initialize(settings.lang, corpusDir, corpusExt, corpusTabWidth, settings.rebuild);
			if (!ok) return false;
		} catch (Exception e) {
			Log.error(this, ErrorType.MODEL_BUILD_FAILURE.msg, e);
			errMgr.toolError(ErrorType.MODEL_BUILD_FAILURE, e, "Failed to create model manager");
			return false;
		}

		return true;
	}

	public void execute() {
		documents = loadDocuments(sourceFiles);
		Log.info(this, documents.size() + " source documents to process.");
		for (Document doc : documents) {
			if (settings.learn) {	// add document model to corpus model
				mgr.update(corpusDir, doc);
				continue;
			}

			ISourceParser parser = mgr.getLanguageParser();
			Collector collector = new Collector(doc);
			try {
				parser.process(collector, doc);
			} catch (RecognitionException e) {
				Log.error(this, ErrorType.PARSE_ERROR.msg + ": " + doc.getPathname());
				errMgr.toolError(ErrorType.PARSE_ERROR, doc.getPathname());
				continue;
			} catch (Exception e) {
				Log.error(this, ErrorType.PARSE_FAILURE.msg + ": " + doc.getPathname());
				errMgr.toolError(ErrorType.PARSE_FAILURE, e, doc.getPathname());
				continue;
			}

			if (settings.check) continue;

			try {
				parser.annotateFeatures(collector);
			} catch (Exception e) {
				Log.error(this, ErrorType.VISITOR_FAILURE.msg + ": " + doc.getPathname());
				errMgr.toolError(ErrorType.VISITOR_FAILURE, e, doc.getPathname());
				continue;
			}

			collector.annotateComments();
			collector.genLocalEdges();
			mgr.createDocModel(collector);

			try {			// compare document model to corpus model
				mgr.evaluate();
			} catch (Exception e) {
				Log.error(this, ErrorType.MODEL_BUILD_FAILURE.msg + ": " + doc.getPathname(), e);
				errMgr.toolError(ErrorType.MODEL_BUILD_FAILURE, e, doc.getPathname());
				continue;
			}

			if (settings.format) {
				mgr.apply();
				if (settings.save) doc.saveModified(settings.backup);
			}
		}
	}

	private List<Document> loadDocuments(List<String> filenames) {
		List<Document> documents = new ArrayList<>();
		if (filenames != null) {
			for (String fileName : filenames) {
				try {
					Document document = loadDocument(fileName);
					if (document == null) continue; // came back as error
					documents.add(document);
				} catch (IOException e) {
					errMgr.toolError(ErrorType.CANNOT_OPEN_FILE, e, fileName);
				}
			}
		}
		mgr.perfData.setSize(documents.size());
		return documents;
	}

	private Document loadDocument(String filename) throws IOException {
		info("Parsing " + filename);

		File file = new File(filename);
		if (!file.exists()) {
			throw new IOException("doc does not exist: " + filename);
		}

		byte[] bytes = Files.readAllBytes(file.toPath());
		return new Document(filename, settings.tabWidth, new String(bytes, StandardCharsets.UTF_8));
	}

	public CoreMgr getMgr() {
		return mgr;
	}

	/** Retrieves the doc objects produced through execution, or null */
	public List<Document> getDocuments() {
		return documents;
	}

	public String getFormatted() {
		return mgr.getDocModel().getDocument().getModified();
	}

	/** Returns the currently collected performance data */
	public PerfData getPerfData() {
		return mgr.perfData;
	}

	public Path getCorpusDir() {
		return corpusDir;
	}

	/** Sets whether to create a backup file of a doc being saved */
	public void setBackup(boolean backup) {
		chkSettings();
		settings.backup = backup;
	}

	/** Sets whether to only perform a parse evaluation of the documents */
	public void setCheck(boolean check) {
		chkSettings();
		settings.check = check;
	}

	/** Sets the pathname of the directory containing the corpus root */
	public void setCorpusRoot(String corpusRoot) {
		chkSettings();
		settings.corpusRoot = corpusRoot;
	}

	/** Sets whether to apply the model to the doc to create a format modified document */
	public void setFormat(boolean format) {
		chkSettings();
		settings.format = format;
	}

	public void setLang(String lang) {
		chkSettings();
		settings.lang = lang;
	}

	/** Sets whether to add the current model to the training, and doc document to the corpus */
	public void setLearn(boolean learn) {
		chkSettings();
		settings.learn = learn;
	}

	public void setRebuild(boolean rebuild) {
		chkSettings();
		settings.rebuild = rebuild;
	}

	/** Sets whether to save a formated document */
	public void setSave(boolean save) {
		chkSettings();
		settings.save = save;
	}

	/** Sets the settings value object defining the formatted output settings. */
	public void setSettings(Settings obj) {
		settings = obj;
		output = null;
	}

	/** Sets the pathname of the file (**.json) containing the formatted output settings. */
	public void setSettings(String pathname) {
		output = pathname;
	}

	/** Provides the set of doc pathnames of the files to be processed */
	public void setSourceFiles(String... pathnames) {
		sourceFiles = Arrays.asList(pathnames);
	}

	/** Provides the set of doc pathnames of the files to be processed */
	public void setSourceFiles(List<String> pathnames) {
		sourceFiles = pathnames;
	}

	public void setTabWidth(int tabWidth) {
		chkSettings();
		settings.tabWidth = tabWidth;
	}

	public void setVerbose(Level level) {
		chkSettings();
		settings.verbose = level.toString();
	}

	/** Sets the verbosity level of the tool */
	public void setVerbose(String verbose) {
		chkSettings();
		settings.verbose = verbose;
	}

	private boolean chkSettings() {
		if (settings == null) {
			if (output != null && !output.endsWith(".json")) {
				output += ".json";
			}

			ClassLoader cl = this.getClass().getClassLoader();
			try (Reader reader = output != null ? Files.newBufferedReader(Paths.get(output))
					: new InputStreamReader(cl.getResourceAsStream("settings.json"), "UTF-8")) {
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();
				settings = gson.fromJson(reader, Settings.class);
				if (output != null) {
					settings.output = output;
				}
			} catch (Exception e) {
				errMgr.toolError(ErrorType.CONFIG_FAILURE, "Failed reading output settings (" + e.getMessage() + ")");
				settings = new Settings();
				return false;
			}
		}
		return true;
	}

	public void help() {
		version();
		for (Options o : optionDefs) {
			String name = o.name + (o.argType != OptionType.BOOL ? " ___" : "");
			String s = String.format(" %-19s %s", name, o.description);
			info(s);
		}
	}

	@Override
	public void version() {
		info("Adept Version " + version);
	}

	public void exit(int e) {
		System.exit(e);
	}
}
