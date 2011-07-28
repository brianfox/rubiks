package com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection;

import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.BooleanOperator;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.BooleanPath;

public class Ring extends BooleanPath {

	public Ring(double width) {
		construct(width);
	}

	public void construct(double width) {
		Circle outer = new Circle();
		Circle inner = new Circle();

		outer.scale(1 + width/2);
		inner.scale(1 - width/2);

		paths.add(outer);
		operators.add(BooleanOperator.SUBTRACT);
		paths.add(inner);
	}

	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HollowCircle (P):  TODO");
		return sb.toString();
	}
	
}
