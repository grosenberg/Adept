/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.renderers;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import net.certiv.adept.vis.models.BaseTableModel;

public abstract class BaseCellRenderer extends DefaultTableCellRenderer {

	private BaseTableModel model;
	private int alignment;

	public BaseCellRenderer(BaseTableModel model, int alignment) {
		this.model = model;
		this.alignment = alignment;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		setHorizontalAlignment(alignment);
		switch (alignment) {
			case SwingConstants.LEFT:
				setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
				break;
			case SwingConstants.RIGHT:
				setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
				break;
			default:
		}

		adjustColors(c, table, model, isSelected, hasFocus, row, column);

		return c;
	}

	public abstract Component adjustColors(Component c, JTable table, BaseTableModel model, boolean isSelected,
			boolean hasFocus, int row, int column);
}
