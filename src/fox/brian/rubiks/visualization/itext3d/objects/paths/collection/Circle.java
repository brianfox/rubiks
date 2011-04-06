package fox.brian.rubiks.visualization.itext3d.objects.paths.collection;

import fox.brian.rubiks.visualization.itext3d.objects.paths.Path;

public class Circle extends Path {

	public Circle() {
		construct();
	}

	public void construct() {
		segments.add(new Quadratic(
				 0.0, 0.5, 0.0, 
				 0.5, 0.5, 0.0,
				 0.5, 0.0, 0.0    ) ); 
		segments.add(new Quadratic(
				 0.5, 0.0, 0.0, 
				 0.5,-0.5, 0.0,
				 0.0,-0.5, 0.0    ) ); 
		segments.add(new Quadratic(
				 0.0,-0.5, 0.0, 
				-0.5,-0.5, 0.0,
				-0.5, 0.0, 0.0    ) ); 
		segments.add(new Quadratic(
			    -0.5, 0.0, 0.0, 
				-0.5, 0.5, 0.0,
				 0.0, 0.5, 0.0    ) ); 

	}


}
