/*
 * $Log: ProofRing.java,v $
 * Revision 1.3  2010/06/22 16:27:40  bfox
 * Cleaned up compound.
 *
 * Revision 1.2  2010/06/22 15:32:14  bfox
 * Refactored package locations to be a little more intuitive.
 * Added "bounding boxes" to help in debugging.
 *
 * Revision 1.1  2010/05/14 21:01:52  bfox
 * Split paths into simple and boolean paths.  Still working on
 * the boolean side.
 *
*/


package com.seefoxrun.rubiks.apps.drawing.proofs3d.paths;

import com.seefoxrun.rubiks.apps.drawing.proofs3d.PathProof;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.collection.Ring;

public class ProofRing extends PathProof {

	public static void main(String[] args) {
        PathProof proof = new ProofRing();
		proof.createProof(new Ring(0.3));
	}

}

