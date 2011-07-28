package com.seefoxrun.matrix;


public class MatrixTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Matrix a = new Matrix( new double[][]
		    { 
				{ 1, 2, 3, 4 }, 
				{ 5, 6, 7, 8}, 
				{ 9, 10, 11, 12 } 
			} );
		Matrix b = new Matrix( new double[][]
		    { 
				{ 1, 2, 3, 4, 5}, 
				{ 6, 7, 8, 9,10}, 
				{11,12,13,14,15}, 
				{16,17,18,19,20} 
			} );
		System.out.println(a);
		System.out.println(b);
		System.out.println(a.multiply(b));
		
		Matrix c = new TranslationMatrix(1,2,3);
		System.out.println(c);
		
	}

}
