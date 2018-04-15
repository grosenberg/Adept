package net.certiv.adept.vis.graph.graph.transformer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import net.certiv.adept.model.Feature;

public class TopoVertexRenderer<V, E> implements Renderer.Vertex<V, E> {

	@Override
	public void paintVertex(RenderContext<V, E> rc, Layout<V, E> layout, V v) {
		Graph<V, E> graph = layout.getGraph();
		if (rc.getVertexIncludePredicate().apply(Context.<Graph<V, E>, V>getInstance(graph, v))) {
			Shape shape = rc.getVertexShapeTransformer().apply(v);

			Point2D p = layout.apply(v);
			p = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, p);
			float x = (float) p.getX();
			float y = (float) p.getY();

			// translate to the location of the vertex to be rendered
			AffineTransform xform = AffineTransform.getTranslateInstance(x, y);
			shape = xform.createTransformedShape(shape);

			if (vertexHit(rc, shape)) {
				paintShapeForVertex(rc, v, shape);
			}
		}
	}

	private boolean vertexHit(RenderContext<V, E> rc, Shape shape) {
		JComponent vv = rc.getScreenDevice();
		Rectangle rect = null;
		if (vv != null) {
			Dimension d = vv.getSize();
			rect = new Rectangle(0, 0, d.width, d.height);
		}
		return rc.getMultiLayerTransformer().getTransformer(Layer.VIEW).transform(shape).intersects(rect);
	}

	private void paintShapeForVertex(RenderContext<V, E> rc, V v, Shape shape) {
		Feature f = (Feature) v;
		Color base = Color.LIGHT_GRAY;
		switch (f.getKind()) {
			case BLOCKCOMMENT:
				base = new Color(160, 255, 160); // green
				break;
			case LINECOMMENT:
				base = new Color(254, 180, 200); // red
				break;
			case RULE:
				base = new Color(190, 200, 250); // blue
				break;
			case TERMINAL:
				base = new Color(220, 220, 220); // gray
				break;
		}

		GraphicsDecorator g = rc.getGraphicsContext();
		Paint oldPaint = g.getPaint();
		Rectangle r = shape.getBounds();
		float y2 = (float) r.getMaxY();

		Paint fillPaint = new GradientPaint((float) r.getMinX(), (float) r.getMinY(), base, (float) r.getMinX(), y2,
				base, false);

		if (fillPaint != null) {
			g.setPaint(fillPaint);
			g.fillRoundRect(r.x, r.y, r.width, r.height, 8, 8);
			// g.fill(shape);
			g.setPaint(oldPaint);
		}
		Paint drawPaint = rc.getVertexDrawPaintTransformer().apply(v);
		if (drawPaint != null) {
			g.setPaint(drawPaint);
		}
		Stroke oldStroke = g.getStroke();
		Stroke stroke = rc.getVertexStrokeTransformer().apply(v);
		if (stroke != null) {
			g.setStroke(stroke);
		}
		// g.draw(shape);
		g.drawRoundRect(r.x, r.y, r.width, r.height, 8, 8);
		g.setPaint(oldPaint);
		g.setStroke(oldStroke);
	}
}
