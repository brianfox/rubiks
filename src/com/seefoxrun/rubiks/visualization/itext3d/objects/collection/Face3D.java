package com.seefoxrun.rubiks.visualization.itext3d.objects.collection;

import java.awt.Color;
import java.util.ArrayList;

import com.seefoxrun.rubiks.visualization.itext3d.objects.Compound;
import com.seefoxrun.rubiks.visualization.itext3d.objects.Simple;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection.RoundedRectangle;

public class Face3D extends Compound {

	public static double LABEL_GAP_SIZE  = 0.10; 
	public static double LABEL_ROUNDNESS = 0.20; 

	private int cubesize;

	ArrayList<Simple> faces;
	
	public Face3D(int cubesize) {
		this(cubesize, LABEL_GAP_SIZE, LABEL_ROUNDNESS);
	}

	public Face3D(int cubesize, double gap, double round) {
		super();
		this.cubesize = cubesize;
		faces = new ArrayList<Simple>();
		for (int y = 0; y < cubesize; y++) {
			for (int x=0; x < cubesize; x++) {
				Simple next = new Simple(new RoundedRectangle(LABEL_ROUNDNESS));
				next.scale(1-LABEL_GAP_SIZE, 1-LABEL_GAP_SIZE, 1,  getClass().getSimpleName() + " construction.  Scaling label for gap.");
				next.move(x+(-cubesize/2.0 + 0.5), y+(-cubesize/2.0 + 0.5), 0, getClass().getSimpleName() + " construction.  Positioning label.");
				add(next);
				faces.add(next);
			}
		}
		this.scale(1.0/cubesize,1.0/cubesize,1, getClass().getSimpleName() + " construction.  Scaling to unit size.");
	}

	
	public void paintTestPattern(Color base) {
		for (int i=0; i < cubesize; i++)
			for (int j=0; j < cubesize; j++) 
				faces.get(i*cubesize+j).setColor(base);
			
		faces.get(0).setColor(new Color(0x00,0x66,0x00));
		faces.get(cubesize*cubesize-1).setColor(new Color(0x66,0x00,0x00));
		faces.get(cubesize-1).setColor(Color.gray);
	}
}

