package net.certiv.adept.vis.transformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.renderers.Renderer;

public class TopoVertexRenderer<V, E> implements Renderer.Vertex<V, E> {

	@Override
	public void paintVertex(RenderContext<V, E> rc, Layout<V, E> layout, V v) {}

}
