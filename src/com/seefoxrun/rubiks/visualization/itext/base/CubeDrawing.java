/*
 * $Id: CubeDrawing.java,v 1.2 2010/03/10 23:04:21 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: CubeDrawing.java,v $
 * Revision 1.2  2010/03/10 23:04:21  bfox
 * Added cvs logging.
 *
 *
*/


package com.seefoxrun.rubiks.visualization.itext.base;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;

import com.seefoxrun.rubiks.model.cube.Cube;
import com.seefoxrun.rubiks.model.cube.Face;

public class CubeDrawing extends Area {


	
	public static double FACE_GAP = -0.00;
	public static double LABEL_GAP = 0.15;
	public static double LABEL_CORNER_ROUND = 0.2;

	private Cube c;
	private double x;
	private double y;
	private double size;

	
	public CubeDrawing(Cube c, double x, double y, double size) {
		this.c = c;
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	
	public void draw(Graphics2D g) {
		HashMap<Face, Color[][]> colorMap = c.getLabelColors();
		// size = 4 faces * unit 
		
		double unit = size / ( 4 + FACE_GAP );
		drawFace(g, colorMap.get(Face.TOP), x, y - 1.5 * (unit * (1 + FACE_GAP)), unit);
		drawFace(g, colorMap.get(Face.FRONT), x, y - 0.5 * (unit * (1 + FACE_GAP)), unit);
		drawFace(g, colorMap.get(Face.BOTTOM), x, y + 0.5 * (unit * (1 + FACE_GAP)), unit);
		drawFace(g, colorMap.get(Face.BACK), x, y + 1.5 * (unit * (1 + FACE_GAP)), unit);

		drawFace(g, colorMap.get(Face.LEFT), x - (unit * (1 + FACE_GAP)), y - 0.5 * (unit * (1 + FACE_GAP)), unit);
		drawFace(g, colorMap.get(Face.RIGHT), x + (unit * (1 + FACE_GAP)), y + 0.5 * (unit * (1 + FACE_GAP)), unit);
	}
	
	private void drawFace(Graphics2D g, Color[][] face, double x, double y, double size) {

		// size = cube_dim * ( unit + unit * gap_size ) 
		//      = cube_dim * ( ( 1 + gap_size) * unit )
		
		// unit = size / ( cube_dim * ( 1 + gap_size) )
		
		double unit = size / ( face.length * ( 1 + LABEL_GAP));
		double gap = unit * LABEL_GAP;
		
		double startX = x - size / 2 + unit / 2 + gap / 2;
		double startY = y - size / 2 + unit / 2 + gap / 2;
		double nextX = startX;
		double nextY = startY;
		
		for (Color[] row : face) {
			
			for (Color col : row) {
				drawLabel(g, nextX, nextY, unit, col);
				nextX += unit * (1 + LABEL_GAP);
			}
			nextX = startX;
			nextY += unit * (1 + LABEL_GAP);
		}
	}

	private void drawLabel(Graphics2D g, double x, double y, double size, Color c) {
		RoundRectangle2D.Double rect = new RoundRectangle2D.Double(
				x - size/2, y - size/2, 
				size, size, 
				size*LABEL_CORNER_ROUND,size*LABEL_CORNER_ROUND
		);
		g.setColor(c);
		g.fill(rect);
		g.setColor(Color.black);
		g.setStroke(new BasicStroke((float)size/20));
		g.draw(rect);
	}
}
