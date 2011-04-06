/*
 * $Id: Graph.java,v 1.3 2010/03/10 23:04:21 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Graph.java,v $
 * Revision 1.3  2010/03/10 23:04:21  bfox
 * Added cvs logging.
 *
 *
*/


package fox.brian.rubiks.visualization.itext.base;

import java.awt.geom.Ellipse2D;

import com.itextpdf.text.BaseColor;

import fox.brian.options.OptionManager;
import fox.brian.rubiks.model.stateful.tree.*;
import fox.brian.visualization.Document;
import fox.brian.visualization.Point;
import fox.brian.visualization.RectRegion;
import fox.brian.visualization.measurement.Units;
import fox.brian.visualization.measurement.Value;

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
