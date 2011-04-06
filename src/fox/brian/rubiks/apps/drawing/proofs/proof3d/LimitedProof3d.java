/*
 * $Id: LimitedProof3d.java,v 1.5 2010/08/25 21:06:36 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: LimitedProof3d.java,v $
 * Revision 1.5  2010/08/25 21:06:36  bfox
 * Solved the perspective issue.  The problem: normalize w.
 *
 * Revision 1.4  2010/08/24 21:25:08  bfox
 * Playing around with perspective.
 *
 * Revision 1.3  2010/08/24 18:35:24  bfox
 * Playing around with perspective.
 *
 * Revision 1.2  2010/08/23 23:12:47  bfox
 * Think I finally found the pesky ordering problem.  It was related to centering corrections to the face as a whole, not the individual labels.
 *
 * Revision 1.1  2010/08/20 23:35:19  bfox
 * Dear diary...
 *
 * Revision 1.11  2010/07/29 22:07:32  bfox
 * Sanity check.  Why is Eclipse showing these files changed?
 *
 * Revision 1.10  2010/07/02 17:16:34  bfox
 * *** empty log message ***
 *
 * Revision 1.9  2010/06/22 16:27:40  bfox
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


package fox.brian.rubiks.apps.drawing.proofs.proof3d;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;


import com.itextpdf.text.DocumentException;

import fox.brian.matrix.Axis;
import fox.brian.matrix.Matrix;
import fox.brian.matrix.TranslationMatrix;
import fox.brian.rubiks.visualization.itext3d.objects.Object3D;
import fox.brian.rubiks.visualization.itext3d.objects.Simple;
import fox.brian.rubiks.visualization.itext3d.objects.paths.Path;
import fox.brian.visualization.Document;
import fox.brian.visualization.measurement.Units;
import fox.brian.visualization.measurement.Value;
import fox.brian.visualization.medium.Border;
import fox.brian.visualization.medium.Page;

public abstract class LimitedProof3d {

	public void createProof(Object3D o) {
		String filename="test.pdf";
		Document doc = openDocument(filename);
	
		Graphics2D g = doc.getGraphics2D();
		g.setColor(Color.black);
		g.fillRect(0, 0, 5000, 5000);
		g.setBackground(Color.black);

		
		double scale = 200;
		
		o.scale(scale,scale,scale, getClass().getSimpleName() + " testing.  Visibility scale.");

		g.translate((int)doc.width().convertTo(Units.POINTS)/2, (int)doc.height().convertTo(Units.POINTS)/2);
		for (int i=0; i < 7; i++) {
			o.rotateDeg(Axis.X, 15, getClass().getSimpleName() + ": Testing x-axis rotation.");
			o.rotateDeg(Axis.Y, 15, getClass().getSimpleName() + ": Testing Y-axis rotation.");
		}

		Matrix perspective = new Matrix(
				new double[][]{
						{1,0,0,0},
						{0,1,0,0},
						{0,0,1,0},
						{0,0,0.001,1}
						});
		Matrix move = new TranslationMatrix(0,0,-4);
		Matrix projection = perspective.multiply(move);
		
		o.draw(g, projection);
		
		doc.close();
		launchDocument(filename);
	}


	private static Document openDocument(String filename) {
		Border border = new Border(
				.5F, 
				.5F, 
				.5F, 
				.5F, 
				Units.INCHES
		);
		
		Page page = new Page(
				new Value(11, Units.INCHES), 
				new Value(8.5, Units.INCHES), 
				border
		);
		Document doc = null;
		try {
			doc = new Document(filename, page);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		} catch (DocumentException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		return doc;
	}
	
	
	private static void launchDocument(String filename) {
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            File pdfFile = new File(filename);
	        try {
	            desktop.open(pdfFile);
	        }
	        catch (IOException ioe) {
	            ioe.printStackTrace();
	        }
        }
	}

}
