package com.seefoxrun.rubiks.model.stateful.tree;

import java.util.ArrayList;
import java.util.TreeSet;

import com.seefoxrun.rubiks.model.cube.Cube;
import com.seefoxrun.rubiks.model.cube.Dir;
import com.seefoxrun.rubiks.model.cube.Face;

import fox.brian.storage.Storage;

public class SolutionTree {
	
	protected int depth = 0;
	protected Cube root;
	
	private ArrayList<Cube> vestal;
	private TreeSet<Cube> stored;
	private Storage bigStore;
	
	public SolutionTree(Cube root) {
		depth = 1;
		this.root = root;
		
		vestal = new ArrayList<Cube>();
		stored = new TreeSet<Cube>();
		bigStore = new Storage();
		
		vestal.add(root);
		stored.add(root);
		bigStore.add(root.byteSerialize());
		
	}

	public int getDepth() {
		return depth;
	}

	public int getCubeSize() {
		return root.size();
	}
	
	public AdvancementStats advanceTree() {
		
		long start = System.nanoTime();

		ArrayList<Cube> discovered = new ArrayList<Cube>();
	
		int twists = 0;
		
		for (Cube c : vestal) {
			for (Face f : Face.values()) {
				for (Dir d : Dir.values()) {
					for (int s=0; s < c.countSlices(f); s++) {
						twists++;
						Cube candidate = c.twist(f, d, s).getRepresentativeCube(Cube.USE_SPATIAL);
						boolean bs = bigStore.add(candidate.byteSerialize());
						boolean ns = stored.add(candidate);
						if (bs) 
							discovered.add(candidate);
							
					}
				}
			}
		}
		if (twists == 0)
			return null;
		
		vestal = discovered;
		long end = System.nanoTime();
		AdvancementStats stats = new AdvancementStats(depth, twists, vestal.size(), twists - vestal.size(), end-start);
		depth++;
		return stats;
	}
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.toString();
	}
	
}
