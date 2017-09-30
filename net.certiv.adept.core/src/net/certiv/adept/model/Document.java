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
import net.certiv.adept.model.parser.ParseData;
import net.certiv.adept.model.util.Info;
import net.certiv.adept.tool.ErrorType;

public class Document {

	private DocModel model;
	private ParseData data;

	private String pathname;
	private int docId;
	private int tabWidth;
	private String content;
	private String modified;

	private List<Info> infos;

	public Document(String pathname, int tabWidth, String content) {
		this.pathname = pathname;
		this.docId = pathname.hashCode();
		this.tabWidth = tabWidth;
		this.content = content;

		infos = new ArrayList<>();
		Info prior = null;
		for (String text : content.split("\\r?\\n", -1)) {
			Info curr = new Info(text, tabWidth);
			if (prior != null) {
				curr.priorIndents = prior.indents;
				curr.blankAbove = prior.blank;
				prior.blankBelow = curr.blank;
			}
			prior = curr;
			infos.add(curr);
		}
	}

	public DocModel getModel() {
		return model;
	}

	public void setModel(DocModel model) {
		this.model = model;
	}

	public ParseData getParseData() {
		return data;
	}

	public void setParseData(ParseData data) {
		this.data = data;
	}

	public String getPathname() {
		return pathname;
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

	/** Returns the Info at the given index representing the document line number (0..n) */
	public Info getInfo(int idx) {
		return infos.get(idx);
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
