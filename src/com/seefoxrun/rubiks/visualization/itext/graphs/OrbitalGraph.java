package com.seefoxrun.rubiks.visualization.itext.graphs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import com.seefoxrun.rubiks.model.stateful.tree.original.SolutionNode;
import com.seefoxrun.rubiks.model.stateful.tree.original.SolutionTree;
import com.seefoxrun.rubiks.visualization.itext.base.Graph;
import com.seefoxrun.visualization.Document;
import com.seefoxrun.visualization.Point;
import com.seefoxrun.visualization.measurement.Units;

public class OrbitalGraph extends Graph {

	// private TreeStatistics stats;
	private Point center;
	Graphics2D g2;
	
	public OrbitalGraph(SolutionTree tree, Document document) throws IOException {
		super(tree, document);
		g2 = document.getGraphics2D();
		
		center = document.center();
		// stats = new TreeStatistics(tree);
		
		// Value maxRadius = document.height().compareTo(document.width()) > 0 ? 
		//		document.width().div(2) : document.height().div(2);
		document.addLayer("Guides");
		document.addLayer("Wedges");
		document.addLayer("Connectors");
		document.addLayer("Nodes");
	}

	@Override
	public void draw() {
		OrbitalNode root = new OrbitalNode(null, tree.getSolutionNode(tree.getRoot()), center, 360.0, 0.0);
		root.draw();
	}


	
	
	
	/* ----------------------------------------------------------------------------*/
	/* ----------------------------------------------------------------------------*/
	

	protected class OrbitalConnector extends Graph.Connector {

		public OrbitalConnector(Point a, Point b) {
			super(a, b);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void draw() {
			Graphics2D g = document.switchLayer("Connectors");
			g.setColor(Color.red);
			g.drawLine(
					(int)a.x().convertTo(Units.POINTS), 
					(int)a.y().convertTo(Units.POINTS),
					(int)b.x().convertTo(Units.POINTS),
					(int)b.y().convertTo(Units.POINTS)
			);
		}
		
	}

	
	/* ----------------------------------------------------------------------------*/
	/* ----------------------------------------------------------------------------*/

	protected class OrbitalNode extends Graph.GraphNode {

		double arc_high;
		double arc_low;
		OrbitalConnector connector;
		
		public OrbitalNode(GraphNode parent, SolutionNode n, Point location, Double arc_high, Double arc_low) {
			super(parent, n, location);
			this.arc_high = arc_high;
			this.arc_low = arc_low;
			this.connector = parent == null ? null : new OrbitalConnector(parent.location(), this.location);
		}

		/*
		private void drawConnector() {
			if (connector != null)
				connector.draw();
		}
		*/

		@Override
		public void draw() {
		}

	
	}
}
