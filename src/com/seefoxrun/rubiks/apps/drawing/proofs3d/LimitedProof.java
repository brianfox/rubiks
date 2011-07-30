package com.seefoxrun.rubiks.apps.drawing.proofs3d;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;


import com.itextpdf.text.DocumentException;

import com.seefoxrun.matrix.Axis;
import com.seefoxrun.matrix.Matrix;
import com.seefoxrun.matrix.TranslationMatrix;
import com.seefoxrun.rubiks.visualization.itext3d.objects.Object3D;
import com.seefoxrun.visualization.Document;
import com.seefoxrun.visualization.measurement.Units;
import com.seefoxrun.visualization.measurement.Value;
import com.seefoxrun.visualization.medium.Border;
import com.seefoxrun.visualization.medium.Page;

public abstract class LimitedProof {

	public void createProof(Object3D o) {
		String filename="test.pdf";
		Document doc = openDocument(filename);
	
		Graphics2D g = doc.getGraphics2D();
		g.setColor(Color.black);
		g.fillRect(0, 0, 5000, 5000);
		g.setBackground(Color.black);

		
		double scale = 200;
		
		o.scale(scale,scale,scale, getClass().getSimpleName() + " testing.  Visibility scale.");

		g.translate((int)doc.width().convertTo(Units.POINTS)/2, (int)doc.height().convertTo(Units.POINTS)/2);
		for (int i=0; i < 7; i++) {
			o.rotateDeg(Axis.X, 15, getClass().getSimpleName() + ": Testing x-axis rotation.");
			o.rotateDeg(Axis.Y, 15, getClass().getSimpleName() + ": Testing Y-axis rotation.");
		}

		Matrix perspective = new Matrix(
				new double[][]{
						{1,0,0,0},
						{0,1,0,0},
						{0,0,1,0},
						{0,0,0.001,1}
						});
		Matrix move = new TranslationMatrix(0,0,-4);
		Matrix projection = perspective.multiply(move);
		
		o.draw(g, projection);
		
		doc.close();
		launchDocument(filename);
	}


	private static Document openDocument(String filename) {
		Border border = new Border(
				.5F, 
				.5F, 
				.5F, 
				.5F, 
				Units.INCHES
		);
		
		Page page = new Page(
				new Value(11, Units.INCHES), 
				new Value(8.5, Units.INCHES), 
				border
		);
		Document doc = null;
		try {
			doc = new Document(filename, page);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		} catch (DocumentException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		return doc;
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
