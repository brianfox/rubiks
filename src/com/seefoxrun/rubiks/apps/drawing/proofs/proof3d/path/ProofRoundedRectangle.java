/*
 * $Log: ProofRoundedRectangle.java,v $
 * Revision 1.4  2010/06/22 16:27:40  bfox
 * Cleaned up compound.
 *
 * Revision 1.3  2010/06/22 15:32:14  bfox
 * Refactored package locations to be a little more intuitive.
 * Added "bounding boxes" to help in debugging.
 *
 * Revision 1.2  2010/06/11 23:33:49  bfox
 * Cleaning up a lot of stuff.
 *
 * Revision 1.1  2010/05/14 21:01:52  bfox
 * Split paths into simple and boolean paths.  Still working on
 * the boolean side.
 *
*/


package com.seefoxrun.rubiks.apps.drawing.proofs.proof3d.path;

import com.seefoxrun.rubiks.apps.drawing.proofs.proof3d.PathProof3d;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection.RoundedRectangle;

public class ProofRoundedRectangle extends PathProof3d {

	public static void main(String[] args) {
        PathProof3d proof = new ProofRoundedRectangle();
		proof.createProof(new RoundedRectangle(0.3));
	}

}

