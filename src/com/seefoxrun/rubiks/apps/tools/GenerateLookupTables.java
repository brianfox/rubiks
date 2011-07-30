package com.seefoxrun.rubiks.apps.tools;

import com.seefoxrun.rubiks.model.cube.Piece;

public class GenerateLookupTables {

	public static void main(String[] args) {
		Piece.printLabels();
		Piece.printRotations();
		Piece.printWhichFace();
		
	}
}
