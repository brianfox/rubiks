package com.seefoxrun.rubiks.model.cube;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

import com.seefoxrun.util.ArrayUtil;
import com.seefoxrun.util.StringUtil;

public abstract class Cube implements Comparable<Cube>, Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	public static final int[] SUPPORTED_CUBE_SIZES = { 1, 2, 3 };
	public static final int USE_SPATIAL  = 1;
	public static final int USE_COLORMAP = 2;
	public static final int USE_REVERSE  = 4;
	
	// short hand notation, nothing more
	private static int[][][] lu_pr = Piece.PIECE_ROTATE_FACEDIRPIECE;
	private static char[]    lu_fc = Piece.FACECOLORS_FACE;
	private static int[][]   lu_pl = Piece.PIECE_LABELS_FACEPIECE;

	protected     byte[]          pieces = null;
	protected     FaceMap         faceMap = null;

	
	@Override
	abstract public Cube clone();
	abstract public int size();
	abstract public int countSlices(Face face);
	abstract protected byte[][] getFacePieces(Face face);
	abstract protected byte[][] getLoopPieces(Face face, int slice);
	
	abstract protected int indexSize();
	abstract protected int indexGroup();

	
	
	
	private static TreeSet<Cube> cloneTreeSet(TreeSet<Cube> src) {
		TreeSet<Cube> dest = new TreeSet<Cube>();
		for (Cube c : src) {
			dest.add(c);
		}
		return dest;
	}

	/* ----------------------------------------------------------------------------*/
	/* ----------------------------------------------------------------------------*/

	/* CONSTRUCTORS and FACTORY */
	/* CONSTRUCTORS and FACTORY */
	/* CONSTRUCTORS and FACTORY */

	protected Cube() {}

	public static Cube createCube(int size) {
		Cube c = null;
		switch (size) {
			case 1:	c = new Cube1(); break;
			case 2:	c = new Cube2(); break;
			case 3:	c = new Cube3(); break;
		}
		if (c == null)
			throw new CubeException("Unsupported cube size.");
		return c;
	}

	protected void finishClone(Cube ret) {
		for (int i = 0; i < pieces.length; i++)
			ret.pieces[i] = pieces[i];
		ret.faceMap = faceMap;
	}


	/* ----------------------------------------------------------------------------*/
	/* ----------------------------------------------------------------------------*/

	/* CUBE MANIPULATIONS */
	/* CUBE MANIPULATIONS */
	/* CUBE MANIPULATIONS */

	
	public Cube reverseTwist(Face f, Dir d, int s) {
		Face f1 = faceMap.translateInvert(f);
		Cube c = this.clone();
		twist(c, f1, d, s);
		return c;
	}

	public Cube twist(Face f, Dir d, int s) {
		Face f1 = faceMap.translateInvert(f);
		Cube c = this.clone();
		twist(c, f1, d, s);
		return c;
	}

		
	private static void fullTwist(Cube c, Face f, Dir d) {
		for (int s = 0; s < c.countSlices(f); s++) {
			twist(c, f, d, s);
		}
	}

	
	private static void twist(Cube c, Face f, Dir d, int s) {
		byte[][] loop = c.getLoopPieces(f, s);

		for (int ring = 0; ring < loop.length; ring++) {
			int ringsize = loop[ring].length - 1;
			if (d == Dir.CW) {
				byte temp = c.pieces[loop[ring][ringsize]];
				for (int i = ringsize; i > 0; i--) {
					byte piece = c.pieces[loop[ring][i - 1]];
					c.pieces[loop[ring][i]] = (byte) lu_pr[f.val()][d.val()][piece];
				}
				c.pieces[loop[ring][0]] = (byte) lu_pr[f.val()][d.val()][temp];
			} else {
				byte temp = c.pieces[loop[ring][0]];
				for (int i = 0; i < ringsize; i++) {
					byte piece = c.pieces[loop[ring][i + 1]];
					c.pieces[loop[ring][i]] = (byte) lu_pr[f.val()][d.val()][piece];
				}
				c.pieces[loop[ring][ringsize]] = (byte) lu_pr[f.val()][d.val()][temp];
			}
		}
	}

	
	/* ----------------------------------------------------------------------------*/
	/* ----------------------------------------------------------------------------*/

	/* RELATED CUBES */
	/* RELATED CUBES */
	/* RELATED CUBES */
	
	public Cube getRepresentativeCube(int parms) {
		return getRelatedCubes(parms).first();
	}

	public TreeSet<Cube> getRelatedCubes(int parms) {
		TreeSet<Cube> ret = new TreeSet<Cube>();
		ret.add(this);

		if ((parms & USE_COLORMAP) != 0) {
			TreeSet<Cube> ret2 = cloneTreeSet(ret);
			for (Cube r : ret2) {
				for (Cube c : r.getColorMapCubes())
					ret.add(c);
			}
		}
		if ((parms & USE_SPATIAL) != 0) {
			TreeSet<Cube> ret2 = cloneTreeSet(ret);
			for (Cube r : ret2) {
				for (Cube c : r.getSpatialCubes())
					ret.add(c);
			}
		}
		return ret;
	}

	
	public Cube getColorMapCube(int index) {
		return getColorMapCubeArray()[index];
	}

	
	public Cube getSpatialCube(int index) {
		return getSpatialCubeArray()[index];
	}

	
	public TreeSet<Cube> getColorMapCubes() {
		TreeSet<Cube> ret = new TreeSet<Cube>();
		for (Cube c : getColorMapCubeArray())
			ret.add(c);
		return ret;
	}


	public TreeSet<Cube> getSpatialCubes() {
		TreeSet<Cube> ret = new TreeSet<Cube>();
		for (Cube c : getSpatialCubeArray())
			ret.add(c);
		return ret;
	}


	private Cube[] getSpatialCubeArray() {
		Cube[] ret = new Cube[24];

		ret[0]  = this; // front -> front
		ret[4]  = getSpatialCube(ret[0], Face.RIGHT, Dir.CW, 4);  // bottom -> front
		ret[8]  = getSpatialCube(ret[0], Face.RIGHT, Dir.CCW, 8); // top -> front
		ret[12] = getSpatialCube(ret[0], Face.TOP, Dir.CW, 12);   // right -> front
		ret[16] = getSpatialCube(ret[0], Face.TOP, Dir.CCW, 16);  // left -> front
		ret[20] = getSpatialCube(ret[4], Face.RIGHT, Dir.CW, 20); // back -> front
		for (int i = 0; i < 6; i++) {
			for (int rot = 1; rot < 4; rot++) {
				ret[i * 4 + rot] = getSpatialCube(ret[i * 4 + rot - 1],
						Face.FRONT, Dir.CW, i * 4 + rot);
			}
		}
		for (int i = 1; i < 24; i++) {
			ret[i].faceMap = FaceMap.remapForward(ret[i].faceMap, i);
		}

		return ret;
	}

	private static Cube getColorMapCube(Cube cube, Face face, Dir dir, int index) {
		Cube clone = cube.clone();
		for (int i = 0; i < cube.pieces.length; i++) {
			int newaxis = Piece.PIECE_WHICHFACE_FACEPIECE[face.val()][cube.pieces[i]];
			clone.pieces[i] = (byte) Piece.PIECE_ROTATE_FACEDIRPIECE[newaxis][dir
					.val()][cube.pieces[i]];
		}
		fullTwist(clone, face, dir.getOpposite());
		return clone;
	}

	private static Cube getSpatialCube(Cube cube, Face face, Dir dir, int index) {
		Cube clone = cube.clone();
		fullTwist(clone, face, dir);
		return clone;
	}

	private Cube[] getColorMapCubeArray() {
		Cube[] ret = new Cube[24];

		ret[0]  = this; // front -> front
		ret[4]  = getColorMapCube(ret[0], Face.RIGHT, Dir.CCW, 4);   // top    -> front
		ret[8]  = getColorMapCube(ret[0], Face.TOP, Dir.CW, 8);      // right  -> front
		ret[12] = getColorMapCube(ret[0], Face.TOP, Dir.CCW, 12);    // left   -> front
		ret[16] = getColorMapCube(ret[0], Face.RIGHT, Dir.CW, 16);   // bottom -> front
		ret[20] = getColorMapCube(ret[4], Face.RIGHT, Dir.CCW, 20);  // back   -> front
		for (int i = 0; i < 6; i++) {
			Cube temp = ret[i * 4];
			for (int rot = 1; rot < 4; rot++) {
				temp = getColorMapCube(temp, Face.FRONT, Dir.CW, i * 4 + rot);
				ret[i * 4 + rot] = temp;
			}
		}
		return ret;
	}


	


	
	/* ----------------------------------------------------------------------------*/
	/* ----------------------------------------------------------------------------*/

	/* COMPARISONS */
	/* COMPARISONS */
	/* COMPARISONS */
	
	public boolean isEquivalent(Cube c, int parms) {
		boolean ret = true;

		ArrayList<TreeSet<Cube>> list = new ArrayList<TreeSet<Cube>>();

		TreeSet<Cube> base1 = this.getRelatedCubes(parms);

		list.add(base1);
		for (Cube x : base1)
			list.add(x.getRelatedCubes(parms));

		TreeSet<Cube> base2 = c.getRelatedCubes(parms);
		list.add(base2);
		for (Cube x : base2)
			list.add(x.getRelatedCubes(parms));

		for (TreeSet<Cube> set : list) {
			ret = ret && isSameTreeSet(base1, set);
		}
		return ret; 
	}

	private static boolean isSameTreeSet(TreeSet<Cube> ts1, TreeSet<Cube> ts2) {
		boolean ret = true;

		if (ts1.size() != ts2.size()) {
			System.err.printf("Sizes are unequal:  %d versus %d%n",ts1.size(), ts2.size());
			System.err.printf("%s%n%n%n--------%n%s%n",Cube.getCubeTreeString(ts1),Cube.getCubeTreeString(ts2));
		}
		ret = ret && ts1.size() == ts2.size();

		for (Cube c : ts1)
			ret = ret && ts2.contains(c);

		return ret;
	}

	public int compareSimilar(Cube c, int parms) {
		TreeSet<Cube> s1 = this.getRelatedCubes(parms);
		TreeSet<Cube> s2 = c.getRelatedCubes(parms);

		if (s1.size() != s2.size())
			return s1.size() - s2.size();

		while (s1.size() > 0) {
			Cube c1 = s1.first();
			s1.remove(c1);
			Cube c2 = s2.first();
			s2.remove(c2);
			if (c1.compareTo(c2) != 0)
				return c1.compareTo(c2);
		}

		return 0;
	}

	/**
	 * Compares two cubes.
	 * 
	 * @return int
	 */
	@Override
	public int compareTo(Cube c) {
		int temp;
		if (c.pieces.length != this.pieces.length)
			return c.pieces.length - this.pieces.length;
		
		
		for (int i = 0; i < this.pieces.length; i++) {
			temp = this.pieces[i] - c.pieces[i];
			if (temp != 0)
				return temp;
		}
		return 0;
	}



	/* ----------------------------------------------------------------------------*/
	/* ----------------------------------------------------------------------------*/

	/* INFORMATIONAL */

	
	public static boolean isSupportedSize(int size) {
		for (int i : SUPPORTED_CUBE_SIZES) {
			if (i == size)
				return true;
		}
		return false;
	}




	public FaceMap getFaceMap() {
		return faceMap;
	}

	

	/* ----------------------------------------------------------------------------*/
	/* ----------------------------------------------------------------------------*/

	/* SERIALIZATION */
	/* SERIALIZATION */
	/* SERIALIZATION */

	

	public static Cube byteDeserialize(byte[] src) {
		int cubesize = ArrayUtil.byteArrayToInt(Arrays.copyOfRange(src, 0, 4));
		int arraysize = ArrayUtil.byteArrayToInt(Arrays.copyOfRange(src, 4, 8));
		int fmap = ArrayUtil.byteArrayToInt(Arrays.copyOfRange(src, 8, 12));
		byte[] cube = Arrays.copyOfRange(src, 12, 12 + arraysize);

		Cube c = Cube.createCube(cubesize);
		c.pieces = cube;
		c.faceMap = FaceMap.getMap(fmap);
		return c;
	} 

	public byte[] byteSerialize() {
		/*
		byte[] cubesize = ArrayUtil.intToByteArray(size());
		byte[] arraysize = ArrayUtil.intToByteArray(pieces.length);
		byte[] fmap = ArrayUtil.intToByteArray(faceMap.val());
		byte[] cube = pieces.clone();
		return ArrayUtil.concatenateArrays(cubesize, arraysize, fmap, cube);
		*/
		
		return pieces.clone();
	}



	/* ----------------------------------------------------------------------------*/
	/* ----------------------------------------------------------------------------*/

	/* STRINGS AND OTHER RENDERINGS */
	/* STRINGS AND OTHER RENDERINGS */
	/* STRINGS AND OTHER RENDERINGS */


	/**
	 * Creates an ASCII drawing of this cube. The drawing is in a dagger like
	 * format. An example of a 2x2 cube is shown:
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		byte[][] ptop = getFacePieces(Face.TOP);
		byte[][] pleft = getFacePieces(Face.LEFT);
		byte[][] pfront = getFacePieces(Face.FRONT);
		byte[][] pright = getFacePieces(Face.RIGHT);
		byte[][] pbottom = getFacePieces(Face.BOTTOM);
		byte[][] pback = getFacePieces(Face.BACK);

		StringBuilder top = faceToString(ptop, Face.TOP);
		StringBuilder left = faceToString(pleft, Face.LEFT);
		StringBuilder front = faceToString(pfront, Face.FRONT);
		StringBuilder right = faceToString(pright, Face.RIGHT);
		StringBuilder bottom = faceToString(pbottom, Face.BOTTOM);
		StringBuilder back = faceToString(pback, Face.BACK);

		StringBuilder blank = makeBlankFace(getFacePieces(Face.TOP));

		sb.append(StringUtil.concatenateMultipleStrings("", blank, top, blank));
		sb.append(StringUtil.concatenateMultipleStrings("", left, front, right));
		sb.append(StringUtil.concatenateMultipleStrings("", blank, bottom, blank));
		sb.append(StringUtil.concatenateMultipleStrings("", blank, back, blank));
		return sb.toString();
	}

	public String rawString() {
		return StringUtil.hexString(pieces);
	}

	
	public String cubeArrayToString(int nPerRow) {
		Cube[] array = this.getSpatialCubeArray();
		StringBuilder sb = new StringBuilder();
		String temp = "";
		int count = 0;

		while (count < array.length) {
			if (count % nPerRow == 0 && count > 0) {
				sb.append(temp + "\n");
				temp = "";
			}
			temp = StringUtil.concatenateStrings("   ", temp, array[count].toString());
			count++;
		}
		if (count % nPerRow == 0 && count > 0)
			sb.append(temp + "\n");

		return sb.toString();
	}

	
	public static String getCubeTreeString(TreeSet<Cube> ts) {
		String ret="";
		for (Cube c : ts)
			ret = StringUtil.concatenateStrings("  ", ret, c.toString());
		return ret;
	}

	

	/**
	 * Creates an ASCII representation of a single face composed only of spaces.
	 * This method is useful for shifting faces the proper number of columns
	 * when building ASCII drawings of a cube.
	 */
	private StringBuilder makeBlankFace(byte[][] facepieces) {
		StringBuilder sb = new StringBuilder();
		for (byte[] row : facepieces) {
			for (int i = 0; i < row.length; i++)
				sb.append(' ');
			sb.append("\n");
		}
		return sb;
	}

	
	/**
	 * Creates an ASCII representation of a single face. This method is useful
	 * for building ASCII drawings of a cube.
	 */
	private StringBuilder faceToString(byte[][] facepieces, Face f) {
		StringBuilder sb = new StringBuilder();
		for (byte[] row : facepieces) {
			for (byte slot : row)
				sb.append(lu_fc[lu_pl[f.val()][pieces[slot]]]);
			sb.append("\n");
		}
		return sb;
	}


	public Color[][] getFaceLabels(Face f) {
		
		byte[][] facepieces = getFacePieces(f);
		Color[][] ret = new Color[facepieces.length][];
		
		int i=0; int j=0;
		for (byte[] row : facepieces) {
			ret[i] = new Color[row.length];
			for (byte slot : row) {
				ret[i][j] = Piece.AWTCOLOR_FACE[lu_pl[f.val()][pieces[slot]]];
				j++;
			}
			j=0;
			i++;
		}
		System.err.println();
		return ret;
	}

	
	public HashMap<Face,Color[][]> getLabelColors() {
		
		HashMap<Face,Color[][]> ret = new HashMap<Face,Color[][]>();
		
		for (Face f : Face.values()) {
			byte[][] facepieces = getFacePieces(f);
			Color[][] colors = new Color[facepieces.length][];
			
			for (int i=0; i < facepieces.length; i++) {
				colors[i] = new Color[facepieces[i].length];
				for (int j=0; j < facepieces[i].length; j++) {
					colors[i][j] = Piece.AWTCOLOR_FACE[lu_pl[f.val()][pieces[facepieces[i][j]]]];
				}
			}
			ret.put(f, colors);
		}
		return ret;
	}
	
	/*
	public Cube undo() {
		return undo(Integer.MAX_VALUE);
	}

	public Cube undo(int n) {
		
		Cube working = Cube.createCube(size());
		State_old state = getState();
		ArrayList<Operator> moves = state.getMoves();
		
		// Why 1? Reset doesn't count. 
		if (moves.size() <= 1)
			return this;

		
		// We copy to the end of the array... 
		int stopIndex = moves.size()-1;
		
		// ... unless otherwise specified.    
		int nUndone = 0;
		int i;
		for (i = moves.size()-1; i >= 0; i--) {
			// Have we reached the number of undos? 
			if (nUndone >= n)
				break;
			// Otherwise, this is an undo. 
			stopIndex=i;
			nUndone++;
		}
		for (i=0; i < stopIndex; i++) {
			working = moves.get(i).apply(working);
		}
		working.undoStack = (this.undoStack == null) ? new UndoStack() : this.undoStack.clone();
		this.undoStack = null;
		
		for (i=stopIndex; i < moves.size(); i++) 
			working.undoStack.push(moves.get(i));
		return working;
	}

	public Cube redo() {
		return redo(Integer.MAX_VALUE);
	}

	public Cube redo(int n) {
		Cube working = this;
		
		UndoStack stack = this.undoStack;
		if (stack == null)
			return this;
		int count = 0;
		while (count < n && stack.size() > 0) {
			working = stack.pop().apply(working);
			count++;
		}
		working.undoStack = stack.size() == 0 ? null : stack;
		return working;
	}
	*/
	
	
}
