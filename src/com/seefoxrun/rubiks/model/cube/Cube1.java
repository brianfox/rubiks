package com.seefoxrun.rubiks.model.cube;

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
