package net.certiv.adept.vis.components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderCounter extends Counter {

	private JSlider sld;
	private boolean disable;

	public SliderCounter() {
		super();
	}

	@Override
	protected void build() {
		super.build();

		sld = new JSlider(min, max, 1);
		sld.setToolTipText("Selector");
		sld.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int old = getCount();
				int count = sld.getValue();
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
						int old = sld.getValue();
						int count = (int) evt.getNewValue();
						if (old != count) {
							disable = true;
							sld.setValue(count);
							disable = false;
						}
						break;
				}
			}
		});

		add(sld, 1);
	}

	@Override
	public void setRange(int max) {
		super.setRange(max);
		sld.setMaximum(max);
	}

	@Override
	public void setRange(int min, int max) {
		super.setRange(min, max);
		sld.setMinimum(min);
		sld.setMaximum(max);
	}
}
