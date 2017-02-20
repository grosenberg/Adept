package net.certiv.adept.vis.components;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Counter extends JPanel {

	// event require strings
	public static final String ADD = "ADD";
	public static final String SUB = "SUB";
	public static final String SET = "SET";

	private JTextField text;
	private JButton sub;
	private JButton add;

	private int max = 100;
	private int min = 0;
	private int inc = 1;
	private int value;

	private boolean disable;

	public Counter() {
		super(new FlowLayout());
		build();
	}

	public Counter(int max, int min, int inc) {
		this();
		this.max = max;
		this.min = min;
		this.inc = inc;
	}

	public void setMaximum(int max) {
		this.max = max;
	}

	public void setMinimum(int min) {
		this.min = min;
	}

	public void setIncrement(int inc) {
		this.inc = inc;
	}

	private void build() {
		sub = new JButton("<");
		sub.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int oldValue = value;
				if (value - inc >= min) {
					setValue(value - inc);
					firePropertyChange(SUB, oldValue, value);
				}
			}
		});
		add(sub);

		text = new JTextField(4);
		text.setEditable(false);
		text.setHorizontalAlignment(JTextField.CENTER);
		add(text);

		add = new JButton(">");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int oldValue = value;
				if (value + inc <= max) {
					setValue(value + inc);
					firePropertyChange(ADD, oldValue, value);
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		if (value >= min && value <= max) {
			disable = true;
			this.value = value;
			text.setText(String.valueOf(value));
			disable = false;
		}
	}

	public void reset() {
		setValue(min);
		firePropertyChange(SET, min - 1, min);
	}

	public void firePropertyChange(String propertyName, int oldValue, int newValue) {
		if (!disable) super.firePropertyChange(propertyName, oldValue, newValue);
	}
}
