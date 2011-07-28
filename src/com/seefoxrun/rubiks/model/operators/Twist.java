/*
 * $Id: Twist.java,v 1.3 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Twist.java,v $
 * Revision 1.3  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package com.seefoxrun.rubiks.model.operators;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

import com.seefoxrun.rubiks.model.cube.Cube;
import com.seefoxrun.rubiks.model.cube.Dir;
import com.seefoxrun.rubiks.model.cube.Face;
import com.seefoxrun.util.ArrayUtil;

public final class Twist implements Cloneable, Serializable, Comparable<Twist> {
	
	private static final long serialVersionUID = 1L;
	private final Face f;
	private final Dir d;
	private final int slice;

	static HashMap<Integer,Twist> cache = new HashMap<Integer,Twist>();

	static private int getKey(Face f, Dir d, int s) {
		return (f.val() << 16) + (d.val() << 8) + s; 
	}
	
	
	public Twist(Face f, Dir d, int slice) {
		this.f = f;
		this.d = d;
		this.slice = slice;
		cache.put(getKey(f,d,slice),this);
	}

	public static Twist createTwist(Face f, Dir d, int slice) {
		int key = getKey(f,d,slice);
		if (cache.containsKey(key)) {
			return cache.get(key);
		}
		return new Twist(f,d,slice);
	}

	public Cube apply(Cube c) {
		return c.reverseTwist(f, d, slice);
	}

	
	public Dir getDir() {
		return d;
	}

	
	public Face getFace() {
		return f;
	}
	
	
	public int getSlice() {
		return slice;
	}

	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-4s", f.toString() + d.toString() + (slice == 0 ? "" : slice)));
		return sb.toString();
	}

	public boolean isInverse(Twist t) {
		return (t.f == f && t.slice == slice && t.d == d.getOpposite());
	}

	@Override
	public int compareTo(Twist t) {
		int diff;
		
		diff = f.val() - t.f.val();
		if (diff != 0)
			return diff;

		diff = d.val() - t.d.val();
		if (diff != 0)
			return diff;

		return slice - t.slice;
	}

	

	/*
	 
	 | f3 | f2 | f1 | f0 | d3 | d2 | d1 | d0 | s3 | s2 | s1 | s0 |
	 |       face        |        dir        |       slice       |
	 
	 Total size: 12 bytes 
	 
	 */
	
	public static Twist byteDeserialize(byte[] src) {
		int face = ArrayUtil.byteArrayToInt(Arrays.copyOfRange(src, 0, 4));
		int dir = ArrayUtil.byteArrayToInt(Arrays.copyOfRange(src, 4, 8));
		int s = ArrayUtil.byteArrayToInt(Arrays.copyOfRange(src, 8, 12));
		return createTwist(Face.getFace(face), Dir.getDir(dir), s);
	} 

	public byte[] byteSerialize() {
		
		byte[] face = ArrayUtil.intToByteArray(f.val());
		byte[] dir = ArrayUtil.intToByteArray(d.val());
		byte[] s = ArrayUtil.intToByteArray(slice);
		return ArrayUtil.concatenateArrays(face, dir, s);
	}
}
