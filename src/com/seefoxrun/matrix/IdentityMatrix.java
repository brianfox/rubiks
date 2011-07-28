package com.seefoxrun.matrix;

import java.util.Hashtable;

public class IdentityMatrix extends Matrix {

	private static Hashtable<Integer, IdentityMatrix> cache = new Hashtable<Integer, IdentityMatrix>();

	
	private IdentityMatrix(int n) {
		super(n,n);
		for (int i=0; i < n; i++) 
			matrix[i*n+i] = 1;
	}
	
	public static IdentityMatrix create(int n) {
		if (cache.containsKey(n))
			return cache.get(n);
		IdentityMatrix ret = new IdentityMatrix(n);
		cache.put(n,ret);
		return ret;
	}

}
