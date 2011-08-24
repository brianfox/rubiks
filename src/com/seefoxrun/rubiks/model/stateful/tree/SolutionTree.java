package com.seefoxrun.rubiks.model.stateful.tree;

import java.util.ArrayList;
import java.util.TreeSet;

import com.seefoxrun.rubiks.model.cube.Cube;
import com.seefoxrun.rubiks.model.cube.Dir;
import com.seefoxrun.rubiks.model.cube.Face;

public class SolutionTree {
	
	protected int depth = 0;
	protected Cube root;
	
	private ArrayList<SolutionNode> vestal;
	private TreeSet<SolutionNode> stored;
	
	
	public SolutionTree(Cube root) {
		depth = 1;
		this.root = root;
		vestal = new ArrayList<SolutionNode>();
		stored = new TreeSet<SolutionNode>();
		vestal.add(new SolutionNode(root));
		stored.add(new SolutionNode(root));
	}

	public int getDepth() {
		return depth;
	}

	public int getCubeSize() {
		return root.size();
	}
	
	public AdvancementStats advanceTree() {

		ArrayList<SolutionNode> discovered = new ArrayList<SolutionNode>();
		depth++;
		
		int twists = 0;
		
		for (SolutionNode n : vestal) {
			Cube c = n.getCube();
			for (Face f : Face.values()) {
				for (Dir d : Dir.values()) {
					for (int s=0; s < c.countSlices(f); s++) {
						Cube candidate = c.twist(f, d, s);
						SolutionNode ncandidate = new SolutionNode(candidate);
						if (stored.add(ncandidate)) 
							discovered.add(ncandidate);
							
					}
				}
			}
		}
		
		vestal = discovered;
		
		return new AdvancementStats(depth, twists, vestal.size(), twists - vestal.size(), 0);
	}
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.toString();
	}
	
}
