/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;

import net.certiv.adept.vis.models.SourceListModel.Item;

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

	public SourceListModel(Path dir, String ext) {
		super();
		addElements(dir, ext);
	}

	private void addElements(Path dir, String ext) {
		for (Path path : read(dir, ext)) {
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
		if (item == null) return "";
		return item.pathname;
	}
}
