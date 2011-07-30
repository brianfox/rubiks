package com.seefoxrun.rubiks.visualization.itext.base;

import java.awt.geom.Ellipse2D;

import com.itextpdf.text.BaseColor;

import com.seefoxrun.options.OptionManager;
import com.seefoxrun.rubiks.model.stateful.tree.*;
import com.seefoxrun.visualization.Document;
import com.seefoxrun.visualization.Point;
import com.seefoxrun.visualization.RectRegion;
import com.seefoxrun.visualization.measurement.Units;
import com.seefoxrun.visualization.measurement.Value;

/**
 * @author bfox
 *
 */
abstract public class Graph {

	protected SolutionTree   tree;
	protected Document       document;
	protected GraphNode      root;
	protected RectRegion     drawingRegion; 
	protected OptionManager  optionManager;


	
	public Graph(
			SolutionTree tree, 
			Document document 
	) {
		this.tree = tree;
		this.document = document;
		this.drawingRegion = null;
	}


	public abstract void draw();

	public Ellipse2D getCircle(Point center, Value radius) {
		double d = radius.convertTo(Units.POINTS) * 2;
		double x = center.x().convertTo(Units.POINTS) - d / 2;
		double y = center.y().convertTo(Units.POINTS) - d / 2;
		return new Ellipse2D.Double(x, y, d, d);
	}

	abstract public class GraphNode extends SolutionNode {
		
		protected Point location;
		protected GraphNode parent;
		
		abstract public void draw();


		public GraphNode(SolutionNode n, Point location) {
			super(n);
			this.location = location;
		}

		public GraphNode(GraphNode parent, SolutionNode n, Point location) {
			super(n);
			this.parent = parent;
			this.location = location;
		}


		public Point location() {
			return new Point(location);
		}
	}

	
	abstract public class Connector {
		
		protected Point a;
		protected Point b;
		protected BaseColor lineColor = BaseColor.BLACK;
		protected float lineWidth;

		public Connector(Point a, Point b) {
			this.a = a;
			this.b = b;
			// TODO
			// lineColor = o.getBaseColor(scope, GraphDefault.CONNECTOR_LINE_COLOR);
			// lineWidth = o.getFloat(scope, GraphDefault.CONNECTOR_LINE_WIDTH);
		}
		
		abstract public void draw();
	}


}
