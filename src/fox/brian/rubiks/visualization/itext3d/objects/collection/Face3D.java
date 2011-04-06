/*
 * $Id: Face3D.java,v 1.6 2010/08/24 00:31:13 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Face3D.java,v $
 * Revision 1.6  2010/08/24 00:31:13  bfox
 * Now with backface culling.
 *
 * Revision 1.5  2010/08/23 23:12:48  bfox
 * Think I finally found the pesky ordering problem.  It was related to centering corrections to the face as a whole, not the individual labels.
 *
 * Revision 1.4  2010/07/29 22:07:32  bfox
 * Sanity check.  Why is Eclipse showing these files changed?
 *
 * Revision 1.3  2010/07/06 22:32:41  bfox
 * Still working on 3d cube drawings.
 * Somewhat stable now.
 *
 * Revision 1.2  2010/07/02 17:16:34  bfox
 * *** empty log message ***
 *
 * Revision 1.1  2010/06/22 15:32:15  bfox
 * Refactored package locations to be a little more intuitive.
 * Added "bounding boxes" to help in debugging.
 *
 * Revision 1.9  2010/06/17 16:11:05  bfox
 * Still working on 3d cube drawings.
 *
 * Revision 1.8  2010/06/16 21:42:31  bfox
 * Still working on 3d cube drawings.
 *
 * Revision 1.7  2010/06/15 21:19:51  bfox
 * Still working on 3d cube drawings.
 *
 * Revision 1.6  2010/06/14 23:20:22  bfox
 * Still working on 3d cube drawings.
 *
 * Revision 1.5  2010/06/07 21:57:17  bfox
 * *** empty log message ***
 *
 * Revision 1.4  2010/06/04 23:07:17  bfox
 * Trying a new idea of a "ChainedMatrix" with better
 * debugging and a string of notes.
 *
 * Revision 1.3  2010/05/29 18:42:31  bfox
 * *** empty log message ***
 *
 * Revision 1.2  2010/05/29 01:56:09  bfox
 * *** empty log message ***
 *
 * Revision 1.1  2010/05/28 20:45:34  bfox
 * Still building up 3d library.
 *
*/

package fox.brian.rubiks.visualization.itext3d.objects.collection;

import java.awt.Color;
import java.util.ArrayList;

import fox.brian.rubiks.visualization.itext3d.objects.Compound;
import fox.brian.rubiks.visualization.itext3d.objects.Simple;
import fox.brian.rubiks.visualization.itext3d.objects.paths.collection.RoundedRectangle;

public class Face3D extends Compound {

	public static double LABEL_GAP_SIZE  = 0.10; 
	public static double LABEL_ROUNDNESS = 0.20; 

	private int cubesize;

	ArrayList<Simple> faces;
	
	public Face3D(int cubesize) {
		this(cubesize, LABEL_GAP_SIZE, LABEL_ROUNDNESS);
	}

	public Face3D(int cubesize, double gap, double round) {
		super();
		this.cubesize = cubesize;
		faces = new ArrayList<Simple>();
		for (int y = 0; y < cubesize; y++) {
			for (int x=0; x < cubesize; x++) {
				Simple next = new Simple(new RoundedRectangle(LABEL_ROUNDNESS));
				next.scale(1-LABEL_GAP_SIZE, 1-LABEL_GAP_SIZE, 1,  getClass().getSimpleName() + " construction.  Scaling label for gap.");
				next.move(x+(-cubesize/2.0 + 0.5), y+(-cubesize/2.0 + 0.5), 0, getClass().getSimpleName() + " construction.  Positioning label.");
				add(next);
				faces.add(next);
			}
		}
		this.scale(1.0/cubesize,1.0/cubesize,1, getClass().getSimpleName() + " construction.  Scaling to unit size.");
	}

	
	public void paintTestPattern(Color base) {
		for (int i=0; i < cubesize; i++)
			for (int j=0; j < cubesize; j++) 
				faces.get(i*cubesize+j).setColor(base);
			
		faces.get(0).setColor(new Color(0x00,0x66,0x00));
		faces.get(cubesize*cubesize-1).setColor(new Color(0x66,0x00,0x00));
		faces.get(cubesize-1).setColor(Color.gray);
	}
}

