package com.seefoxrun.rubiks.apps.tools;

import java.util.ArrayList;

import com.seefoxrun.rubiks.model.cube.Face;
import com.seefoxrun.rubiks.model.cube.Dir;
import com.seefoxrun.rubiks.model.cube.Piece;

/*
 * Maps the spatial orientations of the 1x1 cube (the alignment of 
 * faces compared to the fixed RH XYZ Cartesian axis system) of the 
 * 1x1 cube.<br/>
 * <br/>
 * The starting orientation is 0: 
 * <li>
 *     <ul>X aligns with the front face.</ul>
 *     <ul>Y aligns with the right face.</ul>  
 *     <ul>Z aligns with the top face.</ul>
 * </li>
 * <br/>
 * The map will then twist this orientation by each of the six 
 * axes in both a counterclockwise and clockwise motion, producing
 * 12 new orientations.  These 12 orientations are treated in a
 * similar recursive manner.<br/>
 * <br/>
 * The map produces a total graph of 24 vertices and 288 paths.
 * Many of the paths can be eliminated, simplifying things
 * a great deal.<br/>
 * </br>
 * Half of the paths duplicates as rotating the cube along the 
 * front-axis CW is the same as rotating the cube along the 
 * back-axis CCW. Also, Orientation1(SOMEFACE_CCW) yields 
 * Orientation2 is the same as Orientation2(SOMEFACE_CW) yields 
 * Orientation1.
 * <br/>
 * The result is a graph with 24 vertices and 72 simplified 
 * paths or 288 total paths.<br/>
 * <br/>  
 *   
 */
public class Map1x1Cube {

	private static class Association implements Comparable<Association> {

		private int start, end;
		private Face face;
		private Dir dir;
		
		public Association(int start, Face face, Dir dir, int end) {
			this.start = start;
			this.end = end;
			this.face = face;
			this.dir = dir;
		}
		
		@Override
		public int compareTo(Association assoc) {
			if (
					(assoc.start == this.start && assoc.end == this.end) 
					|| (assoc.end == this.start && assoc.start == this.end)
					)
				return 0;
			return -1;
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Association))
				return false;
			Association assoc = (Association)o;
			return (assoc.start == this.start && assoc.end == this.end) 
			|| (assoc.end == this.start && assoc.start == this.end);
		}
		
		
		@Override
		public String toString() {
			return String.format("%s %s%-3s -> %s",
					getLabels(start),
					face,
					dir,
					getLabels(end)
			);

		}
	}
	
	
	
	static ArrayList<Integer> visited = new ArrayList<Integer>();
	static ArrayList<Association> directed = new ArrayList<Association>(); 
	static ArrayList<Association> undirected = new ArrayList<Association>(); 
	
	public static void main(String[] args) {
		recurse(0);

		System.out.println("DIRECTED GRAPH");
		System.out.println("--------------");
		for (Association a : directed) 
			System.out.println(a);
		System.out.println("Total:  " + directed.size());
		System.out.println("\n\n\n");

		System.out.println("UNDIRECTED GRAPH");
		System.out.println("----------------");
		for (Association a : undirected) 
			System.out.println(a);
		System.out.println("Total:  " + undirected.size());
		System.out.println("\n\n\n");
}
	
	public static void recurse(int cube) {
		if (visited.contains(cube))
			return;
		visited.add(cube);

		ArrayList<Integer> todo = new ArrayList<Integer>();
		for (Face f : Face.values()) {
			for (Dir d : Dir.values()) {
				int next = Piece.PIECE_ROTATE_FACEDIRPIECE[f.ordinal()][d.ordinal()][cube];
				Association a = new Association(cube, f, d, next);
				if (!undirected.contains(a))
					undirected.add(a);
				directed.add(a);
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
