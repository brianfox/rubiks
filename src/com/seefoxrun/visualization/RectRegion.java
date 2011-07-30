package com.seefoxrun.visualization;

import com.seefoxrun.visualization.measurement.Value;

public class RectRegion {
	private Point ll;
	private Point ur;

	public RectRegion(Point lowerLeft, Point upperRight) {
		ll = lowerLeft;
		ur = upperRight;
	}

	public Point upperRight() {
		return ur;
	}

	public Point lowerLeft() {
		return ll;
	}

	public Point upperLeft() {
		return new Point(ll.x(), ur.y());
	}

	public Point lowerRight() {
		return new Point(ur.x(), ll.y());
	}

	public Value width() {
		return ur.x().sub(ll.x());
	}

	public Value height() {
		return ur.y().sub(ll.y());
	}

	public Point center() {
		return new Point(ll.x().add(ur.x()).div(2), ll.y().add(ur.y()).div(2));
	}

	public Value getMaxHeightWidth() {
		return (width().compareTo(height()) < 0) ? width() : height();
	}

}
