package com.seefoxrun.rubiks.apps.walker.original;

import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.Option;
import jargs.gnu.CmdLineParser.OptionException;

import java.io.*;
import java.util.TreeMap;


import com.seefoxrun.rubiks.model.cube.Cube;
import com.seefoxrun.rubiks.model.cube.Dir;
import com.seefoxrun.rubiks.model.cube.Face;
import com.seefoxrun.rubiks.model.operators.Twist;
import com.seefoxrun.rubiks.model.stateful.tree.original.SolutionNode;
import com.seefoxrun.rubiks.model.stateful.tree.original.SolutionTree;
import com.seefoxrun.util.*;


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
