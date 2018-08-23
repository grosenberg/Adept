/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Myers Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.models;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.Context;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.util.Refs;

public abstract class BaseTableModel extends AbstractTableModel {

	protected static final Comparator<Number> NumComp = new Comparator<Number>() {

		@Override
		public int compare(Number o1, Number o2) {
			if (o1.doubleValue() < o2.doubleValue()) return -1;
			if (o1.doubleValue() > o2.doubleValue()) return 1;
			return 0;
		}
	};

	public BaseTableModel(List<String> ruleNames, List<String> tokenNames) {
		super();
		Refs.setup(ruleNames, tokenNames);
	}

	protected void setColWidths(TableColumnModel cols, int[] widths) {
		for (int idx = 0; idx < widths.length; idx++) {
			cols.getColumn(idx).setPreferredWidth(widths[idx]);
		}
	}

	protected void setComparator(TableRowSorter<TableModel> sorter, Comparator<?> comp, int... cols) {
		for (int col : cols) {
			sorter.setComparator(col, comp);
		}
	}

	// =============================================================

	protected static String tPlace(RefToken ref) {
		return ref.place.toString();
	}

	protected static String tAssoc(int type, Context context) {
		return Refs.tAssoc(type, context);
	}

	protected static String tIndent(RefToken ref) {
		return Refs.tIndent(ref);
	}

	protected static String tAlign(RefToken ref) {
		return Refs.tAlign(ref);
	}

	protected static String tSpace(RefToken ref) {
		return Refs.tSpace(ref);
	}

	protected static String tLocation(AdeptToken token) {
		return Refs.tLocation(token);
	}

	protected static String tText(int type, String text) {
		return Refs.fType(type);
	}

	protected static String fType(int type) {
		return Refs.fType(type);
	}

	protected static String sType(int type) {
		return Refs.sType(type);
	}

	protected static String evalAncestors(List<Integer> ancestors) {
		return Refs.evalAncestors(ancestors);
	}

	protected static String evalTokens(Set<Integer> indexes, boolean showType) {
		return Refs.evalTokens(indexes, showType);
	}
}
