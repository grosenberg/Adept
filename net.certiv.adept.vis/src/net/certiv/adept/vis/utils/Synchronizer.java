package net.certiv.adept.vis.utils;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class Synchronizer implements AdjustmentListener {

	private JScrollBar v1, h1, v2, h2;

	public static Synchronizer link(JScrollPane sp1, JScrollPane sp2) {
		return new Synchronizer(sp1, sp2);
	}

	public Synchronizer(JScrollPane sp1, JScrollPane sp2) {
		v1 = sp1.getVerticalScrollBar();
		h1 = sp1.getHorizontalScrollBar();
		v2 = sp2.getVerticalScrollBar();
		h2 = sp2.getHorizontalScrollBar();

		v1.addAdjustmentListener(this);
		h1.addAdjustmentListener(this);
		v2.addAdjustmentListener(this);
		h2.addAdjustmentListener(this);
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		JScrollBar scrollBar = (JScrollBar) e.getSource();
		int value = scrollBar.getValue();
		JScrollBar target = null;

		if (scrollBar == v1) target = v2;
		if (scrollBar == h1) target = h2;
		if (scrollBar == v2) target = v1;
		if (scrollBar == h2) target = h1;

		target.setValue(value);
	}
}
