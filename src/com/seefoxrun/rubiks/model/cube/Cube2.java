package com.seefoxrun.rubiks.model.cube;

class Cube2 extends Cube {

	private static final long serialVersionUID = 1L;
	
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

	@Override
	protected byte[][] getLoopPieces(Face face, int slice) {
		return loops[face.val()][slice];
	}

	@Override
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
