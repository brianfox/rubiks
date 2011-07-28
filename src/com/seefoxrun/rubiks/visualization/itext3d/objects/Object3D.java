/*
 * $Id: Object3D.java,v 1.11 2010/08/24 00:31:13 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 *
*/
package com.seefoxrun.rubiks.visualization.itext3d.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.seefoxrun.matrix.*;

public abstract class Object3D {
	
	public static final boolean DRAW_BOUNDING_BOX = true;
	public static final Color DEFAULT_PATH_COLOR = Color.gray;
	public static final Color DEFAULT_BOUNDING_BOX_COLOR = Color.green;
	public static final Stroke DEFAULT_BOUNDING_BOX_STROKE = new BasicStroke(0.01F);

	private static Matrix defaultCenter = new Matrix(new double[]{0,0,0,1});
	private static Matrix defaultNormal = new Matrix(new double[]{0,0,1,1});

	protected ChainedMatrix operations;
	protected BoundingBox box;
	
	abstract protected BoundingBox getBoundingBox(Matrix m);
	
	private Matrix center = defaultCenter;
	private Matrix normal = defaultNormal;
	private String memo = "(none)";
	
	abstract public void setColor(Color c);
	abstract public void draw(Graphics2D g, Matrix m);

	
	public Object3D() {
		this.operations = new ChainedMatrix();
	}

	public Matrix getCenter() {
		return squeeze().multiply(center);
	}

	public Matrix getCenter(Matrix m) {
		return squeeze(m).multiply(center);
	}

	public Matrix getNormal() {
		Matrix end = squeeze().multiply(normal);
		Matrix start = squeeze().multiply(defaultCenter);
		return end.subtract(start);
	}

	public Matrix getNormal(Matrix m) {
		Matrix end = squeeze(m).multiply(normal);
		Matrix start = squeeze(m).multiply(defaultCenter);
		return end.subtract(start);
	}

	public Matrix squeeze() {
		Matrix op = operations.multiply();
		if (op == null)
			op = IdentityMatrix.create(4);
		return op;
	}

	public Matrix squeeze(Matrix m) {
		Matrix op = operations.multiply();
		if (op == null)
			return m;
		if (m == null)
			return op;
		return m.multiply(op);
	}


	/* All the pretty operations */
	
	public void rotate(RotationMatrix m, String note) {
		operations.addHead(m, note);
	}

	public void rotate(Axis a, double radians, String note) {
		operations.addHead(new RotationMatrix(a,radians), note);
	}
	
	public void rotateDeg(Axis a, double degrees, String note) {
		rotate(a,Math.toRadians(degrees), note);
	}
	
	public void scale(ScalingMatrix m, String note) {
		operations.addHead(m, note);
	}

	public void scale(double x, double y, double z, String note) {
		operations.addHead(new ScalingMatrix(x, y, z), note);
	}

	public void scale(double d, String note) {
		operations.addHead(new ScalingMatrix(d, d, d), note);
	}

	public void move(TranslationMatrix m, String note) {
		operations.addHead(m, note);
	}

	public void move(double x, double y, double z, String note) {
		operations.addHead(new TranslationMatrix(x, y, z), note);
	}

	
	public static final String STRING_INDENTATION = "    ";
	private static final String INDENTATIONS[] = new String[20];


	static {
		INDENTATIONS[0] = "";
		INDENTATIONS[1] = STRING_INDENTATION;
		for (int i=2; i < INDENTATIONS.length; i++)
			INDENTATIONS[i] = INDENTATIONS[i-1] + STRING_INDENTATION;
	}

	public static String indent(int level) {
		return INDENTATIONS[level];
	}
	
	public String toDebugString(Matrix m) {
		Matrix fin = this.squeeze(m).multiply(getCenter()).transpose();
		return String.format("%30s %20s", "[Unknown]_" + toString(), "[Center]_" + fin);
	}

	public String getMemo() {
		return memo;
	}
	

	public void setMemo(String s) {
		memo = s;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}



