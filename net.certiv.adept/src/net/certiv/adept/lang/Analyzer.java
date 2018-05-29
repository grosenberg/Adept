/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang;

import java.util.List;

import net.certiv.adept.Tool;
import net.certiv.adept.core.util.Form;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.util.Calc;
import net.certiv.adept.util.Refs;

public class Analyzer {

	private static Tool _tool;
	private static ParseRecord _data;
	private static List<Feature> _features;

	private Analyzer() {}

	public static void evaluate(Tool tool, ParseRecord data, List<Feature> features) {
		_tool = tool;
		_data = data;
		_features = features;

		checkCorpus();
	}

	private static void checkCorpus() {
		Refs.setup(_data.getRuleNames(), _data.getTokenNames());

		for (Feature feature : _features) {
			Calc.inc(Form.FEAT);

			Calc.delta(Form.REF, feature.getRefs().size());
			for (RefToken ref : feature.getRefs()) {

				int tIndex = ref.index;
				String name = Refs.tText(ref.type, ref.text);
				String place = Refs.tPlace(ref);
				// String loc = Refs.tLocation(ref);
				// String ancestors = Refs.evalAncestors(feature.getAncestors());
				// String dents = Refs.tIndent(ref);
				// String space = Refs.tSpace(ref);
				// String align = Refs.tAlign(ref);

				if (ref.contexts == null) {
					String msg = String.format("Null: %3d %s %s", tIndex, name, place);
					_tool.toolInfo(Analyzer.class, msg);

				} else if (ref.contexts.isEmpty()) {
					String msg = String.format("None: %3d %s %s", tIndex, name, place);
					_tool.toolInfo(Analyzer.class, msg);

				} else {
					Calc.delta(Form.CTX, ref.contexts.size());

					// for (Context context : ref.contexts) {
					// if (context.lAssocs.isEmpty() || context.rAssocs.isEmpty()) {
					// String assoc = Refs.tAssoc(ref.type, context);
					// String msg = String.format("One.: %3d %s %s %s\t%s", tIndex, name, loc, place, assoc);
					// _tool.toolInfo(Analyzer.class, msg);
					// }
					// }
				}
			}
		}

		double docs = Calc.total(Form.DOCS);

		double toks = Calc.total(Form.TOKS);
		double mtoks = Calc.median(Form.TOKS);
		double xtoks = Calc.max(Form.TOKS);

		double cfeats = Calc.total(Form.FEAT);

		double crefs = Calc.total(Form.REF);
		double mrefs = Calc.median(Form.REF);
		double xrefs = Calc.max(Form.REF);

		double cctxs = Calc.total(Form.CTX);
		double mctxs = Calc.median(Form.CTX);
		double xctxs = Calc.max(Form.CTX);

		String DMsg = "Docs     %5.0f %6.0f : %5.2f %5.2f %4.0f (mean/median/max) tokens per doc";
		String FMsg = "Features %5.0f";
		String RMsg = "Refs     %5.0f : %5.2f %5.2f %4.0f (mean/median/max) per feature";
		String CMsg = "Contexts %5.0f : %5.2f %5.2f %4.0f (mean/median/max) per ref";

		_tool.toolInfo(Analyzer.class, "=============================================================================");
		_tool.toolInfo(Analyzer.class, String.format(DMsg, docs, toks, toks / docs, mtoks, xtoks));
		_tool.toolInfo(Analyzer.class, String.format(FMsg, cfeats));
		_tool.toolInfo(Analyzer.class, String.format(RMsg, crefs, crefs / cfeats, mrefs, xrefs));
		_tool.toolInfo(Analyzer.class, String.format(CMsg, cctxs, cctxs / crefs, mctxs, xctxs));
		_tool.toolInfo(Analyzer.class, "=============================================================================");
	}
}
