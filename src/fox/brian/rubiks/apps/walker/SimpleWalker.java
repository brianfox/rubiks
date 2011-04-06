/*
 * $Id: SimpleWalker.java,v 1.9 2010/02/12 14:44:25 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: SimpleWalker.java,v $
 * Revision 1.9  2010/02/12 14:44:25  bfox
 * *** empty log message ***
 *
 * Revision 1.8  2010/02/08 18:21:14  bfox
 * *** empty log message ***
 *
 * Revision 1.7  2010/02/01 16:43:54  bfox
 * *** empty log message ***
 *
 * Revision 1.6  2010/01/25 16:44:33  bfox
 * Restructuring Options
 *
 * Revision 1.5  2010/01/15 18:42:47  bfox
 * Solidified command line switches.
 * Oh, and the serialization broke.  DOH!
 *
 * Revision 1.4  2010/01/14 23:16:10  bfox
 * Boiled down SolutionTree and SolutionNode.  SolutionNode
 * should be extendable now such that GraphNode and
 * derivatives do not need to duplicate its functionality.
 *
 * Revision 1.3  2010/01/08 03:05:26  bfox
 * *** empty log message ***
 *
 */

package fox.brian.rubiks.apps.walker;
import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.Option;

import java.util.TreeSet;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import fox.brian.rubiks.apps.visualization.GenerateRadialPDF.CurrentClassGetter;
import fox.brian.rubiks.model.cube.*;
import fox.brian.rubiks.model.stateful.experimental.SymmetryPath;
import fox.brian.rubiks.model.stateful.tree.Report;
import fox.brian.rubiks.model.stateful.tree.SolutionTree;
import fox.brian.util.PerformanceTimer;



public class SimpleWalker {

	private SolutionTree tree;

	private static int parms = 0;
	private static String outDir    = null; 
	private static int maxlevel     = Integer.MAX_VALUE;
	private static int minlevel     = 0;
	private static Boolean verbose  = null;
	private static Boolean spatial  = null;
	private static Boolean reverse  = null;
	private static Boolean colormap = null;

	
	public SimpleWalker(int size, int parms) {
		tree = new SolutionTree(Cube.createCube(size), parms);
	}

	
	public void process(int writeMin, int writeMax) {
		int step = 0;
		while (true) {
			if (!tree.advance())
				break;
			
			if (step++ > writeMax)
				break;
			
			PerformanceTimer timer = new PerformanceTimer();
			int nliving = 0;
			int ndead = 0;
			
			TreeSet<SymmetryPath> symmetry = tree.findSymmetry();
			int max = 0;
			for (SymmetryPath p : symmetry) 
				max = p.size() > max ? p.size() : max;
				
			int[] histogram = new int[max];
			for (SymmetryPath p : symmetry) {
				histogram[p.size()-1]++;
			}

			if (step >= minlevel && step <= maxlevel) {
				Report.printLevel(step, tree.countLeaves(), tree.size(), nliving, ndead, symmetry.size(), timer.millisecondsElapsed()/1000, histogram);
				String filename = 
						"rubiks_lvl_" + step  
						+ (spatial ? "_spatial" : "") 
						+ (colormap ? "_colormap" : "")
						+ (reverse ? "_reverse" : "")
						+ ".tree";
				try {
					FileOutputStream out = new FileOutputStream(outDir + filename);
					BufferedOutputStream buff = new BufferedOutputStream(out);
					DataOutputStream data = new DataOutputStream(buff);
					tree.writeTree(data);
					data.close();
					buff.close();
					out.close();
				} catch(IOException e) {
					System.err.println("Things not going as planned.");
					e.printStackTrace();
					System.out.printf("Failed.%n");
					System.out.flush();
				}
			}
		}
		
		
		Report.printFooter();


	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CmdLineParser parser = new CmdLineParser();
		Option optInFile   = parser.addStringOption('d',"outdir");
		Option optOutFile  = parser.addStringOption('l',"levels");
		Option optVerbose  = parser.addStringOption('v',"verbose");
		Option optSpatial  = parser.addBooleanOption("spatial");
		Option optColorMap = parser.addBooleanOption("colormap");
		Option optReverse  = parser.addBooleanOption("reverse");

		try {
			parser.parse(args);
		}
		catch (Exception e) {
			System.out.printf("Could not parse command line: %s%n%s%n", e.getMessage(), usage());
			System.exit(-1);
		}
		
		parms = 0;
		outDir  = (String)parser.getOptionValue(optInFile);
		verbose = (Boolean)parser.getOptionValue(optVerbose);
		if (verbose == null)
			verbose = false;

		String levels  = (String)parser.getOptionValue(optOutFile);
		if (levels != null) {
			try {
				String[] tokens = levels.split(":");
				if (tokens.length == 0)
					throw new Exception("At least one level must be listed.");
				else if (tokens.length == 1)
					minlevel = Integer.parseInt(tokens[0]);
				else if (tokens.length == 2) {
					minlevel = Integer.parseInt(tokens[0]);
					maxlevel = Integer.parseInt(tokens[1]);
				}
				else 
					throw new Exception("Too many levels were listed.");
			}
			catch (Exception e) {
				System.out.printf("Could not parse levels: %s%n%s%n", levels, usage());
				System.exit(-1);
			}
		}

		spatial = parser.getOptionValue(optSpatial) != null;
		colormap = parser.getOptionValue(optColorMap) != null;
		reverse = parser.getOptionValue(optReverse) != null;

		if (spatial)
			parms += Cube.USE_SPATIAL;
		if (colormap)
			parms += Cube.USE_COLORMAP;
		if (reverse) 
			parms += Cube.USE_REVERSE;

		
		if (verbose) 
			System.out.println("Verbose output.");

		File dir = new File(System.getProperty("user.dir")); 
		if (outDir != null) {
			File ndir = new File(outDir);
			if (ndir.exists() && ndir.isDirectory())
				dir = ndir;
			else {
				System.out.println("Output directory does not exist: " + outDir);
				System.exit(-1);
			}
		}
		
		Report.printHeader(parms);
		new SimpleWalker(2,parms).process(minlevel, maxlevel);
	
	}

	public static String usage() {
		String pname = new CurrentClassGetter().getClassName();
		return String.format("Usage: %s [-d <output directory>] [-l <start>[:<end>]] [--spatial] [--colormap] [--reverse]%n", pname);
	}
}
