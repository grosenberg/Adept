/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Myers Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.renderers;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class AlignCellRenderer extends DefaultTableCellRenderer {

	private int alignment;

	public AlignCellRenderer(int alignment) {
		this.alignment = alignment;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
			int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value, selected, focused, row, column);
		setHorizontalAlignment(alignment);
		switch (alignment) {
			case SwingConstants.LEFT:
				setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0));
				break;
			case SwingConstants.RIGHT:
				setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
				break;
			default:
		}
		return c;
	}
}
