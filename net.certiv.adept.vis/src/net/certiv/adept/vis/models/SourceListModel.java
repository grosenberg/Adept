package net.certiv.adept.vis.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;

import net.certiv.adept.vis.models.SourceListModel.SrcItem;

public class SourceListModel extends DefaultComboBoxModel<SrcItem> {

	public static class SrcItem {

		public String name;
		public String pathname;

		public SrcItem(String name, String pathname) {
			super();
			this.name = name;
			this.pathname = pathname;
		}

		@Override
		public String toString() {
			return String.format("%s", name);
		}
	}

	public SourceListModel(String rootDir, String srcExt) {
		super();
		addElements(rootDir, srcExt);
	}

	private void addElements(String rootDir, String srcExt) {
		List<Path> paths = read(Paths.get(rootDir), srcExt);
		for (Path path : paths) {
			SrcItem item = new SrcItem(path.getFileName().toString(), path.toString());
			addElement(item);
		}
		setSelectedItem(getElementAt(0));
	}

	public String getSelectedPathname() {
		SrcItem item = (SrcItem) super.getSelectedItem();
		return item.pathname;
	}

	private List<Path> read(Path rootDir, String ext) {
		List<Path> paths;
		try {
			paths = Files.walk(rootDir) //
					.filter(Files::isRegularFile) //
					.filter(p -> p.getFileName().toString().endsWith(ext)) //
					.collect(Collectors.toList());
		} catch (IOException e) {
			return Collections.emptyList();
		}
		return paths;
	}
}
