package com.seefoxrun.rubiks.visualization.itext3d.objects.collection;

import com.seefoxrun.matrix.Axis;
import com.seefoxrun.rubiks.model.cube.Face;
import com.seefoxrun.rubiks.model.cube.Piece;
import com.seefoxrun.rubiks.visualization.itext3d.objects.Compound;

public class RubiksCube3D extends Compound {

	private int cubesize;

	public RubiksCube3D(int cubesize) {
		super();
		
		this.cubesize = cubesize;
		construct();
	}

	private void construct() {
		Face3D front = createFace(Face.FRONT);
		front.move(0,0,-0.5, "RubiksCube3D construction. Front face.");
		this.add(front);

		Face3D back = createFace(Face.BACK);
		back.rotateDeg(Axis.X, 180, "Cube3D construction.  Top face.");
		back.move(0,0,0.5, "RubiksCube3D construction. Back face.");
		this.add(back);

		Face3D right = createFace(Face.RIGHT);
		right.rotateDeg(Axis.Y, -90, "Cube3D construction.  Top face.");
		right.move(0.5,0,0, "RubiksCube3D construction.  Top face.");
		this.add(right);

		Face3D left = createFace(Face.LEFT);
		left.rotateDeg(Axis.Y, 90, "RubiksCube3D construction.  Top face.");
		left.move(-0.5,0,0, "RubiksCube3D construction.  Top face.");
		this.add(left);
		
		Face3D top = createFace(Face.TOP);
		top.rotateDeg(Axis.X, -90, "RubiksCube3D construction.  Top face.");
		top.move(0,-0.5,0, "RubiksCube3D construction.  Top face.");
		this.add(top);

		Face3D bottom = createFace(Face.BOTTOM);
		bottom.rotateDeg(Axis.X, 90, "RubiksCube3D construction.  Bottom face.");
		bottom.move(0,0.5,0, "RubiksCube3D construction.  Bottom face.");
		this.add(bottom);
	}
	
	private Face3D createFace(Face f) {
		Face3D face3d = new Face3D(cubesize); 
		face3d.setMemo(String.format("%-7s (%s)", f.getDescription(), Piece.FACECOLORS_FACE[f.val()]));
		face3d.paintTestPattern(Piece.AWTCOLOR_FACE[f.val()]);
		return face3d;
	}

}

