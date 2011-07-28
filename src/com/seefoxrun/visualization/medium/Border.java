/*
 * $Id: Border.java,v 1.2 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Border.java,v $
 * Revision 1.2  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package com.seefoxrun.visualization.medium;

import com.seefoxrun.visualization.measurement.Units;
import com.seefoxrun.visualization.measurement.Value;

public class Border {
	private Value left;
	private Value right;
	private Value top;
	private Value bottom;
	private Units units;
	
	public Border() {
		left = right = top = bottom = new Value(0.25F,Units.INCHES);
	}
	
	public Border(float left, float top, float right, float bottom, Units units) {
		this.top = new Value(top, units);
		this.left = new Value(left, units);
		this.right = new Value(right, units);
		this.bottom = new Value(bottom, units);
		this.units = units;
	}

	public Border(Border border) {
		this.left   = border.left;
		this.right  = border.right;
		this.top    = border.top;
		this.bottom = border.bottom;
		this.units  = border.units;
	}

	public Value getLeft() {
		return left;
	}

	public Value getRight() {
		return right;
	}

	public Value getTop() {
		return top;
	}

	public Value getBottom() {
		return bottom;
	}

	
	
}
