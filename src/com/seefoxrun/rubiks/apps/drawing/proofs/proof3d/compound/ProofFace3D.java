/*
 * $Log: ProofFace3D.java,v $
 * Revision 1.8  2010/07/06 22:32:40  bfox
 * Still working on 3d cube drawings.
 * Somewhat stable now.
 *
 * Revision 1.7  2010/06/22 16:27:40  bfox
 * Cleaned up compound.
 *
 * Revision 1.6  2010/06/22 15:32:14  bfox
 * Refactored package locations to be a little more intuitive.
 * Added "bounding boxes" to help in debugging.
 *
 * Revision 1.5  2010/06/17 16:11:05  bfox
 * Still working on 3d cube drawings.
 *
 * Revision 1.4  2010/05/29 01:56:08  bfox
 * *** empty log message ***
 *
 * Revision 1.3  2010/05/28 22:31:56  bfox
 * Code cleanup - removed unnecessary imports.
 *
 * Revision 1.2  2010/05/28 22:30:47  bfox
 * Corrected the order of matrix multiplication.
 *
 * Revision 1.1  2010/05/28 20:45:34  bfox
 * Still building up 3d library.
 *
 * Revision 1.1  2010/05/14 21:01:52  bfox
 * Split paths into simple and boolean paths.  Still working on
 * the boolean side.
 *
*/


package com.seefoxrun.rubiks.apps.drawing.proofs.proof3d.compound;

import java.awt.Color;

import com.seefoxrun.rubiks.drawing.proofs.Proof3d;
import com.seefoxrun.rubiks.visualization.itext3d.objects.collection.Face3D;

public class ProofFace3D extends Proof3d {

	public static void main(String[] args) {
        Proof3d proof = new ProofFace3D();
        Face3D obj = new Face3D(5);
        obj.paintTestPattern(Color.red);
		proof.createProof(obj);
	}

}

