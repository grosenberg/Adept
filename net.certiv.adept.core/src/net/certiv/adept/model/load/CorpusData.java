package net.certiv.adept.model.load;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
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
import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.Edge;
import net.certiv.adept.model.Feature;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.util.Log;

public class CorpusData {

	/**
	 * Load the corpus model from persistent store. The model is stored in two parts: (1) the base
	 * model file; and (2) a plurality of feature set data files.
	 * 
	 * @param mgr
	 * @param corpusDir directory to load from
	 * @param pathnames the pathnames of the corpus files expected to be included in the corpus model
	 * @return the corpus model
	 * @throws Exception if any persisted file cannot found or read
	 */
	public static CorpusModel loadModel(ProcessMgr mgr, Path corpusDir, List<String> pathnames) throws Exception {
		Path path = corpusDir.resolve(Config.MODEL + Config.DOT + Config.EXT);
		if (!Files.isRegularFile(path)) {
			throw new IOException("Corpus directory does not exist: " + path.toString());
		}

		CorpusModel corModel;
		Gson gson = configBuilder();
		try {
			Log.debug(CorpusData.class, "Loading " + path.toString());

			InputStream is = new GZIPInputStream(Files.newInputStream(path));
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
			corModel = gson.fromJson(reader, CorpusModel.class);
			corModel.setMgr(mgr);
			corModel.setCorpusDir(corpusDir);
			if (corModel.getBoosts().isEmpty()) corModel.initBoosts();
		} catch (IOException | JsonSyntaxException e) {
			Log.error(CorpusData.class, "Failed loading corpus model file " + path.toString() + ": " + e.getMessage());
			throw e;
		}

		List<Path> paths = getDataFiles(corpusDir);
		for (Path dPath : paths) {
			try {
				Log.debug(CorpusData.class, "Loading " + dPath.toString());

				InputStream is = new GZIPInputStream(Files.newInputStream(dPath));
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
				FeatureSet featureSet = gson.fromJson(reader, FeatureSet.class);
				if (pathnames.contains(featureSet.getPathname())) {
					featureSet.fixEdgeRefs(mgr);
					corModel.merge(featureSet);
				}
			} catch (IOException | JsonSyntaxException e) {
				Log.error(CorpusData.class,
						"Failed loading corpus data file " + path.toString() + ": " + e.getMessage());
				throw e;
			}
		}
		return corModel;
	}

	/** Remove all of the data files that represent the corpus. */
	public static boolean removeData(Path corpusDir) {
		boolean successful = true;
		for (Path datafile : getDataFiles(corpusDir)) {
			successful = successful && datafile.toFile().delete();
		}
		return successful;
	}

	/* Returns a list of the corpus data file pathnames. */
	private static List<Path> getDataFiles(Path corpusDir) {
		try {
			return Files.walk(corpusDir, 1) //
					.filter(Files::isRegularFile) //
					.filter(p -> p.getFileName().toString().startsWith(Config.DATA)) //
					.filter(p -> p.getFileName().toString().endsWith(Config.DOT + Config.EXT)) //
					.collect(Collectors.toList());
		} catch (IOException e) {
			Tool.errMgr.toolError(ErrorType.CANNOT_OPEN_FILE, "Failed reading Data file names");
		}
		return Collections.emptyList();
	}

	private static Gson configBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.enableComplexMapKeySerialization() //
				.disableHtmlEscaping() //
				.enableComplexMapKeySerialization() //
				.excludeFieldsWithoutExposeAnnotation() //
				.registerTypeAdapter(ArrayListMultimap.class, new MultiMapAdapter<Long, Edge>()) //
				// .registerTypeAdapter(HashTreeSet.class, new HashTreeAdapter<Long, Edge>()) //
				.serializeNulls() //
				.setDateFormat(DateFormat.LONG) //
				.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY) //
				.setPrettyPrinting();
		return builder.create();
	}

	/** Configure writer for 2-space indents */
	private static JsonWriter configWriter(OutputStream out) throws IOException {
		JsonWriter writer = new JsonWriter(new BufferedWriter(new OutputStreamWriter(out, "UTF-8")));
		writer.setIndent("  ");
		return writer;
	}

	/**
	 * Saves the corpus model to a Json file in the given directory. Overwrites any existing file. Saves
	 * in a compact form.
	 * 
	 * @param corpusDir directory to save to
	 * @throws Exception if an existing file cannot be overwritten
	 */
	public static void save(Path corpusDir, CorpusModel model) throws Exception {
		save(corpusDir, model, true);
	}

	/**
	 * Saves the corpus model to a Json file in the given directory. Overwrites any existing file. Saves
	 * in a compact form.
	 * 
	 * @param corpusDir directory to save to
	 * @param all whether to also save the corpus data files
	 * @throws Exception if an existing file cannot be overwritten
	 */
	public static void save(Path corpusDir, CorpusModel model, boolean all) throws Exception {
		Gson gson = configBuilder();

		Path path = corpusDir.resolve(Config.MODEL + Config.DOT + Config.EXT);
		JsonWriter writer = configWriter(new GZIPOutputStream(Files.newOutputStream(path)));

		try {
			Log.debug(CorpusData.class, "Saving " + path.toString());
			gson.toJson(model, CorpusModel.class, writer);
			writer.close();
		} catch (IOException | JsonSyntaxException e) {
			Log.error(CorpusData.class, "Failed saving corpus model file " + path.toString() + ": " + e.getMessage());
			throw e;
		}

		if (all) {
			int idx = 0;
			for (int docId : model.getDocFeatures().keySet()) {
				String pathname = model.getPathname(docId);
				List<Feature> features = model.getDocFeatures().get(docId);

				path = corpusDir.resolve(String.format("%s%03d%s", Config.DATA, idx, Config.DOT + Config.EXT));
				writer = configWriter(new GZIPOutputStream(Files.newOutputStream(path)));

				try {
					Log.debug(Config.class, "Saving " + path.toString());
					gson.toJson(new FeatureSet(docId, pathname, features), FeatureSet.class, writer);
					writer.close();
				} catch (IOException | JsonSyntaxException e) {
					Log.error(CorpusData.class,
							"Failed saving corpus data file " + path.toString() + ": " + e.getMessage());
					throw e;
				}
				idx++;
			}
		}
		Log.debug(CorpusData.class, "Save complete.");
	}

	private static final class MultiMapAdapter<K, V>
			implements JsonSerializer<Multimap<K, V>>, JsonDeserializer<Multimap<K, V>> {

		private static final Type retType;
		static {
			try {
				retType = Multimap.class.getDeclaredMethod("asMap").getGenericReturnType();
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
			return TypeToken.of(multimapType).resolveType(retType).getType();
		}
	}

	// private static final class HashTreeAdapter<K, V>
	// implements JsonSerializer<HashTreeSet<K, V>>, JsonDeserializer<HashTreeSet<K, V>> {
	//
	// private static final Type retType;
	// static {
	// try {
	// retType = HashTreeSet.class.getDeclaredMethod("asMap").getGenericReturnType();
	// } catch (NoSuchMethodException e) {
	// throw new AssertionError(e);
	// }
	// }
	//
	// @Override
	// public JsonElement serialize(HashTreeSet<K, V> src, Type typeOfSrc, JsonSerializationContext
	// context) {
	// return context.serialize(src.asMap(), asMapType(typeOfSrc));
	// }
	//
	// @Override
	// public HashTreeSet<K, V> deserialize(JsonElement json, Type typeOfT,
	// JsonDeserializationContext context)
	// throws JsonParseException {
	//
	// Map<K, TreeSet<V>> asMap = context.deserialize(json, asMapType(typeOfT));
	// return new HashTreeSet<K, V>(asMap);
	// }
	//
	// private static Type asMapType(Type multimapType) {
	// return TypeToken.of(multimapType).resolveType(retType).getType();
	// }
	// }

}
