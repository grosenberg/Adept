/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Myers Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.tree.Trees;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import net.certiv.adept.Tool;
import net.certiv.adept.lang.Record;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.components.LineRuler;
import net.certiv.adept.vis.components.SliderDialog;
import net.certiv.adept.vis.components.TreeViewer;
import net.certiv.adept.vis.models.SourceListModel;
import net.certiv.adept.vis.models.TokenTableModel;
import net.certiv.adept.vis.panels.AbstractViewBase;
import net.certiv.adept.vis.panels.ParseTreeSelectPanel;

public class ParseTreeGraph extends AbstractViewBase {

	private static final String name = "ParseTreeGraph";
	private static final String corpusRoot = "../net.certiv.adept/corpus";

	private JSplitPane mainPane;
	private TreeViewer viewer;
	private JSplitPane bottomPane;
	private JTextPane textPane;
	private JTable table;

	private int level;
	private ParseTreeSelectPanel selectPanel;
	private Tool tool;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {}
		new ParseTreeGraph();
	}

	public ParseTreeGraph() {
		super(name, "tree.gif");

		createSelectPane();
		createMainPane();
		createTreePane();
		createTablePane();
		createTextPane();

		applyPrefs();
		frame.setVisible(true);

		String lang = (String) selectPanel.langBox.getSelectedItem();
		loadTool(lang);
	}

	private void createSelectPane() {
		selectPanel = new ParseTreeSelectPanel(this);
		content.add(selectPanel, BorderLayout.NORTH);
	}

	private void createMainPane() {
		mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainPane.setDividerLocation(0.5);

		bottomPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		bottomPane.setDividerLocation(0.5);

		mainPane.setBottomComponent(bottomPane);
		content.add(mainPane, BorderLayout.CENTER);
		// content.add(createToolBar(), BorderLayout.NORTH);
	}

	private void createTreePane() {
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
		mainPane.setTopComponent(scrollTree);

		selectPanel.btnCapture.addActionListener(getPngAction(viewer));
	}

	private void createTablePane() {
		table = new JTable();
		table.setFillsViewportHeight(true);
		JScrollPane scrollTable = new JScrollPane(table);
		bottomPane.setLeftComponent(scrollTable);
	}

	private void createTextPane() {
		textPane = new JTextPane();
		JScrollPane scrollText = new JScrollPane(textPane);
		LineRuler nums = new LineRuler(textPane);
		scrollText.setRowHeaderView(nums);
		bottomPane.setRightComponent(scrollText);
	}

	@SuppressWarnings("unused")
	private JToolBar createToolBar() {
		JToolBar bar = new JToolBar();
		bar.add(getOpenAction()).setText("");
		bar.addSeparator();
		bar.add(getPngAction(viewer)).setText("");
		return bar;
	}

	@Override
	protected void applyCustomPrefs() {
		// Dimension minimumSize = new Dimension(100, 50);
		// scrollTree.setMinimumSize(minimumSize);
		// scrollText.setMinimumSize(minimumSize);
		// scrollTable.setMinimumSize(minimumSize);
		int split = prefs.getInt(KEY_SPLIT_VERT, 300);
		mainPane.setDividerLocation(split);
		split = prefs.getInt(KEY_SPLIT_HORZ, 350);
		bottomPane.setDividerLocation(split);
	}

	@Override
	protected void saveCustomPrefs(Preferences prefs) {
		prefs.putInt(KEY_SPLIT_VERT, mainPane.getDividerLocation());
		prefs.putInt(KEY_SPLIT_HORZ, bottomPane.getDividerLocation());
	}

	@Override
	protected String getBaseName() {
		return name;
	}

	// @Override
	// protected void handleFileOpen(File file) {
	// createData(file);
	// }

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

	public void loadTool(String lang) {
		tool = new Tool();
		tool.setCorpusRoot(corpusRoot);
		tool.setLang(lang);
		tool.setTabWidth(4);
		tool.setCheck(true);
		tool.setRebuild(false);

		boolean ok = tool.loadResources();
		ok = ok && tool.validateOptions();

		if (!ok) {
			Log.error(this, "Failed to initialize model");
		} else {
			process();
		}
	}

	public void process() {
		SourceListModel model = (SourceListModel) selectPanel.srcBox.getModel();
		String pathname = model.getSelectedPathname();
		tool.setSourceFiles(pathname);
		tool.execute();

		Record data = tool.getDocument().getParseRecord();

		// graphical rep of tree
		viewer.inspect(data.getParseTreeRoot(), data.getRuleNames());

		// tokens table
		TokenTableModel ttmodel = new TokenTableModel(data.getTokens(), data.getTokenNames());
		table.setModel(ttmodel);
		ttmodel.configCols(table);

		// indented string tree
		String tree = createTextTree(data.getParseTreeRoot(), data.getRuleNames());
		textPane.setText(tree);
		textPane.setCaretPosition(0);
	}

	/**
	 * Pretty print out a whole tree. {@link #getNodeText} is used on the node payloads to get the text
	 * for the nodes. (Derived from Trees.toStringTree(....))
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
