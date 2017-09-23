package net.certiv.adept.model.load;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.certiv.adept.Tool;
import net.certiv.adept.tool.ErrorManager;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.tool.LangDescriptor;

public class Config {

	static final String MODEL = "CorpusModel";
	static final String DATA = "CorpusData";
	static final String DOT = ".";
	static final String EXT = "json.gz";

	/** Returns the tool version identifier. */
	public static String loadVersion(ErrorManager errMgr) {
		ClassLoader cl = Tool.class.getClassLoader();

		try (InputStream in = cl.getResourceAsStream("adept.properties")) {
			Properties prop = new Properties();
			prop.load(in);
			return (String) prop.get("version");
		} catch (IOException e) {
			errMgr.toolError(ErrorType.CONFIG_FAILURE, "Failed reading version property (" + e.getMessage() + ")");
			return null;
		}
	}

	/** Returns a list of the language descriptors recognized by the tool. */
	public static List<LangDescriptor> loadLanguages(ErrorManager errMgr) {
		ClassLoader cl = Tool.class.getClassLoader();

		try (Reader reader = new InputStreamReader(cl.getResourceAsStream("languages.json"), "UTF-8")) {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			Type collection = new TypeToken<Collection<LangDescriptor>>() {}.getType();
			return gson.fromJson(reader, collection);
		} catch (Exception e) {
			errMgr.toolError(ErrorType.CONFIG_FAILURE, "Failed reading lang descriptors (" + e.getMessage() + ")");
			return null;
		}
	}

	public static List<String> getContainedFilenames(Path dir, String ext) throws IOException {
		return getContainedPathnames(dir, ext) //
				.stream() //
				.map(Path::toString) //
				.collect(Collectors.toList());
	}

	public static List<Path> getContainedPathnames(Path dir, String ext) throws IOException {
		return getContainedPathnames(dir, Integer.MAX_VALUE, "", ext);
	}

	public static List<Path> getContainedPathnames(Path dir, int depth, String prefix, String ext) throws IOException {

		return Files.walk(dir, depth) //
				.filter(Files::isRegularFile) //
				.filter(p -> p.getFileName().toString().startsWith(prefix)) //
				.filter(p -> p.getFileName().toString().endsWith(DOT + ext)) //
				.collect(Collectors.toList());
	}
}
