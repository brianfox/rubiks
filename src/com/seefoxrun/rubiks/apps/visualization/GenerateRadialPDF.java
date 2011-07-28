package com.seefoxrun.rubiks.apps.visualization;

import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.Option;

import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import com.itextpdf.text.DocumentException;

import com.seefoxrun.options.OptionException;
import com.seefoxrun.rubiks.model.stateful.tree.SolutionTree;
import com.seefoxrun.rubiks.serialization.SerializationException;
import com.seefoxrun.rubiks.visualization.itext.graphs.RadialGraph;
import com.seefoxrun.rubiks.visualization.itext.options.Defaults;
import com.seefoxrun.rubiks.visualization.itext.options.GraphicalOptionManager;
import com.seefoxrun.visualization.Document;
import com.seefoxrun.visualization.measurement.Units;
import com.seefoxrun.visualization.medium.Border;
import com.seefoxrun.visualization.medium.Page;

public class GenerateRadialPDF {

	public static void main(String[] args) throws IOException {
		
		CmdLineParser parser = new CmdLineParser();
		Option optInFile     = parser.addStringOption ('i',"infile" );
		Option optOutFile    = parser.addStringOption ('o',"outfile");
		Option optConfigFile = parser.addStringOption ('c',"config" );
		Option optVerbose    = parser.addBooleanOption('v',"verbose");
		Option optLaunch     = parser.addBooleanOption('l',"launch" );

		try {
			parser.parse(args);
		}
		catch (Exception e) {
			System.out.println(usage());
			System.exit(-1);
		}

		
		String infile     = (String) parser.getOptionValue(optInFile);
		String outfile    = (String) parser.getOptionValue(optOutFile);
		String configFile = (String) parser.getOptionValue(optConfigFile);
		Boolean verbose   = (Boolean)parser.getOptionValue(optVerbose);
		Boolean launch    = (Boolean)parser.getOptionValue(optLaunch);
		
		if (launch == null)
			launch = false;
		if (verbose == null)
			verbose = false;
		
		if (infile == null) {
			System.out.println(usage());
			System.exit(-1);
		}

		if (verbose) 
			System.out.println("Verbose output.");
		
		if (outfile == null || outfile.trim().length() == 0)
			outfile = infile + ".pdf";
		
		GraphicalOptionManager om = null;
		if (configFile == null)
			om = new GraphicalOptionManager(Defaults.getList());
		else
			try {
				om = new GraphicalOptionManager(configFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			} catch (OptionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
			

		Border border = new Border(
				1, 
				1, 
				1, 
				1, 
				Units.INCHES
		);
		
		Page page = new Page(
				om.getValue(Defaults.RADIAL_SCOPE, Defaults.PAPER_WIDTH), 
				om.getValue(Defaults.RADIAL_SCOPE, Defaults.PAPER_HEIGHT), 
				border
		);
		Document doc = null;
		try {
			doc = new Document(outfile, page);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		} catch (DocumentException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		SolutionTree tree = openTree(infile);
		RadialGraph graph = new RadialGraph(tree, doc, om);
		graph.draw();
		
		doc.close();
		if (launch)
			launchDocument(outfile);
	}

	private static SolutionTree openTree(String filename) {
		SolutionTree tree = null;
		try {
			tree = new SolutionTree(filename);
		}
		catch (SerializationException e) {
			System.err.printf("The input file could not be deserialized.  Is it corrupt?  %s%n",filename);
			System.exit(-1);
		}
		catch (IOException e) {
			System.err.printf("The input file could not be opened: %s%n",filename);
			System.exit(-1);
		}
		return tree;
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

	
	public static class CurrentClassGetter extends SecurityManager {
		public String getClassName() {
			return getClassContext()[1].getSimpleName();
		}
	}

	
	public static String usage() {
		String pname = new CurrentClassGetter().getClassName();
		return String.format("Usage: %s -i <input file> [-o <output file>] [-v] [-l]%n", pname);
	}

}
