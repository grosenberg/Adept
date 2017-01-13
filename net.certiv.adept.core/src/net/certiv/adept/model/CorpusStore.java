package net.certiv.adept.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;

import net.certiv.adept.Tool;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.util.Log;

public abstract class CorpusStore {

	private static final String MODEL = "CorpusModel";
	private static final String DATA = "CorpusData";
	private static final String DOT = ".";
	private static final String EXT = "json";

	public CorpusStore() {}

	public List<Document> read(Path corpusDir, String ext, int tabWidth) {
		List<Document> documents = new ArrayList<>();
		List<File> files;
		try {
			files = Files.walk(corpusDir) //
					.filter(Files::isRegularFile) //
					.filter(p -> p.getFileName().toString().endsWith(DOT + ext)) //
					.map(Path::toFile) //
					.collect(Collectors.toList());
		} catch (IOException e) {
			return documents;
		}

		for (File file : files) {
			try {
				byte[] bytes = Files.readAllBytes(file.toPath());
				String content = new String(bytes, StandardCharsets.UTF_8);
				documents.add(new Document(file.getPath(), tabWidth, content));
			} catch (IOException e) {
				Log.error(this, "Failed to read corpus file" + file.getPath() + ": " + e.getMessage());
			}
		}

		return documents;
	}

	void write(Path corpusDir, List<Document> docs) {
		for (Document doc : docs) {
			write(corpusDir, doc);
		}
	}

	void write(Path corpusDir, Document doc) {
		Path path = Paths.get(doc.getPathname()).getFileName();
		path = corpusDir.resolve(path);

		try {
			Files.write(path, doc.getContent().getBytes());
		} catch (IOException e) {
			Log.error(this, "Failed to write corpus file" + path.toString() + ": " + e.getMessage());
		}
	}

	/**
	 * Load the corpus model from persistent store or initialize
	 * 
	 * @param corpusDir directory to save to
	 * @param rebuild
	 * @return the corpus model
	 * @throws Exception if a file cannot found or read
	 */
	public static CorpusModel load(Path corpusDir, boolean rebuild) throws Exception {
		Path path = corpusDir.resolve(MODEL + DOT + EXT);
		if (rebuild || !Files.isRegularFile(path)) return new CorpusModel(corpusDir);

		CorpusModel model;
		Gson gson = configBuilder();
		try {
			Log.debug(CorpusStore.class, "Loading " + path.toString());
			byte[] bytes = Files.readAllBytes(path);
			model = gson.fromJson(new String(bytes, StandardCharsets.UTF_8), CorpusModel.class);
			model.setCorpusDir(corpusDir);
		} catch (IOException | JsonSyntaxException e) {
			Log.error(CorpusStore.class, "Failed loading corpus model file " + path.toString() + ": " + e.getMessage());
			throw e;
		}

		List<Path> paths = getDataFiles(corpusDir);
		for (Path dPath : paths) {
			try {
				Log.debug(CorpusStore.class, "Loading " + dPath.toString());
				byte[] bytes = Files.readAllBytes(dPath);
				Features features = gson.fromJson(new String(bytes, StandardCharsets.UTF_8), Features.class);
				features.fixEdgeRefs();
				model.addAll(features);
			} catch (IOException | JsonSyntaxException e) {
				Log.error(CorpusStore.class,
						"Failed loading corpus data file " + path.toString() + ": " + e.getMessage());
				throw e;
			}
		}
		return model;
	}

	private static List<Path> getDataFiles(Path corpusDir) {
		try {
			List<Path> paths = Files.walk(corpusDir, 1) //
					.filter(Files::isRegularFile) //
					.filter(p -> p.getFileName().toString().startsWith(DATA)) //
					.filter(p -> p.getFileName().toString().endsWith(DOT + EXT)) //
					.collect(Collectors.toList());
			return paths;
		} catch (IOException e) {
			Tool.errMgr.toolError(ErrorType.CANNOT_OPEN_FILE, "Failed reading Data file names");
		}
		return Collections.emptyList();
	}

	/**
	 * Saves the corpus model to a Json file in the given directory. Overwrites any existing file.
	 * Saves in a non-compact Facet.
	 * 
	 * @param corpusDir directory to save to
	 * @throws Exception if an existing file cannot be overwritten
	 */
	void save(Path corpusDir) throws Exception {
		Gson gson = configBuilder();

		Path path = corpusDir.resolve(MODEL + DOT + EXT);
		JsonWriter writer = configWriter(Files.newOutputStream(path));
		try {
			Log.debug(this, "Saving " + path.toString());
			gson.toJson(this, CorpusModel.class, writer);
			writer.close();
		} catch (IOException | JsonSyntaxException e) {
			Log.error(this, "Failed saving corpus model file " + path.toString() + ": " + e.getMessage());
			throw e;
		}

		int idx = 0;
		for (int docId : getDocFeatures().keySet()) {
			String pathname = getPathname(docId);
			List<Feature> features = getDocFeatures().get(docId);

			path = corpusDir.resolve(String.format("%s%03d%s", DATA, idx, DOT + EXT));
			writer = configWriter(Files.newOutputStream(path));
			try {
				Log.debug(this, "Saving " + path.toString());
				gson.toJson(new Features(docId, pathname, features), Features.class, writer);
				writer.close();
			} catch (IOException | JsonSyntaxException e) {
				Log.error(this, "Failed saving corpus data file " + path.toString() + ": " + e.getMessage());
				throw e;
			}
			idx++;
		}
		Log.debug(this, "Save completed.");
	}

	public abstract String getPathname(int docId);

	public abstract Map<Integer, List<Feature>> getDocFeatures();

	/** Configures builder for pretty printing */
	private static Gson configBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.enableComplexMapKeySerialization() //
				.excludeFieldsWithoutExposeAnnotation() //
				.disableHtmlEscaping() //
				.serializeNulls() //
				.setDateFormat(DateFormat.LONG) //
				.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY) //
				.setPrettyPrinting();
		return builder.create();
	}

	/** Configures writer for 2-space indents */
	private static JsonWriter configWriter(OutputStream out) throws IOException {
		JsonWriter writer = new JsonWriter(new BufferedWriter(new OutputStreamWriter(out, "UTF-8")));
		writer.setIndent("  ");
		return writer;
	}
}
