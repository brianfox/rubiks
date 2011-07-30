package com.seefoxrun.rubiks.visualization.itext.base;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;

import com.itextpdf.text.DocumentException;

import com.seefoxrun.rubiks.model.cube.Cube;
import com.seefoxrun.rubiks.model.cube.Face;
import com.seefoxrun.visualization.Document;
import com.seefoxrun.visualization.measurement.Units;
import com.seefoxrun.visualization.measurement.Value;
import com.seefoxrun.visualization.medium.Border;
import com.seefoxrun.visualization.medium.Page;

public class CubeDraw {

	static Graphics2D g;
	
	public static void main(String[] args) {
		
		Border border = new Border(
				1, 
				1, 
				1, 
				1, 
				Units.INCHES
		);
		
		Page page = new Page(
				new Value(10, Units.INCHES), 
				new Value(10, Units.INCHES), 
				border
		);
		Document doc = null;
		String filename = "d:\\test.pdf";
		try {
			doc = new Document(filename, page);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		} catch (DocumentException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		g = doc.getGraphics2D();
		
		Cube c = Cube.createCube(2);
		// drawCube(c.twist(Face.LEFT, Dir.CW,0), 200, 200, 100);
		drawCube(c, 200, 200, 100);
		double x = 200;
		double y = 200;
		double width = 100;
		Rectangle2D.Double rect = new Rectangle2D.Double(x - width / 2,y - width / 2,width,width);
		g.draw(rect);
		System.err.println(c);
		
		doc.close();

		launchDocument(filename);
	}

	public static double FACE_GAP = -0.00;
	public static double LABEL_GAP = 0.15;
	public static double LABEL_CORNER_ROUND = 0.2;

	public static void drawCube(Cube c, double x, double y, double size) {

		
		HashMap<Face, Color[][]> colorMap = c.getLabelColors();
		// size = 4 faces * unit 
		
		double unit = size / ( 4 + FACE_GAP );
		drawFace(colorMap.get(Face.TOP), x, y - 1.5 * (unit * (1 + FACE_GAP)), unit);
		drawFace(colorMap.get(Face.FRONT), x, y - 0.5 * (unit * (1 + FACE_GAP)), unit);
		drawFace(colorMap.get(Face.BOTTOM), x, y + 0.5 * (unit * (1 + FACE_GAP)), unit);
		drawFace(colorMap.get(Face.BACK), x, y + 1.5 * (unit * (1 + FACE_GAP)), unit);

		drawFace(colorMap.get(Face.LEFT), x - (unit * (1 + FACE_GAP)), y - 0.5 * (unit * (1 + FACE_GAP)), unit);
		drawFace(colorMap.get(Face.RIGHT), x + (unit * (1 + FACE_GAP)), y + 0.5 * (unit * (1 + FACE_GAP)), unit);
		
	}
	
	private static void drawFace(Color[][] face, double x, double y, double size) {

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
				drawLabel(nextX, nextY, unit, col);
				nextX += unit * (1 + LABEL_GAP);
			}
			nextX = startX;
			nextY += unit * (1 + LABEL_GAP);
		}
	}

	private static void drawLabel(double x, double y, double size, Color c) {
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


	
	private static void launchDocument(String filename) {
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            File pdfFile = new File(filename);
	        try {
	            desktop.open(pdfFile);
	        }
	        catch (IOException ioe) {
	            ioe.printStackTrace();
	        }
        }
	}

	
}
