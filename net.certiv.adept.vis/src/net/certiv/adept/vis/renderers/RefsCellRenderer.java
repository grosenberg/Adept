/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import net.certiv.adept.vis.models.BaseTableModel;
import net.certiv.adept.vis.models.CorpusRefsTableModel;

public class RefsCellRenderer extends BaseCellRenderer {

	public RefsCellRenderer(CorpusRefsTableModel model, int alignment) {
		super(model, alignment);
	}

	@Override
	public Component adjustColors(Component c, JTable table, BaseTableModel model, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (isSelected) {
			c.setForeground(Color.white);
			c.setBackground(new Color(100, 150, 250));

		} else {
			// int mRow = table.convertRowIndexToModel(row);
			// Object kind = model.getValueAt(mRow, 2); // kind

			c.setForeground(Color.black);
			c.setBackground(Color.white);
		}
		return c;
	}
}
