package fox.brian.storage;

import java.util.ArrayList;

public class Metadata extends Node {

	/*	
			        	  this.routeByte
			        	        | 
			parent.routeByte    |
			                 \  |
			parent.parent.   |  |    
				routeByte \  |  |    ---------------
			              |  |  |    |     tail    |
					      v  v  v    |             |
					     [0][1][2][3][4][5][6]...[N]
					               ^
					               |
					               |
					           pivotByte
		
		The array's prefix can be rebuilt by assembling the routeByte
		of this object and its parent objects.
		
		The pivotByte provides this Metadata object with one of 256 
		other Node storage locations, all children to this node.
		
		Any time a byte array is added to this object, the tail is
		cut from the byte array and stored in the appropriate child
		Node.
		
		If a byte array is added to this object, and it doesn't have a
		tail, it is a "perfect fit."  The exactMatch flag is set to 
		true.
		
	*/	
	
	private Metadata parent;
	private byte routeByte;
	
	private ArrayList<Node> routes;
	private boolean exactMatch = false;
	private int depth;

	/*
	 * Constructs the root.
	 */
	public Metadata() {
		this(null,null);
	}
	
	Metadata(Byte routeByte, Metadata parent) {
		if (routeByte != null && parent != null) {
			this.routeByte = routeByte;
			this.parent = parent;
			this.depth = parent.depth + 1;
		} 
		else {
			depth = 0;
			parent = null;
			routeByte = 0;
		}
		this.routes = new ArrayList<Node>();
		for (int i=0; i < 256; i++) {
			routes.add(i,new Canister(this));
		}
	}
	
	void splitNode(Canister n) {
		// System.out.println("Split!");
		Metadata replacement = new Metadata(this.routeByte, this);
		for (byte[] b : n.store) {
			replacement.add(b);
		}
		int ix = routes.indexOf(n);
		routes.set(ix, replacement);
	
	}
	
	public int getDepth() {
		return depth;
	}

	


	private byte[] ltrim(byte[] array, int count) {
		int newc = array.length - count;
		byte[] ret = new byte[newc];
		for (int i=0; i < newc; i++)
			ret[i] = array[i+count];
		return ret;
	}
	
	
	
	/** Routes a byte array to the appropriate Storage Node.
	 *  Routing should always begin at the top of the tree.
	 *  
	 *  Returns false if the array already exists.  Otherwise
	 *  places the array and returns true.
	 */
	@Override
	public boolean add(byte[] array) {

		// possible exact match?
		if (array.length == 0) {
			if (exactMatch) 
				return false;
			return (exactMatch = true);
		}
		int routeValue = array[0]&0xFF;
		byte[] tail = ltrim(array, 1);
		Node n = routes.get(routeValue);
		boolean ret = n.add(tail);
		return ret;
	}

	
	
	
	/** Finds a byte array.  Returns true if the array is stored. 
	 *  Otherwise returns false.
	 */
	@Override
	public boolean find(byte[] array) {
		if (array.length < this.depth)
			return false;
		if (array.length == this.depth && exactMatch)
			return true;
		return routes.get(array[this.depth + 1]).find(array);
	}

	
	public int size() {
		int sum = 0;
		for (int i=0; i < 256; i++)
			sum += routes.get(i).size();
		if (exactMatch)
			sum++;
		return sum;
	}
	
	public int depth() {
		return depth;
	}
}
