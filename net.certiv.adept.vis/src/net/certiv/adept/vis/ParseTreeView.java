package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.tree.Trees;

import net.certiv.adept.Tool;
import net.certiv.adept.model.Document;
import net.certiv.adept.parser.ParseData;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.components.AbstractBase;
import net.certiv.adept.vis.components.SliderDialog;
import net.certiv.adept.vis.components.TextLineNumber;
import net.certiv.adept.vis.components.TreeViewer;
import net.certiv.adept.vis.models.TokenTableModel;
import net.certiv.adept.vis.renderers.TokenCellRenderer;

public class ParseTreeView extends AbstractBase {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		new ParseTreeView();
	}

	private static final String name = "ParseTree";

	private static final String KEY_SPLIT_VERT = "frame_split_vert";
	private static final String KEY_SPLIT_HORZ = "frame_split_horz";

	private JSplitPane mainPane;
	private TreeViewer viewer;
	private JSplitPane bottomPane;
	private JTextPane textPane;
	private JTable table;

	private int level;

	public ParseTreeView() {
		super("ParseData tree visiualization", "tree.gif");

		viewer = new TreeViewer();
		viewer.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					handleScaleChange();
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					handleAlignment(e.getX(), e.getY());
				}
			}
		});
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

		content.add(mainPane, BorderLayout.CENTER);
		content.add(createToolBar(), BorderLayout.NORTH);

		setLocation();

		int split = prefs.getInt(KEY_SPLIT_VERT, 300);
		mainPane.setDividerLocation(split);
		split = prefs.getInt(KEY_SPLIT_HORZ, 350);
		bottomPane.setDividerLocation(split);

		frame.setVisible(true);
	}

	@Override
	protected void saveWindowClosingPrefs(Preferences prefs) {
		prefs.putInt(KEY_SPLIT_VERT, mainPane.getDividerLocation());
		prefs.putInt(KEY_SPLIT_HORZ, bottomPane.getDividerLocation());
	}

	@Override
	protected String getBaseName() {
		return name;
	}

	@Override
	protected void handleFileOpen(File file) {
		createData(file);
	}

	protected void handleScaleChange() {
		int value = (int) ((viewer.getScale() - 1.0) * 1000);

		SliderDialog dialog = new SliderDialog(frame, "Adjust Scale", viewer);
		dialog.pack();
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);

		int result = dialog.getResult();
		if (result == JOptionPane.CANCEL_OPTION) {
			viewer.setScale(value);
		}
	}

	protected void handleAlignment(int x, int y) {
		Tree tree = viewer.getGridTree(x, y);
		if (tree == null) return;

		int index = 0;
		if (tree instanceof TerminalNode) {
			TerminalNode node = (TerminalNode) tree;
			index = node.getSymbol().getTokenIndex();
		} else {
			ParserRuleContext ctx = (ParserRuleContext) tree;
			index = ctx.getStart().getTokenIndex();
		}

		table.getSelectionModel().setSelectionInterval(index, index);
		table.scrollRectToVisible(table.getCellRect(index, 0, true));
	}

	protected JToolBar createToolBar() {
		JToolBar bar = new JToolBar();
		bar.add(getOpenAction()).setText("");
		bar.addSeparator();
		bar.add(getPngAction(viewer)).setText("");
		return bar;
	}

	@SuppressWarnings("deprecation")
	void createData(File file) {
		Tool tool = new Tool();
		tool.setCheck(true);
		tool.setCorpusRoot("../net.certiv.adept.core/corpus");
		tool.setLang("antlr");
		tool.setTabWidth(4);
		tool.setSourceFiles(file.getAbsolutePath());

		tool.setRebuild(true);

		boolean ok = tool.loadResources();
		ok = ok && tool.validateOptions();

		if (!ok) {
			Log.error(this, "Failed to initialize model");
			return;
		}

		tool.execute();
		Document doc = tool.getDocuments().get(0);
		ParseData data = doc.getParse();

		// graphical rep of tree
		viewer.inspect(data.getTree(), data.getRuleNames());

		// tokens table
		TokenTableModel model = new TokenTableModel(data.getTokens(), data.lexer.getTokenNames());
		table.setModel(model);
		table.setDefaultRenderer(Object.class, new TokenCellRenderer(model));
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
}
