package net.certiv.adept.model.load;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.certiv.adept.model.Document;
import net.certiv.adept.util.Log;

public class Corpus {

	/** Returns a list of the training documents that define the corpus. */
	public static List<Document> readDocuments(Path corpusDir, String ext, int tabWidth) {
		List<Document> documents = new ArrayList<>();
		List<File> files;
		try {
			files = Files.walk(corpusDir) //
					.filter(Files::isRegularFile) //
					.filter(p -> p.getFileName().toString().endsWith(Config.DOT + ext)) //
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
				Log.error(Config.class, "Failed to read corpus file" + file.getPath() + ": " + e.getMessage());
			}
		}

		return documents;
	}

	public static void writeDocuments(Path corpusDir, List<Document> docs) {
		for (Document doc : docs) {
			writeDocument(corpusDir, doc);
		}
	}

	public static void writeDocument(Path corpusDir, Document doc) {
		Path path = Paths.get(doc.getPathname()).getFileName();
		path = corpusDir.resolve(path);

		try {
			Files.write(path, doc.getContent().getBytes());
		} catch (IOException e) {
			Log.error(Config.class, "Failed to write corpus file" + path.toString() + ": " + e.getMessage());
		}
	}

}
