package com.seefoxrun.rubiks.apps.walker.original;
import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.Option;

import java.util.TreeSet;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.seefoxrun.rubiks.apps.visualization.GenerateRadialPDF.CurrentClassGetter;
import com.seefoxrun.rubiks.model.cube.*;
import com.seefoxrun.rubiks.model.stateful.experimental.SymmetryPath;
import com.seefoxrun.rubiks.model.stateful.tree.original.Report;
import com.seefoxrun.rubiks.model.stateful.tree.original.SolutionTree;
import com.seefoxrun.util.PerformanceTimer;



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

		/*
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
		*/
		
		Report.printHeader(parms);
		new SimpleWalker(2,parms).process(minlevel, maxlevel);
	
	}

	public static String usage() {
		String pname = new CurrentClassGetter().getClassName();
		return String.format("Usage: %s [-d <output directory>] [-l <start>[:<end>]] [--spatial] [--colormap] [--reverse]%n", pname);
	}
}
