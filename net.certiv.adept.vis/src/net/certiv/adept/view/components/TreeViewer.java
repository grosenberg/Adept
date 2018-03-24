package net.certiv.adept.view.components;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.abego.treelayout.NodeExtentProvider;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.antlr.v4.gui.TreeLayoutAdaptor;
import org.antlr.v4.gui.TreeTextProvider;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.tree.Trees;

public class TreeViewer extends TreeViewerBase {

	public static class DefaultTreeTextProvider implements TreeTextProvider {

		private final List<String> ruleNames;

		public DefaultTreeTextProvider(List<String> ruleNames) {
			this.ruleNames = ruleNames;
		}

		@Override
		public String getText(Tree node) {
			return String.valueOf(Trees.getNodeText(node, ruleNames));
		}
	}

	public static class VariableExtentProvider implements NodeExtentProvider<Tree> {

		TreeViewer viewer;

		public VariableExtentProvider(TreeViewer viewer) {
			this.viewer = viewer;
		}

		@Override
		public double getWidth(Tree tree) {
			FontMetrics fontMetrics = viewer.getFontMetrics(viewer.font);
			String s = viewer.getText(tree);
			int w = fontMetrics.stringWidth(s) + viewer.nodeWidthPadding * 2;
			return w;
		}

		@Override
		public double getHeight(Tree tree) {
			FontMetrics fontMetrics = viewer.getFontMetrics(viewer.font);
			int h = fontMetrics.getHeight() + viewer.nodeHeightPadding * 2;
			String s = viewer.getText(tree);
			String[] lines = s.split("\n");
			return h * lines.length;
		}
	}

	protected TreeTextProvider treeTextProvider;
	protected TreeLayout<Tree> treeLayout;
	protected List<Tree> highlightedNodes;

	public TreeViewer() {
		super();
		setFont(font);
	}

	public void inspect(ParserRuleContext tree, List<String> ruleNames) {
		setRuleNames(ruleNames);
		if (tree != null) setTree(tree);
	}

	// ---------------------------------------------------

	@Override
	protected Rectangle2D.Double getBoundsOfNode(Tree node) {
		return treeLayout.getNodeBounds().get(node);
	}

	@Override
	protected String getText(Tree tree) {
		String s = treeTextProvider.getText(tree);
		s = Utils.escapeWhitespace(s, true);
		return s;
	}

	public TreeTextProvider getTreeTextProvider() {
		return treeTextProvider;
	}

	public void setTreeTextProvider(TreeTextProvider treeTextProvider) {
		this.treeTextProvider = treeTextProvider;
	}

	/** Slow for big lists of highlighted nodes */
	public void addHighlightedNodes(Collection<Tree> nodes) {
		highlightedNodes = new ArrayList<>();
		highlightedNodes.addAll(nodes);
	}

	public void removeHighlightedNodes(Collection<Tree> nodes) {
		if (highlightedNodes != null) {
			// only remove exact objects defined by ==, not equals()
			for (Tree t : nodes) {
				int i = getHighlightedNodeIndex(t);
				if (i >= 0) highlightedNodes.remove(i);
			}
		}
	}

	@Override
	protected boolean isHighlighted(Tree node) {
		return getHighlightedNodeIndex(node) >= 0;
	}

	protected int getHighlightedNodeIndex(Tree node) {
		if (highlightedNodes == null) return -1;
		for (int i = 0; i < highlightedNodes.size(); i++) {
			Tree t = highlightedNodes.get(i);
			if (t == node) return i;
		}
		return -1;
	}

	@Override
	protected TreeLayout<Tree> getTreeLayout() {
		return treeLayout;
	}

	@Override
	protected TreeForTreeLayout<Tree> getTree() {
		return treeLayout.getTree();
	}

	public void setTree(Tree root) {
		if (root != null) {
			boolean useIdentity = true; // compare node identity
			this.treeLayout = new TreeLayout<>(getTreeLayoutAdaptor(root), new TreeViewer.VariableExtentProvider(this),
					new DefaultConfiguration<Tree>(gapBetweenLevels, gapBetweenNodes), useIdentity);
			updatePreferredSize();
		} else {
			this.treeLayout = null;
			repaint();
		}
	}

	/**
	 * Get an adaptor for root that indicates how to walk ANTLR trees. Override to change the adapter
	 * from the default of {@link TreeLayoutAdaptor}
	 */
	public TreeForTreeLayout<Tree> getTreeLayoutAdaptor(Tree root) {
		return new TreeLayoutAdaptor(root);
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		if (scale <= 0) {
			scale = 1;
		}
		this.scale = scale;
		updatePreferredSize();
	}

	public void setRuleNames(List<String> ruleNames) {
		setTreeTextProvider(new DefaultTreeTextProvider(ruleNames));
	}

	private void updatePreferredSize() {
		setPreferredSize(getScaledTreeSize());
		invalidate();
		if (getParent() != null) {
			getParent().validate();
		}
		repaint();
	}

	private Dimension getScaledTreeSize() {
		Dimension scaledTreeSize = treeLayout.getBounds().getBounds().getSize();
		scaledTreeSize = new Dimension((int) (scaledTreeSize.width * scale), (int) (scaledTreeSize.height * scale));
		return scaledTreeSize;
	}

	public Tree getGridTree(int x, int y) {
		int col = (int) Math.round(x / gapBetweenLevels);
		int row = (int) Math.round(y / gapBetweenLevels);
		Tree tree = table.get(col, row);
		if (tree == null) {
			tree = table.get(col - 1, row);
		}
		return tree;
	}
}
