package fox.brian.rubiks.model.cube;

import java.awt.Color;

import fox.brian.rubiks.model.cube.Dir;
import fox.brian.rubiks.model.cube.Face;

/**
 *
 */

public class Piece implements Comparable<Piece> {

	private Face[] axis;

	public static final int[][][] PIECE_ROTATE_FACEDIRPIECE;
	public static final int[][]   PIECE_LABELS_FACEPIECE;
	public static final int[][]   PIECE_WHICHFACE_FACEPIECE;

	static {
		PIECE_ROTATE_FACEDIRPIECE = new int[6][2][24];
		Piece pieces[] = getPieces();

		for (int face = 0; face < 6; face++) 
			for (int dir = 0; dir < 2; dir++) 
				for (int piece = 0; piece < 24; piece++) {
					Piece p = pieces[piece].rotate(face, dir);
					for (int i = 0; i < 24; i++)
						if (pieces[i].compareTo(p) == 0) {
							PIECE_ROTATE_FACEDIRPIECE[face][dir][piece] = i;
							break;
						}
				}
	}
	
	static {
		PIECE_LABELS_FACEPIECE = new int[6][24];
		Piece pieces[] = getPieces();

		for (byte face = 0; face < 6; face++) 
			for (int piece = 0; piece < 24; piece++) 
				PIECE_LABELS_FACEPIECE[face][piece] = pieces[piece].faceLabel(face);
	}

	static {
		PIECE_WHICHFACE_FACEPIECE = new int[6][24];
		Piece pieces[] = getPieces();

		for (byte face = 0; face < 6; face++) {
			for (int piece = 0; piece < 24; piece++) {
				PIECE_WHICHFACE_FACEPIECE[face][piece] = pieces[piece].whichDirection(face);
			}
		}
	}
    public final static char[] FACECOLORS_FACE = new char[] { 
		'R',	// front  // red 
		'Y', 	// right  // yellow
		'B', 	// top    // blue
		'G', 	// bottom // green
		'W', 	// left   // white
		'O' 	// back   // orange
	};

    
    public final static Color[] AWTCOLOR_FACE = new Color[] {
    	new Color(0x99,0x00,0x00), // red 
    	new Color(0xFF,0xFF,0x00), // yellow
    	new Color(0x00,0x00,0xCC), // blue
    	new Color(0x00,0x66,0x00), // green
    	Color.white,
    	new Color(0xFF,0x66,0x00),  // orange
    };
    
    
	private Piece()
	{
		axis = new Face[3];  
		axis[0] = Face.FRONT;	
		axis[1] = Face.RIGHT;
		axis[2] = Face.TOP;
	}

	
	private Piece(Piece source)
	{
		axis = new Face[3];  
		axis[0] = source.axis[0];
		axis[1] = source.axis[1];
		axis[2] = source.axis[2];
	}

	
	private Face faceLabel(Face f)
	{
		if (f == Face.FRONT)  return axis[0];
		if (f == Face.RIGHT)  return axis[1];
		if (f == Face.TOP)    return axis[2];
		if (f == Face.BOTTOM) return axis[2].getOpposite();
		if (f == Face.LEFT)   return axis[1].getOpposite();
		if (f == Face.BACK)   return axis[0].getOpposite();
		return Face.FRONT;
	}

	private int faceLabel(int f)
	{
		return faceLabel(Face.getFace(f)).val();
	}

	private Face whichDirection(Face f)
	{
		for (int i=0; i < 3; i++) {
			if (axis[i] == f) return Face.getFace(i);
			if (axis[i] == f.getOpposite()) return Face.getFace(i).getOpposite();
		}
		return Face.FRONT;
	}

	
	private int whichDirection(int f)
	{
		return whichDirection(Face.getFace(f)).val();
	}

	private Piece rotate(int f, int d) {
		return rotate(Face.getFace(f), Dir.getDir(d));
	}

	
	private Piece rotate(Face f, Dir d) {
		
		Piece p = new Piece(this);

		if ( f == Face.BACK || f == Face.LEFT || f == Face.BOTTOM ) { 
			f = f.getOpposite(); 
			d = d.getOpposite(); 
		}
		
		if ( f == Face.FRONT ) {
			if (d == Dir.CW)
			{
				// labels[0] stays the same
				Face temp = p.axis[1];					
				p.axis[1] = p.axis[2];
				p.axis[2] = temp.getOpposite();
			}
			else
			{
				// labels[0] stays the same
				Face temp = p.axis[2];
				p.axis[2] = p.axis[1];
				p.axis[1] = temp.getOpposite();
			}
		}
		else if (f == Face.RIGHT)
		{
			if (d == Dir.CCW )
			{
				// labels[2] stays the same
				Face temp = p.axis[0];
				p.axis[0] = p.axis[2];
				p.axis[2] = temp.getOpposite();
			}
			else
			{
				// labels[2] stays the same
				Face temp = p.axis[0];
				p.axis[0] = p.axis[2].getOpposite();
				p.axis[2] = temp;
			}
		}
		else if (f == Face.TOP)
		{
			if (d == Dir.CW)
			{
				// labels[1] stays the same
				Face temp = p.axis[0];
				p.axis[0] = p.axis[1];
				p.axis[1] = temp.getOpposite();
			}
			else
			{
				// labels[1] stays the same
				Face temp = p.axis[0];
				p.axis[0] = p.axis[1].getOpposite();
				p.axis[1] = temp;
			}
		}
		return p;
	}



	private boolean IsValidPiece() {
		// TODO: Fix this check which means fixing the triplets table
		return true; // (triplets_axisaxis2[axis[0]][axis[1]] == axis[2]);
	}

	public int compareTo(Piece p) {
		for (int i=0; i < 3; i++)
			if (p.axis[i] != axis[i])
				return axis[i].val() - p.axis[i].val();
		return 0;
	}
	
	
	
	public static void printLabels() {
		Piece pieces[] = getPieces();
		int[][] facepiece = new int[6][24];

		for (byte face = 0; face < 6; face++) {
			for (int piece = 0; piece < 24; piece++) {
				facepiece[face][piece] = pieces[piece].faceLabel(face);
			}
		}
		System.out
				.printf("public static final byte[][] piece_labels_facepiece = new byte[][]\n");

		System.out.printf("    {");
		for (int face = 0; face < 6; face++) {
			if (face != 0)
				System.out.printf("     ");
			System.out.printf(" { ");
			System.out.printf("%d", facepiece[face][0]);
			for (int piece = 1; piece < 24; piece++) {
				System.out.printf(", %d", facepiece[face][piece]);
			}
			if (face < 5)
				System.out.printf(" },\n");
			else
				System.out.printf(" }");
		}
		System.out.printf(" };\n\n\n");

	}

	public static void printWhichFace() {
		Piece pieces[] = getPieces();
		int[][] facepiece = new int[6][24];

		for (byte face = 0; face < 6; face++) {
			for (int piece = 0; piece < 24; piece++) {
				facepiece[face][piece] = pieces[piece].whichDirection(face);
			}
		}
		System.out
				.printf("public static final byte[][] piece_whichface_facepiece = new byte[][]\n");
		System.out.printf("    {");
		for (int face = 0; face < 6; face++) {
			if (face == 0)
				System.out.printf(" {");
			else
				System.out.printf("      {");
			System.out.printf("%2d", facepiece[face][0]);
			for (int piece = 1; piece < 24; piece++) {
				System.out.printf(",%2d", facepiece[face][piece]);
			}
			if (face < 5)
				System.out.printf("},\n");
			else
				System.out.printf("} ");
		}
		System.out.printf("}\n\n\n");

	}

	public static void printRotations() {
		Piece pieces[] = getPieces();

		byte[][][] facedirpiece = new byte[6][2][24];

		for (int face = 0; face < 6; face++) {
			for (int dir = 0; dir < 2; dir++) {
				for (int piece = 0; piece < 24; piece++) {
					Piece p = pieces[piece].rotate(face, dir);
					if (!p.IsValidPiece())
						System.err.println("Not a valid piece!");

					boolean match = false;
					for (byte i = 0; i < 24; i++)
						if (pieces[i].compareTo(p) == 0) {
							if (match)
								System.err
										.println("Found more than one match!");
							facedirpiece[face][dir][piece] = i;
							match = true;
						}
					if (!match)
						System.err.println("Found a match!");
				}
			}
		}
		System.out
				.printf("public static final byte[][][] piece_rotate_facedirpiece = new byte[][][]\n");
		for (int face = 0; face < 6; face++) {
			for (int dir = 0; dir < 2; dir++) {
				if (face == 0 && dir == 0)
					System.out.printf("    {{{ ");
				else {
					if (dir == 0)
						System.out.printf("     {{ ");
					else
						System.out.printf("      { ");
				}
				System.out.printf("%2d", facedirpiece[face][dir][0]);
				for (int piece = 1; piece < 24; piece++) {
					if (piece != 12)
						System.out.printf(",%3d",
								facedirpiece[face][dir][piece]);
					else
						System.out.printf(",\n       %3d",
								facedirpiece[face][dir][piece]);
				}
				if (dir < 1)
					System.out.printf(" },\n");
				else
					System.out.printf(" }");
			}
			if (face < 5)
				System.out.printf("},\n");
			else
				System.out.printf("}");
		}
		System.out.printf("};\n\n\n");
	}


	private static Piece[] getPieces() {
		Piece pieces[] = new Piece[24];

		pieces[0] = new Piece();
		pieces[4] = pieces[0].rotate(Face.RIGHT, Dir.CW);
		pieces[8] = pieces[0].rotate(Face.RIGHT, Dir.CCW);
		pieces[12] = pieces[0].rotate(Face.TOP, Dir.CW);
		pieces[16] = pieces[0].rotate(Face.TOP, Dir.CCW);
		pieces[20] = pieces[4].rotate(Face.RIGHT, Dir.CW);

		for (int i = 0; i < 6; i++) {
			for (int rot = 1; rot < 4; rot++) {
				pieces[i * 4 + rot] = pieces[i * 4 + rot - 1].rotate(
						Face.FRONT, Dir.CW);
			}
		}

		for (int i = 0; i < 24; i++) {
			if (!pieces[i].IsValidPiece())
				System.err.println("Not a valid piece!");
			for (int j = i + 1; j < 24; j++) {
				if (pieces[i].compareTo(pieces[j]) == 0)
					System.out.println("Duplicate! " + i + " " + j);

			}
		}
		return pieces;

	}


}
