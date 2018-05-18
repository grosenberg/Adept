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
import javax.swing.table.DefaultTableCellRenderer;

import net.certiv.adept.vis.models.TokenTableModel;

public class TokenCellRenderer extends DefaultTableCellRenderer {

	private TokenTableModel model;

	public TokenCellRenderer(TokenTableModel model) {
		this.model = model;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		Object val = model.getValueAt(row, 1); // hidden

		if (val.equals("Hdn")) {
			c.setForeground(Color.BLUE);
		} else {
			c.setForeground(Color.BLACK);
		}
		return c;
	}
}
