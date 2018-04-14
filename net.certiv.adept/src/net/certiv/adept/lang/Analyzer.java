package net.certiv.adept.lang;

import java.util.List;

import net.certiv.adept.core.util.Form;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.util.Calc;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Refs;

public class Analyzer {

	private static ParseRecord _data;
	private static List<Feature> _features;

	private Analyzer() {}

	public static void evaluate(ParseRecord data, List<Feature> features) {
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
				String loc = Refs.tLocation(ref);
				// String ancestors = Refs.evalAncestors(feature.getAncestors());
				// String dents = Refs.tIndent(ref);
				// String space = Refs.tSpace(ref);
				// String align = Refs.tAlign(ref);

				if (ref.contexts == null) {
					String msg = String.format("Null: %3d %s %s %s", tIndex, name, loc, place);
					Log.debug(Analyzer.class, msg);

				} else if (ref.contexts.isEmpty()) {
					String msg = String.format("None: %3d %s %s %s", tIndex, name, loc, place);
					Log.debug(Analyzer.class, msg);

				} else {
					Calc.delta(Form.CTX, ref.contexts.size());

					// for (Context context : ref.contexts) {
					// if (context.lAssocs.isEmpty() || context.rAssocs.isEmpty()) {
					// String assoc = Refs.tAssoc(ref.type, context);
					// String msg = String.format("One.: %3d %s %s %s\t%s", tIndex, name, loc, place, assoc);
					// Log.debug(Analyzer.class, msg);
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

		Log.info(Analyzer.class, "=============================================================================");
		Log.info(Analyzer.class, String.format(DMsg, docs, toks, toks / docs, mtoks, xtoks));
		Log.info(Analyzer.class, String.format(FMsg, cfeats));
		Log.info(Analyzer.class, String.format(RMsg, crefs, crefs / cfeats, mrefs, xrefs));
		Log.info(Analyzer.class, String.format(CMsg, cctxs, cctxs / crefs, mctxs, xctxs));
		Log.info(Analyzer.class, "=============================================================================");
	}
}
