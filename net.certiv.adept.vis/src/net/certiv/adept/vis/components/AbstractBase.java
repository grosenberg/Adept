package net.certiv.adept.vis.components;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.uci.ics.jung.visualization.control.GraphMouseListener;
import net.certiv.adept.util.Log;

public abstract class AbstractBase {

	protected static final FileFilter pngFilter = new FileNameExtensionFilter("PNG Files", "png");
	protected static final String ext = ".png";
	protected static final String Eol = System.lineSeparator();

	protected static final String KEY_WIDTH = "frame_width";
	protected static final String KEY_HEIGHT = "frame_height";
	protected static final String KEY_X = "frame_x";
	protected static final String KEY_Y = "frame_y";

	protected Action openAction;
	protected Action pngAction;

	protected JFrame frame;
	protected Container content;
	protected Preferences prefs;

	public AbstractBase(String title, String icon) {
		frame = new JFrame(title);
		ImageIcon imgicon = new ImageIcon(this.getClass().getClassLoader().getResource(icon));
		frame.setIconImage(imgicon.getImage());

		content = frame.getContentPane();
		prefs = Preferences.userNodeForPackage(this.getClass());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				prefs.putDouble(KEY_X, frame.getLocationOnScreen().getX());
				prefs.putDouble(KEY_Y, frame.getLocationOnScreen().getY());
				prefs.putInt(KEY_WIDTH, (int) frame.getSize().getWidth());
				prefs.putInt(KEY_HEIGHT, (int) frame.getSize().getHeight());
				saveWindowClosingPrefs(prefs);
			}
		});
	}

	protected void saveWindowClosingPrefs(Preferences prefs) {}

	protected void handleFileOpen(File file) {}

	/** Base file name for png name to save proposals */
	protected String getBaseName() {
		return "";
	}

	protected void setLocation() {
		int width = prefs.getInt(KEY_WIDTH, 600);
		int height = prefs.getInt(KEY_HEIGHT, 600);
		content.setPreferredSize(new Dimension(width, height));
		frame.setLocation((int) prefs.getDouble(KEY_X, 100), (int) prefs.getDouble(KEY_Y, 100));
		frame.pack();
	}

	protected Action getOpenAction() {
		if (openAction == null) {
			openAction = new OpenAction();
		}
		return openAction;
	}

	protected Action getPngAction(JComponent comp) {
		if (pngAction == null) {
			pngAction = new PngAction(comp);
		} else {
			((PngAction) pngAction).setViewer(comp);
		}
		return pngAction;
	}

	class OpenAction extends AbstractAction {

		public OpenAction() {
			super("Open", new ImageIcon("resources/open.png"));
		}

		@Override
		public void actionPerformed(ActionEvent ev) {
			JFileChooser chooser = new JFileChooser();
			Path path = Paths.get("../net.certiv.adept.test/test.snippets");
			chooser.setCurrentDirectory(path.toFile());

			if (chooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) return;
			File file = chooser.getSelectedFile();
			if (file == null || !file.isFile()) return;

			handleFileOpen(file);
		}
	}

	class PngAction extends AbstractAction {

		private JComponent comp;

		public PngAction(JComponent comp) {
			super("Save png", new ImageIcon("resources/png.png"));
			this.comp = comp;
		}

		public void setViewer(JComponent comp) {
			this.comp = comp;
		}

		@Override
		public void actionPerformed(ActionEvent ev) {
			JFileChooser chooser = new JFileChooser();
			Path path = Paths.get("doc");
			if (!path.toFile().exists()) {
				try {
					Files.createDirectory(path);
				} catch (IOException e) {}
			}

			chooser.setSelectedFile(proposeName(path));
			chooser.setCurrentDirectory(path.toFile());
			chooser.addChoosableFileFilter(pngFilter);
			chooser.setFileFilter(pngFilter);

			if (chooser.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) return;
			File file = chooser.getSelectedFile();

			generatePng(comp, file);
		}
	}

	private File proposeName(Path path) {
		String name = getBaseName();
		int cnt = 1;
		File png = new File(path.toString(), name + ext);
		while (png.exists()) {
			png = new File(path.toString(), String.format("%s%03d%s", name, cnt, ext));
			cnt++;
		}
		return png;
	}

	private void generatePng(JComponent comp, File png) {
		BufferedImage bi = new BufferedImage(comp.getSize().width, comp.getSize().height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		comp.paint(g);
		g.dispose();

		try {
			ImageIO.write(bi, "png", png);
		} catch (IOException e) {
			Log.error(this, "Write failed to " + png.getPath());
		}

		try {
			Desktop.getDesktop().open(png);
		} catch (Exception ex) {
			Log.error(this, "Failed to open " + png.getPath());
		}
	}

	protected class GraphMouseAdaptor<V> implements GraphMouseListener<V> {

		public GraphMouseAdaptor() {}

		public void graphClicked(V v, MouseEvent e) {}

		public void graphPressed(V v, MouseEvent e) {}

		public void graphReleased(V v, MouseEvent e) {}
	}
}
