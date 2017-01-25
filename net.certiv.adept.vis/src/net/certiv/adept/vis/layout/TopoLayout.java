package net.certiv.adept.vis.layout;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.util.RandomLocationTransformer;
import edu.uci.ics.jung.algorithms.shortestpath.Distance;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.algorithms.util.IterativeContext;
import edu.uci.ics.jung.algorithms.util.MapSettableTransformer;
import edu.uci.ics.jung.graph.Graph;
import net.certiv.adept.model.Edge;
import net.certiv.adept.model.Feature;

public class TopoLayout extends AbstractLayout<Feature, Edge> implements IterativeContext {

	private static final double UNIT = 0.8;
	private static final int MAX = 5;

	private String status = "Topo Layout";
	private int currentIteration;
	private int L; // the ideal length of an edge
	private MapSettableTransformer<Edge, EdgeData> edt;

	private Point2D[] xydata;

	// key=feature; value=vertex index
	private BiMap<Feature, Integer> vertices;
	/** Graph distances between vertices of the visible graph */
	protected Distance<Feature> distance;
	/** The diameter of the visible graph. */
	protected double diameter;
	private int vertexCnt;

	public TopoLayout(Graph<Feature, Edge> graph, MapSettableTransformer<Edge, EdgeData> edt) {
		super(graph);
		this.edt = edt;
		this.distance = new UnweightedShortestPath<Feature, Edge>(graph);
	}

	@Override
	public void initialize() {
		currentIteration = 0;
		if (graph != null && size != null) {
			vertexCnt = graph.getVertexCount();
			if (vertexCnt < 2) return;

			vertices = HashBiMap.create(vertexCnt);
			xydata = new Point2D[vertexCnt];

			while (true) {
				try {

					// assign indices to vertices
					int index = 0;
					for (Feature feature : graph.getVertices()) {
						vertices.put(feature, index);
						index++;
					}

					// calculate unit length
					List<Edge> edges = new ArrayList<>(graph.getEdges());
					List<Feature> roots = getRoots(edges);
					double oX = size.getWidth() / (roots.size() + 1);
					double oY = size.getHeight() / 2;
					int range = getRange(edges);
					double L0 = Math.min(size.getHeight(), size.getWidth());
					L = (int) Math.round((L0 / range) * UNIT);

					// assign locations to vertices
					for (Edge edge : edges) {
						Feature f1 = edge.root;
						Feature f2 = edge.leaf;

						EdgeData data = edt.apply(edge);
						int v1 = vertices.get(f1);
						int v2 = vertices.get(f2);
						Point2D xy1 = xydata[v1];
						Point2D xy2 = xydata[v2];
						if (xy1 == null && xy2 == null) {
							xydata[v1] = new Point2D.Double(0, 0);
							xydata[v2] = data.getRelative(xydata[v1]);
						} else if (xy1 == null) {
							xydata[v1] = data.getRelativeInv(xydata[v2]);
						} else if (xy2 == null) {
							xydata[v2] = data.getRelative(xydata[v1]);
						}
					}

					// scale & shift locations
					for (int i = 0; i < vertexCnt; i++) {
						Feature feature = vertices.inverse().get(i);
						int m = roots.indexOf(feature) + 1;
						double x = xydata[i].getX() * L + m * oX;
						double y = xydata[i].getY() * L + oY;
						xydata[i].setLocation(x, y);
					}

					break;
				} catch (ConcurrentModificationException cme) {}
			}
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

	private int getRange(List<Edge> edges) {
		double maxX = Double.NEGATIVE_INFINITY;
		double minX = Double.POSITIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		for (Edge edge : edges) {
			Point2D xy = edt.apply(edge).deltaXY;
			maxX = Math.max(maxX, xy.getX());
			minX = Math.min(minX, xy.getX());
			maxY = Math.max(maxY, xy.getY());
			minY = Math.min(minY, xy.getY());
		}
		return (int) Math.round(Math.max(maxX - minX, maxY - minY));
	}

	@Override
	public void step() {
		currentIteration++;
		if (vertexCnt > 1) {
			adjustSpacing();
			center();
			for (int i = 0; i < vertexCnt; i++) {
				Feature feature = vertices.inverse().get(i);
				setLocation(feature, xydata[i]);
			}
		}
	}

	/** Shift vertices relative to screen center. */
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

	private void adjustSpacing() {
		int offset = L * 2;
		int n = graph.getVertexCount();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) continue;

				double ix = xydata[i].getX();
				double iy = xydata[i].getY();
				double jx = xydata[j].getX();
				double jy = xydata[j].getY();

				if (within(ix, iy, offset * 2 - 1)) {
					ix += offset;
					jx -= offset;
				}
				if (within(jx, jy, offset * 2 - 1)) {
					iy += offset;
					jy -= offset;
				}

				xydata[i].setLocation(ix, iy);
				xydata[j].setLocation(jx, jy);
			}
		}
	}

	private boolean within(double p1, double p2, int range) {
		int diff = (int) Math.abs(Math.round(p1) - Math.round(p2));
		return diff < range;
	}

	@Override
	public void setSize(Dimension size) {
		if (initialized == false) setInitializer(new RandomLocationTransformer<Feature>(size));
		super.setSize(size);
	}

	public String getStatus() {
		return status + this.getSize();
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
}
