package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.google.common.base.Function;
import com.google.common.base.Functions;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.GraphMouseListener;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.renderers.BasicVertexLabelRenderer.InsidePositioner;
import edu.uci.ics.jung.visualization.renderers.GradientVertexRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import net.certiv.adept.Tool;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Edge;
import net.certiv.adept.model.Feature;
import net.certiv.adept.util.Log;

public class DataVisualization {

	DirectedSparseGraph<Feature, Edge> graph;
	VisualizationViewer<Feature, Edge> viewer;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		new DataVisualization();
	}

	public DataVisualization() {
		graph = new DirectedSparseGraph<>();

		Tool tool = new Tool();
		tool.setCorpusRoot("corpus");
		tool.setLang("antlr");
		tool.setTabWidth(4);
		// tool.setRebuild(true);

		boolean ok = tool.loadResources();
		ok = ok && tool.validateOptions();

		if (!ok) {
			Log.error(this, "Failed to initialize model");
			return;
		}

		int id = Document.getDocId("corpus\\antlr\\docs\\AntlrParser.g4");
		List<Feature> data = Tool.mgr.getCorpusModel().getDocFeatures().get(id);
		createEdges(data);

		Layout<Feature, Edge> layout = new CircleLayout<Feature, Edge>(graph);
		layout.setSize(new Dimension(1024, 1024));
		viewer = new VisualizationViewer<Feature, Edge>(layout);
		viewer.addGraphMouseListener(new TestGraphMouseListener<Feature>());
		viewer.getRenderer().setVertexRenderer(new GradientVertexRenderer<Feature, Edge>(Color.white, Color.red,
				Color.white, Color.blue, viewer.getPickedVertexState(), false));
		viewer.setForeground(Color.darkGray);
		viewer.setBackground(Color.white);

		viewer.getRenderContext().setEdgeDrawPaintTransformer(Functions.<Paint>constant(Color.lightGray));
		viewer.getRenderContext().setArrowFillPaintTransformer(Functions.<Paint>constant(Color.lightGray));
		viewer.getRenderContext().setArrowDrawPaintTransformer(Functions.<Paint>constant(Color.lightGray));
		viewer.getRenderer().getVertexLabelRenderer().setPositioner(new InsidePositioner());
		viewer.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.AUTO);
		viewer.getRenderContext().setVertexLabelTransformer(new Function<Feature, String>() {

			@Override
			public String apply(Feature v) {
				return v.getAspect() + " " + v.getY();
			}

		});

		// add my listeners for ToolTips
		viewer.setVertexToolTipTransformer(new Function<Feature, String>() {

			@Override
			public String apply(Feature feature) {
				return feature.toString();
			}

		});
		viewer.setEdgeToolTipTransformer(new Function<Edge, String>() {

			@Override
			public String apply(Edge edge) {
				return edge.toString();
			}
		});

		// create a frome to hold the graph
		final JFrame frame = new JFrame("Adept Data Visiualization");
		ImageIcon imgicon = new ImageIcon(getClass().getClassLoader().getResource("vis.gif"));
		frame.setIconImage(imgicon.getImage());

		Container content = frame.getContentPane();
		content.setPreferredSize(new Dimension(1256, 1024));
		final GraphZoomScrollPane panel = new GraphZoomScrollPane(viewer);
		content.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse<String, Number>();
		viewer.setGraphMouse(graphMouse);

		viewer.addKeyListener(graphMouse.getModeKeyListener());
		viewer.setToolTipText("<html>Type 'p' for Pick mode<p>Type 't' for Transform mode");

		final ScalingControl scaler = new CrossoverScalingControl();

		JButton plus = new JButton("+");
		plus.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				scaler.scale(viewer, 1.1f, viewer.getCenter());
			}
		});
		JButton minus = new JButton("-");
		minus.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				scaler.scale(viewer, 1 / 1.1f, viewer.getCenter());
			}
		});

		JButton reset = new JButton("reset");
		reset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				viewer.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).setToIdentity();
				viewer.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).setToIdentity();
			}
		});

		JPanel controls = new JPanel();
		controls.add(plus);
		controls.add(minus);
		controls.add(reset);
		content.add(controls, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true);
	}

	void createEdges(List<Feature> features) {
		int count = 0;
		for (Feature feature : features) {
			if (count > 30) break;
			count++;
			graph.addVertex(feature);
			Collection<List<Edge>> edgeLists = feature.getEdges().values();
			for (List<Edge> edges : edgeLists) {
				for (Edge edge : edges) {
					graph.addEdge(edge, feature, edge.leaf, EdgeType.DIRECTED);
				}
			}
		}
	}

	static class TestGraphMouseListener<V> implements GraphMouseListener<V> {

		public void graphClicked(V v, MouseEvent me) {
			System.err.println("Vertex " + v);
		}

		public void graphPressed(V v, MouseEvent me) {}

		public void graphReleased(V v, MouseEvent me) {}
	}
}
