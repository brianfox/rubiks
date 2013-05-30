package com.seefoxrun.rubiks.visualization.itext3d.objects.paths;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import com.seefoxrun.matrix.Matrix;
import com.seefoxrun.matrix.ScalingMatrix;
import com.seefoxrun.rubiks.visualization.itext3d.objects.BoundingBox;
import com.seefoxrun.rubiks.visualization.itext3d.objects.Object3D;

/**
 * The Path class provides the smallest building block available for
 * creating larger and more complex 3D objects.  A Path is intended 
 * to create a vector-like 2D planar object which can be manipulated
 * with typical 3D transformation vectors.
 * 
 * As a general class contract, all paths are created in the X-Y plane 
 * with a barycenter at the origin (0,0,0,1) and a normal aligned with 
 * the unit Z axis (0,0,1,1).
 *  
 * @author brianfox
 *
 */

public abstract class Path {
	
	protected Color c = Object3D.DEFAULT_PATH_COLOR;
	protected ArrayList<Segment> segments;

	
	protected Path() {
		super();
		segments = new ArrayList<Segment>();
	}
	
	
	protected Path(Path p) {
		this();
		for (Segment s : p.segments)
			segments.add(s);
		this.c = p.c;
	}
 
	public void scale(double x) {
		scale(x,x);
	}

	public void scale(double x, double y) {
		for (Segment s : segments) {
			s.scale(x,y);
		}
	}
	
	public void setColor(Color c) {
		this.c = c;
	}
	
	public void draw(Graphics2D g, Matrix m) {
		System.out.println(this);
		Area a = getArea(m);
		g.setColor(c);
		g.fill(a);
	}
	
	protected Area getArea(Matrix m) {
		if (segments.size() == 0)
			return new Area();
		
		GeneralPath gp = new GeneralPath();
		boolean first = true;
		for (Segment s : segments) {
			if (first) {
				s.moveTo(gp, m);
				s.drawTo(gp,m);
				first = false;
			}
			s.drawTo(gp,m);
		}
		gp.closePath();
		return new Area(gp);
	}


	public BoundingBox getBoundingBox() {
		BoundingBox b = new BoundingBox();
		for (Segment s : segments) {
			b = b.expandByBoundingBox(s.getBoundingBox());
		}
		return b;
	}

	
	

	/* inner classes below */
	/* inner classes below */
	/* inner classes below */
	/* inner classes below */
	/* inner classes below */
	/* inner classes below */
	/* inner classes below */
	/* inner classes below */
	/* inner classes below */
	/* inner classes below */
	/* inner classes below */
	
	protected abstract class Segment {
		public abstract Matrix drawTo(GeneralPath p, Matrix m);
		public abstract Matrix moveTo(GeneralPath p, Matrix m);
		public abstract void scale(double x, double y);
		public abstract BoundingBox getBoundingBox();
	}

	
	protected class Line extends Segment {

		private Matrix start,stop;
		
		public Line(Matrix start, Matrix stop) {
			this.start = start;
			this.stop  = stop;

		}
		
		
		
		public Line(double startX, double startY, double startZ, double stopX, double stopY, double stopZ) {
			this.start = new Matrix(new double[] {startX, startY, startZ, 1} );
			this.stop  = new Matrix(new double[] { stopX,  stopY,  stopZ, 1} );
		}

		@Override
		public void scale(double x, double y) {
			Matrix scale = new Matrix(new double[]{x,y,1,1});
			start =    start.multiply(scale);
			stop =     stop.multiply(scale);
		}

		@Override 
		public String toString() {
			return String.format("point: %s  to  point: %s", start, stop);
		}



		@Override
		public Matrix drawTo(GeneralPath path, Matrix m) {
			Matrix p = m == null ? stop : m.multiply(stop);
			double px = p.getAt(0) / p.getAt(3);
			double py = p.getAt(1) / p.getAt(3);
			
			path.lineTo(px, py);
			return p;
		}



		@Override
		public Matrix moveTo(GeneralPath path, Matrix m) {
			Matrix p = m == null ? start : m.multiply(start);
			double px = p.getAt(0) / p.getAt(3);
			double py = p.getAt(1) / p.getAt(3);

			path.moveTo(px, py);
			return p;
		}

		@Override
		public BoundingBox getBoundingBox() {
			return new BoundingBox(start,stop);
		}
	}

	protected class Quadratic extends Segment {

		private Matrix start,stop,control;
		
		public Quadratic(Matrix start, Matrix control, Matrix stop) {
			this.start   = start;
			this.stop    = stop;
			this.control = control;
		}
		
		
		
		public Quadratic(
				double startX, double startY, double startZ, 
				double controlX, double controlY, double controlZ, 
				double stopX, double stopY, double stopZ) {
			this.start   = new Matrix(new double[] {startX, startY, startZ, 1} );
			this.stop    = new Matrix(new double[] { stopX,  stopY,  stopZ, 1} );
			this.control = new Matrix(new double[] { controlX, controlY, controlZ, 1} );

		}

		@Override
		public void scale(double x, double y) {
			Matrix scale = new ScalingMatrix(x, y, 1);
			start =    scale.multiply(start);
			stop =     scale.multiply(stop);
			control =  scale.multiply(control);
		}

		
		@Override
		public BoundingBox getBoundingBox() {
			return new BoundingBox(start,stop).expandByPoint(control);
		}

		@Override 
		public String toString() {
			return String.format("point: %s  to  point: %s", start, stop);
		}

		@Override
		public Matrix drawTo(GeneralPath path, Matrix m) {
			Matrix p2 = m.multiply(stop);
			double p2x = p2.getAt(0) / p2.getAt(3);
			double p2y = p2.getAt(1) / p2.getAt(3);
			
			Matrix c  = m.multiply(control);
			double cx = c.getAt(0) / c.getAt(3);
			double cy = c.getAt(1) / c.getAt(3);

			path.quadTo(cx, cy, p2x, p2y);
			return p2;
		}



		@Override
		public Matrix moveTo(GeneralPath path, Matrix m) {
			Matrix p1 = m.multiply(start);
			double p1x = p1.getAt(0) / p1.getAt(3);
			double p1y = p1.getAt(1) / p1.getAt(3);
			
			Matrix p2 = m.multiply(stop);
			double p2x = p2.getAt(0) / p2.getAt(3);
			double p2y = p2.getAt(1) / p2.getAt(3);
			
			Matrix c  = m.multiply(control);
			double cx = c.getAt(0) / c.getAt(3);
			double cy = c.getAt(1) / c.getAt(3);

			path.moveTo(p1x, p1y);
			path.quadTo(cx, cy, p2x, p2y);
			return p2;
		}

	}


}
