package net.certiv.adept.vis.components;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Counter extends JPanel {

	public static final String ADD = "ADD";
	public static final String SUB = "SUB";

	private JTextField text;
	private JButton sub;
	private JButton add;
	private int value;
	private boolean disable;

	public Counter() {
		super(new FlowLayout());
		build();
	}

	private void build() {
		sub = new JButton("<");
		sub.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int oldValue = value;
				value--;
				setValue(value);
				firePropertyChange(SUB, oldValue, value);
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
				value++;
				setValue(value);
				firePropertyChange(ADD, oldValue, value);
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
		disable = true;
		this.value = value;
		text.setText(String.valueOf(value));
		disable = false;
	}

	public void reset() {
		setValue(0);
	}

	public void firePropertyChange(String propertyName, int oldValue, int newValue) {
		if (!disable) super.firePropertyChange(propertyName, oldValue, newValue);
	}
}
