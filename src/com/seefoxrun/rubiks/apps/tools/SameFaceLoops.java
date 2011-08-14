package com.seefoxrun.rubiks.apps.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import com.seefoxrun.rubiks.model.cube.Dir;
import com.seefoxrun.rubiks.model.cube.Face;
import com.seefoxrun.rubiks.model.cube.Piece;


/**
 * Calculates looping moves found by rotating a 1x1 cube in a
 * given direction and face four times in a row.  There are
 * 24 orientations of a single cube.  There are 144 ways to
 * move from one orientation to the next.
 * 
 * 
 * @author brianfox
 *
 */
public class SameFaceLoops {
	
	private static class Move implements Comparable<Move> {
		private int start;
		private Face f;
		private Dir d;

		public Move(int start, Face f, Dir d) {
			this.f = f;
			this.d = d;
			this.start = start;
		}
		
		@Override
		public int compareTo(Move m) {
			if (this.equals(m))
				return 0;
			
			int v1 = f.val() * 10000 + d.val() * 100 +  start;
			int v2 = m.f.val() * 10000 + m.d.val() * 100 +  m.start;
			return v1 - v2;
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Path))
				return false;
			Move m = (Move)o;
			return m.f == this.f && m.d == this.d && m.start == this.start;
		}
		
		@Override
		public String toString() {
			int end = Piece.PIECE_ROTATE_FACEDIRPIECE[f.val()][d.val()][start];
  	        return String.format("%s @ %s%s -> %s",  getLabels(start), f, d, getLabels(end));
  	        // return String.format("%s ",  getLabels(start), f, d, getLabels(end));
		}
	}
	
	private static class Path implements Comparable<Path> {

		private ArrayList<Move> moves;
		
		public Path(ArrayList<Move> moves) {
			this.moves = new ArrayList<Move>();
			for (Move m : moves)
				this.moves.add(m);
		}
		
		@Override
		public int compareTo(Path assoc) {
			if (this.equals(assoc))
				return 0;
			return -1;
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Path))
				return false;
			Path assoc = (Path)o;
			if (assoc.moves.size() != this.moves.size())
				return false;
			for (int i=0; i < this.moves.size(); i++)
				if (this.moves.get(i) != assoc.moves.get(i))
					return false;
			return true;
		}
		
		
		@Override
		public String toString() {
			if (moves.size() == 0)
				return "<empty>";
			
			StringBuilder sb = new StringBuilder();
			for (Move m : moves)
				sb.append(m + "      ");
			return sb.toString();
		}
	}

	static void discoverPaths(ArrayList<Path> paths, TreeSet<Move> repeats, int cube) {
		for (Face f : new Face[] {Face.FRONT, Face.RIGHT, Face.TOP}) { // , Face.LEFT, Face.BOTTOM, Face.BACK}) {
			for (Dir d : new Dir[] {Dir.CW}) {
				Move repeattest = new Move(cube, f, d);
				if (repeats.contains(repeattest)) {
					continue;
				}
				ArrayList<Move> moves = new ArrayList<Move>();
				int next = cube;
				for (int i=0; i < 4; i++) {
					Move m = new Move(next, f, d);
					moves.add(m);
					repeats.add(m);
					next = Piece.PIECE_ROTATE_FACEDIRPIECE[f.val()][d.val()][next];
				}
				Path p = new Path(moves);
				if (!paths.contains(p))
					paths.add(p);
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		ArrayList<Path> paths = new ArrayList<Path>();   // discovered loops
		TreeSet<Move> repeats = new TreeSet<Move>();     // test for repeated loops

		for (int piece=0; piece < 24; piece++)
			discoverPaths(paths, repeats, piece);
		for (Path p : paths)
			System.out.println(p);
	}

	
	static public String getLabels(int cube) {
		StringBuilder sb = new StringBuilder();
		sb.append(Piece.FACECOLORS_FACE[Piece.PIECE_LABELS_FACEPIECE[Face.FRONT.ordinal()][cube]]);
		sb.append(Piece.FACECOLORS_FACE[Piece.PIECE_LABELS_FACEPIECE[Face.RIGHT.ordinal()][cube]]);
		sb.append(Piece.FACECOLORS_FACE[Piece.PIECE_LABELS_FACEPIECE[Face.TOP.ordinal()][cube]]);

		return sb.toString();
		
	}

}
