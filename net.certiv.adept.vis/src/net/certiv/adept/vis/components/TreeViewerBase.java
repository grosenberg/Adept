package net.certiv.adept.vis.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.Tree;

import com.google.common.collect.HashBasedTable;

import net.certiv.adept.vis.utils.Point;

public abstract class TreeViewerBase extends JComponent {

	public static final Color LIGHT_RED = new Color(244, 213, 211);

	protected String fontName = "Helvetica"; // Font.SANS_SERIF;
	protected int fontStyle = Font.PLAIN;
	protected int fontSize = 11;
	protected Font font = new Font(fontName, fontStyle, fontSize);

	protected double gapBetweenLevels = 17;
	protected double gapBetweenNodes = 7;
	protected int nodeWidthPadding = 2;  // added to left/right
	protected int nodeHeightPadding = 0; // added above/below
	protected int arcSize = 0;           // make an arc in node outline?

	protected double scale = 1.0;

	protected Color boxColor = null;     // set to a color to make it draw background

	protected Color highlightedBoxColor = Color.lightGray;
	protected Color borderColor = null;
	protected Color textColor = Color.black;

	protected HashBasedTable<Integer, Integer, Tree> table;

	public TreeViewerBase() {
		table = HashBasedTable.create();
	}

	// ----------------

	public void setFontSize(int sz) {
		fontSize = sz;
		font = new Font(fontName, fontStyle, fontSize);
	}

	public void setFontName(String name) {
		fontName = name;
		font = new Font(fontName, fontStyle, fontSize);
	}

	@Override
	public Font getFont() {
		return font;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	public int getArcSize() {
		return arcSize;
	}

	public void setArcSize(int arcSize) {
		this.arcSize = arcSize;
	}

	public Color getBoxColor() {
		return boxColor;
	}

	public void setBoxColor(Color boxColor) {
		this.boxColor = boxColor;
	}

	public Color getHighlightedBoxColor() {
		return highlightedBoxColor;
	}

	public void setHighlightedBoxColor(Color highlightedBoxColor) {
		this.highlightedBoxColor = highlightedBoxColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	// ---------------- PAINT -----------------------------------------------

	private boolean useCurvedEdges = false;

	public boolean getUseCurvedEdges() {
		return useCurvedEdges;
	}

	public void setUseCurvedEdges(boolean useCurvedEdges) {
		this.useCurvedEdges = useCurvedEdges;
	}

	protected void paintEdges(Graphics g, Tree parent) {
		if (!getTree().isLeaf(parent)) {
			BasicStroke stroke = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			((Graphics2D) g).setStroke(stroke);

			Rectangle2D.Double parentBounds = getBoundsOfNode(parent);
			double x1 = parentBounds.getCenterX();
			double y1 = parentBounds.getMaxY();
			for (Tree child : getTree().getChildren(parent)) {
				Rectangle2D.Double childBounds = getBoundsOfNode(child);
				double x2 = childBounds.getCenterX();
				double y2 = childBounds.getMinY();
				if (getUseCurvedEdges()) {
					CubicCurve2D c = new CubicCurve2D.Double();
					double ctrlx1 = x1;
					double ctrly1 = (y1 + y2) / 2;
					double ctrlx2 = x2;
					double ctrly2 = y1;
					c.setCurve(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2);
					((Graphics2D) g).draw(c);
				} else {
					g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
				}
				paintEdges(g, child);
			}
		}
	}

	protected abstract TreeForTreeLayout<Tree> getTree();

	protected abstract Rectangle2D.Double getBoundsOfNode(Tree node);

	protected abstract boolean isHighlighted(Tree node);

	protected abstract String getText(Tree tree);

	protected void paintBox(Graphics g, Tree tree) {
		Rectangle2D.Double box = getBoundsOfNode(tree);
		Point grid = calcGrid(box);
		table.put(grid.getCol(), grid.getLine(), tree);

		// draw the box in the background
		boolean ruleFailedAndMatchedNothing = false;
		if (tree instanceof ParserRuleContext) {
			ParserRuleContext ctx = (ParserRuleContext) tree;
			ruleFailedAndMatchedNothing = ctx.exception != null && ctx.stop != null
					&& ctx.stop.getTokenIndex() < ctx.start.getTokenIndex();
		}
		if (isHighlighted(tree) || boxColor != null || tree instanceof ErrorNode || ruleFailedAndMatchedNothing) {
			if (isHighlighted(tree)) {
				g.setColor(highlightedBoxColor);
			} else if (tree instanceof ErrorNode || ruleFailedAndMatchedNothing) {
				g.setColor(LIGHT_RED);
			} else {
				g.setColor(boxColor);
			}
			g.fillRoundRect((int) box.x, (int) box.y, (int) box.width - 1, (int) box.height - 1, arcSize, arcSize);
		}
		if (borderColor != null) {
			g.setColor(borderColor);
			g.drawRoundRect((int) box.x, (int) box.y, (int) box.width - 1, (int) box.height - 1, arcSize, arcSize);
		}

		// draw the text on top of the box (possibly multiple lines)
		g.setColor(textColor);
		String s = getText(tree);
		String[] lines = s.split("\n");
		FontMetrics m = getFontMetrics(font);
		int x = (int) box.x + arcSize / 2 + nodeWidthPadding;
		int y = (int) box.y + m.getAscent() + m.getLeading() + 1 + nodeHeightPadding;
		for (int i = 0; i < lines.length; i++) {
			text(g, lines[i], x, y);
			y += m.getHeight();
		}
	}

	private Point calcGrid(Rectangle2D.Double box) {
		int x = (int) Math.round(box.getCenterX() / gapBetweenLevels);
		int y = (int) Math.round(box.getCenterY() / gapBetweenLevels);
		return new Point(x, y);
	}

	public void text(Graphics g, String s, int x, int y) {
		s = Utils.escapeWhitespace(s, true);
		g.drawString(s, x, y);
	}

	protected abstract TreeLayout<Tree> getTreeLayout();

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (getTreeLayout() == null) return;

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		paintEdges(g, getTree().getRoot());
		for (Tree Tree : getTreeLayout().getNodeBounds().keySet()) {
			paintBox(g, Tree);
		}
	}

	@Override
	protected Graphics getComponentGraphics(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.scale(scale, scale);
		return super.getComponentGraphics(g2d);
	}
}
