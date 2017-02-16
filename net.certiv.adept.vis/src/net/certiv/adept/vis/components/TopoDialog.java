package net.certiv.adept.vis.components;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

import net.certiv.adept.vis.TopologyView;

public class TopoDialog extends JDialog {

	public TopoDialog(JFrame frame, String title, TopologyView view) {
		super(frame, false);
		setTitle(title);

		TopoPanel topo = new TopoPanel(getFont());
		setContentPane(topo);
		view.setTopoDialog(topo);

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent we) {
				view.setTopoDialog(null);
			}
		});
	}
}
