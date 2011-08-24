package com.seefoxrun.rubiks.model.stateful.tree.original;

import java.util.ArrayList;
import java.util.TreeMap;

import com.seefoxrun.rubiks.model.operators.Twist;

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
