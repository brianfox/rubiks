/*
 * $Id: SolutionTree.java,v 1.8 2010/02/18 18:04:29 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: SolutionTree.java,v $
 * Revision 1.8  2010/02/18 18:04:29  bfox
 * Cleaned up the graph.  Added the idea of wedges.
 *
 * Revision 1.7  2010/02/16 22:10:18  bfox
 * Graphing moved to Graphics2D model.
 *
 * Revision 1.6  2010/02/13 05:12:51  bfox
 * *** empty log message ***
 *
 * Revision 1.5  2010/02/12 14:44:25  bfox
 * *** empty log message ***
 *
 * Revision 1.4  2010/02/11 17:32:52  bfox
 * *** empty log message ***
 *
 * Revision 1.3  2010/02/08 18:21:14  bfox
 * *** empty log message ***
 *
 * Revision 1.2  2010/02/01 23:05:04  bfox
 * *** empty log message ***
 *
 * Revision 1.1  2010/02/01 16:43:53  bfox
 * *** empty log message ***
 *
 * Revision 1.9  2010/01/30 01:32:21  bfox
 * Added a child histogram method.
 *
 * Revision 1.8  2010/01/25 16:44:33  bfox
 * Restructuring Options
 *
 * Revision 1.7  2010/01/20 17:06:54  bfox
 * Code rearrangement and documentation.  Eliminated one of the tree iterators.
 *
 * Revision 1.6  2010/01/15 18:42:47  bfox
 * Solidified command line switches.
 * Oh, and the serialization broke.  DOH!
 *
 * Revision 1.5  2010/01/14 23:16:10  bfox
 * Boiled down SolutionTree and SolutionNode.  SolutionNode
 * should be extendable now such that GraphNode and
 * derivatives do not need to duplicate its functionality.
 *
 * Revision 1.4  2010/01/12 22:07:02  bfox
 * Commit before making some aggressive changes.  Looking to make
 * the SolutionNode children an ordinal array.
 *
 * Revision 1.3  2010/01/08 03:05:26  bfox
 * *** empty log message ***
 *
 * Revision 1.2  2010/01/05 03:44:30  bfox
 * Code cleanup.
 *
 * Revision 1.1  2010/01/01 08:28:49  bfox
 * Rearranged packages.
 *
 * Revision 1.7  2009/08/26 14:52:12  bfox
 * *** empty log message ***
 *
 * Revision 1.6  2009/08/19 23:35:04  bfox
 * Placed the graphing classes in the main module.  Threaded RadialGraph.
 *
 * Revision 1.5  2009/08/18 21:37:52  bfox
 * Added parent twist information to a SolutionNode.  This makes it easier to use (and relate) a part of the graph to the whole.
 *
 * Revision 1.4  2009/08/18 01:38:17  bfox
 * *** empty log message ***
 *
 * Revision 1.3  2009/08/15 15:59:14  bfox
 * Renamed.
 *
 * Revision 1.2  2009/08/14 18:10:03  bfox
 * *** empty log message ***
 *
 * Revision 1.1  2009/08/13 15:38:47  bfox
 * Renamed.
 *
 * Revision 1.11  2009/07/16 23:40:15  bfox
 * *** empty log message ***
 *
 * Revision 1.10  2009/07/16 22:45:09  bfox
 * *** empty log message ***
 *
 * Revision 1.9  2009/07/13 15:21:38  bfox
 * *** empty log message ***
 *
 * Revision 1.8  2009/07/01 03:49:50  bfox
 * *** empty log message ***
 *
 * Revision 1.7  2009/06/27 03:25:17  bfox
 * *** empty log message ***
 *
 * Revision 1.6  2009/06/16 18:21:58  bfox
 * Added documentation for byteSerialize and byteDeserialize.
 *
 * Revision 1.5  2009/06/10 00:19:58  bfox
 * Lots of serialization changes.  Went more with home grown
 * that JDK.
 *
 * Revision 1.4  2009/06/08 16:26:49  bfox
 * *** empty log message ***
 *
 * Revision 1.3  2009/06/02 22:46:21  bfox
 * Took a new direction with serialization.
 *
 * The Java interface tries to keep too many references going at once which gobbles memory.  Since a WalkingTree is acyclic, these redundant references just get in the way, causes disk thrashing, and eventually exhausts all memory.
 *
 * It looks like the best approach is to implement serialization by hand for node or node related classes.  This has produced very encouraging results.  But it's unfortunately less clean as well.
 *
 * Revision 1.2  2009/05/27 23:00:15  bfox
 * WalkingTree is now symmetry aware.  Still need to fix a few items.  It should be possible to detect partial symmetry.
 *
 * Revision 1.1  2009/05/15 20:20:00  bfox
 * Modified Cube to be stateless.  State is now carried in other classes.
 *
 */

package com.seefoxrun.rubiks.model.stateful.tree;

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
		
		int nliving = 0;
		int ndead = 0;
		for (Cube c : leaves) {
			boolean living = false;
			for (Face f : Face.values()) {
				for (Dir d : Dir.values()) {
					TreeSet<Cube> equiv = c.twist(f, d, 0).getRelatedCubes(parms);
					Cube prospect = equiv.first();
					if (add(c, prospect, Twist.createTwist(f,d,0))) {
						living = true;
					}
					add(c, prospect, Twist.createTwist(f,d,0));
				}
			}
			if (living)
				nliving++;
			else {
				ndead++;
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
