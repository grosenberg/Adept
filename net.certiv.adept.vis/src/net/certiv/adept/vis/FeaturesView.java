package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.certiv.adept.Tool;
import net.certiv.adept.model.Feature;
import net.certiv.adept.parser.ISourceParser;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.components.FeatureTypeCellRenderer;
import net.certiv.adept.vis.components.FeatureTypeTableModel;

public class FeaturesView {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		FeaturesView view = new FeaturesView();
		view.createData();
	}

	// private static final String Eol = System.lineSeparator();
	private static final String KEY_WIDTH = "frame_width";
	private static final String KEY_HEIGHT = "frame_height";
	private static final String KEY_X = "frame_x";
	private static final String KEY_Y = "frame_y";

	private JFrame frame;
	private JTable table;
	private Preferences prefs;

	private Comparator<Integer> intComparator = new Comparator<Integer>() {

		@Override
		public int compare(Integer o1, Integer o2) {
			if (o1 < o2) return -1;
			if (o1 > o2) return 1;
			return 0;
		}

	};

	public FeaturesView() {
		frame = new JFrame("Features Analysis");
		ImageIcon imgicon = new ImageIcon(getClass().getClassLoader().getResource("tree.gif"));
		frame.setIconImage(imgicon.getImage());

		prefs = Preferences.userNodeForPackage(FeaturesView.class);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				prefs.putDouble(KEY_X, frame.getLocationOnScreen().getX());
				prefs.putDouble(KEY_Y, frame.getLocationOnScreen().getY());
				prefs.putInt(KEY_WIDTH, (int) frame.getSize().getWidth());
				prefs.putInt(KEY_HEIGHT, (int) frame.getSize().getHeight());
			}
		});

		table = new JTable();
		table.setFillsViewportHeight(true);
		JScrollPane scrollTable = new JScrollPane(table);

		Dimension minimumSize = new Dimension(100, 100);
		scrollTable.setMinimumSize(minimumSize);

		Container content = frame.getContentPane();
		content.add(scrollTable, BorderLayout.CENTER);

		int width = prefs.getInt(KEY_WIDTH, 600);
		int height = prefs.getInt(KEY_HEIGHT, 600);
		content.setPreferredSize(new Dimension(width, height));
		frame.pack();

		if (prefs.getDouble(KEY_X, -1) != -1) {
			frame.setLocation((int) prefs.getDouble(KEY_X, 100), (int) prefs.getDouble(KEY_Y, 100));
		}

		frame.setVisible(true);
	}

	void createData() {
		Tool tool = new Tool();
		tool.setCheck(true);
		tool.setCorpusRoot("../net.certiv.adept.core/corpus");
		tool.setLang("antlr");
		tool.setTabWidth(4);

		tool.setRebuild(true);

		boolean ok = tool.loadResources();
		ok = ok && tool.validateOptions();

		if (!ok) {
			Log.error(this, "Failed to initialize model");
			return;
		}

		ISourceParser lang = Tool.mgr.getLanguageParser();
		Map<Integer, List<Feature>> index = Tool.mgr.getCorpusModel().getFeatureIndex();

		// feature type table
		FeatureTypeTableModel model = new FeatureTypeTableModel(index, lang.getRuleNames(), lang.getTokenNames());
		table.setModel(model);

		table.setDefaultRenderer(Object.class, new FeatureTypeCellRenderer(model));
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		sorter.setComparator(0, intComparator);
		sorter.setComparator(2, intComparator);
		sorter.setComparator(4, intComparator);
		
		TableColumnModel cols = table.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(60);
		cols.getColumn(2).setPreferredWidth(10);
		cols.getColumn(4).setPreferredWidth(50);
	}
}
