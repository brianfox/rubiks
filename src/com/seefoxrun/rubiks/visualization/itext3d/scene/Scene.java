package com.seefoxrun.rubiks.visualization.itext3d.scene;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.TreeSet;

import com.seefoxrun.matrix.IdentityMatrix;
import com.seefoxrun.matrix.Matrix;
import com.seefoxrun.rubiks.visualization.itext3d.objects.Compound;
import com.seefoxrun.rubiks.visualization.itext3d.objects.Simple;

public abstract class Scene {

	protected ArrayList<Simple> primatives;
	protected ArrayList<Compound> compounds;
	protected Matrix projection;
	
	Scene() {
		super();
	}

	public void draw(Graphics2D g) {
	}
}
