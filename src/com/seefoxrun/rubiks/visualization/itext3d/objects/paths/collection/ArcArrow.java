package com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection;

import com.seefoxrun.matrix.Matrix;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.BooleanOperator;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.BooleanPath;

public class ArcArrow extends BooleanPath {

	public ArcArrow(double width, double arcRadians, double arrowRadians, double arrowWidth) {
		construct(width, arcRadians, arrowRadians, arrowWidth);
	}

	public void construct(double width, double arcRadians, double arrowRadians, double arrowWidth) {
		
		Circle outer = new Circle();
		Circle inner = new Circle();
		Triangle wedge = new Triangle(
				0,0,0,
				2*Math.cos(0),2*Math.sin(0),0,
				2*Math.cos(arcRadians),2*Math.sin(arcRadians),0
		);
		Triangle arrow = makeArrow(arrowRadians, arrowWidth);



		outer.scale(1 + width/2);
		inner.scale(1 - width/2);

		paths.add(outer);
		operators.add(BooleanOperator.SUBTRACT);
		paths.add(inner);
		operators.add(BooleanOperator.SUBTRACT);
		paths.add(wedge);
		operators.add(BooleanOperator.ADD);
		paths.add(arrow);
	}

	private Triangle makeArrow(double arrowLength, double arrowWidth) {

		Matrix tip = new Matrix(new double[] {0.5, arrowLength, 0 });
		Matrix back1 = new Matrix(new double[] {0.5 + arrowWidth/2, 0, 0 });
		Matrix back2 = new Matrix(new double[] {0.5 - arrowWidth/2, 0, 0 });

		Triangle arrow = new Triangle(tip, back1, back2);
		return arrow;
	}
	
}

