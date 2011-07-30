package com.seefoxrun.visualization.medium;

import com.seefoxrun.visualization.measurement.Units;
import com.seefoxrun.visualization.measurement.Value;

public class Page {
	private Value width;
	private Value height;
	private Border border;
	
	public Page(PageSize pageSize, Border border) {
		this.width =  pageSize.getWidth();
		this.height = pageSize.getHeight();
		this.border = new Border(border);
	}

	public Page(double width, double height, Units units, Border border) {
		this.width =  new Value(width, units);
		this.height = new Value(height, units);
		this.border = new Border(border);
	}

	public Page(Page page) {
		this.width = new Value(page.width);
		this.height = new Value(page.height);
		this.border = new Border(page.border);
	}

	public Page(Value width, Value height, Border border) {
		this.width =  new Value(width);
		this.height = new Value(height);
		this.border = new Border(border);
	}

	public Value getBorderLeft() {
		return border.getLeft();
	}

	public Value getBorderRight() {
		return border.getRight();
	}

	public Value getBorderTop() {
		return border.getTop();
	}

	public Value getBorderBottom() {
		return border.getBottom();
	}

	public Value getWidth() {
		return new Value(width);
	}
		
	public Value getHeight() {
		return new Value(height);
	}

	/*
	public RectRegion getDrawingArea() {
		Point ll;// = new Point();
		Point ur;//
		return null; //new RectRegion(ll,ur);
	}
	*/
	

}
