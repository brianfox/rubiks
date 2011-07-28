/*
 * $Id: RubiksCube3D.java,v 1.4 2010/08/24 00:31:13 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: RubiksCube3D.java,v $
 * Revision 1.4  2010/08/24 00:31:13  bfox
 * Now with backface culling.
 *
 * Revision 1.3  2010/08/23 23:12:48  bfox
 * Think I finally found the pesky ordering problem.  It was related to centering corrections to the face as a whole, not the individual labels.
 *
 * Revision 1.2  2010/07/02 17:16:34  bfox
 * *** empty log message ***
 *
 * Revision 1.1  2010/06/22 15:32:15  bfox
 * Refactored package locations to be a little more intuitive.
 * Added "bounding boxes" to help in debugging.
 *
 * Revision 1.2  2010/06/17 21:15:20  bfox
 * Still working on 3d cube drawings.
 *
 * Revision 1.1  2010/06/17 16:11:05  bfox
 * Still working on 3d cube drawings.
 *
 * Revision 1.6  2010/06/15 21:19:51  bfox
 * Still working on 3d cube drawings.
 *
 * Revision 1.5  2010/06/14 23:20:22  bfox
 * Still working on 3d cube drawings.
 *
 * Revision 1.4  2010/06/07 21:57:17  bfox
 * *** empty log message ***
 *
 * Revision 1.3  2010/06/04 23:07:17  bfox
 * Trying a new idea of a "ChainedMatrix" with better
 * debugging and a string of notes.
 *
 * Revision 1.2  2010/05/29 18:42:30  bfox
 * *** empty log message ***
 *
 * Revision 1.1  2010/05/29 01:56:09  bfox
 * *** empty log message ***
 *
 * Revision 1.1  2010/05/28 20:45:34  bfox
 * Still building up 3d library.
 *
*/

package com.seefoxrun.rubiks.visualization.itext3d.objects.collection;

import com.seefoxrun.matrix.Axis;
import com.seefoxrun.rubiks.model.cube.Face;
import com.seefoxrun.rubiks.model.cube.Piece;
import com.seefoxrun.rubiks.visualization.itext3d.objects.Compound;

public class RubiksCube3D extends Compound {

	private int cubesize;

	public RubiksCube3D(int cubesize) {
		super();
		
		this.cubesize = cubesize;
		construct();
	}

	private void construct() {
		Face3D front = createFace(Face.FRONT);
		front.move(0,0,-0.5, "RubiksCube3D construction. Front face.");
		this.add(front);

		Face3D back = createFace(Face.BACK);
		back.rotateDeg(Axis.X, 180, "Cube3D construction.  Top face.");
		back.move(0,0,0.5, "RubiksCube3D construction. Back face.");
		this.add(back);

		Face3D right = createFace(Face.RIGHT);
		right.rotateDeg(Axis.Y, -90, "Cube3D construction.  Top face.");
		right.move(0.5,0,0, "RubiksCube3D construction.  Top face.");
		this.add(right);

		Face3D left = createFace(Face.LEFT);
		left.rotateDeg(Axis.Y, 90, "RubiksCube3D construction.  Top face.");
		left.move(-0.5,0,0, "RubiksCube3D construction.  Top face.");
		this.add(left);
		
		Face3D top = createFace(Face.TOP);
		top.rotateDeg(Axis.X, -90, "RubiksCube3D construction.  Top face.");
		top.move(0,-0.5,0, "RubiksCube3D construction.  Top face.");
		this.add(top);

		Face3D bottom = createFace(Face.BOTTOM);
		bottom.rotateDeg(Axis.X, 90, "RubiksCube3D construction.  Bottom face.");
		bottom.move(0,0.5,0, "RubiksCube3D construction.  Bottom face.");
		this.add(bottom);
	}
	
	private Face3D createFace(Face f) {
		Face3D face3d = new Face3D(cubesize); 
		face3d.setMemo(String.format("%-7s (%s)", f.getDescription(), Piece.FACECOLORS_FACE[f.val()]));
		face3d.paintTestPattern(Piece.AWTCOLOR_FACE[f.val()]);
		return face3d;
	}

}

