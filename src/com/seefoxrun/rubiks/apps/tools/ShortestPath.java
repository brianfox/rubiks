package com.seefoxrun.rubiks.apps.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.seefoxrun.rubiks.model.cube.Dir;
import com.seefoxrun.rubiks.model.cube.Face;
import com.seefoxrun.rubiks.model.cube.Piece;

/**
 * Calculates the shortest path between two orientations of a 
 * 1x1 cube.
 * 
 * @author brianfox
 *
 */
public class ShortestPath {
	
	private static class Move {
		private Face f;
		private Dir d;

		public Move(Face f, Dir d) {
			this.f = f;
			this.d = d;
		}
	}
	
	private static class Path implements Comparable<Path> {

		private int start, end;
		private ArrayList<Move> moves;
		
		public Path(int start, ArrayList<Move> moves, int end) {
			this.start = start;
			this.end = end;
			this.moves = new ArrayList<Move>();
			for (Move m : moves)
				this.moves.add(m);
		}
		
		@Override
		public int compareTo(Path assoc) {
			if (assoc.start == this.start && assoc.end == this.end)
				return 0;
			return -1;
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Path))
				return false;
			Path assoc = (Path)o;
			return (assoc.start == this.start && assoc.end == this.end);
		}
		
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(getLabels(start) + " ... ");
			int next = start;
			for (int i = 0; i < moves.size(); i++) {
				Move m = moves.get(i);
				next = Piece.PIECE_ROTATE_FACEDIRPIECE[m.f.val()][m.d.val()][next];
				sb.append(String.format("twist %s%s for ", m.f, m.d));
				if (i < moves.size() - 1)
					sb.append(String.format("%s ... ", getLabels(next)));
			}
			sb.append(String.format("%s", getLabels(end)));
			return sb.toString();
		}
	}

	static HashMap<String,Integer> namedPieces = new HashMap<String,Integer>();
	static ArrayList<Path> allPaths = new ArrayList<Path>();
	
	static {
		for (int p=0; p < 24; p++) 
			namedPieces.put(getLabels(p).toUpperCase(), p);

		ArrayList<Path> seed = new ArrayList<Path>();
		for (int p=0; p < 24; p++) {
			for (Face f : Face.values()) {
				for (Dir d : Dir.values()) {
					int next = Piece.PIECE_ROTATE_FACEDIRPIECE[f.val()][d.val()][p];
					ArrayList<Move> moves = new ArrayList<Move>();
					moves.add(new Move(f,d));
					seed.add(new Path(p,moves,next));
				}
			}
		}
		recursePaths(seed);

	}
	
	static void recursePaths(ArrayList<Path> seed) {
		System.out.println("Next seed size: " + seed.size());
		if (seed.size() == 0)
			return;
		ArrayList<Path> nextSeed = new ArrayList<Path>();
		for (Path p : seed) {
			if (allPaths.contains(p))
				continue;
			allPaths.add(p);
			for (Face f : Face.values()) {
				for (Dir d : Dir.values()) {
					int next = Piece.PIECE_ROTATE_FACEDIRPIECE[f.val()][d.val()][p.end];
					ArrayList<Move> moves = new ArrayList<Move>();
					for (Move m : p.moves)
						moves.add(m);
					moves.add(new Move(f,d));
					nextSeed.add(new Path(p.start,moves,next));
				}
			}
		}
		recursePaths(nextSeed);
	}
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String command = "";
		
		HashMap<String,Path> shortest = new HashMap<String,Path>();
		for (Path p : allPaths) {
			String key = getLabels(p.start) + " " + getLabels(p.end);
			shortest.put(key, p);
		}
		System.out.println("Ready.");
		while ((command = in.readLine()) != null) {
			String[] tokens = command.split("\\s+");
			if (tokens.length != 2) {
				System.out.println("Invalid command.  Try a format similar to: RYB RBW");
				continue;
			}
			String start = tokens[0].toUpperCase();
			String end = tokens[1].toUpperCase();
			if (!namedPieces.containsKey(start)) {
				System.out.println("Unknown start orientation: " + tokens[0]);
				continue;
			}
			if (!namedPieces.containsKey(end)) {
				System.out.println("Unknown orientation: " + tokens[1]);
				continue;
			}
			Path p = shortest.get(start + " " + end);
			System.out.println("Shortest path: " + p);
		}
	}
	
	static public String getLabels(int cube) {
		StringBuilder sb = new StringBuilder();
		sb.append(Piece.FACECOLORS_FACE[Piece.PIECE_LABELS_FACEPIECE[Face.FRONT.ordinal()][cube]]);
		sb.append(Piece.FACECOLORS_FACE[Piece.PIECE_LABELS_FACEPIECE[Face.RIGHT.ordinal()][cube]]);
		sb.append(Piece.FACECOLORS_FACE[Piece.PIECE_LABELS_FACEPIECE[Face.TOP.ordinal()][cube]]);

		return sb.toString();
		
	}

}
