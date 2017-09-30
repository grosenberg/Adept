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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.ArrayListMultimap;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.util.MapSettableTransformer;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.renderers.BasicVertexLabelRenderer.OutsidePositioner;
import edu.uci.ics.jung.visualization.renderers.GradientVertexRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import net.certiv.adept.Tool;
import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.Edge;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.parser.ISourceParser;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.components.AbstractBase;
import net.certiv.adept.vis.components.Counter;
import net.certiv.adept.vis.components.PopupGraphMousePlugin;
import net.certiv.adept.vis.components.TopoDialog;
import net.certiv.adept.vis.components.TopoPanel;
import net.certiv.adept.vis.layout.EdgeData;
import net.certiv.adept.vis.layout.TopoLayout;
import net.certiv.adept.vis.models.CorpusListModel;
import net.certiv.adept.vis.models.CorpusListModel.FeatureListItem;

public class TopologyView extends AbstractBase {

	private static final String corpusRoot = "../net.certiv.adept.core/corpus";

	private JComboBox<FeatureListItem> typeBox;
	private JSlider idxSlider;	// offset into the list of features to display
	private Counter idxCounter;
	private JButton detailOpen;

	private DirectedSparseGraph<Feature, Edge> graph;
	private VisualizationViewer<Feature, Edge> viewer;

	private CorpusListModel model;
	private ISourceParser lang;
	private ArrayListMultimap<Integer, Feature> typeIndex;
	private TopoPanel topoDetail;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		TopologyView ft = new TopologyView();
		ft.createBoxModel();
	}

	public TopologyView() {
		super("Feature Topology", "vis.gif");

		GraphZoomScrollPane graphPanel = createGraphPanel();
		graphPanel.setBorder(LineBorder.createBlackLineBorder());
		JPanel featurePanel = createFeatureControls();
		JPanel zoomPanel = createZoomControls();

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(featurePanel, BorderLayout.NORTH);
		mainPanel.add(graphPanel, BorderLayout.CENTER);
		mainPanel.add(zoomPanel, BorderLayout.SOUTH);

		content.add(mainPanel, BorderLayout.CENTER);

		idxSlider.setValue(0);
		setLocation();
		frame.setVisible(true);
	}

	private GraphZoomScrollPane createGraphPanel() {
		graph = new DirectedSparseGraph<>();
		viewer = new VisualizationViewer<Feature, Edge>(createLayout());

		viewer.getRenderer().setVertexRenderer(new GradientVertexRenderer<Feature, Edge>(Color.white, Color.red,
				Color.white, Color.blue, viewer.getPickedVertexState(), false));
		viewer.setForeground(Color.darkGray);
		viewer.setBackground(Color.white);

		viewer.getRenderContext().setEdgeDrawPaintTransformer(Functions.<Paint>constant(Color.lightGray));
		viewer.getRenderContext().setArrowFillPaintTransformer(Functions.<Paint>constant(Color.lightGray));
		viewer.getRenderContext().setArrowDrawPaintTransformer(Functions.<Paint>constant(Color.lightGray));
		viewer.getRenderer().getVertexLabelRenderer().setPositioner(new OutsidePositioner());
		viewer.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.SE);
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

		DefaultModalGraphMouse<String, Number> graphMouse = new DefaultModalGraphMouse<>();
		viewer.setGraphMouse(graphMouse);
		viewer.addKeyListener(graphMouse.getModeKeyListener());
		// viewer.setToolTipText("<html>Type 'p' for Pick mode<p>Type 't' for Transform mode");

		graphMouse.add(new PopupGraphMousePlugin<Feature, Edge>() {

			@Override
			public void addCanvasActions(JPopupMenu popup) {
				popup.add(getPngAction(viewer));
			}
		});

		return new GraphZoomScrollPane(viewer);
	}

	private JPanel createFeatureControls() {
		typeBox = new JComboBox<>();
		typeBox.setSize(250, 32);
		typeBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				idxCounter.reset();
				updateGraph();
			}
		});

		idxSlider = new JSlider(0, 500);
		idxSlider.setToolTipText("FeatureSet list starting offset.");
		idxSlider.setPaintTicks(true);
		idxSlider.setMajorTickSpacing(55);
		idxSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int val = idxSlider.getValue();
				idxCounter.setValue(val);
				updateGraph();
			}
		});

		idxCounter = new Counter();
		idxCounter.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case Counter.ADD:
					case Counter.SUB:
					case Counter.SET:
						int val = (int) evt.getNewValue();
						idxSlider.setValue(val);
						break;
				}
			}
		});

		detailOpen = new JButton();
		detailOpen.setText("Vertex Details");
		detailOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TopoDialog dialog = new TopoDialog(frame, "Topology Details", TopologyView.this);
				dialog.setMinimumSize(new Dimension(800, 400));
				dialog.pack();
				dialog.setLocationRelativeTo(frame);
				dialog.setVisible(true);
				updateGraph();
			}
		});

		JPanel featurePanel = new JPanel();
		featurePanel.add(typeBox);
		featurePanel.add(idxSlider);
		featurePanel.add(idxCounter);
		featurePanel.add(detailOpen);
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
	protected String getBaseName() {
		return "TopologyView";
	}

	protected JToolBar createToolBar() {
		JToolBar bar = new JToolBar();
		bar.add(getPngAction(viewer)).setText("");
		bar.setFloatable(false);
		return bar;
	}

	protected void createBoxModel() {
		Tool tool = new Tool();
		tool.setCheck(true);
		tool.setCorpusRoot(corpusRoot);
		tool.setLang("antlr");
		tool.setTabWidth(4);

		tool.setRebuild(true);

		boolean ok = tool.loadResources();
		ok = ok && tool.validateOptions();

		if (!ok) {
			Log.error(this, "Failed to initialize model");
			return;
		}

		ProcessMgr mgr = tool.getMgr();
		lang = mgr.getLanguageParser();
		typeIndex = mgr.getCorpusModel().getFeatureIndex();
		model = new CorpusListModel(lang.getRuleNames(), lang.getTokenNames());
		typeBox.setModel(model);
		model.addElements(typeIndex);
	}

	protected void updateGraph() {
		if (model != null) {
			clearGraph();
			createGraph(model.getSelectedFeatures());
			reDrawGraph();
		}
	}

	protected void createGraph(List<Feature> features) {
		int idx = idxCounter.getValue();
		Feature feature = features.get(idx);
		graph.addVertex(feature);
		Collection<Edge> edges = feature.getEdgeSet().getEdges();
		for (Edge edge : edges) {
			graph.addEdge(edge, feature, edge.leaf, EdgeType.DIRECTED);
		}

		int max = features.size();
		idxSlider.setMaximum(max - 1);
		idxCounter.setMaximum(max - 1);
		idxCounter.setValue(idx);

		int tck = 1;
		if (max > 2) tck = 2;
		if (max > 10) tck = 5;
		if (max > 50) tck = 10;
		if (max > 100) tck = 25;
		idxSlider.setMajorTickSpacing(tck);
	}

	protected void reDrawGraph() {
		Layout<Feature, Edge> layout = createLayout();
		viewer.getModel().setGraphLayout(layout);
		viewer.getModel().getRelaxer().setSleepTime(2000);
	}

	private void clearGraph() {
		List<Feature> features = new ArrayList<>(graph.getVertices());
		for (Feature feature : features) {
			Collection<Edge> edges = feature.getEdgeSet().getEdges();
			for (Edge edge : edges) {
				graph.removeEdge(edge);
			}
			graph.removeVertex(feature);
		}
		if (topoDetail != null) topoDetail.clear();
	}

	private Layout<Feature, Edge> createLayout() {
		Layout<Feature, Edge> layout = new TopoLayout(graph, createTransformMap(), topoDetail);
		layout.setSize(new Dimension(600, 600));
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

	public void setTopoDialog(TopoPanel topoDetail) {
		this.topoDetail = topoDetail;
	}
}
