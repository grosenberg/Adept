package net.certiv.adept.vis.panels;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.core.util.Facet;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.util.Refs;
import net.certiv.adept.util.Time;

public class FormatPerfPanel extends JPanel {

	private static final String MsFmt = "%s ms";

	private JTextField txtExecute;
	private JTextField txtLoad;
	private JTextField txtBuild;
	private JTextField txtFormat;

	private JTextField txtCorpusFeatures;
	private JTextField txtCorpusRefs;
	private JTextField txtDocFeatures;
	private JTextField txtDocRefs;
	private JTextField txtMatch;
	private JTextField txtParse;

	public FormatPerfPanel() {
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("max(34dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.UNRELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.UNRELATED_GAP_ROWSPEC, }));

		JLabel lblLoad = new JLabel("Load");
		add(lblLoad, "2, 2, right, default");

		txtLoad = new JTextField();
		txtLoad.setHorizontalAlignment(SwingConstants.TRAILING);
		txtLoad.setColumns(10);
		txtLoad.setEditable(false);
		add(txtLoad, "4, 2, 3, 1, fill, default");

		JLabel lblRebuildTime = new JLabel("Build");
		add(lblRebuildTime, "2, 4, right, default");

		txtBuild = new JTextField();
		txtBuild.setHorizontalAlignment(SwingConstants.TRAILING);
		txtBuild.setColumns(10);
		txtBuild.setEditable(false);
		add(txtBuild, "4, 4, 3, 1, fill, default");

		JLabel lblParse = new JLabel("Parse");
		add(lblParse, "2, 6, right, default");

		txtParse = new JTextField();
		txtParse.setHorizontalAlignment(SwingConstants.TRAILING);
		txtParse.setEditable(false);
		add(txtParse, "4, 6, 3, 1, fill, default");
		txtParse.setColumns(10);

		JLabel lblMatching = new JLabel("Match");
		add(lblMatching, "2, 8, right, default");

		txtMatch = new JTextField();
		txtMatch.setHorizontalAlignment(SwingConstants.TRAILING);
		txtMatch.setEditable(false);
		add(txtMatch, "4, 8, 3, 1, fill, default");
		txtMatch.setColumns(10);

		JLabel lblFormatting = new JLabel("Format");
		add(lblFormatting, "2, 10, right, default");

		txtFormat = new JTextField();
		txtFormat.setHorizontalAlignment(SwingConstants.TRAILING);
		txtFormat.setEditable(false);
		add(txtFormat, "4, 10, 3, 1, fill, default");
		txtFormat.setColumns(10);

		JLabel lblExecute = new JLabel("Execute");
		add(lblExecute, "2, 12, right, default");

		txtExecute = new JTextField();
		txtExecute.setHorizontalAlignment(SwingConstants.TRAILING);
		txtExecute.setColumns(10);
		txtExecute.setEditable(false);
		add(txtExecute, "4, 12, 3, 1, fill, default");

		JLabel lblDocFeatures = new JLabel("Features");
		add(lblDocFeatures, "4, 16, center, default");

		JLabel lblRefTokens = new JLabel("Refs");
		add(lblRefTokens, "6, 16, center, default");

		JLabel lblDocument = new JLabel("Source");
		lblDocument.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblDocument, "2, 18, left, default");

		txtDocFeatures = new JTextField();
		txtDocFeatures.setHorizontalAlignment(SwingConstants.TRAILING);
		txtDocFeatures.setColumns(5);
		txtDocFeatures.setEditable(false);
		add(txtDocFeatures, "4, 18, fill, default");

		txtDocRefs = new JTextField();
		txtDocRefs.setHorizontalAlignment(SwingConstants.TRAILING);
		txtDocRefs.setColumns(5);
		txtDocRefs.setEditable(false);
		add(txtDocRefs, "6, 18, fill, default");

		JLabel lblCorpus = new JLabel("Corpus");
		lblCorpus.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblCorpus, "2, 20, left, default");

		txtCorpusFeatures = new JTextField();
		txtCorpusFeatures.setHorizontalAlignment(SwingConstants.TRAILING);
		txtCorpusFeatures.setColumns(5);
		txtCorpusFeatures.setEditable(false);
		add(txtCorpusFeatures, "4, 20, fill, default");

		txtCorpusRefs = new JTextField();
		txtCorpusRefs.setHorizontalAlignment(SwingConstants.TRAILING);
		txtCorpusRefs.setColumns(5);
		txtCorpusRefs.setEditable(false);
		add(txtCorpusRefs, "6, 20, fill, default");
	}

	public void loadPerfData(CoreMgr mgr) {
		ParseRecord rec = mgr.getDocModel().getParseRecord();
		Refs.setup(rec.getRuleNames(), rec.getTokenNames());

		txtLoad.setText(Time.elapsed(Facet.LOAD, MsFmt));
		txtBuild.setText(Time.elapsed(Facet.BUILD, MsFmt));
		txtParse.setText(Time.elapsed(Facet.PARSE, MsFmt));
		txtMatch.setText(Time.elapsed(Facet.MATCH, MsFmt));
		txtFormat.setText(Time.elapsed(Facet.FORMAT, MsFmt));
		txtExecute.setText(Time.elapsed(Facet.EXECUTE, MsFmt));

		txtDocFeatures.setText(String.valueOf(mgr.getDocModel().getFeaturesCount()));
		txtDocRefs.setText(String.valueOf(mgr.getDocModel().getTokenRefsCount()));

		txtCorpusFeatures.setText(String.valueOf(mgr.getCorpusModel().getCorpusFeaturesCount()));
		txtCorpusRefs.setText(String.valueOf(mgr.getCorpusModel().getCorpusRefTokensCount()));
	}

	public void clearAll() {
		txtLoad.setText("");
		txtBuild.setText("");
		txtParse.setText("");
		txtMatch.setText("");
		txtFormat.setText("");
		txtExecute.setText("");

		txtDocFeatures.setText("");
		txtDocRefs.setText("");
		txtCorpusFeatures.setText("");
		txtCorpusRefs.setText("");
	}
}
