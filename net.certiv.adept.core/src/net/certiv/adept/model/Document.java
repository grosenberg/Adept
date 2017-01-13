package net.certiv.adept.model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import net.certiv.adept.Tool;
import net.certiv.adept.parser.Parse;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.topo.FormInfo;

public class Document {

	private DocModel model;
	private Parse data;

	private String pathname;
	private int docId;
	private int tabWidth;
	private String content;
	private String modified;

	private List<FormInfo> formInfos;

	public Document(String filename, int tabWidth, String content) {
		this.pathname = filename;
		this.docId = getDocId(filename);
		this.tabWidth = tabWidth;
		this.content = content;

		formInfos = new ArrayList<>();
		FormInfo prior = null;
		for (String line : content.split("\\r?\\n", -1)) {
			FormInfo curr = new FormInfo(line, tabWidth);
			if (prior != null) {
				curr.priorIndents = prior.indents;
				curr.blankAbove = prior.blank;
				prior.blankBelow = curr.blank;
			}
			prior = curr;
			formInfos.add(curr);
		}
	}

	public DocModel getModel() {
		return model;
	}

	public void setModel(DocModel model) {
		this.model = model;
	}

	public Parse getParse() {
		return data;
	}

	public void setParse(Parse data) {
		this.data = data;
	}

	public String getPathname() {
		return pathname;
	}

	/** Generates an id for the pathname. */
	public static int getDocId(String pathname) {
		return pathname.hashCode();
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

	public FormInfo getInfo(int idx) {
		return formInfos.get(idx);
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
			Path bak = Paths.get(pathname + ".bak");
			try {
				Files.copy(path, bak, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				Tool.errMgr.toolError(ErrorType.CANNOT_WRITE_FILE, e, bak.toString());
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
