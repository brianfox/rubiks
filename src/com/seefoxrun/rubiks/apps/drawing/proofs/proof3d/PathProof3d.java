/*
 * $Id: PathProof3d.java,v 1.1 2010/06/22 16:27:40 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: PathProof3d.java,v $
 * Revision 1.1  2010/06/22 16:27:40  bfox
 * Cleaned up compound.
 *
 * Revision 1.8  2010/06/22 15:32:14  bfox
 * Refactored package locations to be a little more intuitive.
 * Added "bounding boxes" to help in debugging.
 *
 * Revision 1.7  2010/06/17 16:11:05  bfox
 * Still working on 3d cube drawings.
 *
 * Revision 1.6  2010/06/14 23:20:22  bfox
 * Still working on 3d cube drawings.
 *
 * Revision 1.5  2010/06/11 23:04:45  bfox
 * Made the proof sheet a bit more logical.  All operations
 * should return the object to it's original position.
 *
 * Revision 1.4  2010/06/07 21:57:16  bfox
 * *** empty log message ***
 *
 * Revision 1.3  2010/05/29 18:42:30  bfox
 * *** empty log message ***
 *
 * Revision 1.2  2010/05/14 21:01:51  bfox
 * Split paths into simple and boolean paths.  Still working on
 * the boolean side.
 *
 * Revision 1.1  2010/05/13 00:48:15  bfox
 * Returned to Matrix 3d graphics with a vengeance.  Finally feel
 * this design is working out.
 *
 * Revision 1.3  2010/04/14 18:03:43  bfox
 * Backed out Matrix algebra for 3D rendering.
 *
 * Revision 1.1  2010/03/20 17:30:33  bfox
 * Relocated from testbed.  Renamed consistently.
 *
 * Revision 1.1  2010/03/18 22:24:35  bfox
 * Correctly color front, right, left, and right.
 * Still need to test top and bottom.
 *
 * Revision 1.3  2010/03/15 22:39:58  bfox
 * Finally solved the perspective problem.
 * Cube seems to behave well.
 *
 * Revision 1.2  2010/03/15 20:52:30  bfox
 * Still working or perspective projection.
 *
 * Revision 1.1  2010/03/15 00:13:10  bfox
 * Having trouble with the perspective view.
 *
 * Revision 1.2  2010/03/10 23:00:48  bfox
 * Added cvs logging.
 *
 *
*/


package com.seefoxrun.rubiks.apps.drawing.proofs.proof3d;

import com.seefoxrun.rubiks.visualization.itext3d.objects.Simple;
import com.seefoxrun.rubiks.visualization.itext3d.objects.paths.Path;

public abstract class PathProof3d extends Proof3d {

	public void createProof(Path p) {
		super.createProof(new Simple(p));

	}

}
