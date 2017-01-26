package net.certiv.adept.vis.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import javax.swing.JPopupMenu;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;

/**
 * GraphMousePlugin that offers popup menu support
 */
public class PopupGraphMousePlugin<V, E> extends AbstractPopupGraphMousePlugin implements MouseListener {

	public PopupGraphMousePlugin() {
		this(MouseEvent.BUTTON3_MASK);
	}

	public PopupGraphMousePlugin(int modifiers) {
		super(modifiers);
	}

	/**
	 * If this event is over a vertex, an edge or the canvas, pop up a menu to allow the user to
	 * perform actions.
	 */
	protected void handlePopup(MouseEvent e) {
		@SuppressWarnings("unchecked") final VisualizationViewer<V, E> viewer = (VisualizationViewer<V, E>) e
				.getSource();
		final Point2D p = e.getPoint();
		final JPopupMenu popup = new JPopupMenu();

		GraphElementAccessor<V, E> pickSupport = viewer.getPickSupport();
		if (pickSupport != null) {
			final V vertex = pickSupport.getVertex(viewer.getGraphLayout(), p.getX(), p.getY());
			if (vertex != null) {
				addNodeActions(popup, vertex);
			}
			final E edge = pickSupport.getEdge(viewer.getGraphLayout(), p.getX(), p.getY());
			if (edge != null) {
				addEdgeActions(popup, edge);
			}
			if (vertex == null && edge == null) {
				addCanvasActions(popup);
			}

		} else {
			addCanvasActions(popup);
		}
		if (popup.getComponentCount() > 0) {
			popup.show(viewer, e.getX(), e.getY());
		}
	}

	public void addNodeActions(JPopupMenu popup, V node) {}

	public void addEdgeActions(JPopupMenu popup, E edge) {}

	public void addCanvasActions(JPopupMenu popup) {}
}
