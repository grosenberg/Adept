/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.components;

import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.ItemEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class FontChoiceBox extends JComboBox<Font> {

	public static final FontRenderContext FRC = new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
			RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);

	private static final Pattern NON_REGULAR = Pattern.compile(
			".*?(bold|italic|black|condensed|light|heavy|oblique|drawing|typographic|symbols|multinational).*?");

	private final Set<Component> components = new LinkedHashSet<>();

	private Font defaultFont;
	private int style;
	private boolean mono = true;

	public FontChoiceBox(String fontname, int style, boolean mono) {
		super();
		this.style = style;
		this.mono = mono;
		this.defaultFont = new Font(fontname, style, 1);

		setRenderer(new FontCellRenderer());
		loadFonts();
		setSelectedItem(defaultFont);
	}

	public void addComponent(Component... components) {
		this.components.addAll(Arrays.asList(components));
		int idx = getSelectedIndex();
		Font font = (Font) getSelectedItem();
		fireItemStateChanged(new ItemEvent(this, idx, font, ItemEvent.SELECTED));
	}

	public Font getSelectedFont() {
		return (Font) getSelectedItem();
	}

	private void loadFonts() {
		for (Font font : getFonts()) {
			addItem(font);
		}
	}

	private List<Font> getFonts() {
		Font fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		Arrays.sort(fonts, new Comparator<Font>() {

			@Override
			public int compare(Font f1, Font f2) {
				return f1.getName().compareTo(f2.getName());
			}
		});
		List<Font> allFonts = new ArrayList<>();
		for (Font font : fonts) {
			String name = font.getFontName();
			if (font.getStyle() != style) continue;
			if (Character.UnicodeBlock.of('a') != Character.UnicodeBlock.BASIC_LATIN) continue;
			if (font.canDisplayUpTo(name) != -1) continue;
			if (!name.matches("[a-zA-Z ]+")) continue;
			if (NON_REGULAR.matcher(name.toLowerCase()).matches()) continue;

			allFonts.add(font);
		}

		if (!mono) return allFonts;

		List<Font> monoFonts = new ArrayList<>();
		for (Font font : allFonts) {
			Rectangle2D iBounds = font.getStringBounds("i", FRC);
			Rectangle2D mBounds = font.getStringBounds("m", FRC);
			if (iBounds.getWidth() == mBounds.getWidth()) {
				monoFonts.add(font);
			}
		}
		return monoFonts;
	}

	private static class FontCellRenderer implements ListCellRenderer<Font> {

		protected DefaultListCellRenderer renderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(JList<? extends Font> list, Font font, int index,
				boolean isSelected, boolean cellHasFocus) {

			Component result = renderer.getListCellRendererComponent(list, font.getName(), index, isSelected,
					cellHasFocus);
			// float size = result.getFont().getSize();
			result.setFont(font.deriveFont((float) 12));
			return result;
		}
	}
}
