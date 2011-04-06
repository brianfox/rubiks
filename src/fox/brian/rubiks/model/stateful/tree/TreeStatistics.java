/*
 * $Id: TreeStatistics.java,v 1.6 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: TreeStatistics.java,v $
 * Revision 1.6  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package fox.brian.rubiks.model.stateful.tree;

import java.util.ArrayList;
import java.util.TreeMap;

import fox.brian.rubiks.model.operators.Twist;

public class TreeStatistics {
	
	private int size = 0;
	private int height = 0;
	private TreeMap<Twist,Integer> twists;
	private ArrayList<Twist> uniqueTwists;
	
	public TreeStatistics(SolutionTree tree) {
		this.height = tree.depth();
		this.size = tree.size();
		twists = tree.histogramTwist();
		uniqueTwists = new ArrayList<Twist>();
		for (Twist t : twists.keySet())
			uniqueTwists.add(t);
	}
	
	public long getSize() {
		return size;
	}
	
	public int getNumberUniqueTwists() {
		return uniqueTwists.size();
	}

	public ArrayList<Twist> getUniqueTwists() {
		return uniqueTwists;
	}

	public int getDepth() {
		return height;
	}

}
