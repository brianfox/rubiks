package com.seefoxrun.rubiks.visualization.itext.graphs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.HashMap;

import com.seefoxrun.rubiks.model.operators.Twist;
import com.seefoxrun.rubiks.model.stateful.tree.original.SolutionNode;
import com.seefoxrun.rubiks.model.stateful.tree.original.SolutionTree;
import com.seefoxrun.rubiks.model.stateful.tree.original.TreeStatistics;
import com.seefoxrun.rubiks.visualization.itext.base.CubeDrawing;
import com.seefoxrun.rubiks.visualization.itext.base.Graph;
import com.seefoxrun.rubiks.visualization.itext.options.Defaults;
import com.seefoxrun.rubiks.visualization.itext.options.GraphicalOptionManager;
import com.seefoxrun.visualization.Document;
import com.seefoxrun.visualization.Point;
import com.seefoxrun.visualization.measurement.Units;
import com.seefoxrun.visualization.measurement.Value;

public class RadialGraph extends Graph {

	private TreeStatistics stats;
	private Point center;
	Graphics2D g2;
	Guide guides;
	
	GraphicalOptionManager optionManager;
	BasicStroke guideStroke;
	
	static int counter1 = 0;
	static int radialCounter = 1;
	static int counter3 = 0;
	
	public RadialGraph(SolutionTree tree, Document document, GraphicalOptionManager o) throws IOException {
		
		super(tree, document);
		g2 = document.getGraphics2D();
		
		center = document.center();
		stats = new TreeStatistics(tree);
		
		this.optionManager = o;
		
		System.out.println(o.toString());
		Value maxRadius = document.height().compareTo(document.width()) > 0 ? 
				document.width().div(2) : document.height().div(2);
		
		guides = new Guide(maxRadius);
		guideStroke = new BasicStroke(
				(float)o
				.getValue(Defaults.RADIAL_SCOPE, Defaults.GUIDE_LINE_WIDTH)
				.convertTo(Units.POINTS)
		);
		
		
		document.addLayer("Guides");
		document.addLayer("Wedges");
		document.addLayer("Connectors");
		document.addLayer("Nodes");
	}

	@Override
	public void draw() {
		RadialNode root = new RadialNode(null, tree.getSolutionNode(tree.getRoot()), center, 360.0, 0.0);
		root.draw();
	}


	public void drawCenteredString(Graphics2D g, String s, float x, float y) {
		int width = g.getFontMetrics().stringWidth(s);
		int height = g.getFontMetrics().getHeight();
		g.drawString(s, x - width/2, y + height/3);
	}
	
	/* ----------------------------------------------------------------------------*/
	/* ----------------------------------------------------------------------------*/
	

	protected class RadialConnector extends Graph.Connector {

		public RadialConnector(Point a, Point b) {
			super(a, b);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void draw() {
			double x1 = a.x().convertTo(Units.POINTS); 
			double y1 = a.y().convertTo(Units.POINTS);
			double x2 = b.x().convertTo(Units.POINTS);
			double y2 = b.y().convertTo(Units.POINTS);

			
			Graphics2D g = document.switchLayer("Connectors");
			GeneralPath p = new GeneralPath();
			p.moveTo(x1, y1);
			p.lineTo(x2, y2);
			
			g.setColor(optionManager.getColor(Defaults.RADIAL_SCOPE, Defaults.CONNECTOR_LINE_COLOR));
			g.draw(p);
			double x = (x1 + x2) / 2; 
			double y = (y1 + y2) / 2; 

			g.setColor(Color.black);
			
			g.drawString(String.format("%d", radialCounter++),(float)x, (float)y);
		}
		
	}

	
	/* ----------------------------------------------------------------------------*/
	/* ----------------------------------------------------------------------------*/
	
	protected class Guide {

		private Value[] radials;

		public Guide(Value maxRadius) {
			radials = new Value[stats.getDepth()+1];
			for (int i=0; i < stats.getDepth() + 1; i++)
				radials[i] = maxRadius.div(stats.getDepth()).mul(i).mul(0.95);
		}
		
		public void draw() {
			Graphics2D g = document.switchLayer("Guides");
			g.setStroke(guideStroke);
			g.setColor(optionManager.getColor(Defaults.RADIAL_SCOPE, Defaults.GUIDE_LINE_COLOR));
			for (int i=1; i < radials.length; i++) {
				g.draw(getCircle(center, radials[i]));
			}
	 	}

		public Value getGuide(int height) {
			return radials[height];
		}
		
		public Value getNodeRadius(int height) {
			double guideCircum = 2 * Math.PI * guides.radials[height].convertTo(Units.POINTS);
			double numberNodes = Math.pow(stats.getNumberUniqueTwists(), height);
			
			// 2: we have diameter at this point, want radius
			// 1.333: small scaling factor to tidy the graph
			return new Value(guideCircum / numberNodes / 2 / 1.333, Units.POINTS);
		}
	}
	

	/* ----------------------------------------------------------------------------*/
	/* ----------------------------------------------------------------------------*/
	
	protected class Wedge {

		private int low_guide;
		private double angle1;
		private double angle2;

		public Wedge(int low_guide, double angle1, double angle2) {
			this.low_guide = low_guide;
			this.angle1 = angle1;
			this.angle2 = angle2;
		}

		
		public void draw() {
			if (low_guide == 0)
				return;
			double radius1 = guides.radials[low_guide].convertTo(Units.POINTS);
			double radius2 = guides.radials[guides.radials.length - 1].convertTo(Units.POINTS);
			
			double cx = center.x().convertTo(Units.POINTS);
			double cy = center.y().convertTo(Units.POINTS);
			
			double x11 = cx + radius1*Math.cos(angle1*Math.PI/180); 
			double y11 = cy + radius1*Math.sin(angle1*Math.PI/180); 
			double x12 = cx + radius2*Math.cos(angle1*Math.PI/180); 
			double y12 = cy + radius2*Math.sin(angle1*Math.PI/180); 

			
			double x21 = cx + radius2*Math.cos(angle2*Math.PI/180); 
			double y21 = cy + radius2*Math.sin(angle2*Math.PI/180);
			double x22 = cx + radius2*Math.cos(angle2*Math.PI/180); 
			double y22 = cy + radius2*Math.sin(angle2*Math.PI/180);

			// double x = cx + (radius1+radius2)/2*Math.cos(angle1*Math.PI/180); 
			// double y = cy + (radius1+radius2)/2*Math.sin(angle1*Math.PI/180); 

			Graphics2D g = document.switchLayer("Wedges");
			Line2D.Double line1 = new Line2D.Double(x11, y11, x12, y12);
			Line2D.Double line2 = new Line2D.Double(x21, y21, x22, y22);
			
			g.setStroke(new BasicStroke(10 / low_guide));
			g.setColor(optionManager.getColor(Defaults.RADIAL_SCOPE, Defaults.WEDGE_LINE_COLOR));
			g.draw(line1);
			g.draw(line2);

	 	}

	}
	

	/* ----------------------------------------------------------------------------*/
	/* ----------------------------------------------------------------------------*/

	protected class RadialNode extends Graph.GraphNode {

		double arc_high;
		double arc_low;
		double arc_span;
		double arc_increment;
		double next_arc;

		RadialConnector connector;
		Wedge wedge;
		
		public RadialNode(GraphNode parent, SolutionNode n, Point location, Double arc_high, Double arc_low) {
			super(parent, n, location);
			this.arc_high = arc_high;
			this.arc_low = arc_low;
			this.arc_span = arc_high - arc_low;
			this.arc_increment = arc_span / stats.getNumberUniqueTwists();
			this.next_arc = arc_low + arc_increment / 2;

			this.connector = parent == null ? null : new RadialConnector(parent.location(), this.location);
			this.wedge = new Wedge(this.height(), next_arc - arc_increment/2, next_arc + arc_increment/2);
		}

		private void drawCircle() {
			Value radius;
			if (this.height() == 0)
				radius = guides.getNodeRadius(1).div(2);
			else
				radius = guides.getNodeRadius(this.height());
			
			Graphics2D g = document.switchLayer("Nodes");
			Ellipse2D e = getCircle(location, radius);
			g.setStroke(new BasicStroke((float)(radius.convertTo(Units.POINTS)/3)));
			g.setColor(optionManager.getColor(Defaults.RADIAL_SCOPE, Defaults.NODE_LINE_COLOR));
			g.draw(e);
			g.setColor(optionManager.getColor(Defaults.RADIAL_SCOPE, Defaults.NODE_FILL_COLOR));
			g.fill(e);
			// double x = location.x().convertTo(Units.POINTS);
			// double y = location.y().convertTo(Units.POINTS);
			
			/*
			g.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
			double width = g.getFontMetrics().stringWidth(String.format("%d", counter1));
			if (width > radius.convertTo(Units.POINTS)) {
				width = radius.convertTo(Units.POINTS) / 2 + 1;
				g.setFont(new Font("Sans-Serif", Font.PLAIN, (int)width));
			}
			g.setColor(Color.black);
			drawCenteredString(g, String.format("%d", counter1++),(float)x, (float)y);
			*/ 
			
			CubeDrawing d = new CubeDrawing(
					this.source(),
					location.x().convertTo(Units.POINTS),
					location.y().convertTo(Units.POINTS),
					radius.convertTo(Units.POINTS) * 1.5 
			);
			d.draw(g);
			
		}
		

		private void drawConnector() {
			if (connector != null)
				connector.draw();
		}

		private void drawWedge() {
			if (wedge != null)
				wedge.draw();
		}

		@Override
		public void draw() {
			drawPass(0);
			guides.draw();
			drawPass(1);
			drawPass(2);
		}


		public void drawPass(int pass) {
			

			if (pass==0)
				drawWedge();
			if (pass==1)
				drawConnector();
			if (pass==2)
				drawCircle();
			
			HashMap<Twist,SolutionNode> twistMap = new HashMap<Twist,SolutionNode>(); 
			for (SolutionNode n : this.children()) {
				twistMap.put(n.twist(), n); 
			}
			
			for (Twist t : stats.getUniqueTwists()) {
				if (twistMap.containsKey(t)) {
					SolutionNode n = twistMap.get(t);
					double guide_radius = guides.getGuide(this.height() + 1).convertTo(Units.POINTS);
					double x = guide_radius * Math.cos(next_arc * Math.PI / 180)+ center.x().convertTo(Units.POINTS);
					double y = guide_radius * Math.sin(next_arc * Math.PI / 180)+ center.y().convertTo(Units.POINTS);
					Point p = new Point(new Value(x,Units.POINTS),new Value(y, Units.POINTS));

					
					RadialNode rn = new RadialNode(
							this,
							n, 
							p, 
							next_arc - arc_increment/2, 
							next_arc + arc_increment/2
					);
					
					rn.drawPass(pass);
				}
				next_arc += arc_increment;
			}
		}

	
	}
}
