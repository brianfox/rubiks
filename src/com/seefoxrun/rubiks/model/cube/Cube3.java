package com.seefoxrun.rubiks.model.cube;

class Cube3 extends Cube {
	
	private static final long serialVersionUID = 1L;

	/*
	 * Cube Mapping 
	 * ------------ 
	 * The classic cube contains eight corner pieces, each of 
	 * which exposes three labels, twelve side pieces, each 
	 * of which exposes two labels, and six center pieces, each
	 * of which exposes one label. The pieces form an 24 
	 * celled array. See the diagram below.
	 * 
	 *               .TOP-1....... 
	 *               .14 .13 .12 . 
	 *               ............. 
	 *               .16 .21 .17 . 
	 *               ............. 
	 *               . 0 . 1 . 2 . 
	 *               ............. 
	 * .LEFT-3...... .FRONT-0..... .RIGHT-2..... 
	 * .14 .16 . 0 . . 0 . 1 . 2 . . 2 .17 .12 . 
	 * ............. ............. ............. 
	 * .15 .23 . 7 . . 7 .20 . 3 . . 3 .22 .11 . 
	 * ............. ............. .............
	 * . 8 .19 . 6 . . 6 . 5 . 4 . . 4 .18 .10 . 
	 * ............. ............. .............
	 *               .BOTTOM-4.... 
	 *               . 6 . 5 . 4 .
	 *               ............. 
	 *               .19 .24 .18 . 
	 *               ............. 
	 *               . 8 . 9 .10 . 
	 *               ............. 
	 *               .BACK-5...... 
	 *               . 8 . 9 .10 . 
	 *               ............. 
	 *               .15 .25 .11 .
	 *               .............
	 *               .14 .13 .12 .
	 *               .............
	 */
	
	private static final int NUM_PIECES = 26;
	private static final int SIZE = 3;

	@Override
	public int size() {
		return SIZE;
	}

	// [face][slice][onion ring][members]
	private static final byte[][][][] loops = new byte[][][][] { 
		{ { { 0, 1, 2, 3, 4, 5, 6, 7}, {20} },           // front 1
		  { {16, 21, 17, 22, 18, 24, 19, 23} },          // front 2
		  { {14, 13, 12, 11, 10, 9, 8, 15}, {25} } },    // front 3

    	{ { {2, 17, 12, 11, 10, 18, 4, 3}, {22} },       // right 1
	      { {1, 21, 13, 25, 9, 24, 5, 20} },             // right 2
	      { {0, 16, 14, 15, 8, 19, 6, 7}, {23} } },      // right 3

		{ { {14, 13, 12, 17, 2, 1, 0, 16}, {21} },       // top 1
	      { {15, 25, 11, 22, 3, 20, 7, 23} },            // top 2
	      { {6, 19, 8, 9, 10, 18, 4, 5}, {24} } },       // top 3

		{ { {5, 4, 18, 10, 9, 8, 19, 6}, {24} },         // bottom 1
		  { {23, 7, 20, 3, 22, 11, 25, 15} },            // bottom 2
		  { {16, 0, 1, 2, 17, 12, 13, 14}, {21} } },     // bottom 3

    	{ { {7, 6, 19, 8, 15, 14, 16, 0}, {23} },        // left 1
	      { {20, 5, 24, 9, 25, 13, 21, 1} },             // left 2
	      { {3, 4, 18, 10, 11, 12, 17, 2}, {22} } },     // left 3

		{ { {15, 8, 9, 10, 11, 12, 13, 14}, {25} },      // back 1
		  { {23, 19, 24, 18, 22, 17, 21, 16} },          // back 2
		  { {7, 6, 5, 4, 3, 2, 1, 0}, {20} }  },         // back 3
	};

	private static final byte[][][] faces = new byte[][][] { 
		{ {0, 1, 2}, {7, 20, 3}, {6, 5, 4} },         // front
		{ {2, 17, 12}, {3, 22, 11}, {4, 18, 10} },    // right
		{ {14, 13, 12}, {16, 21, 17}, {0, 1, 2} },    // top
		{ {6, 5, 4}, {19, 24, 18}, {8, 9, 10} },      // bottom
		{ {14, 16, 0}, {15, 23, 7}, {8, 19, 6} },     // left
		{ {8, 9, 10}, {15, 25, 11}, {14, 13, 12} }    // back
	};

	protected Cube3() {
		this.faceMap = FaceMap.FRT;
		pieces = new byte[NUM_PIECES];
	}

	@Override 
	public Cube3 clone() {
		Cube3 ret = new Cube3();
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

	@Override
	protected int indexGroup() {
		return pieces[0];
	}

	@Override
	protected int indexSize() {
		return 256;
	}

}
