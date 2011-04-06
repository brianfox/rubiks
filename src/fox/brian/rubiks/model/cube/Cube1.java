/*
 * $Id: Cube1.java,v 1.3 2010/01/30 01:30:50 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Cube1.java,v $
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
 * Revision 1.19  2009/06/08 16:26:49  bfox
 * *** empty log message ***
 *
 * Revision 1.18  2009/05/07 18:49:14  bfox
 * *** empty log message ***
 *
 * Revision 1.17  2009/05/06 17:19:26  bfox
 * *** empty log message ***
 *
 * Revision 1.16  2009/05/06 01:47:09  bfox
 * Preparation before reworking the "state" concept of the cube.
 *
 * Revision 1.15  2009/05/05 23:18:07  bfox
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
 * Revision 1.6  2009/02/26 22:36:58  bfox
 * *** empty log message ***
 *
 * Revision 1.5  2008/11/07 23:52:55  bfox
 * *** empty log message ***
 *
 * Revision 1.4  2008/10/29 22:35:48  bfox
 * Caching efforts.
 *
 * Revision 1.3  2008/10/28 14:47:42  bfox
 * Moved the countPieces() method to base class.
 *
 * Revision 1.2  2008/10/27 16:29:58  bfox
 * Cube is now an object factory.
 * Each concrete cube is no longer visible outside of the package.
 *
 * Revision 1.1  2008/10/27 15:50:06  bfox
 * Added trivial 1x1 cube.
 *
 */

package fox.brian.rubiks.model.cube;

class Cube1 extends Cube {
	
	/*
	 * Cube Mapping 
	 * ------------
	 * This is the trivial cube.  It contains just piece and
	 * can only be rotated as a unit (that is, there are no
	 * pieces which move in relation to each other). 
	 */
	
	private static final long serialVersionUID = 1L;
	private static final int NUM_PIECES = 1;
	private static final int SIZE = 1;

	
	@Override
	public int size() {
		return SIZE;
	}
	
	// [face][slice][onion ring][members]
	private static final byte[][][][] loops = new byte[][][][] { 
		{ { {0} } }, // front
		{ { {0} } }, // right
		{ { {0} } }, // top
		{ { {0} } }, // bottom
		{ { {0} } }, // left
		{ { {0} } }  // back
	};

	private static final byte[][][] faces = new byte[][][] { 
		{ {0} },
		{ {0} },
		{ {0} },
		{ {0} },
		{ {0} },
		{ {0} }
	};

	@Override
	protected byte[][] getLoopPieces(Face face, int slice) {
		return loops[face.val()][slice];
	}

	@Override
	protected byte[][] getFacePieces(Face face) {
		return faces[face.val()];
	}


	protected Cube1() {
		this.faceMap = FaceMap.FRT;
		pieces = new byte[NUM_PIECES];
	}

	@Override 
	public Cube1 clone() {
		Cube1 ret = new Cube1();
		finishClone(ret);
		return ret;
	}

	
	@Override
	public int countSlices(Face face) {
		return loops[face.val()].length;
	}

}
