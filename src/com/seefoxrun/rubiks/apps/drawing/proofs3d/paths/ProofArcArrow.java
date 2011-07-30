/*
 * $Log: ProofArcArrow.java,v $
 * Revision 1.5  2010/06/22 16:27:40  bfox
 * Cleaned up compound.
 *
 * Revision 1.4  2010/06/22 15:32:14  bfox
 * Refactored package locations to be a little more intuitive.
 * Added "bounding boxes" to help in debugging.
 *
 * Revision 1.3  2010/05/28 20:45:34  bfox
 * Still building up 3d library.
 *
 * Revision 1.2  2010/05/28 20:33:28  bfox
 * Still building up 3d library.
 *
 * Revision 1.1  2010/05/27 16:11:51  bfox
 * Added more boolean paths.
 *
 * Revision 1.1  2010/05/14 21:01:52  bfox
 * Split paths into simple and boolean paths.  Still working on
 * the boolean side.
 *
*/


package com.seefoxrun.rubiks.apps.drawing.proofs3d.paths;

import com.seefoxrun.rubiks.apps.drawing.proofs3d.PathProof;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection.ArcArrow;

public class ProofArcArrow extends PathProof {

	public static void main(String[] args) {
        PathProof proof = new ProofArcArrow();
		proof.createProof(new ArcArrow(0.3,2.5,0.5,0.6));
	}

}

