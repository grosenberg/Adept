package net.certiv.adept.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.reflect.TypeToken;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;

import net.certiv.adept.Tool;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.util.Log;

public abstract class CorpusBase {

	private static final String MODEL = "CorpusModel";
	private static final String DATA = "CorpusData";
	private static final String DOT = ".";
	private static final String EXT = "json.gz";

	// private static final Type mmapType = new TypeToken<Multimap<EdgeKey, Edge>>() {}.getType();

	public CorpusBase() {}

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
			Log.debug(CorpusBase.class, "Loading " + path.toString());

			InputStream is = new GZIPInputStream(Files.newInputStream(path));
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
			model = gson.fromJson(reader, CorpusModel.class);
			model.setCorpusDir(corpusDir);
		} catch (IOException | JsonSyntaxException e) {
			Log.error(CorpusBase.class, "Failed loading corpus model file " + path.toString() + ": " + e.getMessage());
			throw e;
		}

		List<Path> paths = getDataFiles(corpusDir);
		for (Path dPath : paths) {
			try {
				Log.debug(CorpusBase.class, "Loading " + dPath.toString());

				InputStream is = new GZIPInputStream(Files.newInputStream(dPath));
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
				Features features = gson.fromJson(reader, Features.class);
				features.fixEdgeRefs();
				model.merge(features);
			} catch (IOException | JsonSyntaxException e) {
				Log.error(CorpusBase.class,
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
	 * Saves in a compact form.
	 * 
	 * @param corpusDir directory to save to
	 * @throws Exception if an existing file cannot be overwritten
	 */
	void save(Path corpusDir) throws Exception {
		Gson gson = configBuilder();

		Path path = corpusDir.resolve(MODEL + DOT + EXT);
		JsonWriter writer = configWriter(new GZIPOutputStream(Files.newOutputStream(path)));

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
			writer = configWriter(new GZIPOutputStream(Files.newOutputStream(path)));

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

	private static Gson configBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.enableComplexMapKeySerialization() //
				.disableHtmlEscaping() //
				.enableComplexMapKeySerialization() //
				.excludeFieldsWithoutExposeAnnotation() //
				.registerTypeAdapter(ArrayListMultimap.class, new MultiMapAdapter<EdgeKey, Edge>()) //
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

	private static final class MultiMapAdapter<K, V>
			implements JsonSerializer<Multimap<K, V>>, JsonDeserializer<Multimap<K, V>> {

		private static final Type asMapReturnType;
		static {
			try {
				asMapReturnType = Multimap.class.getDeclaredMethod("asMap").getGenericReturnType();
			} catch (NoSuchMethodException e) {
				throw new AssertionError(e);
			}
		}

		@Override
		public JsonElement serialize(Multimap<K, V> src, Type typeOfSrc, JsonSerializationContext context) {
			return context.serialize(src.asMap(), asMapType(typeOfSrc));
		}

		@Override
		public Multimap<K, V> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			Map<K, Collection<V>> asMap = context.deserialize(json, asMapType(typeOfT));
			Multimap<K, V> multimap = ArrayListMultimap.create();
			for (Map.Entry<K, Collection<V>> entry : asMap.entrySet()) {
				multimap.putAll(entry.getKey(), entry.getValue());
			}
			return multimap;
		}

		private static Type asMapType(Type multimapType) {
			return TypeToken.of(multimapType).resolveType(asMapReturnType).getType();
		}
	}
}
