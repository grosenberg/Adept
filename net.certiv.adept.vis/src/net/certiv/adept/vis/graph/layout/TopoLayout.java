package net.certiv.adept.vis.graph.layout;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.util.RandomLocationTransformer;
import edu.uci.ics.jung.algorithms.util.IterativeContext;
import edu.uci.ics.jung.graph.Graph;
import net.certiv.adept.model.Edge;
import net.certiv.adept.model.Feature;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Log.LogLevel;
import net.certiv.adept.vis.components.TopoPanel;

public class TopoLayout extends AbstractLayout<Feature, Edge> implements IterativeContext {

	private static final double UNIT = 0.9;
	private static final int MAX = 20;

	private String status = "Topo Layout";
	private int currentIteration;
	private int vertexCnt;

	// key=feature; value=vertex index
	private BiMap<Feature, Integer> vertices;
	private Point2D[] location;

	private List<Edge> edges;
	private List<Feature> roots;

	private TopoPanel dialog;

	public TopoLayout(Graph<Feature, Edge> graph, TopoPanel dialog) {
		super(graph);
		this.dialog = dialog;
		Log.defLevel(LogLevel.Debug);
	}

	@Override
	public void initialize() {
		if (graph == null || size == null) return;

		currentIteration = 0;
		if (!createIndexes()) return;

		if (dialog != null) {
			dialog.clear();
			dialog.load(vertices, roots.get(0));
		}
		initializeLocations();
		if (dialog != null) dialog.setInitLocations(location);

		adjustEdgeLengths();
		if (dialog != null) dialog.setStepLocations(location);
	}

	private void adjustEdgeLengths() {
		Point len = calcUnitLength();
		for (Point2D vertex : location) {
			vertex.setLocation(vertex.getX() * len.getX(), vertex.getY() * len.getY());
		}
	}

	private boolean createIndexes() {
		Collection<Feature> features;
		while (true) {
			try {
				vertexCnt = graph.getVertexCount();
				features = graph.getVertices();
				break;
			} catch (ConcurrentModificationException cme) {}
		}
		if (vertexCnt < 2) return false;

		vertices = HashBiMap.create(vertexCnt);
		location = new Point2D[vertexCnt];
		edges = new ArrayList<>(graph.getEdges());
		roots = getRoots(edges);

		// assign indices to vertices
		int index = 0;
		for (Feature feature : features) {
			vertices.put(feature, index);
			index++;
		}
		return true;
	}

	// assign locations to vertices
	private void initializeLocations() {
		for (Edge edge : edges) {
			Feature root = edge.root;
			Feature leaf = edge.leaf;
			int vroot = vertices.get(root);
			int vleaf = vertices.get(leaf);
			Point2D proot = location[vroot];
			Point2D pleaf = location[vleaf];
			if (proot == null) {
				location[vroot] = new Point2D.Double(root.getVisCol(), root.getLine());
			}
			if (pleaf == null) {
				location[vleaf] = new Point2D.Double(leaf.getVisCol(), leaf.getLine());
			}
		}
	}

	private List<Feature> getRoots(List<Edge> edges) {
		List<Feature> roots = new ArrayList<>();
		for (Edge edge : edges) {
			Feature root = edge.root;
			if (!roots.contains(root)) roots.add(root);
		}
		return roots;
	}

	private Point calcUnitLength() {
		Point range = getRange();
		double x = (size.getWidth() * UNIT) / range.getX();
		double y = (size.getHeight() * UNIT) / range.getY();
		range.setLocation(x, y);
		return range;
	}

	private Point getRange() {
		double maxX = Double.NEGATIVE_INFINITY;
		double minX = Double.POSITIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		for (Point2D vertex : location) {
			maxX = Math.max(maxX, vertex.getX());
			minX = Math.min(minX, vertex.getX());
			maxY = Math.max(maxY, vertex.getY());
			minY = Math.min(minY, vertex.getY());
		}
		return new Point((int) Math.round(maxX - minX), (int) Math.round(maxY - minY));
	}

	@Override
	public void step() {
		currentIteration++;
		// Log.debug(this, "Step " + currentIteration);
		if (vertexCnt > 1) {
			adjustSpacing();
			center();
			for (int i = 0; i < vertexCnt; i++) {
				Feature feature = vertices.inverse().get(i);
				setLocation(feature, location[i]);
			}

			if (dialog != null) dialog.setStepLocations(location);
		}
	}

	private void adjustSpacing() {
		Point range = calcUnitLength();
		int n = graph.getVertexCount();

		Dimension d = getSize();
		double cx = d.getWidth() / 2;
		double cy = d.getHeight() / 2;
		double guard = 150;
		double delta = 0.75;

		// rescale
		for (int i = 0; i < n; i++) {
			double ix = location[i].getX();
			double iy = location[i].getY();
			ix = ((ix - cx) * range.getX()) + cx;
			iy = ((iy - cy) * range.getY()) + cy;
			location[i].setLocation(ix, iy);
		}

		// nudge
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) continue;

				double ix = location[i].getX();
				double iy = location[i].getY();
				double jx = location[j].getX();
				double jy = location[j].getY();

				double dx = Math.abs(jx - ix);
				double dy = Math.abs(jy - iy);
				double dz = Math.sqrt(dx * dx + dy * dy);

				if (dz < guard) {
					ix -= dx * delta + 10;
					iy -= dy * delta + 10;
					jx += dx * delta + 10;
					jy += dy * delta + 10;
				}
				location[i].setLocation(ix, iy);
				location[j].setLocation(jx, jy);
			}
		}
	}

	// Shift vertices relative to screen center
	private void center() {
		Dimension d = getSize();
		double height = d.getHeight();
		double width = d.getWidth();
		double gx = 0;
		double gy = 0;
		for (int i = 0; i < location.length; i++) {
			gx += location[i].getX();
			gy += location[i].getY();
		}
		gx /= location.length;
		gy /= location.length;
		double diffx = width / 2 - gx;
		double diffy = height / 2 - gy;
		for (int i = 0; i < location.length; i++) {
			location[i].setLocation(location[i].getX() + diffx, location[i].getY() + diffy);
		}
	}

	public String getStatus() {
		return status + this.getSize();
	}

	@Override
	public void setSize(Dimension size) {
		if (initialized == false) setInitializer(new RandomLocationTransformer<Feature>(size));
		super.setSize(size);
	}

	@Override
	public void reset() {
		currentIteration = 0;
	}

	@Override
	public boolean done() {
		if (currentIteration >= MAX) return true;
		return false;
	}

	/** Used for changing the size of the layout in response to a component's size. */
	public class ResizeListener extends ComponentAdapter {

		@Override
		public void componentResized(ComponentEvent e) {
			setSize(e.getComponent().getSize());
		}
	}
}
