package net.certiv.adept.vis.panels;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.format.TextEdit;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.vis.FormatView;

public class FormatInfoPanel extends JPanel {

	private FormatPerfPanel perfPane;
	private FormatEditPanel editPane;
	private FormatRealPanel srcPane;
	private FormatRealPanel matchPane;
	private FormatRealPanel resultPane;

	/**
	 * Create the panel.
	 */
	public FormatInfoPanel() {
		super();
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));

		perfPane = new FormatPerfPanel();
		panel.add(perfPane, BorderLayout.NORTH);
		perfPane.setBorder(new CompoundBorder(new EmptyBorder(4, 4, 4, 4),
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Performance", TitledBorder.LEADING,
						TitledBorder.TOP, null, new Color(0, 0, 0))));

		editPane = new FormatEditPanel();
		panel.add(editPane);
		editPane.setBorder(new CompoundBorder(new EmptyBorder(4, 4, 4, 4),
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Edits", TitledBorder.LEADING,
						TitledBorder.TOP, null, new Color(0, 0, 0))));

		JPanel realsPanel = new JPanel();
		realsPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
		add(realsPanel, BorderLayout.CENTER);
		realsPanel.setLayout(new BorderLayout(0, 0));

		srcPane = new FormatRealPanel();
		srcPane.setBorder(new TitledBorder(null, "Source", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		realsPanel.add(srcPane, BorderLayout.NORTH);

		matchPane = new FormatRealPanel();
		matchPane.setBorder(new TitledBorder(null, "Matched", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		realsPanel.add(matchPane, BorderLayout.CENTER);

		resultPane = new FormatRealPanel();
		resultPane.setBorder(new TitledBorder(null, "Results", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		realsPanel.add(resultPane, BorderLayout.SOUTH);
	}

	public FormatInfoPanel(FormatView view) {
		this();
		srcPane.hideSim();
		matchPane.hideLineCol();
		resultPane.hideAncestors();
		resultPane.hideSim();
	}

	public void loadPerfData(CoreMgr mgr) {
		perfPane.loadPerfData(mgr);
	}

	public void loadData(Feature feature, AdeptToken token, RefToken ref, RefToken matched, double sim, TextEdit ledit,
			TextEdit redit) {

		srcPane.loadData(1, feature, token, ref, matched, sim, ledit, redit);
		matchPane.loadData(2, feature, token, ref, matched, sim, ledit, redit);
		resultPane.loadData(3, feature, token, ref, matched, sim, ledit, redit);
		editPane.loadData(ref, matched, ledit, redit);
	}

	public void clearData() {
		srcPane.clearData();
		matchPane.clearData();
		resultPane.clearData();
	}

	public void clearAll() {
		perfPane.clearAll();
	}
}
