package fox.brian.rubiks.visualization.itext3d.objects.paths.collection;


import fox.brian.rubiks.visualization.itext3d.objects.paths.Path;

public class Rectangle extends Path {

	public Rectangle() {
		constructRectangle();
	}

	public void constructRectangle() {
		
		double full = 0.5;
		
		// counter clockwise assuming positive y goes upwards
		// (doesn't really matter except for readability)
		segments.add(new Line(	
				-full, full, 0,  
				 full, full, 0   ) ); // top

		segments.add(new Line( 
				 full,  full, 0,  
				 full, -full, 0) ); // right

		segments.add(new Line(
				 full, -full, 0, 
				-full, -full, 0) ); // bottom

		segments.add(new Line(
				-full, -full, 0, 
				-full,  full, 0) ); // left
	}
}
