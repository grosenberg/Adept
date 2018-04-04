package net.certiv.adept.model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;

import net.certiv.adept.ITextEdit;
import net.certiv.adept.Tool;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.tool.ErrorType;

public class Document {

	private DocModel model;
	private ParseRecord data;

	private String pathname;
	private int docId;
	private int tabWidth;
	private String content;

	private String modified;
	private List<ITextEdit> edits = Collections.emptyList();

	public Document(String pathname, int tabWidth, String content) {
		this.pathname = pathname;
		this.docId = pathname.hashCode();
		this.tabWidth = tabWidth;
		this.content = content;
	}

	public DocModel getModel() {
		return model;
	}

	public void setModel(DocModel model) {
		this.model = model;
	}

	public ParseRecord getParseRecord() {
		return data;
	}

	public void setBuilder(ParseRecord data) {
		this.data = data;
	}

	public String getPathname() {
		return pathname;
	}

	public String getFilename() {
		return Paths.get(pathname).getFileName().toString();
	}

	/** Returns the id of this document */
	public int getDocId() {
		return docId;
	}

	public int getTabWidth() {
		return tabWidth;
	}

	public String getContent() {
		return content;
	}

	public List<ITextEdit> getEdits() {
		return edits;
	}

	public void setEdits(List<ITextEdit> edits) {
		this.edits = edits;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public boolean saveModified(boolean overwrite) {
		Path path = Paths.get(pathname);
		if (!overwrite) {
			Path backup = Paths.get(pathname + ".bak");
			try {
				Files.copy(path, backup, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				Tool.errMgr.toolError(ErrorType.CANNOT_WRITE_FILE, e, backup.toString());
				return false;
			}
		}

		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write(modified);
		} catch (IOException e) {
			Tool.errMgr.toolError(ErrorType.CANNOT_WRITE_FILE, e, path.toString());
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return String.format("Document %s", pathname);
	}
}
