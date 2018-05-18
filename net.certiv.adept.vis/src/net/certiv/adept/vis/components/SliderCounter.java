/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderCounter extends Counter {

	private JSlider slider;
	private boolean disable;

	public SliderCounter() {
		super();
	}

	@Override
	protected void build() {
		super.build();

		slider = new JSlider(min, max, 1);
		slider.setToolTipText("Selector");
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int old = getCount();
				int count = slider.getValue();
				if (old != count) {
					setCount(count);
					if (!disable) {
						// Log.info(this, "Firing slider change " + count);
						firePropertyChange(SET, old, count);
					}
				}
			}
		});

		addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				switch (evt.getPropertyName()) {
					case Counter.ADD:
					case Counter.SUB:
					case Counter.SET:
						int old = slider.getValue();
						int count = (int) evt.getNewValue();
						if (old != count) {
							disable = true;
							slider.setValue(count);
							disable = false;
						}
						break;
				}
			}
		});

		add(slider, 1);
	}

	@Override
	public void setRange(int max) {
		super.setRange(max);
		slider.setMaximum(max);
	}

	@Override
	public void setRange(int min, int max) {
		super.setRange(min, max);
		slider.setMinimum(min);
		slider.setMaximum(max);
	}
}
