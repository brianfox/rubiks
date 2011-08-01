package com.seefoxrun.rubiks.apps.tools;

import java.util.ArrayList;

import com.seefoxrun.rubiks.model.cube.Face;
import com.seefoxrun.rubiks.model.cube.Dir;
import com.seefoxrun.rubiks.model.cube.Piece;

public class Map1x1Cube {

	static ArrayList<Integer> visited = new ArrayList<Integer>();

	public static void main(String[] args) {
		recurse(0);
	}
	
	public static void recurse(int cube) {
		if (visited.contains(cube))
			return;
		visited.add(cube);
		
		ArrayList<Integer> todo = new ArrayList<Integer>();
		for (Face f : Face.values()) {
			for (Dir d : Dir.values()) {
				int next = Piece.PIECE_ROTATE_FACEDIRPIECE[f.ordinal()][d.ordinal()][cube];
				System.out.println(String.format("%s %s%-3s -> %s",
						getLabels(cube),
						f,
						d,
						getLabels(next)
				));
				todo.add(next);
			}
		}
		
		for (int i : todo)
			recurse(i);
	}
	
	static public String getLabels(int cube) {
		StringBuilder sb = new StringBuilder();
		sb.append(Piece.FACECOLORS_FACE[Piece.PIECE_LABELS_FACEPIECE[Face.FRONT.ordinal()][cube]]);
		sb.append(Piece.FACECOLORS_FACE[Piece.PIECE_LABELS_FACEPIECE[Face.RIGHT.ordinal()][cube]]);
		sb.append(Piece.FACECOLORS_FACE[Piece.PIECE_LABELS_FACEPIECE[Face.TOP.ordinal()][cube]]);

		return sb.toString();
		
	}
}
