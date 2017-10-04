package net.certiv.adept.vis.components;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Counter extends JPanel {

	// event identifiers
	public static final String ADD = "ADD";
	public static final String SUB = "SUB";
	public static final String SET = "SET";

	protected JTextField txt;
	protected JButton sub;
	protected JButton add;

	protected int max = 100;
	protected int min = 0;
	protected int inc = 1;
	protected int count;

	private boolean disable;

	public Counter() {
		super(new FlowLayout());
		build();
	}

	public Counter(int max, int min) {
		this();
		this.max = max;
		this.min = min;
	}

	public void setRange(int max) {
		this.max = max;
	}

	public void setRange(int min, int max) {
		this.min = min;
		this.max = max;
	}

	protected void build() {
		sub = new JButton("<");
		sub.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int oldValue = count;
				if (count - inc >= min) {
					setCount(count - inc);
					firePropertyChange(SUB, oldValue, count);
				}
			}
		});
		add(sub);

		txt = new JTextField(6);
		txt.setEditable(false);
		txt.setHorizontalAlignment(JTextField.CENTER);
		add(txt);

		add = new JButton(">");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int oldValue = count;
				if (count + inc <= max) {
					setCount(count + inc);
					firePropertyChange(ADD, oldValue, count);
				}
			}
		});
		add(add);
	}

	public JButton getAdd() {
		return add;
	}

	public JButton getSub() {
		return sub;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		if (count >= min && count <= max) {
			disable = true;
			this.count = count;
			txt.setText(String.valueOf(count));
			disable = false;
		}
	}

	public void reset() {
		setCount(min);
		firePropertyChange(SET, min - 1, min);
	}

	public void firePropertyChange(String propertyName, int oldValue, int newValue) {
		if (!disable) {
			// Log.debug(this, String.format("Issuing property change: %s %s -> %s", propertyName, oldValue, newValue));
			super.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
}
