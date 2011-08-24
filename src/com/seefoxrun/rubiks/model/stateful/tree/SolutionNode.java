package com.seefoxrun.rubiks.model.stateful.tree;

import com.seefoxrun.rubiks.model.cube.Cube;

public class SolutionNode implements Comparable<SolutionNode> {
	
	protected Cube c;
	
	public SolutionNode(Cube c) {
		this.c = c;
	}
	
	public Cube getCube() {
		return c;
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() != SolutionNode.class)
			return false;
		return this.compareTo((SolutionNode)o) == 0;
	}
	
	@Override
	public int compareTo(SolutionNode node) {
		return this.c.compareTo(node.c);
	}
}
