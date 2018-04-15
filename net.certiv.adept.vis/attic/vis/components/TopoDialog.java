package net.certiv.adept.vis.graph.components;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

import net.certiv.adept.vis.graph.TopologyView;

public class TopoDialog extends JDialog {

	public TopoDialog(JFrame frame, String title, TopologyView view) {
		super(frame, false);
		setTitle(title);

		TopoPanel detail = new TopoPanel(getFont());
		setContentPane(detail);
		view.setTopoDialog(detail);

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent we) {
				view.setTopoDialog(null);
			}
		});
	}
}
