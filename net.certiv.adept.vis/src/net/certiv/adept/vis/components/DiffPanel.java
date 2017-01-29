package net.certiv.adept.vis.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import difflib.DiffUtils;
import difflib.Patch;
import difflib.PatchFailedException;
import net.certiv.adept.topo.Point;
import net.certiv.adept.vis.utils.TextUtils;

public class DiffPanel extends JPanel {

	public static final String CLICK1_LEFT = "click1_left";

	private int width;
	private int height;
	private Font font;

	private String prevTitle;
	private JTextPane prev;

	private String currTitle;
	private JTextPane curr;

	public DiffPanel(int width, int height, Font font, //
			String prevTitle, String currTitle) {

		super();
		this.width = width;
		this.height = height;
		this.font = font;
		this.prevTitle = prevTitle;
		this.currTitle = currTitle;

		create();
	}

	private void create() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 2));

		JPanel prevPanel = new JPanel();
		JPanel currPanel = new JPanel();
		prevPanel.setLayout(new BorderLayout());
		currPanel.setLayout(new BorderLayout());

		prev = new JTextPane();
		prev.setSize(width, height);
		prev.setEditable(false);
		prev.setFont(font);
		prev.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					try {
						Document doc = prev.getDocument();
						int offset = prev.getCaretPosition();
						int line = TextUtils.getLineOfOffset(doc, offset);
						int col = offset - TextUtils.getLineStartOffset(doc, line) + 1;
						firePropertyChange(CLICK1_LEFT, null, new Point(col, line + 1));
					} catch (BadLocationException e1) {}
				}
			}
		});

		curr = new JTextPane();
		curr.setSize(width, height);
		curr.setEditable(false);
		curr.setFont(font);

		JScrollPane prevScroll = new JScrollPane(prev);
		TextLineNumber prevNums = new TextLineNumber(prev);
		prevScroll.setRowHeaderView(prevNums);
		prevScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		prevScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		JScrollPane currScroll = new JScrollPane(curr);
		TextLineNumber currNums = new TextLineNumber(curr);
		currScroll.setRowHeaderView(currNums);
		currScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		currScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// currScroll.getVerticalScrollBar().setModel(prevScroll.getVerticalScrollBar().getModel());
		new Synchronizer(prevScroll, currScroll);

		JLabel prevLabel = new JLabel(prevTitle, JLabel.CENTER);
		prevLabel.setForeground(Color.black);
		prevLabel.setFont(font);

		JLabel currLabel = new JLabel(currTitle, JLabel.CENTER);
		currLabel.setForeground(Color.black);
		currLabel.setFont(font);

		prevPanel.add(prevLabel, BorderLayout.NORTH);
		prevPanel.add(prevScroll, BorderLayout.CENTER);

		currPanel.add(currLabel, BorderLayout.NORTH);
		currPanel.add(currScroll, BorderLayout.CENTER);

		mainPanel.add(prevPanel);
		mainPanel.add(currPanel);

		setLayout(new BorderLayout());
		add(mainPanel);
	}

	public void load(String prevContent, String currContent) {
		prev.setText(prevContent);
		curr.setText(currContent);
		// curr.setText(diff(prevContent, currContent));

		prev.setCaretPosition(0);
	}

	@SuppressWarnings("unused")
	private String diff(String prevContent, String currContent) {
		Patch<String> patch = DiffUtils.diff(Arrays.asList(prevContent), Arrays.asList(currContent));
		try {
			return DiffUtils.patch(Arrays.asList(prevContent), patch).get(0);
		} catch (PatchFailedException e) {
			return "";
		}
	}

	private class Synchronizer implements AdjustmentListener {

		private JScrollBar v1, h1, v2, h2;

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
}
