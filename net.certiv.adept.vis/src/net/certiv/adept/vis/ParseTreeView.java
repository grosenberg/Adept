package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;

import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.tree.Trees;

import net.certiv.adept.Tool;
import net.certiv.adept.model.Document;
import net.certiv.adept.parser.Parse;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.components.TextLineNumber;
import net.certiv.adept.vis.components.TokenTableCellRenderer;
import net.certiv.adept.vis.components.TokenTableModel;
import net.certiv.adept.vis.components.TreeViewer;

public class ParseTreeView {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		new ParseTreeView();
	}

	private static final String Eol = System.lineSeparator();
	private static final String KEY_WIDTH = "frame_width";
	private static final String KEY_HEIGHT = "frame_height";
	private static final String KEY_X = "frame_x";
	private static final String KEY_Y = "frame_y";
	private static final String KEY_SPLIT_VERT = "frame_split_vert";
	private static final String KEY_SPLIT_HORZ = "frame_split_horz";

	private JFrame frame;
	private JSplitPane mainPane;
	private TreeViewer viewer;
	private JSplitPane bottomPane;
	private JTextPane textPane;
	private JTable table;
	private Preferences prefs;

	private Action openAction = new OpenAction();
	private int level;

	public ParseTreeView() {
		frame = new JFrame("Parse tree visiualization");
		ImageIcon imgicon = new ImageIcon(getClass().getClassLoader().getResource("tree.gif"));
		frame.setIconImage(imgicon.getImage());

		prefs = Preferences.userNodeForPackage(TreeViewer.class);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				prefs.putDouble(KEY_X, frame.getLocationOnScreen().getX());
				prefs.putDouble(KEY_Y, frame.getLocationOnScreen().getY());
				prefs.putInt(KEY_WIDTH, (int) frame.getSize().getWidth());
				prefs.putInt(KEY_HEIGHT, (int) frame.getSize().getHeight());
				prefs.putInt(KEY_SPLIT_VERT, mainPane.getDividerLocation());
				prefs.putInt(KEY_SPLIT_HORZ, bottomPane.getDividerLocation());
			}
		});

		viewer = new TreeViewer();
		JScrollPane scrollTree = new JScrollPane(viewer);

		table = new JTable();
		JScrollPane scrollTable = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		textPane = new JTextPane();
		JScrollPane scrollText = new JScrollPane(textPane);
		TextLineNumber nums = new TextLineNumber(textPane);
		scrollText.setRowHeaderView(nums);

		mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		bottomPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		mainPane.setTopComponent(scrollTree);
		mainPane.setBottomComponent(bottomPane);
		bottomPane.setLeftComponent(scrollTable);
		bottomPane.setRightComponent(scrollText);

		Dimension minimumSize = new Dimension(100, 50);
		scrollTree.setMinimumSize(minimumSize);
		scrollText.setMinimumSize(minimumSize);
		scrollTable.setMinimumSize(minimumSize);
		mainPane.setDividerLocation(0.5);
		bottomPane.setDividerLocation(0.5);

		Container content = frame.getContentPane();
		content.add(mainPane, BorderLayout.CENTER);
		content.add(createToolBar(), BorderLayout.NORTH);

		int width = prefs.getInt(KEY_WIDTH, 600);
		int height = prefs.getInt(KEY_HEIGHT, 600);
		content.setPreferredSize(new Dimension(width, height));
		frame.pack();

		int split = prefs.getInt(KEY_SPLIT_VERT, 300);
		mainPane.setDividerLocation(split);
		split = prefs.getInt(KEY_SPLIT_HORZ, 350);
		bottomPane.setDividerLocation(split);

		if (prefs.getDouble(KEY_X, -1) != -1) {
			frame.setLocation((int) prefs.getDouble(KEY_X, 100), (int) prefs.getDouble(KEY_Y, 100));
		}

		frame.setVisible(true);
	}

	protected JToolBar createToolBar() {
		JToolBar bar = new JToolBar();
		bar.add(getOpenAction()).setText("");
		return bar;
	}

	protected Action getOpenAction() {
		return openAction;
	}

	@SuppressWarnings("deprecation")
	void createData(File file) {
		Tool tool = new Tool();
		tool.setCheck(true);
		tool.setCorpusRoot("../net.certiv.adept.core/corpus");
		tool.setLang("antlr");
		tool.setTabWidth(4);
		tool.setSourceFiles(file.getAbsolutePath());

		tool.setRebuild(false);

		boolean ok = tool.loadResources();
		ok = ok && tool.validateOptions();

		if (!ok) {
			Log.error(this, "Failed to initialize model");
			return;
		}

		tool.execute();
		Document doc = tool.getDocuments().get(0);
		Parse data = doc.getParse();

		// graphical rep of tree
		viewer.inspect(data.getTree(), data.getRuleNames());

		// tokens table
		TokenTableModel model = new TokenTableModel(data.getTokens(), data.lexer.getTokenNames());
		table.setModel(model);
		table.setDefaultRenderer(Object.class, new TokenTableCellRenderer(model));
		TableColumnModel cModel = table.getColumnModel();
		cModel.getColumn(0).setPreferredWidth(10);
		cModel.getColumn(1).setPreferredWidth(10);
		cModel.getColumn(2).setPreferredWidth(100);
		cModel.getColumn(4).setPreferredWidth(10);
		cModel.getColumn(5).setPreferredWidth(10);

		// indented string tree
		String tree = createTextTree(data.getTree(), data.getRuleNames());
		textPane.setText(tree);
		textPane.setCaretPosition(0);
	}

	/**
	 * Pretty print out a whole tree. {@link #getNodeText} is used on the node payloads to get the
	 * text for the nodes. (Derived from Trees.toStringTree(....))
	 */
	private String createTextTree(final Tree t, final List<String> ruleNames) {
		level = 0;
		return process(t, ruleNames).replaceAll("(?m)^\\s+$", "").replaceAll("\\r?\\n\\r?\\n", Eol);
	}

	private String process(final Tree t, final List<String> ruleNames) {
		if (t.getChildCount() == 0) return Utils.escapeWhitespace(Trees.getNodeText(t, ruleNames), false);
		StringBuilder sb = new StringBuilder();
		sb.append(lead(level));
		level++;
		String s = Utils.escapeWhitespace(Trees.getNodeText(t, ruleNames), false);
		sb.append(s + ' ');
		for (int i = 0; i < t.getChildCount(); i++) {
			sb.append(process(t.getChild(i), ruleNames));
		}
		level--;
		sb.append(lead(level));
		return sb.toString();
	}

	private String lead(int level) {
		StringBuilder sb = new StringBuilder();
		if (level > 0) {
			sb.append(Eol);
			for (int cnt = 0; cnt < level; cnt++) {
				sb.append(getIndents());
			}
		}
		return sb.toString();
	}

	private String getIndents() {
		return "    ";
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

			createData(file);
		}
	}
}
