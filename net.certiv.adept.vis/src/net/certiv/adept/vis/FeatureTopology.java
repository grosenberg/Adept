package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.common.base.Function;
import com.google.common.base.Functions;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.util.MapSettableTransformer;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.renderers.BasicVertexLabelRenderer.InsidePositioner;
import edu.uci.ics.jung.visualization.renderers.GradientVertexRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import net.certiv.adept.Tool;
import net.certiv.adept.model.Edge;
import net.certiv.adept.model.Feature;
import net.certiv.adept.parser.ISourceParser;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.components.AbstractBase;
import net.certiv.adept.vis.components.Counter;
import net.certiv.adept.vis.layout.EdgeData;
import net.certiv.adept.vis.layout.TopoLayout;
import net.certiv.adept.vis.models.FeaturesListModel;
import net.certiv.adept.vis.models.FeaturesListModel.FeatureListItem;

public class FeatureTopology extends AbstractBase {

	private static final String KEY_START = "vertex_start";
	private static final String KEY_LIMIT = "vertex_limit";

	private static final String TOPO = "Topo";
	private static final String KK = "KK";
	private static final String SPRING = "Spring";
	private static final String FR = "FR";

	private static final String[] LAYOUTS = { TOPO, KK, SPRING, FR };

	private JComboBox<String> layoutBox;
	private JComboBox<FeatureListItem> typeBox;
	private JSlider idxSlider;	// offset into the list of features to display
	private Counter idxCounter;
	private JSlider numSlider;	// count of features to display together
	private Counter numCounter;

	private DirectedSparseGraph<Feature, Edge> graph;
	private VisualizationViewer<Feature, Edge> viewer;

	private FeaturesListModel model;
	private ISourceParser lang;
	private Map<Integer, List<Feature>> index;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		FeatureTopology ft = new FeatureTopology();
		ft.createBoxModel();
	}

	public FeatureTopology() {
		super("Feature Topology", "vis.gif");

		GraphZoomScrollPane graphPanel = createGraphPanel();
		JPanel featurePanel = createFeatureControls();
		JPanel zoomPanel = createZoomControls();

		JPanel mainPanel = new JPanel(new BorderLayout(1, 1));
		mainPanel.add(featurePanel, BorderLayout.NORTH);
		mainPanel.add(graphPanel, BorderLayout.CENTER);
		mainPanel.add(zoomPanel, BorderLayout.SOUTH);

		content.add(createToolBar(), BorderLayout.NORTH);
		content.add(mainPanel, BorderLayout.CENTER);

		idxSlider.setValue(prefs.getInt(KEY_START, 0));
		numSlider.setValue(prefs.getInt(KEY_LIMIT, 1));

		setLocation();
		frame.setVisible(true);
	}

	private GraphZoomScrollPane createGraphPanel() {
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
				return v.getAspect();
			}
		});
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

		GraphZoomScrollPane graphPanel = new GraphZoomScrollPane(viewer);

		final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse<String, Number>();
		viewer.setGraphMouse(graphMouse);

		viewer.addKeyListener(graphMouse.getModeKeyListener());
		viewer.setToolTipText("<html>Type 'p' for Pick mode<p>Type 't' for Transform mode");
		return graphPanel;
	}

	private JPanel createFeatureControls() {
		layoutBox = new JComboBox<>(LAYOUTS);
		layoutBox.setSize(150, 32);
		layoutBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reDrawGraph();
			}
		});

		typeBox = new JComboBox<>();
		typeBox.setSize(250, 32);
		typeBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateGraph();
			}
		});

		idxSlider = new JSlider(0, 500);
		idxSlider.setToolTipText("Features list starting offset.");
		idxSlider.setPaintTicks(true);
		idxSlider.setMajorTickSpacing(55);
		idxSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				updateGraph();
			}
		});

		idxCounter = new Counter();
		idxCounter.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				int v = idxSlider.getValue();
				switch (evt.getPropertyName()) {
					case Counter.ADD:
						int m = idxSlider.getMaximum();
						if (v < m) v++;
						break;
					case Counter.SUB:
						m = idxSlider.getMinimum();
						if (v > m) v--;
						break;
				}
				idxCounter.setValue(v);
				idxSlider.setValue(v);
			}
		});

		numSlider = new JSlider(1, 50);
		numSlider.setToolTipText("Number of features to display together.");
		numSlider.setPaintTicks(true);
		numSlider.setMajorTickSpacing(5);
		numSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				updateGraph();
			}
		});

		numCounter = new Counter();
		numCounter.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				int v = numSlider.getValue();
				switch (evt.getPropertyName()) {
					case Counter.ADD:
						int m = numSlider.getMaximum();
						if (v < m) v++;
						break;
					case Counter.SUB:
						m = numSlider.getMinimum();
						if (v > m) v--;
						break;
				}
				numCounter.setValue(v);
				numSlider.setValue(v);
			}
		});

		JPanel featurePanel = new JPanel();
		featurePanel.add(layoutBox);
		featurePanel.add(typeBox);
		featurePanel.add(idxSlider);
		featurePanel.add(idxCounter);
		featurePanel.add(numSlider);
		featurePanel.add(numCounter);
		return featurePanel;
	}

	private JPanel createZoomControls() {
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
		return controls;
	}

	@Override
	protected void saveWindowClosingPrefs(Preferences prefs) {
		prefs.putInt(KEY_START, idxSlider.getValue());
		prefs.putInt(KEY_LIMIT, numSlider.getValue());
	}

	@Override
	protected String getBaseName() {
		return "FeatureTopology";
	}

	protected JToolBar createToolBar() {
		JToolBar bar = new JToolBar();
		bar.add(getPngAction(viewer)).setText("");
		return bar;
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
		if (model != null) {
			clearGraph();
			createGraph(model.getSelectedFeatures());
			reDrawGraph();
		}
	}

	protected void createGraph(List<Feature> features) {
		int start = idxSlider.getValue();
		int limit = start + numSlider.getValue();

		for (int idx = start; idx < limit && idx < features.size(); idx++) {
			Feature feature = features.get(idx);
			graph.addVertex(feature);
			Collection<List<Edge>> edgeLists = feature.getEdgesMap().values();
			for (List<Edge> edges : edgeLists) {
				for (Edge edge : edges) {
					graph.addEdge(edge, feature, edge.leaf, EdgeType.DIRECTED);
				}
			}
		}

		int max = features.size();
		idxSlider.setMaximum(max - 1);
		idxCounter.setValue(start);

		int tck = 1;
		if (max > 2) tck = 2;
		if (max > 10) tck = 5;
		if (max > 50) tck = 10;
		if (max > 100) tck = 25;
		idxSlider.setMajorTickSpacing(tck);
	}

	protected void reDrawGraph() {
		Layout<Feature, Edge> layout = getLayout();
		viewer.getModel().setGraphLayout(layout);
	}

	private void clearGraph() {
		List<Feature> features = new ArrayList<>(graph.getVertices());
		for (Feature feature : features) {
			Collection<List<Edge>> edgeLists = feature.getEdgesMap().values();
			for (List<Edge> edges : edgeLists) {
				for (Edge edge : edges) {
					graph.removeEdge(edge);
				}
			}
			graph.removeVertex(feature);
		}
	}

	private Layout<Feature, Edge> getLayout() {
		String sel = TOPO;
		if (layoutBox != null) {
			sel = (String) layoutBox.getSelectedItem();
			if (sel == null) sel = layoutBox.getItemAt(0);
		}

		Layout<Feature, Edge> layout = null;
		switch (sel) {
			case TOPO:
				layout = new TopoLayout(graph, createTransformMap());
				break;
			case KK:
				layout = new KKLayout<Feature, Edge>(graph);
				break;
			case SPRING:
				layout = new SpringLayout<Feature, Edge>(graph);
				break;
			case FR:
				layout = new FRLayout<Feature, Edge>(graph);
				break;
		}

		layout.setSize(new Dimension(800, 800));
		return layout;
	}

	private MapSettableTransformer<Edge, EdgeData> createTransformMap() {
		Map<Edge, EdgeData> map = new HashMap<>();
		if (graph != null) {
			for (Edge edge : graph.getEdges()) {
				map.put(edge, EdgeData.create(edge));
			}
		}
		return new MapSettableTransformer<Edge, EdgeData>(map);
	}
}
