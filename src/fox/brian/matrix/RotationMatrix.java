package fox.brian.matrix;

public class RotationMatrix extends Matrix {

	private Axis axis;
	private double radians;
	
	public RotationMatrix(Axis axis, double radians) {
		super(4,4);
		for (int i=0; i < 4; i++) matrix[i*4+i] = 1;
		if (axis == Axis.X) {
			matrix[1*4+1] =  Math.cos(radians);
			matrix[1*4+2] = -Math.sin(radians);
			matrix[2*4+1] =  Math.sin(radians);
			matrix[2*4+2] =  Math.cos(radians);
		}
		if (axis == Axis.Y) {
			matrix[0*4+0] =  Math.cos(radians);
			matrix[0*4+2] =  Math.sin(radians);
			matrix[2*4+0] = -Math.sin(radians);
			matrix[2*4+2] =  Math.cos(radians);
		}
		if (axis == Axis.Z) {
			matrix[0*4+0] =  Math.cos(radians);
			matrix[0*4+1] = -Math.sin(radians);
			matrix[1*4+0] =  Math.sin(radians);
			matrix[1*4+1] =  Math.cos(radians);
		}
		this.axis = axis;
		this.radians = radians;
	}
	
	@Override
	public String toString() {
		return String.format("%s %4.1f deg", axis, Math.toDegrees(radians));
	}

}
