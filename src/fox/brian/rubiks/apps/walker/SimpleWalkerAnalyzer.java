/*
 * $Id: SimpleWalkerAnalyzer.java,v 1.6 2010/05/28 22:31:56 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: SimpleWalkerAnalyzer.java,v $
 * Revision 1.6  2010/05/28 22:31:56  bfox
 * Code cleanup - removed unnecessary imports.
 *
 * Revision 1.5  2010/02/08 18:21:14  bfox
 * *** empty log message ***
 *
 * Revision 1.4  2010/02/01 16:43:54  bfox
 * *** empty log message ***
 *
 * Revision 1.3  2010/01/15 18:42:47  bfox
 * Solidified command line switches.
 * Oh, and the serialization broke.  DOH!
 *
 * Revision 1.2  2010/01/14 23:16:10  bfox
 * Boiled down SolutionTree and SolutionNode.  SolutionNode
 * should be extendable now such that GraphNode and
 * derivatives do not need to duplicate its functionality.
 *
 * Revision 1.1  2010/01/01 08:28:47  bfox
 * Rearranged packages.
 *
 * Revision 1.10  2009/12/31 02:45:19  bfox
 * *** empty log message ***
 *
 * Revision 1.9  2009/08/25 05:26:16  bfox
 * Command line parsing changes.
 * Solution flag changes.
 *
 * Revision 1.8  2009/08/15 16:18:37  bfox
 * *** empty log message ***
 *
 * Revision 1.7  2009/08/13 18:10:38  bfox
 * Renamed WalkingTree -> SolutionTree.
 *
 * Revision 1.6  2009/07/16 23:40:15  bfox
 * *** empty log message ***
 *
 * Revision 1.5  2009/07/13 15:21:38  bfox
 * *** empty log message ***
 *
 * Revision 1.4  2009/06/10 20:09:33  bfox
 * Reworked to use command line arguments.
 *
 * Revision 1.3  2009/06/10 00:19:12  bfox
 * Did away with traditional serialization.  Data IO is now completely manual.
 *
 * Revision 1.2  2009/06/08 16:26:49  bfox
 * *** empty log message ***
 *
 * Revision 1.1  2009/06/02 22:46:21  bfox
 * Took a new direction with serialization.
 *
 * The Java interface tries to keep too many references going at once which gobbles memory.  Since a SolutionTree is acyclic, these redundant references just get in the way, causes disk thrashing, and eventually exhausts all memory.
 *
 * It looks like the best approach is to implement serialization by hand for node or node related classes.  This has produced very encouraging results.  But it's unfortunately less clean as well.
 *
 * Revision 1.25  2009/05/15 20:20:00  bfox
 * Modified Cube to be stateless.  State is now carried in other classes.
 *
 * Revision 1.24  2009/04/20 23:37:11  bfox
 * *** empty log message ***
 *
 * Revision 1.23  2009/04/09 16:26:58  bfox
 * Cosmetic changes.
 *
 * 
 * Revision 1.22  2009/03/31 15:50:32  bfox
 * Rewrote related cube logic for multiple passes.  Deleted
 * getSimilarCubes method.
 * Wrote new junit tests.
 *
 * Revision 1.21  2009/03/26 21:21:55  bfox
 * Added some book keeping text per run.  Should be easier to
 * identify runtime parameters.
 *
 * Revision 1.20  2009/03/25 23:16:56  bfox
 * Still working on reverse.  Levels seem fine.  Count it off however.
 *
 * Revision 1.19  2009/03/25 20:20:53  bfox
 * Potential "reverse" solution.
 *
 * Revision 1.18  2009/03/23 05:14:06  bfox
 * Another stab at incorporating reverse in similar cubes.
 *
 * Revision 1.17  2009/03/18 23:07:30  bfox
 * Broken spatial cube routine.  Searching for clues in previous commits.
 *
 * Revision 1.16  2009/03/17 16:18:30  bfox
 * Still going for that FTR to FRT transition for some reason.
 *
 * Revision 1.15  2009/03/12 19:21:08  bfox
 * Still going for that FTR to FRT transition for some reason.
 *
 * Revision 1.13  2009/03/03 22:50:04  bfox
 * Performed a few new runs.
 *
 * Revision 1.12  2009/03/03 19:33:09  bfox
 * Added reverse.
 *
 * Revision 1.6  2008/12/10 17:49:17  bfox
 * Added a more intuitive way to handle cube equivalency flags.
 *
 * Revision 1.4  2008/10/27 16:30:28  bfox
 * Cube is now used as an object factory.
 *
 * Revision 1.3  2008/10/27 15:52:10  bfox
 * Added copyright and cvs logging.
 *
 */

package fox.brian.rubiks.apps.walker;

import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.Option;
import jargs.gnu.CmdLineParser.OptionException;

import java.io.*;
import java.util.TreeMap;


import fox.brian.rubiks.model.cube.Cube;
import fox.brian.rubiks.model.cube.Dir;
import fox.brian.rubiks.model.cube.Face;
import fox.brian.rubiks.model.operators.Twist;
import fox.brian.rubiks.model.stateful.tree.SolutionNode;
import fox.brian.rubiks.model.stateful.tree.SolutionTree;
import fox.brian.util.*;


public class SimpleWalkerAnalyzer {
	
	public static void main(String[] args) {
		
		CmdLineParser parser = new CmdLineParser();
		Option optInFile  = parser.addStringOption('i',"infile");

		try {
			parser.parse(args);
		}
		catch (OptionException e) {
			printUsage(System.out);
			System.exit(-1);
		}
		
		String inFile = (String)parser.getOptionValue(optInFile);
		SolutionTree tree = null;
		
		PerformanceTimer timer = new PerformanceTimer();
		System.out.println("De-serializing tree.");
		try {
			FileInputStream in = new FileInputStream(inFile);
			BufferedInputStream buff = new BufferedInputStream(in);
			DataInputStream data = new DataInputStream(buff);
			tree = new SolutionTree(data);
			data.close();
			buff.close();
			in.close();
		} catch(Exception e) {
			System.err.println("Things not going as planned.");
			e.printStackTrace();
		} 
		System.out.printf("Done.%n");
		System.out.printf("Finished in %.3f seconds.%n%n", timer.millisecondsElapsed()/1000);

		/*
		timer.reset();
		System.out.println("Re-serializing tree.");
		try {
			FileOutputStream out = new FileOutputStream(file.value()+".redone");
			BufferedOutputStream buff = new BufferedOutputStream(out);
			DataOutputStream data = new DataOutputStream(buff);
			tree.writeTree(data);
			data.close();
			buff.close();
			out.close();
		} catch(IOException e) {
			System.err.println("Things not going as planned.");
			e.printStackTrace();
		} 
		timer.stop();
		System.out.printf("Done.%n", timer.secondsElapsed());
		System.out.printf("Finished in %.3f seconds.%n%n", timer.secondsElapsed());

		*/
		//////////////////////////////////////////////
		//////////////////////////////////////////////
		
		timer.reset();
		System.out.println("Height Histogram: ");
		int hist1[] = tree.histogramNodesPerHeight();
		for (int x=0; x < hist1.length; x++)
			System.out.printf("%2d %8d%n",x,hist1[x]);
		System.out.printf("%n%nDone.%n", timer.millisecondsElapsed()/1000);
		System.out.printf("Finished in %.3f seconds.%n%n", timer.millisecondsElapsed()/1000);

		
		timer.reset();
		System.out.println("Twist Histogram: ");
		TreeMap<Twist,Integer> hist2 = tree.histogramTwist();
		for (Twist t : hist2.keySet())
			System.out.printf("%10s %8d%n",t,hist2.get(t));
		System.out.printf("%n%nDone.%n", timer.millisecondsElapsed()/1000);
		System.out.printf("Finished in %.3f seconds.%n%n", timer.millisecondsElapsed()/1000);

		
		
		System.out.printf("%n%nDone.%n", timer.millisecondsElapsed()/1000);
		System.out.printf("Finished in %.3f seconds.%n%n", timer.millisecondsElapsed()/1000);
		
		SolutionNode n = tree.getNode();
		int cubesize = n.source().size();
		
		for (Face f : Face.values()) {
			for (Dir d : Dir.values()) {
				for (int i=0; i < cubesize; i++) {
					Twist t = Twist.createTwist(f, d, i);
					Cube c = n.getChild(t).source();
					if (c != null)
						System.out.println(t + "\n" + c + "\n\n");
				}
			}
		}

	}

	public static class CurrentClassGetter extends SecurityManager {
		public String getClassName() {
			return getClassContext()[1].getSimpleName();
		}
	}

	public static void printUsage(PrintStream out) {
		String pname = new CurrentClassGetter().getClassName();
		out.printf("Usage: %s -f <file> [-l <int>] [-v]%n", pname);
	}

}