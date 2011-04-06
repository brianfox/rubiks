/*
 * $Id: RoundedRectangle.java,v 1.2 2010/08/20 23:35:19 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: RoundedRectangle.java,v $
 * Revision 1.2  2010/08/20 23:35:19  bfox
 * Dear diary...
 *
 * Revision 1.1  2010/06/22 15:32:14  bfox
 * Refactored package locations to be a little more intuitive.
 * Added "bounding boxes" to help in debugging.
 *
 * Revision 1.6  2010/06/19 03:56:01  bfox
 * *** empty log message ***
 *
 * Revision 1.5  2010/06/18 00:48:01  bfox
 * *** empty log message ***
 *
 * Revision 1.4  2010/06/14 23:20:21  bfox
 * Still working on 3d cube drawings.
 *
 * Revision 1.3  2010/06/13 22:05:45  bfox
 * *** empty log message ***
 *
 * Revision 1.2  2010/06/04 23:07:17  bfox
 * Trying a new idea of a "ChainedMatrix" with better
 * debugging and a string of notes.
 *
 * Revision 1.1  2010/05/14 21:01:51  bfox
 * Split paths into simple and boolean paths.  Still working on
 * the boolean side.
 *
 * Revision 1.1  2010/05/13 00:48:15  bfox
 * Returned to Matrix 3d graphics with a vengeance.  Finally feel
 * this design is working out.
 *
 * Revision 1.6  2010/04/14 18:02:06  bfox
 * Backed out Matrix algebra for 3D rendering.
 *
 * Revision 1.4  2010/04/06 22:59:49  bfox
 * Got rid of the idea of operation stacks, set, and reset.
 *
 * Revision 1.3  2010/03/30 23:15:54  bfox
 * Added the idea of a rollback, called "reset/set" that will undo
 * all the operations of a Object3D.  Another method originate
 * will dispose of the transformation stack as though the object
 * were created with the current state, parameters, and values.
 *
 * Revision 1.2  2010/03/29 17:30:17  bfox
 * Moved Cube3D from a 1:1 binding with Cube.  Now Cube3D can
 * be seen as a 3d cube waiting to be painted.  It can be repainted as
 * many times as necessary.
 *
 * Revision 1.1  2010/03/18 22:33:53  bfox
 * Mved from testbed to a proper package.
 *
 * Revision 1.4  2010/03/15 00:13:10  bfox
 * Having trouble with the perspective view.
 *
 * Revision 1.3  2010/03/10 23:00:48  bfox
 * Added cvs logging.
 *
 *
*/


package fox.brian.rubiks.visualization.itext3d.objects.paths.collection;

import fox.brian.rubiks.visualization.itext3d.objects.paths.Path;

public class RoundedRectangle extends Path {

	public RoundedRectangle(double cornerRadius) {
		constructLabel(cornerRadius);
	}

	public void constructLabel(double cornerRadius) {
		double full = 0.5;
		double part = full - cornerRadius / 2;
		
		// counter clockwise assuming positive y goes upwards
		// (doesn't really matter except for readability)
		segments.add(new Line(	
				-part, full, 0,  
				 part, full, 0   ) ); // top

		segments.add(new Quadratic(
				 part, full, 0, 
				 full, full, 0,
				 full, part, 0)  ); // top-right
		
		segments.add(new Line( 
				 full,  part, 0,  
				 full, -part, 0) ); // right

		segments.add(new Quadratic(
				 full, -part, 0, 
				 full, -full, 0,
				 part, -full, 0)  ); // bottom-right

		segments.add(new Line(
				 part, -full, 0, 
				-part, -full, 0) ); // bottom

		segments.add(new Quadratic(
				-part, -full, 0, 
				-full, -full, 0,
				-full, -part, 0) ); // top-left

		segments.add(new Line(
				-full, -part, 0, 
				-full,  part, 0) ); // left

		segments.add(new Quadratic(
				-full,  part, 0, 
				-full,  full, 0,
				-part,  full, 0)  ); // upper-left
	}

}
