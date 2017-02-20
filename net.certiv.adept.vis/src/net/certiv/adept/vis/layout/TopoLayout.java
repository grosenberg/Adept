package net.certiv.adept.vis.layout;

import java.awt.Dimension;
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
import edu.uci.ics.jung.algorithms.util.MapSettableTransformer;
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
	private Point2D[] xydata;
	// private MapSettableTransformer<Edge, EdgeData> transform;

	private List<Edge> edges;
	private List<Feature> roots;

	private TopoPanel detail;

	public TopoLayout(Graph<Feature, Edge> graph, MapSettableTransformer<Edge, EdgeData> transform, TopoPanel detail) {
		super(graph);
		// this.transform = transform;
		this.detail = detail;
		Log.defLevel(LogLevel.Debug);
	}

	@Override
	public void initialize() {
		if (graph != null && size != null) {
			currentIteration = 0;
			Collection<Feature> features;
			while (true) {
				try {
					vertexCnt = graph.getVertexCount();
					features = graph.getVertices();
					break;
				} catch (ConcurrentModificationException cme) {}
			}
			if (vertexCnt < 2) return;

			vertices = HashBiMap.create(vertexCnt);
			xydata = new Point2D[vertexCnt];
			edges = new ArrayList<>(graph.getEdges());
			roots = getRoots(edges);

			// assign indices to vertices
			int index = 0;
			for (Feature feature : features) {
				vertices.put(feature, index);
				index++;
			}

			if (detail != null) {
				detail.load(vertices, roots.get(0));
			}

			// assign locations to vertices
			for (Edge edge : edges) {
				Feature root = edge.root;
				Feature leaf = edge.leaf;
				int vroot = vertices.get(root);
				int vleaf = vertices.get(leaf);
				Point2D proot = xydata[vroot];
				Point2D pleaf = xydata[vleaf];
				if (proot == null) {
					xydata[vroot] = new Point2D.Double(root.getCol(), root.getLine());
				}
				if (pleaf == null) {
					xydata[vleaf] = new Point2D.Double(leaf.getCol(), leaf.getLine());
				}
			}
			if (detail != null) detail.setInitLocations(xydata);

			// determine initial unit length
			double L = calcUnitLength();

			// adust vertex locations for unit length
			for (Point2D vertex : xydata) {
				vertex.setLocation(vertex.getX() * L, vertex.getY() * L);
			}
			if (detail != null) detail.setStepLocations(xydata);
		}
	}

	private List<Feature> getRoots(List<Edge> edges) {
		List<Feature> roots = new ArrayList<>();
		for (Edge edge : edges) {
			Feature f1 = edge.root;
			if (!roots.contains(f1)) roots.add(f1);
		}
		return roots;
	}

	private double calcUnitLength() {
		int range = getRange();
		double L0 = Math.min(size.getHeight(), size.getWidth());
		double L1 = (L0 * UNIT) / range;
		// Log.debug(this, String.format("Unit length: %s (%s) %s", range, L0, L1));
		return L1;
	}

	private int getRange() {
		double maxX = Double.NEGATIVE_INFINITY;
		double minX = Double.POSITIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		for (Point2D vertex : xydata) {
			maxX = Math.max(maxX, vertex.getX());
			minX = Math.min(minX, vertex.getX());
			maxY = Math.max(maxY, vertex.getY());
			minY = Math.min(minY, vertex.getY());
		}
		return (int) Math.round(Math.max(maxX - minX, maxY - minY));
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
				setLocation(feature, xydata[i]);
			}

			if (detail != null) detail.setStepLocations(xydata);
		}
	}

	private void adjustSpacing() {
		double Ladj = calcUnitLength();
		int n = graph.getVertexCount();

		Dimension d = getSize();
		double cx = d.getWidth() / 2;
		double cy = d.getHeight() / 2;
		double guard = 50;
		double delta = 0.5;

		// rescale
		for (int i = 0; i < n; i++) {
			double ix = xydata[i].getX();
			double iy = xydata[i].getY();

			ix = ((ix - cx) * Ladj) + cx;
			iy = ((iy - cy) * Ladj) + cy;

			xydata[i].setLocation(ix, iy);
		}

		// nudge
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) continue;

				double ix = xydata[i].getX();
				double iy = xydata[i].getY();
				double jx = xydata[j].getX();
				double jy = xydata[j].getY();

				double dx = Math.abs(jx - ix);
				double dy = Math.abs(jy - iy);
				double dz = Math.sqrt(dx * dx + dy * dy);

				if (dz < guard) {
					ix -= dx * delta + 10;
					iy -= dy * delta + 10;
					jx += dx * delta + 10;
					jy += dy * delta + 10;
				}
				xydata[i].setLocation(ix, iy);
				xydata[j].setLocation(jx, jy);
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
		for (int i = 0; i < xydata.length; i++) {
			gx += xydata[i].getX();
			gy += xydata[i].getY();
		}
		gx /= xydata.length;
		gy /= xydata.length;
		double diffx = width / 2 - gx;
		double diffy = height / 2 - gy;
		for (int i = 0; i < xydata.length; i++) {
			xydata[i].setLocation(xydata[i].getX() + diffx, xydata[i].getY() + diffy);
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
