package com.seefoxrun.rubiks.model.stateful.tree.original;

import java.util.*;
import java.io.*;

import com.seefoxrun.rubiks.model.cube.*;
import com.seefoxrun.rubiks.model.operators.*;
import com.seefoxrun.rubiks.model.stateful.experimental.Path;
import com.seefoxrun.rubiks.model.stateful.experimental.SymmetryPath;
import com.seefoxrun.rubiks.serialization.SerializationException;
import com.seefoxrun.util.*;

/**
 * This class maintains an tree of Cubes where each parent can
 * have an arbitrary number of children.    
 * 
 * @author Brian Fox
 *
 */
public class SolutionTree implements Serializable, Iterable<SolutionNode> {

	
	private static final long serialVersionUID = 1L;
	private SolutionNode root = null;  
	private int parms = 0;
	/* secondary data structure for O(log(n)) lookups */
	private TreeMap<Cube,SolutionNode> stored;
	
	
	/**
	 * Creates a SolutionTree.  The solution is seeded with the Cube
	 * parameter provided to the constructor.  For most purposes,
	 * this should be a solved cube. 
	 * 
	 * @param start Cube which seeds this SolutionTree.
	 */
	public SolutionTree(Cube start) {
		root = new SolutionNode(null, start, null, 0);
		stored = new TreeMap<Cube,SolutionNode>();
		stored.put(start,root);
	}
	

	/**
	 * Creates a SolutionTree.  The solution is seeded with the Cube
	 * parameter provided to the constructor.  For most purposes,
	 * this should be a solved cube. 
	 * 
	 * This constructor allows parameters to be specified which
	 * govern how the solution can be reduced.  Strategies include
	 * USE_SPATIAL, USE_COLORMAP, USE_REVERSE and others.  The Cube
	 * class has a full list.
	 * 
	 * @param start Cube which seeds this SolutionTree.
	 * @param parms an integer value made from summing the values 
	 *              USE_SPATIAL, USE_COLORMAP, USE_REVERSE
	 */
	public SolutionTree(Cube start, int parms) {
		this(start);
		this.parms = parms;
	}

	
	/**
	 * Creates a SolutionTree from the InputStream provided.  The 
	 * InputStream is assumed to have a contain data consistent with
	 * the writeTree() method.  
	 * 
	 * This constructor uses custom serialization designed to work
	 * across other programming languages.
	 * 
	 * @param in InputStream containing a serialized SolutionTree
	 * @throws SerializationException 
	 */
	public SolutionTree(InputStream in) throws IOException, SerializationException {
		readTree(in);
	}


	/**
	 * Creates a SolutionTree from the file specified by filename.  
	 * This constructor opens the file, reads in the serialized
	 * SolutionTree saved previously, and reconstructs a SolutionTree
	 * from that data.
	 * 
	 * This constructor uses custom serialization designed to work
	 * across other programming languages.
	 * 
	 * @param filename a String specifying a file containing a 
	 *                 serialized SolutionTree
	 * @throws SerializationException 
	 */
	public SolutionTree(String filename) throws IOException, SerializationException {

		FileInputStream in = new FileInputStream(filename);
		BufferedInputStream buff = new BufferedInputStream(in);
		DataInputStream data = new DataInputStream(buff);
		
		readTree(data);
		
		data.close();
		buff.close();
		in.close();

	}

	

	public SolutionNode getSolutionNode(Cube cube) {
		if (cube == null || !stored.containsKey(cube))
			return null;
		
		return stored.get(cube);
	}

	
	public SolutionNode getNode() {
		return getSolutionNode(root.source());
	}

	/**
	 * Adds a child to the WalkingTree.  The Cube parent should already
	 * be present in the tree.  The Cube c should not be present
	 * in the tree.  If these two conditions are satisfied, c will 
	 * be added as a child to parent and the method will return
	 * true.
	 * 
	 * @param start the Cube which will be the root of the tree.
	 * @return true if the child is added to the tree.
	 */
	public boolean add(Cube parent, Cube c, Twist t) {
		
		if (!stored.containsKey(parent))
			throw new NoSuchElementException();
		
		if (stored.containsKey(c))
			return false;
		
		SolutionNode nparent = stored.get(parent);
		SolutionNode nchild = new SolutionNode(nparent, c, t, nparent.height() + 1);
		nparent.addChild(nchild);
		stored.put(c, nchild);
		return true;
	}

	
	public Cube getRoot() {
		return root.source();
	}
	
	
	
	public int size() {
		return root.size();
	}

	public int depth() {
		int max = 0;
		PreorderIterator p = new PreorderIterator();
		while (p.hasNext()) {
			SolutionNode n = p.next();
			if (n.height() > max) {
				max = n.height();
			}
		}
		return max;
	}

	
	public int getMaxChildren() {
		int max = 0;
		PreorderIterator p = new PreorderIterator();
		while (p.hasNext()) {
			SolutionNode n = p.next();
			if (n.children().size() > max) {
				max = n.children().size();
			}
		}
		return max;
	}

	
	
	/**
	 * Finds symmetrical paths within a WalkingTree.  A symmetrical path is
	 * any path that travels from the root of the tree to a leaf of a tree 
	 * that shares similar twists (identical Face and slice).
	 *  
	 * @param sofar
	 * @param path1
	 * @param child1
	 * @param path2
	 * @param child2
	 */
	public TreeSet<SymmetryPath> findSymmetry() {
		TreeSet<SymmetryPath> ret = new TreeSet<SymmetryPath>();
		findSymmetry(ret, new Path(root.source()), root, new Path(root.source()), root);
		return ret;
	}

	public boolean advance() {

		TreeSet<Cube> leaves = getLeaves();
		if (leaves.isEmpty())
			return false;
		
		// int nliving = 0;
		// int ndead = 0;
		for (Cube c : leaves) {
			boolean living = false;
			for (Face f : Face.values()) {
				for (Dir d : Dir.values()) {
					TreeSet<Cube> equiv = c.twist(f, d, 0).getRelatedCubes(parms);
					Cube prospect = equiv.first();
					if (add(c, prospect, Twist.createTwist(f,d,0))) 
						living = true;
					else
						retireCube(c);
					
				}
			}
			if (living)
				;
			// 	nliving++;
			else {
			//	ndead++;
			    retireCube(c);
			}
		}
		return true;
	}


	public int countLeaves() {
		return root.getLeaves().size();
	}


	
	/*-------------------------------------------------------------------------------------------*/
	/* 
	 * PUBLIC METHODS
	 * Various Histograms
	 */
	/*-------------------------------------------------------------------------------------------*/

	public int[] histogramNodesPerHeight() {
		int[] ret = new int[1000];
		int max = 0;
		PreorderIterator p = new PreorderIterator();
		while (p.hasNext()) {
			SolutionNode n = p.next();
			ret[n.height()]++;
			if (n.height() > max) {
				max = n.height();
			}
		}
		int[] copy = new int[max+1];
		for (int i=0; i < copy.length; i++)
			copy[i] = ret[i];
		return copy;
	}


	
	/**
	 * Counts the number of times a Twist has been used to derive
	 * a new cube.  The results are stored in a TreeMap.  
	 * 
	 * @return a TreeMap<Twist, Integer> where the Integer value reflects
	 *         how many times the move Twist created a new cube.  
	 */
	public TreeMap<Twist,Integer> histogramTwist() {
		TreeMap<Twist,Integer> ret = new TreeMap<Twist,Integer>();
		PreorderIterator p = new PreorderIterator();
		boolean first = true;
		while (p.hasNext()) {
			SolutionNode n = p.next();
			if (first) {
				first = false;
				continue;
			}
			Twist t = n.twist();
			int c = 0;
			if (ret.containsKey(t)) {
				c = ret.get(t);
				ret.remove(t);
			}
			c++;
			ret.put(t, c);
		}
		return ret;
	}

	/*
	public int[] histogramChildrenPerHeight() {
		int[] ret = new int[1000];
		int max = 0;
		PreorderIterator p = new PreorderIterator();
		while (p.hasNext()) {
			SolutionNode n = p.next();
			ret[n.children().size()]++;
			if (n.children().size() > max) {
				max = n.children().size();
			}
		}
		int[] copy = new int[max+1];
		for (int i=0; i < copy.length; i++)
			copy[i] = ret[i];
		return copy;
	}
	*/



	
	/*-------------------------------------------------------------------------------------------*/
	/* 
	 * PRIVATE METHODS
	 * 
	 */
	/*-------------------------------------------------------------------------------------------*/
	
	private TreeSet<Cube> getLeaves() {
		return root.getLeaves();
	}

	
	
	private void retireCube(Cube c) {
		stored.get(c).retire();
	}

	
	/**
	 * Helper function that finds WalkingTree symmetry by traversing the tree 
	 * recursively.
	 * 
	 * @param sofar
	 * @param path1
	 * @param child1
	 * @param path2
	 * @param child2
	 */
	private void findSymmetry(
			TreeSet<SymmetryPath> sofar, 
			Path path1, 
			SolutionNode child1, 
			Path path2, 
			SolutionNode child2) {
		
		/* end condition at leaf */
		int size1 = child1.children().size();
		int size2 = child2.children().size();
		if (size1 == 0 || size2 == 0) 
			/* Done: chased down as far as possible */
			if (size1 == size2) {
				SymmetryPath ret = new SymmetryPath(path1.clone(), path2.clone());
				sofar.add(ret);
			}
		
		for (int i=0; i < child1.children().size(); i++) {
			for (int j=0; j < child2.children().size(); j++) {
				SolutionNode test1 = child1.children().get(i);
				SolutionNode test2 = child2.children().get(j);

				if (!test1.twist().isInverse(test2.twist()))
					continue;
				Path newpath1 = path1.clone();
				Path newpath2 = path2.clone();
				newpath1.add(test1.twist());
				newpath2.add(test2.twist());
				findSymmetry(sofar, newpath1, test1, newpath2, test2);
			}
		}
	}
	

	
	
	/*-------------------------------------------------------------------------------------------*/
	/* 
	 * ITERATORS
	 * 
	 */
	/*-------------------------------------------------------------------------------------------*/

	
	@Override
	public Iterator<SolutionNode> iterator() {
		// TODO Auto-generated method stub
		return new PreorderIterator();
	}

	
	private class PreorderIterator implements Iterator<SolutionNode> {

		Stack<SolutionNode> stack = new Stack<SolutionNode>();
		
		PreorderIterator() {
			if (root != null) stack.push(root);
		}
		
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public SolutionNode next() {
			if (!hasNext()) throw new NoSuchElementException();
			SolutionNode n = stack.pop();
			for (int i = n.children().size() - 1; i >= 0; i--)
				stack.push(n.children().get(i));
			return n;
		}

		@Override
		public void remove() {
			// not supported
		}
	}

	
	
	
	
	/*-------------------------------------------------------------------------------------------*/
	/* 
	 * SERIALIZATION
	 * 
	 */
	/*-------------------------------------------------------------------------------------------*/

	
	/**
	 * This method is the core serialization method for <code>WalkingTree</code>.
	 * It is used in lieu of Java's default serialization due to practical issues
	 * with Java's reference housekeeping when serializing objects.
	 * 
	 * Namely, the Java default method tries to keep references to all SolutionNodes 
	 * when the need doesn't exist.  Memory consumption explodes in a process 
	 * that is already a memory hog.  And suddenly we halve the available 
	 * memory.
	 * 
	 * In contrast, this method performs a preorder traversal of the 
	 * <code>WalkingTree</code> and writes each SolutionNode keeping necessary stats
	 * on items such as the number of children belonging to the SolutionNode.  In this
	 * way, the housekeeping can be kept to that needed just for the preorder
	 * traversal.
	 * 
	 * @param out
	 * @throws IOException
	 */
	public void writeTree(OutputStream out) throws IOException {

		int count = 0;
		PreorderIterator i = new PreorderIterator();
		while (i.hasNext()) {
			count++;
			i.next();
		}
		out.write(ArrayUtil.intToByteArray(count));
		
		
		/* 
		 * | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |    n + 8     |
		 * |    SolutionNode size  |    children   |    SolutionNode      |
		 */
		
		i = new PreorderIterator();
		while (i.hasNext()) {
			SolutionNode n = i.next();
			byte[] serialSolutionNode = SolutionNode.packByteArray(n);
			out.write(ArrayUtil.intToByteArray(serialSolutionNode.length));
			out.write(ArrayUtil.intToByteArray(n.children().size()));
			out.write(serialSolutionNode);
		}
		
	}


	public void readTree(InputStream in) throws IOException, SerializationException {
		int size = ArrayUtil.byteArrayToInt(StreamUtil.readFully(in, new byte[4]));
		root = readSolutionNode(in, null);
		if (size != root.size())
			throw new SerializationException("Reading tree failed.");	
		stored = new TreeMap<Cube,SolutionNode>();
		PreorderIterator i = new PreorderIterator();
		while (i.hasNext()) {
			SolutionNode n = i.next();
			stored.put(n.source(), n);
		}
	}

	/**
	 * Serialization method which writes the state of the WalkingTree to
	 * the ObjectOutputStream so that readObject can restore it. 
	 *
	 * This method is used in conjunction with the public <code>writeTree</code>
	 * method to get around practical issues with Java's default serialization
	 * method.  
	 *  
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		writeTree(out);
	}

	
	/**
	 * Helper function to readObject(java.io.ObjectInputStream in).  Recursively 
	 * reads from the ObjectInputStream to restore the WalkingTree.
	 * 
	 * @param in
	 * @param parent
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private SolutionNode readSolutionNode(InputStream in, SolutionNode parent) throws IOException {
		/* | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |    n + 8     |
		 * |    SolutionNode size  |    children   |    SolutionNode      |
		 */

		int SolutionNodeSize;
		int nChildren;
		byte[] byteSolutionNode;
		byte[] scratch = new byte[4];
		
		
		SolutionNodeSize = ArrayUtil.byteArrayToInt(StreamUtil.readFully(in, scratch));
		nChildren = ArrayUtil.byteArrayToInt(StreamUtil.readFully(in, scratch));
		byteSolutionNode = new byte[SolutionNodeSize];
		StreamUtil.readFully(in, byteSolutionNode);
		
		SolutionNode n = new SolutionNode(byteSolutionNode);
		n.setHeight(parent == null? 0 : parent.height() + 1);
		for (int x=0; x < nChildren; x++) {
			SolutionNode c = readSolutionNode(in, n);
			n.addChild(c);
		}
		return n;
	}



}
