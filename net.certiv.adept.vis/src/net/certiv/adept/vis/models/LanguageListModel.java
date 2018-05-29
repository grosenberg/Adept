package net.certiv.adept.vis.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;

import net.certiv.adept.Tool;
import net.certiv.adept.model.load.ConfigMgr;
import net.certiv.adept.tool.LangDescriptor;

public class LanguageListModel extends DefaultComboBoxModel<String> {

	private Map<String, LangDescriptor> languages;

	public LanguageListModel(String corpusRoot) {
		super();
		languages = new HashMap<>();
		for (LangDescriptor desc : ConfigMgr.loadLanguages(new Tool())) {
			languages.put(desc.name, desc);
		}
		addLangs(corpusRoot);
	}

	private void addLangs(String dir) {
		List<Path> paths = read(Paths.get(dir));
		for (Path path : paths) {
			String lang = path.getFileName().toString();
			if (languages.containsKey(lang)) {
				addElement(lang);
			}
		}
		setSelectedItem(getElementAt(0));
	}

	private List<Path> read(Path dir) {
		List<Path> paths;
		try {
			paths = Files.walk(dir) //
					.filter(Files::isDirectory) //
					.collect(Collectors.toList());
		} catch (IOException e) {
			return Collections.emptyList();
		}
		return paths;
	}

	public int getIndexOfSelected() {
		return getIndexOf(getSelectedItem());
	}

	public String getSelected() {
		return (String) getSelectedItem();
	}

	public String getSelectedExt() {
		String name = (String) getSelectedItem();
		return "." + languages.get(name).ext;
	}
}
