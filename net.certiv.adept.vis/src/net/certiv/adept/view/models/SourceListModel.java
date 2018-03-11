package net.certiv.adept.view.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;

import net.certiv.adept.view.models.SourceListModel.Item;

public class SourceListModel extends DefaultComboBoxModel<Item> {

	public static class Item {

		public String name;
		public String pathname;

		public Item(String name, String pathname) {
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

	private void addElements(String dir, String ext) {
		List<Path> paths = read(Paths.get(dir), ext);
		for (Path path : paths) {
			Item item = new Item(path.getFileName().toString(), path.toString());
			addElement(item);
		}
		setSelectedItem(getElementAt(0));
	}

	private List<Path> read(Path dir, String ext) {
		List<Path> paths;
		try {
			paths = Files.walk(dir) //
					.filter(Files::isRegularFile) //
					.filter(p -> p.getFileName().toString().endsWith(ext)) //
					.collect(Collectors.toList());
		} catch (IOException e) {
			return Collections.emptyList();
		}
		return paths;
	}

	public int getIndexOfSelected() {
		return getIndexOf(getSelectedItem());
	}

	public String getSelectedPathname() {
		Item item = (Item) getSelectedItem();
		return item.pathname;
	}
}
