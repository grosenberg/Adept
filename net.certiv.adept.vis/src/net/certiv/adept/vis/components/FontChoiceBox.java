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

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class FontChoiceBox extends JComboBox<Font> {

	private final Set<Component> components = new LinkedHashSet<>();

	private Font defaultFont;
	private boolean mono = true;

	public FontChoiceBox(String fontname, int style, boolean mono) {
		super();
		this.defaultFont = new Font(fontname, style, 1);
		this.mono = mono;

		setRenderer(new FontCellRenderer());
		loadFonts();
		setSelectedItem(defaultFont);
	}

	private void loadFonts() {
		for (Font font : getFonts(mono)) {
			if (font.canDisplayUpTo(font.getName()) == -1) {
				addItem(font);
			}
		}
	}

	public void addComponent(Component... components) {
		this.components.addAll(Arrays.asList(components));
		int idx = getSelectedIndex();
		Font font = (Font) getSelectedItem();
		fireItemStateChanged(new ItemEvent(this, idx, font, ItemEvent.SELECTED));
	}

	private List<Font> getFonts(boolean mono) {
		Font fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		Arrays.sort(fonts, new Comparator<Font>() {

			@Override
			public int compare(Font f1, Font f2) {
				return f1.getName().compareTo(f2.getName());
			}
		});
		List<Font> allFonts = new ArrayList<>();
		for (Font font : fonts) {
			if (font.getFontName().matches("[a-zA-Z ]+")) {
				allFonts.add(font);
			}
		}

		if (!mono) return allFonts;

		FontRenderContext frc = new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);

		List<Font> monoFonts = new ArrayList<>();
		for (Font font : allFonts) {
			Rectangle2D iBounds = font.getStringBounds("i", frc);
			Rectangle2D mBounds = font.getStringBounds("m", frc);
			if (iBounds.getWidth() == mBounds.getWidth()) {
				monoFonts.add(font);
			}
		}
		return monoFonts;
	}

	private static class FontCellRenderer implements ListCellRenderer<Font> {

		protected DefaultListCellRenderer renderer = new DefaultListCellRenderer();

		public Component getListCellRendererComponent(JList<? extends Font> list, Font font, int index,
				boolean isSelected, boolean cellHasFocus) {

			Component result = renderer.getListCellRendererComponent(list, font.getName(), index, isSelected,
					cellHasFocus);
			float size = result.getFont().getSize();
			result.setFont(font.deriveFont(size));
			return result;
		}
	}
}
