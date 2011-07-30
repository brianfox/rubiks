package com.seefoxrun.rubiks.apps.drawing.proofs3d;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
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

public abstract class ObjectProof {

	public static Font smallFont = new Font("Serif", Font.ITALIC, 6);
	public static Font largeFont = new Font("Serif", Font.PLAIN, 12);

	private static Matrix perspective;
	private static Matrix move;
	private static Matrix projection;
	
	static {
		perspective = new Matrix(
				new double[][]{
						{1,0,0,0},
						{0,1,0,0},
						{0,0,1,0},
						{0,0,0.01,1}
						});
		move = new TranslationMatrix(0,0,-20);
		projection = perspective.multiply(move);
	}
	
	
	public void createProof(Object3D o) {
		String filename="test.pdf";
		Document doc = openDocument(filename);
	
		Graphics2D g = doc.getGraphics2D();
		g.setColor(Color.black);
		g.fillRect(0, 0, 5000, 5000);
		g.setBackground(Color.black);
		
		int step = 40;
		int count = 12;
		double scale = 13;
		double rot = 10;
		
		rot = 180 / count;
		
		o.scale(scale,scale,scale, getClass().getSimpleName() + " testing.  Visibility scale.");

		
		g.translate(step,step);
		
		processSimpleAxis(g, step, rot, count, Axis.X, o);
		processSimpleAxis(g, step, rot, count, Axis.Y, o);
		processSimpleAxis(g, step, rot, count, Axis.Z, o);

		processComplexAxis(g, step, rot, count, Axis.X, Axis.Y, o);
		processComplexAxis(g, step, rot, count, Axis.X, Axis.Z, o);
		

		doc.close();
		launchDocument(filename);
	}

	
	private void processComplexAxis(Graphics2D g, int step, double rot, int count, Axis axis1, Axis axis2, Object3D o) {
		System.out.printf("%n%nRotation about the x-axis, y-axis:%n");

		for (int i=0; i < count; i++) {
			o.draw(g, projection);
			drawRotationLabel(g,step, String.format("%3.1f",i*1.0*rot));
			rotate(o, axis1, rot);
			rotate(o, axis2, rot);
			g.translate(step,0);
		}
		
		g.translate(-step*count,step);
		
		for (int i=0; i < count; i++) {
			rotate(o, axis2, -rot);
			rotate(o, axis1, -rot);
			o.draw(g, projection);
			drawRotationLabel(g,step, String.format("%3.1f",(count + i)*rot*1.0));
			g.translate(step,0);
		}

		
		g.translate(0,-step/2);
		g.setFont(largeFont);
		g.setColor(new Color(0xCC,0xCC,0xCC));
		g.drawString("Rotation about " + axis1.toString() + "," + axis2.toString() + " axis",0,0);
		g.translate(0,step/2);

		g.translate(-step*count,step*1.5);

	}




	private void processSimpleAxis(Graphics2D g, double step, double rot, double count, Axis axis, Object3D o) {

		System.out.printf("Object Description:%n%n%s%n", o.toString());
		System.out.printf("%n%nRotation about the x-axis:%n");

		
		for (int i=0; i < count; i++) {
			o.draw(g, projection);
			drawRotationLabel(g,step, String.format("%3.1f",i*1.0*rot));
			rotate(o, axis, rot);
			g.translate(step,0);
		}
		g.translate(-step*count,step);
		for (int i=0; i < count; i++) {
			o.draw(g, projection);
			drawRotationLabel(g, step, String.format("%3.1f",(count + i)*rot*1.0));
			rotate(o, axis, rot);
			g.translate(step,0);
		}
		drawSimpleLabel(g, axis, step, rot, count);
		g.translate(-step*count, 1.5 * step);

	}

	private void drawSimpleLabel(Graphics2D g, Axis axis, double step, double rot, double count) {
		String label = "Rotation about the " + axis.toString() + " axis";
		g.translate(0,-step/2);
		g.setFont(largeFont);
		g.setColor(new Color(0xCC,0xCC,0xCC));
		
		g.drawString(label, 0, 0);
		g.translate(0,step/2);
	}
	
	private void rotate(Object3D o, Axis axis, double rot) {
		o.rotateDeg(
				axis, 
				rot, 
				getClass().getSimpleName() 
				+ String.format("Testing %s rotation.", axis.toString())
				);
	}
	
	private void drawRotationLabel(Graphics2D g, double step, String s) {
		g.translate(0,step/2);
		g.setColor(new Color(0x88,0x88,0x88));
		g.setFont(smallFont);
		g.drawString(s,0,0);
		g.translate(0,-step/2);
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
