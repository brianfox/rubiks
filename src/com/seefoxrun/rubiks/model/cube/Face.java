package com.seefoxrun.rubiks.model.cube;

public enum Face {
	FRONT ("front", "F", (byte)0),
	RIGHT ("right", "R", (byte)1),
	TOP   ("top",   "T", (byte)2),
	BOTTOM("bottom","B", (byte)3),
	LEFT  ("left",  "L", (byte)4),
	BACK  ("back",  "K", (byte)5);
	
	private String description;
	private String abbreviation;
	private byte byteval;
	
	private Face(String description, String abbreviation, byte byteval) {
		this.description = description;
		this.abbreviation = abbreviation;
		this.byteval = byteval;
	}
	
	public Face getOpposite() {
		switch (byteval) {
			case 0: return BACK;
			case 1: return LEFT;
			case 2: return BOTTOM;
			case 3: return TOP;
			case 4: return RIGHT;
			case 5: return FRONT;
		}
		return FRONT;
	}
	
	public static Face getFace(int f) {
		switch (f) {
			case 0: return FRONT;
			case 1: return RIGHT;
			case 2: return TOP;
			case 3: return BOTTOM;
			case 4: return LEFT;
			case 5: return BACK;
		}
		return FRONT;
	}
	
	public byte val() {
		return byteval;
	}
	
	public String getDescription() {
		return description;
	}

	@Override public String toString() {
		return abbreviation;
	}
}


