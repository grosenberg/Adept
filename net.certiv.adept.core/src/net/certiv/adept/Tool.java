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
			new Options("tabWidth", "-t", OptionType.INT, "width of a tab"),
			new Options("verbose", "-v", OptionType.STRING, "verbosity (one of 'quiet', 'info', 'warn', 'error')") //
	};

	// fields set by option manager
	public boolean backup = true;
	public boolean check;
	public boolean format = true;
	public boolean learn;
	public boolean rebuild;
	public boolean save;

	public String corpusRoot;
	public String lang;
	public String output;
	public int tabWidth; 		// in the source document and for the formatter
	public int corpusTabWidth;	// in the corpus documents
	public String verbose;

	public Path corpusDir;		// root dir + lang
	public String corpusExt;	// lang extention type

	// fields set by init
	public static CoreMgr mgr; // holds the corpus model and doc document model
	public static Settings settings;
	private String version;
	private List<String> sourceFiles;
	private List<LangDescriptor> languages;
	private List<Document> documents;

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
	}

	/** Retrieves the doc objects produced through execution, or null */
	public List<Document> getDocuments() {
		return documents;
	}

	/** Provides the set of doc pathnames of the files to be processed */
	public void setSourceFiles(List<String> pathnames) {
		this.sourceFiles = pathnames;
	}

	/** Provides the set of doc pathnames of the files to be processed */
	public void setSourceFiles(String... pathnames) {
		this.sourceFiles = Arrays.asList(pathnames);
	}

	/** Sets whether to only perform a parse evaluation of the documents */
	public void setCheck(boolean check) {
		this.check = check;
	}

	/** Sets whether to create a backup file of a doc being saved */
	public void setBackup(boolean backup) {
		this.backup = backup;
	}

	/** Sets whether to apply the model to the doc to create a format modified document */
	public void setFormat(boolean format) {
		this.format = format;
	}

	/** Sets whether to add the current model to the training, and doc document to the corpus */
	public void setLearn(boolean learn) {
		this.learn = learn;
	}

	/** Sets whether to save a formated document */
	public void setSave(boolean save) {
		this.save = save;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	/** Sets the pathname of the directory containing the corpus root */
	public void setCorpusRoot(String corpusRoot) {
		this.corpusRoot = corpusRoot;
	}

	public Path getCorpusDir() {
		return corpusDir;
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

	public void setRebuild(boolean rebuild) {
		this.rebuild = rebuild;
	}

	public void setTabWidth(int tabWidth) {
		this.tabWidth = tabWidth;
	}

	public void setVerbose(Level level) {
		this.verbose = level.toString();
	}

	/** Sets the verbosity level of the tool */
	public void setVerbose(String verbose) {
		this.verbose = verbose;
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
		if (lang == null) {
			errMgr.toolError(ErrorType.INVALID_CMDLINE_ARG, "Must specify the language type");
			return false;
		}

		boolean found = false;
		for (LangDescriptor language : languages) {
			if (language.name.equals(lang)) {
				found = true;
				corpusExt = language.ext;
				if (corpusRoot == null) corpusRoot = language.corpusRoot;
				corpusTabWidth = language.tabWidth;
				if (tabWidth == 0) tabWidth = corpusTabWidth;
				break;
			}
		}

		if (!found) {
			errMgr.toolError(ErrorType.INVALID_CMDLINE_ARG, "Unrecognized language type: " + lang);
			return false;
		}

		if (tabWidth < 1) tabWidth = 4;
		if (tabWidth > 10) tabWidth = 4;

		corpusDir = Paths.get(corpusRoot, lang);
		File corpusFile = corpusDir.toFile();
		if (!corpusFile.exists()) {
			corpusFile.mkdirs();
		}
		if (corpusFile.isFile()) {
			errMgr.toolError(ErrorType.INVALID_CMDLINE_ARG, "Model directory cannot be a file");
			return false;
		}

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
			} catch (Exception e) {
				errMgr.toolError(ErrorType.CONFIG_FAILURE, "Failed reading output settings (" + e.getMessage() + ")");
				return false;
			}
		}

		mgr = new CoreMgr(lang);
		try {
			mgr.initialize(corpusDir, corpusExt, corpusTabWidth, rebuild);
		} catch (Exception e) {
			Log.error(this, ErrorType.MODEL_BUILD_FAILURE.msg, e);
			errMgr.toolError(ErrorType.MODEL_BUILD_FAILURE, e, "Failed to create model manager");
			return false;
		}

		if (verbose != null) {
			try {
				getDefaultListener().setLevel(Level.valueOf(verbose.trim().toUpperCase()));
			} catch (IllegalArgumentException e) {
				Log.error(this, ErrorType.INVALID_VERBOSE_LEVEL.msg + ": " + verbose);
				errMgr.toolError(ErrorType.INVALID_VERBOSE_LEVEL, verbose);
			}
		}

		return true;
	}

	// FIX: partial duplicate of CoreMgr#rebuild
	public void execute() {
		documents = loadDocuments(sourceFiles);
		Log.info(this, documents.size() + " source documents to process.");
		for (Document doc : documents) {
			if (learn) {	// add document model to corpus model
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

			try {
				parser.annotateFeatures(collector);
			} catch (Exception e) {
				Log.error(this, ErrorType.VISITOR_FAILURE.msg + ": " + doc.getPathname());
				errMgr.toolError(ErrorType.VISITOR_FAILURE, e, doc.getPathname());
				continue;
			}

			collector.annotateComments();
			collector.index();
			collector.genLocalEdges();

			mgr.createDocModel(collector);

			if (check) continue;

			try {			// compare document model to corpus model
				mgr.evaluate();
			} catch (Exception e) {
				Log.error(this, ErrorType.MODEL_BUILD_FAILURE.msg + ": " + doc.getPathname(), e);
				errMgr.toolError(ErrorType.MODEL_BUILD_FAILURE, e, doc.getPathname());
				continue;
			}

			if (format) {
				mgr.apply();
				if (save) doc.saveModified(backup);
			}
		}
	}

	public String getFormatted() {
		return mgr.getDocModel().getDocument().getModified();
	}

	private List<Document> loadDocuments(List<String> filenames) {
		List<Document> documents = new ArrayList<>();
		for (String fileName : filenames) {
			try {
				Document document = loadDocument(fileName);
				if (document == null) continue; // came back as error
				documents.add(document);
			} catch (IOException e) {
				errMgr.toolError(ErrorType.CANNOT_OPEN_FILE, e, fileName);
			}
		}
		return documents;
	}

	private Document loadDocument(String filename) throws IOException {
		info("Parsing " + filename);

		File file = new File(filename);
		if (!file.exists()) {
			throw new IOException("doc does not exist: " + filename);
		}

		byte[] bytes = Files.readAllBytes(file.toPath());
		return new Document(filename, tabWidth, new String(bytes, StandardCharsets.UTF_8));
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
