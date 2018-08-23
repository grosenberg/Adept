/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Myers Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.components;

import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderDialog extends JDialog implements PropertyChangeListener {

	private static final String msg = "Set image scale";
	private static final String okBtn = "Ok";
	private static final String cnBtn = "Cancel";

	private JOptionPane optionPane;
	private int result;

	public SliderDialog(Frame frame, String title, TreeViewer viewer) {
		super(frame, true);
		setTitle(title);

		int value = (int) ((viewer.getScale() - 1.0) * 1000);
		JSlider slider = new JSlider(JSlider.HORIZONTAL, -999, 1000, value);

		Object[] data = { msg, slider };
		Object[] options = { okBtn, cnBtn };
		optionPane = new JOptionPane(data, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, options,
				options[0]);
		setContentPane(optionPane);
		optionPane.addPropertyChangeListener(this);

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				viewer.setScale(slider.getValue() / 1000.0 + 1.0);
			}
		});

		addComponentListener(new ComponentAdapter() {

			public void componentShown(ComponentEvent ce) {
				slider.requestFocusInWindow();
			}
		});

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent we) {
				optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
			}
		});
	}

	public int getResult() {
		return result;
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		String prop = e.getPropertyName();

		if (isVisible() //
				&& e.getSource() == optionPane
				&& (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {

			Object value = optionPane.getValue();

			if (value == JOptionPane.UNINITIALIZED_VALUE) return;
			optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

			if (okBtn.equals(value)) {
				result = JOptionPane.OK_OPTION;
			} else { // user closed dialog or clicked cancel
				result = JOptionPane.CANCEL_OPTION;
			}
			setVisible(false);
		}
	}
}
