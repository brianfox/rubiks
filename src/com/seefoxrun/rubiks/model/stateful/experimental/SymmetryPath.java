package com.seefoxrun.rubiks.model.stateful.experimental;


public class SymmetryPath implements Comparable<SymmetryPath> {
	final private Path left;
	final private Path right;
	
	public SymmetryPath(Path left, Path right) throws StateException {
		if (left.size() != right.size())
			throw new StateException("Symmetry requirements not met.  Paths are different sizes.");
		for (int i=0; i < left.size(); i++)
			if (!left.get(i).isInverse(right.get(i)))
				throw new StateException("Symmetry requirements not met.  Paths are not symmetrical.");
	
		this.left = left;
		this.right = right;
	}

	@Override
	public int compareTo(SymmetryPath p) {
		for (int i=0; i < left.size(); i++) {
			int test = left.compareTo(right);
			if (test != 0)
				return test;
		}
		return 0;
	}

	@Override
	public String toString() {
		return String.format("%s%n%s%n", left, right);
	}

	public int size() {
		return left.size();
	}

}
