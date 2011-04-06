/*
 * $Id: GenerateLookupTables.java,v 1.1 2010/01/02 06:22:26 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: GenerateLookupTables.java,v $
 * Revision 1.1  2010/01/02 06:22:26  bfox
 * Rearranged packages.
 *
 * Revision 1.1  2010/01/01 08:28:49  bfox
 * Rearranged packages.
 *
 * Revision 1.2  2009/03/25 20:20:53  bfox
 * Potential "reverse" solution.
 *
 * Revision 1.1  2009/03/21 21:54:10  bfox
 * Simple class to dump lookup tables for inspection.
 *
 * Revision 1.1  2009/03/11 21:07:29  bfox
 * Changing FTR to FRT
 *
 * Revision 1.14  2009/03/04 23:27:41  bfox
 * *** empty log message ***
 *
 * Revision 1.13  2009/03/03 22:50:04  bfox
 * Performed a few new runs.
 *
 * Revision 1.12  2009/03/03 19:33:09  bfox
 * Added reverse.
 *
 * Revision 1.11  2009/03/02 19:13:03  bfox
 * *** empty log message ***
 *
 * Revision 1.10  2009/02/26 22:36:58  bfox
 * *** empty log message ***
 *
 * Revision 1.9  2009/01/09 17:41:05  bfox
 * *** empty log message ***
 *
 * Revision 1.8  2008/12/15 17:12:51  bfox
 * *** empty log message ***
 *
 * Revision 1.7  2008/12/12 23:48:39  bfox
 * *** empty log message ***
 *
 * Revision 1.6  2008/12/10 17:49:17  bfox
 * Added a more intuitive way to handle cube equivalency flags.
 *
 * Revision 1.5  2008/11/07 23:52:54  bfox
 * *** empty log message ***
 *
 * Revision 1.4  2008/10/27 16:30:28  bfox
 * Cube is now used as an object factory.
 *
 * Revision 1.3  2008/10/27 15:52:10  bfox
 * Added copyright and cvs logging.
 *
 */

package fox.brian.rubiks.apps.tools;

import fox.brian.rubiks.model.cube.Piece;

public class GenerateLookupTables {

	public static void main(String[] args) {
		Piece.printLabels();
		Piece.printRotations();
		Piece.printWhichFace();
		
	}
}
