/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.model.load;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.certiv.adept.model.Document;
import net.certiv.adept.util.Log;

public class CorpusDocs {

	/** Returns a list of the corpus document pathnames. */
	public static List<String> readPathnames(Path corpusDir, String ext) {
		List<String> pathnames;
		try {
			pathnames = Files.walk(corpusDir) //
					.filter(Files::isRegularFile) //
					.filter(p -> p.getFileName().toString().endsWith(ConfigMgr.DOT + ext)) //
					.map(Path::toString) //
					.distinct() //
					.collect(Collectors.toList());
			return pathnames;
		} catch (IOException e) {
			Log.error(ConfigMgr.class, "Failed to read corpus pathnames" + ": " + e.getMessage());
		}
		return Collections.emptyList();
	}

	/** Returns a list of the training documents that define the corpus. */
	public static List<Document> readDocuments(Path corpusDir, String ext, int tabWidth) {
		List<Document> documents = new ArrayList<>();
		List<File> files;
		try {
			files = Files.walk(corpusDir) //
					.filter(Files::isRegularFile) //
					.filter(p -> p.getFileName().toString().endsWith(ConfigMgr.DOT + ext)) //
					.map(Path::toFile) //
					.distinct() //
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
				Log.error(ConfigMgr.class, "Failed to read corpus file" + file.getPath() + ": " + e.getMessage());
			}
		}

		return documents;
	}

	/** Writes a list of the documents to the corpus document directory. */
	public static void writeDocuments(Path corpusDir, List<Document> docs) {
		for (Document doc : docs) {
			writeDocument(corpusDir, doc);
		}
	}

	/** Writes a document to the corpus document directory. */
	public static void writeDocument(Path corpusDir, Document doc) {
		Path path = Paths.get(doc.getPathname()).getFileName();
		path = corpusDir.resolve(path);

		try {
			Files.write(path, doc.getContent().getBytes());
		} catch (IOException e) {
			Log.error(ConfigMgr.class, "Failed to write corpus file" + path.toString() + ": " + e.getMessage());
		}
	}
}
