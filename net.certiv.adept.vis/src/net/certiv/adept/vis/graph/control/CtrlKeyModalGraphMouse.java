/*
 * Copyright (c) 2005, The JUNG Authors 
 *
 * All rights reserved.
 *
 * This software is open-source under the BSD license; see either
 * "license.txt" or
 * https://github.com/jrtom/jung/blob/master/LICENSE for a description.
 * Created on Mar 8, 2005
 *
 */
package net.certiv.adept.vis.graph.control;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.ItemSelectable;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.RotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ShearingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;

public class CtrlKeyModalGraphMouse<V, E> extends AbstractModalGraphMouse implements ModalGraphMouse, ItemSelectable {

	public CtrlKeyModalGraphMouse() {
		this(1.1f, 1 / 1.1f);
	}

	public CtrlKeyModalGraphMouse(float in, float out) {
		super(in, out);
		setModeKeyListener(new CtrlKeyModeAdapter(this));
		loadPlugins();
	}

	/** Create and load plugins; set for TRANSFORMING mode. */
	@Override
	protected void loadPlugins() {
		pickingPlugin = new PickingGraphMousePlugin<V, E>();
		animatedPickingPlugin = new AnimatedPickingGraphMousePlugin<V, E>();
		translatingPlugin = new TranslatingGraphMousePlugin(InputEvent.BUTTON1_MASK);
		scalingPlugin = new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, in, out);
		rotatingPlugin = new RotatingGraphMousePlugin();
		shearingPlugin = new ShearingGraphMousePlugin();

		add(scalingPlugin);
		setMode(Mode.TRANSFORMING);
	}

	private class CtrlKeyModeAdapter extends KeyAdapter {

		protected ModalGraphMouse mouse;

		public CtrlKeyModeAdapter(ModalGraphMouse mouse) {
			this.mouse = mouse;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.isShiftDown() && e.isControlDown()) {
				((Component) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				mouse.setMode(Mode.PICKING);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (mode != Mode.TRANSFORMING) {
				((Component) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				mouse.setMode(Mode.TRANSFORMING);
			}
		}
	}
}
