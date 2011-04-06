package fox.brian.matrix;

public class TranslationMatrix extends Matrix {

	double x,y,z;
	
	public TranslationMatrix(double x, double y, double z) {
		super(4,4);
		for (int i=0; i < 4; i++) matrix[i*4+i] = 1;
		//matrix[0*4+3] = x;
		//matrix[1*4+3] = y;
		//matrix[2*4+3] = z;

		matrix[0*4+3] = x;
		matrix[1*4+3] = y;
		matrix[2*4+3] = z;
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		return String.format("%4.1f %4.1f %4.1f", x, y, z);
	}
}
