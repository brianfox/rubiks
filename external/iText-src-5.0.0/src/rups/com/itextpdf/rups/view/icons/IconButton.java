/*
 * $Id: IconButton.java,v 1.1 2010/01/02 22:57:12 bfox Exp $
 *
 * Copyright 2007 Bruno Lowagie.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.itextpdf.rups.view.icons;

import javax.swing.JButton;

/**
 * JButton with an Icon that performs an Action if you push it.
 */
public class IconButton extends JButton {

	/**
	 * Creates a button with an Icon that performs an
	 * action when you push it.
	 */
	public IconButton(IconActionListener listener) {
		super(listener.getIcon());
		addActionListener(listener);
	}
	
	/** A Serial Version UID. */
	private static final long serialVersionUID = 3523016393512117003L;
}
