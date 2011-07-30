package com.seefoxrun.rubiks.model.cube;

public enum Dir {
	CW  ("clockwise",        "CW",  (byte)0),
	CCW ("counterclockwise", "CCW", (byte)1);

	private String description;
	private String abbreviation;
	private byte byteval;
	
	private Dir(String description, String abbreviation, byte byteval) {
		this.description = description;
		this.abbreviation = abbreviation;
		this.byteval = byteval;
	}
	
	public Dir getOpposite() {
		switch (byteval) {
			case 0: return CCW;
			case 1: return CW;
		}
		return CW;
	}
	
	public static Dir getDir(int d) {
		switch (d) {
			case 0: return CW;
			case 1: return CCW;
		}
		return CW;
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
