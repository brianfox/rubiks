/*
 * $Id: Cube2.java,v 1.3 2010/01/30 01:30:50 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Cube2.java,v $
 * Revision 1.3  2010/01/30 01:30:50  bfox
 * The distinct move addition was a bad idea.  Distinct changes
 * for different "similar cube" strategies.  Removed.
 *
 * Revision 1.2  2010/01/27 16:35:54  bfox
 * Radial graph coming together.
 *
 * Revision 1.1  2010/01/01 08:28:44  bfox
 * Rearranged packages.
 *
 * Revision 1.19  2009/07/13 15:21:38  bfox
 * *** empty log message ***
 *
 * Revision 1.18  2009/05/07 18:49:14  bfox
 * *** empty log message ***
 *
 * Revision 1.17  2009/05/06 17:19:26  bfox
 * *** empty log message ***
 *
 * Revision 1.16  2009/05/06 01:47:10  bfox
 * Preparation before reworking the "state" concept of the cube.
 *
 * Revision 1.15  2009/05/05 23:18:08  bfox
 * Preparation before reworking the "state" concept of the cube.
 *
 * Revision 1.14  2009/04/29 23:13:51  bfox
 * Undo mechanism is solid.  Some loose ends still need to be tied - mostly
 * resetting the stack after various operations and fixing the Toy
 * class to properly handle numbers in the undo command.
 *
 * Revision 1.13  2009/04/28 15:50:21  bfox
 * Some refactoring.
 *
 * Revision 1.12  2009/03/23 05:13:48  bfox
 * Moved common clone method to abstract Cube class.
 *
 * Revision 1.11  2009/03/17 16:18:30  bfox
 * Still going for that FTR to FRT transition for some reason.
 *
 * Revision 1.10  2009/03/11 21:07:29  bfox
 * Changing FTR to FRT
 *
 * Revision 1.9  2009/03/06 00:07:37  bfox
 * Facemap added to constructor.
 *
 * Revision 1.8  2009/03/02 22:11:15  bfox
 * size variable is now properly cloned.
 *
 * Revision 1.7  2009/03/02 19:13:03  bfox
 * *** empty log message ***
 *
 * Revision 1.6  2008/11/07 23:52:55  bfox
 * *** empty log message ***
 *
 * Revision 1.5  2008/10/29 22:35:48  bfox
 * Caching efforts.
 *
 * Revision 1.4  2008/10/28 14:47:42  bfox
 * Moved the countPieces() method to base class.
 *
 * Revision 1.3  2008/10/27 16:29:58  bfox
 * Cube is now an object factory.
 * Each concrete cube is no longer visible outside of the package.
 *
 * Revision 1.2  2008/10/27 15:50:39  bfox
 * Added copyright and cvs logging.
 *
 */

package com.seefoxrun.rubiks.model.cube;

class Cube2 extends Cube {

	private static final long serialVersionUID = 1L;
	
	/* From wikipedia:
	   
	   The number f of positions that require n full twists and 
	   number q of positions that require n quarter turn twists are:
	  
			n 	f 		q
			0 	1 		1
			1 	9 		6
			2 	54 		27
			3 	321 	120
			4 	1847 	534
			5 	9992 	2256
			6 	50136 	8969
			7 	227536 	33058
			8 	870072 	114149
			9 	1887748 360508
			10 	623800 	930588
			11 	2644 	1350852
			12 			782536
			13 			90280
			14 			276
	
	*/
	/*
	 * Cube Mapping 
	 * ------------ 
	 * The pocket cube contains eight corner pieces, each of 
	 * which exposes three labels. The pieces form an eight 
	 * celled array. See the diagram below.
	 * 
	 *           .TOP-1... 
	 *           . 7 . 6 . 
	 *           ......... 
	 *           . 0 . 1 . 
	 *           ......... 
	 * .LEFT-3.. .FRONT-0. .RIGHT-2. 
	 * . 7 . 0 . . 0 . 1 . . 1 . 6 . 
	 * ......... ......... ......... 
	 * . 4 . 3 . . 3 . 2 . . 2 . 5 . 
	 * ......... ......... ......... 
	 *           .BOTTOM-4 
	 *           . 3 . 2 .
	 *           ......... 
	 *           . 4 . 5 . 
	 *           ......... 
	 *           .BACK-5.. 
	 *           . 4 . 5 . 
	 *           ......... 
	 *           . 7 . 6 .
	 *           .........
	 *           
	 *           
	 * Pieces grouped into faces. Faces can twist in a clockwise or
	 * counterclockwise direction.
	 */
	
	private static final int NUM_PIECES = 8;
	
	// [face][slice][onion ring][members]
	private static final byte[][][][] loops = new byte[][][][] { 
		{ { {0, 1, 2, 3} }, { {7, 6, 5, 4} } }, // front
		{ { {1, 6, 5, 2} }, { {4, 3, 0, 7} } }, // right
		{ { {7, 6, 1, 0} }, { {4, 5, 2, 3} } }, // top
		{ { {3, 2, 5, 4} }, { {0, 1, 6, 7} } }, // bottom
		{ { {7, 0, 3, 4} }, { {2, 5, 6, 1} } }, // left
		{ { {4, 5, 6, 7} }, { {3, 2, 1, 0} } }  // back
	};

	private static final byte[][][] faces = new byte[][][] { 
		{ {0, 1}, {3, 2} },
		{ {1, 6}, {2, 5} },
		{ {7, 6}, {0, 1} },
		{ {3, 2}, {4, 5} },
		{ {7, 0}, {4, 3} },
		{ {4, 5}, {7, 6} }
	};

	private static final int SIZE = 2;

	@Override
	public int size() {
		return SIZE;
	}

	protected Cube2() {
		pieces = new byte[NUM_PIECES];
		this.faceMap = FaceMap.FRT;
	}

	@Override 
	public Cube2 clone() {
		Cube2 ret = new Cube2();
		finishClone(ret);
		return ret;
	}

	
	@Override
	public int countSlices(Face face) {
		return loops[face.val()].length;
	}

	protected byte[][] getLoopPieces(Face face, int slice) {
		return loops[face.val()][slice];
	}

	protected byte[][] getFacePieces(Face face) {
		return faces[face.val()];
	}
	
}
