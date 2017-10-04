package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.google.common.base.Function;
import com.google.common.base.Functions;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.renderers.VertexLabelAsShapeRenderer;
import net.certiv.adept.Tool;
import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.Edge;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.parser.ISourceParser;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.components.AbstractBase;
import net.certiv.adept.vis.components.Counter;
import net.certiv.adept.vis.components.SliderCounter;
import net.certiv.adept.vis.components.TopoDialog;
import net.certiv.adept.vis.components.TopoPanel;
import net.certiv.adept.vis.graph.control.PopupGraphMousePlugin;
import net.certiv.adept.vis.graph.layout.TopoLayout;
import net.certiv.adept.vis.graph.transformer.TopoVertexRenderer;
import net.certiv.adept.vis.models.CorpusListModel;
import net.certiv.adept.vis.models.CorpusListModel.FeatureListItem;

public class TopologyView extends AbstractBase {

	private static final String corpusRoot = "../net.certiv.adept.core/corpus";

	private JComboBox<FeatureListItem> typeBox;
	private SliderCounter counter;	// features list offset
	private JButton detailOpen;
	private TopoPanel detailPanel;

	private DirectedSparseGraph<Feature, Edge> graph;
	private VisualizationViewer<Feature, Edge> viewer;
	private DefaultModalGraphMouse<String, Number> mouse;

	private CorpusListModel model;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		TopologyView topo = new TopologyView();
		topo.createBoxModel();
	}

	public TopologyView() {
		super("Feature Topology", "vis.gif");

		GraphZoomScrollPane graphPanel = createGraphPanel();
		graphPanel.setBorder(LineBorder.createBlackLineBorder());

		JMenuBar menubar = new JMenuBar();
		menubar.add(mouse.getModeMenu());
		graphPanel.setCorner(menubar);

		JPanel featurePanel = createFeatureControls();
		JPanel zoomPanel = createZoomControls();

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(featurePanel, BorderLayout.NORTH);
		mainPanel.add(graphPanel, BorderLayout.CENTER);
		mainPanel.add(zoomPanel, BorderLayout.SOUTH);

		content.add(mainPanel, BorderLayout.CENTER);

		counter.setCount(0);
		setLocation();
		frame.setVisible(true);
	}

	private GraphZoomScrollPane createGraphPanel() {
		graph = new DirectedSparseGraph<>();
		Layout<Feature, Edge> layout = createLayout();
		viewer = new VisualizationViewer<>(layout);
		layout.setSize(viewer.getSize());

		viewer.setForeground(Color.black);
		viewer.setBackground(Color.white);

		viewer.getRenderContext().setEdgeDrawPaintTransformer(Functions.<Paint>constant(Color.darkGray));
		viewer.getRenderContext().setArrowFillPaintTransformer(Functions.<Paint>constant(Color.darkGray));
		viewer.getRenderContext().setArrowDrawPaintTransformer(Functions.<Paint>constant(Color.darkGray));

		// --

		VertexLabelAsShapeRenderer<Feature, Edge> vertex = new VertexLabelAsShapeRenderer<>(viewer.getRenderContext());
		viewer.getRenderContext().setVertexLabelTransformer( //
				new Function<Feature, String>() {

					@Override
					public String apply(Feature f) {
						return "<html><center>&nbsp;" + f.getAspect() + "&nbsp;";
					}
				});

		viewer.getRenderContext().setVertexShapeTransformer(vertex);
		viewer.getRenderer().setVertexRenderer(new TopoVertexRenderer<Feature, Edge>());
		viewer.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
		viewer.getRenderContext().setVertexLabelRenderer(new DefaultVertexLabelRenderer(Color.black));

		// --

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

		// --

		mouse = new DefaultModalGraphMouse<>();
		viewer.setGraphMouse(mouse);
		viewer.addKeyListener(mouse.getModeKeyListener());
		// viewer.setToolTipText("<html>Type 'p' for Pick mode<p>Type 't' for Transform mode");

		mouse.add(new PopupGraphMousePlugin<Feature, Edge>() {

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
		typeBox.setMinimumSize(new Dimension(250, 32));
		typeBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				counter.reset();
				int maxCount = updateGraph();
				counter.setRange(maxCount);
			}
		});

		counter = new SliderCounter();
		counter.setRange(0, 500);
		counter.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case Counter.ADD:
					case Counter.SUB:
					case Counter.SET:
						int maxCount = updateGraph();
						counter.setRange(maxCount);
						break;
				}
			}
		});

		// ----------------------

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
				int maxCount = updateGraph();
				counter.setRange(maxCount);
			}
		});

		JPanel featurePanel = new JPanel(new FlowLayout());
		featurePanel.add(typeBox);
		featurePanel.add(counter);
		featurePanel.add(detailOpen);
		return featurePanel;
	}

	private JPanel createZoomControls() {
		final ScalingControl scaler = new CrossoverScalingControl();

		JButton plus = new JButton("+");
		plus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scaler.scale(viewer, 1.1f, viewer.getCenter());
			}
		});
		JButton minus = new JButton("-");
		minus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scaler.scale(viewer, 1 / 1.1f, viewer.getCenter());
			}
		});

		JButton reset = new JButton("reset");
		reset.addActionListener(new ActionListener() {

			@Override
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

	protected boolean createBoxModel() {
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
			return false;
		}

		ProcessMgr mgr = tool.getMgr();
		ISourceParser parser = mgr.getLanguageParser();
		model = new CorpusListModel(parser.getRuleNames(), parser.getTokenNames());
		model.addElements(mgr.getCorpusModel().getFeatureIndex());
		typeBox.setModel(model);
		return true;
	}

	protected int updateGraph() {
		if (model != null) {
			clearGraph();
			List<Feature> features = model.getSelectedFeatures();
			createGraph(features);
			reDrawGraph();
			return features.size() - 1;
		}
		return 0;
	}

	protected void createGraph(List<Feature> features) {
		int idx = counter.getCount();
		Feature feature = features.get(idx);
		graph.addVertex(feature);
		Collection<Edge> edges = feature.getEdgeSet().getEdges();
		for (Edge edge : edges) {
			graph.addEdge(edge, feature, edge.leaf, EdgeType.DIRECTED);
		}
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
		if (detailPanel != null) detailPanel.clear();
	}

	private Layout<Feature, Edge> createLayout() {
		Layout<Feature, Edge> layout = new TopoLayout(graph, detailPanel);
		return layout;
	}

	public void setTopoDialog(TopoPanel topoDetail) {
		this.detailPanel = topoDetail;
	}
}
