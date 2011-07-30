package com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection;

import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.Path;

public class RoundedRectangle extends Path {

	public RoundedRectangle(double cornerRadius) {
		constructLabel(cornerRadius);
	}

	public void constructLabel(double cornerRadius) {
		double full = 0.5;
		double part = full - cornerRadius / 2;
		
		// counter clockwise assuming positive y goes upwards
		// (doesn't really matter except for readability)
		segments.add(new Line(	
				-part, full, 0,  
				 part, full, 0   ) ); // top

		segments.add(new Quadratic(
				 part, full, 0, 
				 full, full, 0,
				 full, part, 0)  ); // top-right
		
		segments.add(new Line( 
				 full,  part, 0,  
				 full, -part, 0) ); // right

		segments.add(new Quadratic(
				 full, -part, 0, 
				 full, -full, 0,
				 part, -full, 0)  ); // bottom-right

		segments.add(new Line(
				 part, -full, 0, 
				-part, -full, 0) ); // bottom

		segments.add(new Quadratic(
				-part, -full, 0, 
				-full, -full, 0,
				-full, -part, 0) ); // top-left

		segments.add(new Line(
				-full, -part, 0, 
				-full,  part, 0) ); // left

		segments.add(new Quadratic(
				-full,  part, 0, 
				-full,  full, 0,
				-part,  full, 0)  ); // upper-left
	}

}
