package com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection;

import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.BooleanOperator;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.BooleanPath;

public class ArcRing extends BooleanPath {

	public ArcRing(double width, double radians) {
		construct(width, radians);
	}

	public void construct(double width, double radians) {
		Circle outer = new Circle();
		Circle inner = new Circle();
		Triangle wedge = new Triangle(
				0,0,0,
				2*Math.cos(0),2*Math.sin(0),0,
				2*Math.cos(radians),2*Math.sin(radians),0
		);

		outer.scale(1 + width/2);
		inner.scale(1 - width/2);

		paths.add(outer);
		operators.add(BooleanOperator.SUBTRACT);
		paths.add(inner);
		operators.add(BooleanOperator.SUBTRACT);
		paths.add(wedge);
	}
}
