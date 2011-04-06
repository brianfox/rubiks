/*
 * $Id: Cube3D.java,v 1.2 2010/07/02 17:16:34 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Cube3D.java,v $
 * Revision 1.2  2010/07/02 17:16:34  bfox
 * *** empty log message ***
 *
 * Revision 1.1  2010/06/22 15:32:15  bfox
 * Refactored package locations to be a little more intuitive.
 * Added "bounding boxes" to help in debugging.
 *
 * Revision 1.8  2010/06/17 21:15:20  bfox
 * Still working on 3d cube drawings.
 *
 * Revision 1.7  2010/06/17 16:11:05  bfox
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

package fox.brian.rubiks.visualization.itext3d.objects.collection;

import java.awt.Color;

import fox.brian.matrix.Axis;
import fox.brian.rubiks.visualization.itext3d.objects.Compound;
import fox.brian.rubiks.visualization.itext3d.objects.Simple;
import fox.brian.rubiks.visualization.itext3d.objects.paths.collection.Rectangle;

public class Cube3D extends Compound {

	public Cube3D() {
		super();
		constructFaces();
	}

	private void constructFaces() {
		Simple top = new Simple(new Rectangle());
		top.rotateDeg(Axis.X, -90, "Cube3D construction.  Top face.");
		top.move(0,-0.5,0, "Cube3D construction.  Top face.");

		Simple left = new Simple(new Rectangle());
		left.rotateDeg(Axis.Y, 90, "Cube3D construction.  Top face.");
		left.move(-0.5,0,0, "Cube3D construction.  Top face.");

		Simple front = new Simple(new Rectangle());
		front.move(0,0,-0.5, "Cube3D construction. Front face.");
		
		Simple right = new Simple(new Rectangle());
		right.rotateDeg(Axis.Y, -90, "Cube3D construction.  Top face.");
		right.move(0.5,0,0, "Cube3D construction.  Top face.");
		
		Simple bottom = new Simple(new Rectangle());
		bottom.rotateDeg(Axis.X, 90, "Cube3D construction.  Top face.");
		bottom.move(0,0.5,0, "Cube3D construction.  Top face.");
		
		Simple back = new Simple(new Rectangle());
		back.rotateDeg(Axis.X, 180, "Cube3D construction.  Top face.");
		back.move(0,0,0.5, "Cube3D construction. Back face.");

		this.add(top);
		this.add(left);
		this.add(front);
		this.add(right);
		this.add(bottom);
		this.add(back);
	}

	public void paintTestPattern() {
		/*
		simples.get(0).setColor(Color.white);
		simples.get(1).setColor(Color.orange);
		simples.get(2).setColor(Color.red);
		simples.get(3).setColor(Color.blue);
		simples.get(4).setColor(Color.yellow);
		simples.get(5).setColor(Color.green);
		*/
	}

}

