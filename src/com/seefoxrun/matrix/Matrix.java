package com.seefoxrun.matrix;

public class Matrix {

	protected double[] matrix;
	int m;
	int n;

	protected Matrix(int m, int n) {
		matrix = new double[m*n];
		this.m = m;
		this.n = n;
	}

	public Matrix(double[][] a) {
		this.m = a.length;
		this.n = a[0].length;
		matrix = new double[m*n];
		for (int i=0; i < m; i++) {
			for (int j=0; j < n; j++) {
				matrix[i*n+j] = a[i][j];
			}
		}
	}

	public Matrix(double[] a) {
		this.m = a.length;
		this.n = 1;
		matrix = new double[m];
		for (int i=0; i < m; i++) {
			matrix[i] = a[i];
		}
	}

	public int m() {
		return m;
	}

	public int n() {
		return n;
	}
	
	public double getAt(int i, int j) {
		return matrix[i*n+j];
	}

	public double getAt(int i) {
		return matrix[i];
	}

	public Matrix multiply(Matrix a) {
		int o = a.m;
		int p = a.n;
		if (n != o)
			throw new IllegalArgumentException("The matrices cannot be multiplied because of dimension differences.");
		Matrix ret = new Matrix(m,p);
		for (int i=0; i < m; i++) {
	       for (int j=0; j < p; j++) {
	           double s = 0;
	           for (int k=0; k < n; k++) 
	               s += this.matrix[i*n+k] * a.matrix[k*p+j];
	           
	           ret.matrix[i*p+j] = s;
	       }
		}
		return ret;
	}

	
	public Matrix add(Matrix a) {
		if (this.n() != a.n() || this.m() != a.m())
			throw new IllegalArgumentException("The matrices cannot be added because of dimension differences.");

		Matrix ret = new Matrix(m,n);
		for (int i=0; i < matrix.length; i++) 
			ret.matrix[i] = this.matrix[i] + a.matrix[i];
		return ret;
	}

	public Matrix subtract(Matrix a) {
		if (this.n() != a.n() || this.m() != a.m())
			throw new IllegalArgumentException("The matrices cannot be subtracted because of dimension differences.");
		Matrix ret = new Matrix(this.m(),this.n());
		for (int i=0; i < matrix.length; i++) 
			ret.matrix[i] = this.matrix[i] - a.matrix[i];
		return ret;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i < m; i++) {
			for (int j=0; j < n; j++)
				sb.append(String.format("%.3f  ", matrix[i*n+j]));
			if (m - i > 1)
			sb.append(String.format("%n"));
		}
		
		return sb.toString();
	}

	public Matrix transpose() {
		Matrix ret = new Matrix(n,m);
		for (int i=0; i < m; i++) {
			for (int j=0; j < n; j++)
				ret.matrix[j*m + i] = this.matrix[i*n + j];
		}
		ret.m = n;  ret.n = m;
		
		return ret;
	}
}
