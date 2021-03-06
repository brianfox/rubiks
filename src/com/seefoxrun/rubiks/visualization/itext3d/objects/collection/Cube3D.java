package com.seefoxrun.rubiks.visualization.itext3d.objects.collection;

import com.seefoxrun.matrix.Axis;
import com.seefoxrun.rubiks.visualization.itext3d.objects.Compound;
import com.seefoxrun.rubiks.visualization.itext3d.objects.Simple;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection.Rectangle;

public class Cube3D extends Compound {

	public Cube3D() {
		super();
		constructFaces();
	}

	private void constructFaces() {
		Simple top = new Simple(new Rectangle());
		top.rotateDeg(Axis.X, -90, "Cube3D construction.  Top face.");
		top.move(0,-0.5,0, "Cube3D construction.  Top face.");

		Simple left = new Simple(new Rectangle());
		left.rotateDeg(Axis.Y, 90, "Cube3D construction.  Top face.");
		left.move(-0.5,0,0, "Cube3D construction.  Top face.");

		Simple front = new Simple(new Rectangle());
		front.move(0,0,-0.5, "Cube3D construction. Front face.");
		
		Simple right = new Simple(new Rectangle());
		right.rotateDeg(Axis.Y, -90, "Cube3D construction.  Top face.");
		right.move(0.5,0,0, "Cube3D construction.  Top face.");
		
		Simple bottom = new Simple(new Rectangle());
		bottom.rotateDeg(Axis.X, 90, "Cube3D construction.  Top face.");
		bottom.move(0,0.5,0, "Cube3D construction.  Top face.");
		
		Simple back = new Simple(new Rectangle());
		back.rotateDeg(Axis.X, 180, "Cube3D construction.  Top face.");
		back.move(0,0,0.5, "Cube3D construction. Back face.");

		this.add(top);
		this.add(left);
		this.add(front);
		this.add(right);
		this.add(bottom);
		this.add(back);
	}

	public void paintTestPattern() {
		/*
		simples.get(0).setColor(Color.white);
		simples.get(1).setColor(Color.orange);
		simples.get(2).setColor(Color.red);
		simples.get(3).setColor(Color.blue);
		simples.get(4).setColor(Color.yellow);
		simples.get(5).setColor(Color.green);
		*/
	}

}

