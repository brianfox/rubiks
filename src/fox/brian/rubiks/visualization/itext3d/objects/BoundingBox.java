package fox.brian.rubiks.visualization.itext3d.objects;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;

import fox.brian.matrix.Matrix;

public class BoundingBox {

	private static final int FLL = 0;
	private static final int FLR = 1;
	private static final int FUL = 2;
	private static final int FUR = 3;
	private static final int BLL = 4;
	private static final int BLR = 5;
	private static final int BUL = 6;
	private static final int BUR = 7;
	private static final Matrix DEFAULT_POINT = new Matrix(new double[]{0,0,0,1});
	

	private Matrix[] points;
	
	public BoundingBox() {
		points = new Matrix[8];
		for (int i=0; i < 8; i++)
			points[i] = DEFAULT_POINT;
	}

	public BoundingBox(BoundingBox b) {
		points = new Matrix[8];
		for (int i=0; i < 8; i++)
			points[i] = b.points[i];
	}

	private BoundingBox(double[] x, double[] y, double[] z) {
		points = new Matrix[8];
		populate(x,y,z);
	}

	public BoundingBox(Matrix ... p) {
		this.points = new Matrix[8];
		double[] x = new double[p.length];
		double[] y = new double[p.length];
		double[] z = new double[p.length];
		
		for (int i=0; i < p.length; i++) {
			x[i] = p[i].getAt(0);
			y[i] = p[i].getAt(1);
			z[i] = p[i].getAt(2);
		}
		populate(x,y,z);
	}

	private void populate(double[] x, double[] y, double[] z) {
		double minx = min(x);
		double miny = min(y);
		double minz = min(z);
		double maxx = max(x);
		double maxy = max(y);
		double maxz = max(z);
		
		points[FLL] = new Matrix(new double[]{minx,miny,minz,1});
		points[FLR] = new Matrix(new double[]{maxx,miny,minz,1});
		points[FUL] = new Matrix(new double[]{minx,maxy,minz,1});
		points[FUR] = new Matrix(new double[]{maxx,maxy,minz,1});
		points[BLL] = new Matrix(new double[]{minx,miny,maxz,1});
		points[BLR] = new Matrix(new double[]{maxx,miny,maxz,1});
		points[BUL] = new Matrix(new double[]{minx,maxy,maxz,1});
		points[BUR] = new Matrix(new double[]{maxx,maxy,maxz,1});
	}

	public BoundingBox expandByBoundingBox(BoundingBox b) {
		double[] x = new double[16];
		double[] y = new double[16];
		double[] z = new double[16];
		for (int i=0; i < 8; i++) {
			x[i]   = this.points[i].getAt(0);
			x[i+8] =    b.points[i].getAt(0);
			y[i]   = this.points[i].getAt(1);
			y[i+8] =    b.points[i].getAt(1);
			z[i]   = this.points[i].getAt(2);
			z[i+8] =    b.points[i].getAt(2);
		}

		return new BoundingBox(x,y,z);
	}

	public BoundingBox expandByPoint(Matrix point) {
		double[] x = new double[9];
		double[] y = new double[9];
		double[] z = new double[9];

		for (int i=0; i < 8; i++)
			x[i] = points[i++].getAt(0);
		x[8] = point.getAt(0);
		
		for (int i=0; i < 8; i++)
			y[i] = points[i++].getAt(1);
		y[8] = point.getAt(1);

		for (int i=0; i < 8; i++)
			z[i] = points[i++].getAt(2);
		z[8] = point.getAt(2);

		return new BoundingBox(x,y,z);
	}

	private double min(double[] a) {
		double ret = Double.MAX_VALUE;
		for (double d : a)
			if (d < ret)
				ret = d;
		return ret;
	}

	private double max(double[] a) {
		double ret = Double.MIN_VALUE;
		for (double d : a)
			if (d > ret)
				ret = d;
		return ret;
	}

	public void draw(Graphics2D g) {
		GeneralPath path;
		g.setColor(Object3D.DEFAULT_BOUNDING_BOX_COLOR);
		g.setStroke(Object3D.DEFAULT_BOUNDING_BOX_STROKE);
		
		// front z=0		
		path = new GeneralPath();
		path.moveTo(points[FLL].getAt(0), points[FLL].getAt(1));
		path.lineTo(points[FLR].getAt(0), points[FLR].getAt(1));
		path.lineTo(points[FUR].getAt(0), points[FUR].getAt(1));
		path.lineTo(points[FUL].getAt(0), points[FUL].getAt(1));
		path.closePath();
		g.draw(new Area(path));

		// back z=1
		path = new GeneralPath();
		path.moveTo(points[BLL].getAt(0), points[BLL].getAt(1));
		path.lineTo(points[BLR].getAt(0), points[BLR].getAt(1));
		path.lineTo(points[BUR].getAt(0), points[BUR].getAt(1));
		path.lineTo(points[BUL].getAt(0), points[BUL].getAt(1));
		path.closePath();
		g.draw(new Area(path));

		// left x=0
		path = new GeneralPath();
		path.moveTo(points[FLL].getAt(0), points[FLL].getAt(1));
		path.lineTo(points[FUL].getAt(0), points[FUL].getAt(1));
		path.lineTo(points[BUL].getAt(0), points[BUL].getAt(1));
		path.lineTo(points[BLL].getAt(0), points[BLL].getAt(1));
		path.closePath();
		g.draw(new Area(path));

		// right x=0
		path = new GeneralPath();
		path.moveTo(points[FLR].getAt(0), points[FLR].getAt(1));
		path.lineTo(points[FUR].getAt(0), points[FUR].getAt(1));
		path.lineTo(points[BUR].getAt(0), points[BUR].getAt(1));
		path.lineTo(points[BLR].getAt(0), points[BLR].getAt(1));
		path.closePath();
		g.draw(new Area(path));
		
		// top
		path = new GeneralPath();
		path.moveTo(points[FUL].getAt(0), points[FUL].getAt(1));
		path.lineTo(points[FUR].getAt(0), points[FUR].getAt(1));
		path.lineTo(points[BUR].getAt(0), points[BUR].getAt(1));
		path.lineTo(points[BUL].getAt(0), points[BUL].getAt(1));
		path.closePath();
		g.draw(new Area(path));

		// bottom y=1
		path = new GeneralPath();
		path.moveTo(points[FLL].getAt(0), points[FLL].getAt(1));
		path.lineTo(points[FLR].getAt(0), points[FLR].getAt(1));
		path.lineTo(points[BLR].getAt(0), points[BLR].getAt(1));
		path.lineTo(points[BLL].getAt(0), points[BLL].getAt(1));
		path.closePath();
		g.draw(new Area(path));
		System.out.println(this);
	}
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Front Lower Left:  %s%n", points[FLL])); 
		sb.append(String.format("Front Lower Right: %s%n", points[FLR])); 
		sb.append(String.format("Front Upper Left:  %s%n", points[FUL])); 
		sb.append(String.format("Front Upper Right: %s%n", points[FUR])); 
		sb.append(String.format("Back  Lower Left:  %s%n", points[BLL])); 
		sb.append(String.format("Back  Lower Right: %s%n", points[BLR])); 
		sb.append(String.format("Back  Upper Left:  %s%n", points[BUL])); 
		sb.append(String.format("Back  Upper Right: %s",   points[FUR]));
		return sb.toString();
	
	}

	public BoundingBox multiply(Matrix m) {
		BoundingBox b = new BoundingBox();
		for (int i=0; i < 8; i++) {
			b.points[i] = m.multiply(this.points[i]);
		}
		return b;
	}

}
