package fox.brian.rubiks.visualization.itext3d.objects.paths.collection;

import fox.brian.matrix.Matrix;
import fox.brian.rubiks.visualization.itext3d.objects.paths.Path;

public class Triangle extends Path {

	public Triangle(Matrix a, Matrix b, Matrix c) {
		construct(a, b, c);
	}

	public Triangle(
			double ax, double ay, double az,
			double bx, double by, double bz,
			double cx, double cy, double cz ) {
		Matrix a = new Matrix( new double[] { ax, ay, az, 0 } ); 
		Matrix b = new Matrix( new double[] { bx, by, bz, 0 } ); 
		Matrix c = new Matrix( new double[] { cx, cy, cz, 0 } ); 
		construct(a, b, c);
	}

	public void construct(Matrix a, Matrix b, Matrix c) {
		segments.add(new Line(	
				a.getAt(0), a.getAt(1), a.getAt(2),  
				b.getAt(0), b.getAt(1), b.getAt(2) ) );

		segments.add(new Line(	
				b.getAt(0), b.getAt(1), b.getAt(2),  
				c.getAt(0), c.getAt(1), c.getAt(2) ) );

		segments.add(new Line(	
				c.getAt(0), c.getAt(1), c.getAt(2),  
				a.getAt(0), a.getAt(1), a.getAt(2) ) );
	}
 
}
