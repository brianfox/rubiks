package com.seefoxrun.rubiks.model.stateful.tree.original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

import com.seefoxrun.rubiks.model.cube.Cube;
import com.seefoxrun.rubiks.model.cube.Dir;
import com.seefoxrun.rubiks.model.cube.Face;
import com.seefoxrun.rubiks.model.operators.Twist;
import com.seefoxrun.util.ArrayUtil;

public class SolutionNode implements Comparable<SolutionNode>, Cloneable {
	
	static final long serialVersionUID = 1L;

	private Cube cube = null;
	private Twist t = null;
	private boolean retired = false;
	private int height = 0;

	
	/* 
	 * QUICK NOTE ON CLASS MEMBERS AND SERIALIZATION
	 * 
	 * Children aren't serialized.  This is handled differently in the
	 * TreeSolution class.  It's important to note that a SolutionNode
	 * cannot be successfully deserialized without letting the 
	 * Solution Tree handle it.
	 */
	private HashMap<Twist,SolutionNode> children = new HashMap<Twist,SolutionNode>();

	

	/*-------------------------------------------------------------------------------------------*/
	/*
	 * CONSTRUCTORS
	 * 
	 * Another package visible constructor can be found at the bottom of this
	 * file.  It is organized with the serialization methods as that is it's
	 * proper use.
	 */
	/*-------------------------------------------------------------------------------------------*/
	

	public SolutionNode(SolutionNode parent, Cube cube, Twist t, int height) {
		this.t = t;
		this.cube = cube;
		this.height = height;
	}

	protected SolutionNode(SolutionNode n) {
		cube    = n.cube;
		t       = n.t;
		retired = n.retired;
		height  = n.height;

		for (Twist x : n.children.keySet())
			children.put(x, n.children.get(x));
	}



	
	/*-------------------------------------------------------------------------------------------*/
	/*
	 * PUBLIC METHODS
	 * 
	 * 
	 */
	/*-------------------------------------------------------------------------------------------*/
	
	public boolean addChild(SolutionNode n) {
		if (children.containsKey(n.twist()) || children.containsValue(n))
			return false;
		children.put(n.twist(),n);
		return true;
	}

	public Cube source() {
		return cube;
	}

	public Twist twist() {
		return t;
	}

	public TreeSet<Cube> getLeaves() {
		TreeSet<Cube> ret = new TreeSet<Cube>();
		getLeaves(ret);
		return ret;
	}

	private void getLeaves(TreeSet<Cube> soFar) {
		if (children.size() == 0 && !retired) 
			soFar.add(this.cube);
		for (SolutionNode child: children.values()) 
			child.getLeaves(soFar);
	}

	public ArrayList<SolutionNode> children() {
		ArrayList<SolutionNode> ret = new ArrayList<SolutionNode>();
		for (SolutionNode n : children.values())
			ret.add(n);
		return ret;
	}

	public void retire() {
		retired = true;
	}

	
	public int size() {
		int size = 1;
		for (SolutionNode child : children.values())
			size += child.size();
		return size;
	}

	
	public int height() {
		return height;
	}
	
	public void setHeight(int h) {
		height = h;
	}

	public SolutionNode getChild(Twist t) {
		return children.get(t);
	}

	@Override
	public int compareTo(SolutionNode o) {
		return cube.compareTo(o.cube);
	}
	

	
	
	
	
	/*-------------------------------------------------------------------------------------------*/
	/* 
	 * SERIALIZATION
	 * 
	 * Please note that this is partial serialization of this class.
	 * Full serialization is accomplished via the SolutionTree class.
	 * Hence, these methods should be kept private or package access
	 * only. 
	 */
	/*-------------------------------------------------------------------------------------------*/
	
	
	/**
	 * Serialization constructor.  Creates a new SolutionNode from
	 * the byte array provided.
	 * 
	 * @param src
	 */
	SolutionNode(byte[] src) {
		SolutionNode.unpackByteArray(this, src);
	} 

	
	
	/**
	 * | cbc0 | cbc1 | cbc2 | cbc3 | c[0] |    ...    | c[cbc] |
	 * |---------------------------|---------------------------| 
	 * |    cube byte count        |        cube array         |
	 *        (four bytes)                  (cbc bytes)
	 * 
	 * 
	 * | tbc3 | tbc2 | tbc1 | tbc0 | t[0] |    ...    | t[tbc] |
	 * |---------------------------|---------------------------| 
	 * |   twist byte count        |       twist array         |
	 * |     (four butes)                  (tbc bytes)
	 * 
	 * 
	 */
	
	
	public static byte[] packByteArray(SolutionNode n) {
		
		byte[] cubearr = n.cube.byteSerialize();
		byte[] cubesize = ArrayUtil.intToByteArray(cubearr.length);
		Twist altt = n.t;
		if (altt == null)
			altt = Twist.createTwist(Face.BACK, Dir.CCW, 0);
		byte[] twistarr = altt.byteSerialize();
		byte[] twistsize = ArrayUtil.intToByteArray(twistarr.length);
		return ArrayUtil.concatenateArrays(cubesize, cubearr, twistsize, twistarr);
	}

	
	public static void unpackByteArray(SolutionNode n, byte[] src) {
		int cubesize = ArrayUtil.byteArrayToInt(Arrays.copyOfRange(src, 0, 4));
		byte[] cube = Arrays.copyOfRange(src, 4, 4 + cubesize);
		int twistsize = ArrayUtil.byteArrayToInt(Arrays.copyOfRange(src, 4 + cubesize, 8 + cubesize));
		byte[] twist = Arrays.copyOfRange(src, 8 + cubesize, 8 + cubesize + twistsize);
		
		n.cube = Cube.byteDeserialize(cube);
		n.t = Twist.byteDeserialize(twist);
	} 

}
