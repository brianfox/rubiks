package com.seefoxrun.rubiks.visualization.itext3d.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import com.seefoxrun.matrix.Matrix;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.Path;

public class Simple extends Object3D {

	protected Path p;
	
	public Simple(Path p) {
		super();
		this.p = p;
	}

	@Override
	protected BoundingBox getBoundingBox(Matrix m) {
		BoundingBox b = p.getBoundingBox();
		b = b.multiply(squeeze(m));
		return b;
		
	}

	public String getName() {
		return p.getClass().getSimpleName();
	}

	@Override
	public void setColor(Color c) {
		p.setColor(c);
	}

	@Override
	public void draw(Graphics2D g, Matrix m) {
		// Matrix vischeck = this.getNormal(m);
		//		if (vischeck.getAt(2) <= 0)
			p.draw(g,this.squeeze(m));
	}

}
