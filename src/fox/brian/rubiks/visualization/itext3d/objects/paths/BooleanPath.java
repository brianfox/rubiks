package fox.brian.rubiks.visualization.itext3d.objects.paths;

import java.awt.geom.Area;
import java.util.ArrayList;

import fox.brian.matrix.Matrix;
import fox.brian.rubiks.visualization.itext3d.objects.BoundingBox;

public abstract class BooleanPath extends Path {


	protected ArrayList<Path> paths;
	protected ArrayList<BooleanOperator> operators;

	protected BooleanPath() {
		super();
		paths = new ArrayList<Path>();
		operators = new ArrayList<BooleanOperator>();
	}
	
	@Override
	public Area getArea(Matrix m) {

		int i = 0;
		Area working = paths.get(0).getArea(m);
		while(i < operators.size()) {
			Path p = paths.get(i+1);
			Area operand2 = p.getArea(m);
			BooleanOperator operator = operators.get(i);
			
			switch (operator) {
				case ADD:
					working.add(operand2);
					break;
				case SUBTRACT:
					working.subtract(operand2);
					break;
				case INTERSECT:
					working.intersect(operand2);
					break;
			}
			i++;
		}
		return working;
	}
	
	@Override
	public BoundingBox getBoundingBox() {
		BoundingBox b = new BoundingBox();
		b = b.expandByBoundingBox(paths.get(0).getBoundingBox());

		int i = 0;
		while(i < operators.size()) {
			switch (operators.get(i)) {
				case ADD:
				case INTERSECT:
					b = b.expandByBoundingBox(paths.get(i+1).getBoundingBox());
					break;
			}
			i++;
		}
		return b;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.toString());
		sb.append(String.format("%n%s%n", "  Boolean Paths:"));

		for (Path p : paths) {
			sb.append(String.format("    %s%n", p.toString().replace("\n", "\n    ")));
		}
		return sb.toString();
	}
}
