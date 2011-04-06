package fox.brian.matrix;

public class ScalingMatrix extends Matrix {

	double x,y,z;
	
	public ScalingMatrix(double x, double y, double z) {
		super(4,4);
		for (int i=0; i < 4; i++) 
			matrix[i*4+i] = 1;
		matrix[0*4+0] = x;
		matrix[1*4+1] = y;
		matrix[2*4+2] = z;
		
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public ScalingMatrix(double allAxis) {
		super(4,4);
		for (int i=0; i < 4; i++) 
			matrix[i*4+i] = 1;
		matrix[0*4+0] = allAxis;
		matrix[1*4+1] = allAxis;
		matrix[2*4+2] = allAxis;
		
		this.x = allAxis;
		this.y = allAxis;
		this.z = allAxis;
	}
	
	@Override
	public String toString() {
		return String.format("%8.3f %8.3f %8.3f", x, y, z);
	}
}
