package com.seefoxrun.rubiks.visualization.itext3d.objects.paths;

public enum BooleanOperator {
	
	ADD("Add"), 
	SUBTRACT("Subtract"), 
	INTERSECT("Intersect");

	String name;
	
	BooleanOperator(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	

}
