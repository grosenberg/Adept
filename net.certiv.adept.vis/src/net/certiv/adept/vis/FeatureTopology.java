package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.google.common.base.Function;
import com.google.common.base.Functions;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
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
import net.certiv.adept.model.Edge;
import net.certiv.adept.model.Feature;
import net.certiv.adept.parser.ISourceParser;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.models.FeaturesListModel;
import net.certiv.adept.vis.models.FeaturesListModel.FeatureListItem;

public class FeatureTopology {

	private static final String KEY_WIDTH = "frame_width";
	private static final String KEY_HEIGHT = "frame_height";
	private static final String KEY_X = "frame_x";
	private static final String KEY_Y = "frame_y";

	private Preferences prefs;
	private DirectedSparseGraph<Feature, Edge> graph;
	private VisualizationViewer<Feature, Edge> viewer;
	private JComboBox<FeatureListItem> typeBox;
	private FeaturesListModel model;
	private ISourceParser lang;
	private Map<Integer, List<Feature>> index;
	private JFrame frame;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		FeatureTopology ft = new FeatureTopology();
		ft.createBoxModel();
	}

	public FeatureTopology() {
		frame = new JFrame("Feature Topology");
		ImageIcon imgicon = new ImageIcon(getClass().getClassLoader().getResource("vis.gif"));
		frame.setIconImage(imgicon.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		prefs = Preferences.userNodeForPackage(FeatureTopology.class);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				prefs.putDouble(KEY_X, frame.getLocationOnScreen().getX());
				prefs.putDouble(KEY_Y, frame.getLocationOnScreen().getY());
				prefs.putInt(KEY_WIDTH, (int) frame.getSize().getWidth());
				prefs.putInt(KEY_HEIGHT, (int) frame.getSize().getHeight());
			}
		});

		Container content = frame.getContentPane();
		content.setPreferredSize(new Dimension(1256, 1024));

		graph = new DirectedSparseGraph<>();
		viewer = new VisualizationViewer<Feature, Edge>(getLayout());
		viewer.addGraphMouseListener(new GraphMouseAdaptor<Feature>());
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

		final GraphZoomScrollPane panel = new GraphZoomScrollPane(viewer);
		content.add(panel);

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

		typeBox = new JComboBox<>();
		typeBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateGraph();
			}
		});

		controls = new JPanel();
		controls.add(typeBox);
		content.add(controls, BorderLayout.NORTH);

		int width = prefs.getInt(KEY_WIDTH, 600);
		int height = prefs.getInt(KEY_HEIGHT, 600);
		content.setPreferredSize(new Dimension(width, height));
		frame.pack();

		if (prefs.getDouble(KEY_X, -1) != -1) {
			frame.setLocation((int) prefs.getDouble(KEY_X, 100), (int) prefs.getDouble(KEY_Y, 100));
		}

		frame.setVisible(true);
	}

	protected void createBoxModel() {
		Tool tool = new Tool();
		tool.setCheck(true);
		tool.setCorpusRoot("../net.certiv.adept.core/corpus");
		tool.setLang("antlr");
		tool.setTabWidth(4);

		tool.setRebuild(true);

		boolean ok = tool.loadResources();
		ok = ok && tool.validateOptions();

		if (!ok) {
			Log.error(this, "Failed to initialize model");
			return;
		}

		lang = Tool.mgr.getLanguageParser();
		index = Tool.mgr.getCorpusModel().getFeatureIndex();
		model = new FeaturesListModel(lang.getRuleNames(), lang.getTokenNames());
		typeBox.setModel(model);
		model.addElements(index);
	}

	protected void updateGraph() {
		clearGraph();
		createGraph(model.getSelectedFeatures());

		Layout<Feature, Edge> layout = getLayout();
		viewer.getModel().setGraphLayout(layout);
	}

	private void clearGraph() {
		List<Feature> features = new ArrayList<>(graph.getVertices());
		for (Feature feature : features) {
			Collection<List<Edge>> edgeLists = feature.getEdges().values();
			for (List<Edge> edges : edgeLists) {
				for (Edge edge : edges) {
					graph.removeEdge(edge);
				}
			}
			graph.removeVertex(feature);
		}
	}

	void createGraph(List<Feature> features) {
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

	private Layout<Feature, Edge> getLayout() {
		Layout<Feature, Edge> layout = new FRLayout<Feature, Edge>(graph);
		layout.setSize(new Dimension(1024, 1024));
		return layout;
	}

	static class GraphMouseAdaptor<V> implements GraphMouseListener<V> {

		public void graphClicked(V v, MouseEvent me) {
			System.err.println("Vertex " + v);
		}

		public void graphPressed(V v, MouseEvent me) {}

		public void graphReleased(V v, MouseEvent me) {}
	}
}
