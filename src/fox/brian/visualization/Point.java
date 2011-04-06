/*
 * $Id: Point.java,v 1.2 2010/03/10 23:04:21 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Point.java,v $
 * Revision 1.2  2010/03/10 23:04:21  bfox
 * Added cvs logging.
 *
 *
*/


package fox.brian.visualization;

import fox.brian.visualization.measurement.Units;
import fox.brian.visualization.measurement.Value;

public class Point {
	Value x, y;
	
	public Point(Value x, Value y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public Point(Point p, Units u) {
		x = new Value(p.x(),u);
		y = new Value(p.y(),u);
	}

	public Value x() {
		return x;
	}

	public Value y() {
		return y;
	}

	public String toString() {
		return String.format("[%2.3f %2.3f]", x, y);
	}
	
	public static double radiansBetween(Point a, Point b) {
		double deltax = b.x.getValue() - a.x.getValue();
		double deltay = b.y.getValue() - a.y.getValue();
		double hyp = Math.sqrt(Math.pow(deltax,2) + Math.pow(deltay,2));
		double radians = Math.asin((deltay)/hyp);
		if (deltax < 0) 
			return Math.PI - radians;
		return radians + (radians < 0 ? 2*Math.PI : 0);
	}

}
