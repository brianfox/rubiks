package com.seefoxrun.rubiks.apps.walker.simple;

import com.seefoxrun.rubiks.apps.visualization.GenerateRadialPDF.CurrentClassGetter;
import com.seefoxrun.rubiks.model.cube.*;
import com.seefoxrun.rubiks.model.stateful.tree.AdvancementStats;
import com.seefoxrun.rubiks.model.stateful.tree.SolutionTree;



public class SimpleWalker {

	private SolutionTree tree;

	public SimpleWalker(int size) {
		tree = new SolutionTree(Cube.createCube(size));
	}

	public void process() {

		AdvancementStats stats = null;

		while (true) {
			stats = tree.advanceTree();
			if (stats == null)
				break;
			System.out.println(stats);
		} 
	}

	public static void main(String[] args) {
		new SimpleWalker(2).process();
	}

	public static String usage() {
		String pname = new CurrentClassGetter().getClassName();
		return String.format("Usage: %s [-d <output directory>] [-l <start>[:<end>]] [--spatial] [--colormap] [--reverse]%n", pname);
	}
}
