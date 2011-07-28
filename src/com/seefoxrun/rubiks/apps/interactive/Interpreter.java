/*
 * $Id: Interpreter.java,v 1.1 2010/01/01 08:28:48 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Interpreter.java,v $
 * Revision 1.1  2010/01/01 08:28:48  bfox
 * Rearranged packages.
 *
 * Revision 1.1  2009/05/08 14:43:32  bfox
 * *** empty log message ***
 *
 * Revision 1.1  2009/05/07 18:49:00  bfox
 * Commands are now all reflective.
 *
 * Revision 1.7  2009/05/06 01:47:10  bfox
 * Preparation before reworking the "state" concept of the cube.
 *
 * Revision 1.6  2009/04/29 23:13:51  bfox
 * Undo mechanism is solid.  Some loose ends still need to be tied - mostly
 * resetting the stack after various operations and fixing the Toy
 * class to properly handle numbers in the undo command.
 *
 * Revision 1.5  2009/04/28 15:50:21  bfox
 * Some refactoring.
 *
 * Revision 1.4  2009/04/20 23:37:11  bfox
 * *** empty log message ***
 *
 * Revision 1.3  2009/04/15 17:25:44  bfox
 * *** empty log message ***
 *
 * Revision 1.2  2009/04/10 00:10:18  bfox
 * Did a lot of work on solving cubes by reversing.  Not stable.
 *
 * Revision 1.1  2009/04/09 16:26:58  bfox
 * Cosmetic changes.
 *
 * Revision 1.17  2009/03/31 15:50:32  bfox
 * Rewrote related cube logic for multiple passes.  Deleted
 * getSimilarCubes method.
 * Wrote new junit tests.
 *
 * Revision 1.16  2009/03/25 20:20:53  bfox
 * Potential "reverse" solution.
 *
 * Revision 1.15  2009/03/23 23:48:04  bfox
 * Working towards reverse cube.  Currently, similar cubes
 * is a non-closed set.
 *
 * Revision 1.14  2009/03/23 05:14:06  bfox
 * Another stab at incorporating reverse in similar cubes.
 *
 * Revision 1.13  2009/03/21 21:56:02  bfox
 * *** empty log message ***
 *
 * Revision 1.12  2009/03/18 23:07:30  bfox
 * Broken spatial cube routine.  Searching for clues in previous commits.
 *
 * Revision 1.11  2009/03/17 16:18:30  bfox
 * Still going for that FTR to FRT transition for some reason.
 *
 * Revision 1.10  2009/03/03 19:32:55  bfox
 * Implemented simple reverse.
 *
 * Revision 1.9  2009/03/02 22:03:20  bfox
 * Added single spatial and colormap moves.
 *
 * Revision 1.8  2009/03/02 19:13:03  bfox
 * *** empty log message ***
 *
 * Revision 1.7  2009/02/26 22:36:58  bfox
 * *** empty log message ***
 *
 * Revision 1.6  2009/02/09 23:29:42  bfox
 * *** empty log message ***
 *
 * Revision 1.5  2008/10/29 22:04:22  bfox
 * Made the command parsing a bit less draconian.
 *
 * Revision 1.4  2008/10/28 14:48:34  bfox
 * Added a cube size command.
 * Reworked the code behind some commands.
 *
 * Revision 1.3  2008/10/27 16:30:28  bfox
 * Cube is now used as an object factory.
 *
 * Revision 1.2  2008/10/27 15:52:10  bfox
 * Added copyright and cvs logging.
 *
 */

package com.seefoxrun.rubiks.apps.interactive;

import java.io.*;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

import com.seefoxrun.rubiks.model.cube.*;
import com.seefoxrun.rubiks.model.stateful.Toy;

public class Interpreter {

	private static HashMap<String,String> abbreviations;
	static StringBuilder abbr = new StringBuilder();
	static StringBuilder help = new StringBuilder();
	private Toy toy;
		
	public Interpreter(int size) {
		toy = new Toy(size);
	}
	
	
	public static class InterpreterException extends Exception {
		private static final long serialVersionUID = 1L;
		private String message;
		
		public InterpreterException(String message) {
			super(message);
			this.message = message;
		}
		
		@Override public String toString() {
			return message;
		}
	}
	
	
	@Override
	public String toString() {
		return toy.toString();
	}


	
	public void abbr(String command, PrintStream out) throws InterpreterException {
		System.out.println(abbr.toString());
	}

	public String clean(String command, PrintStream out) throws InterpreterException {
		return null;
	}

	public void getRelated(String command, PrintStream out) throws InterpreterException {
	}

	public void help(String command, PrintStream out) throws InterpreterException {
		System.out.println(abbr.toString());
	}

	
	public void print(String command, PrintStream out) throws InterpreterException {
		out.println(toy);
	}

	public void quit(String command, PrintStream out) throws InterpreterException {
		out.println("Goodbye.");
		System.exit(0);
	}

	public void redo(String command, PrintStream out) throws InterpreterException {
	}

	public void reset(String command, PrintStream out) throws InterpreterException {
		toy.reset();
		out.println(toy);
	}
	
	public void resize(String command, PrintStream out) throws InterpreterException {
		try {
			int size = Integer.parseInt(command.substring(6));
			if (Cube.isSupportedSize(size)) 
				toy.resize(size);
			else
				throw new InterpreterException("Unsupported cube size.");
		}
		catch (NumberFormatException e) {
			throw new InterpreterException("Cube size must be an integer.");
		}
		out.println(toy);
	}
		
	public void showRelated(String command, PrintStream out) throws InterpreterException {
		/*
		int parms = 0;
		int index = 0;
	
		parms += (input.indexOf("spatial")  == -1) ? 0 : Cube.USE_SPATIAL;
		parms += (input.indexOf("colorMap") == -1) ? 0 : Cube.USE_COLORMAP;
		parms += (input.indexOf("reverse")  == -1) ? 0 : Cube.USE_REVERSE;
		
		index = Integer.parseInt(input.replaceAll("[^0-9]", ""));
		TreeSet<Cube> set = toy.getRelatedCubes(parms);
		if (index >= set.size()) 
			System.err.printf("Index %d out of bounds.  Valid values are [%d:%d]%n", index, 0, set.size());
		else
			toy = (Cube)(set.toArray()[index]);
		*/
	}

	public void solve(String command, PrintStream out) throws InterpreterException {
	}

	public void twist(String command, PrintStream out) throws InterpreterException {
		Face f = null;
		Dir d = null;
		int slice = 0;
		String sliceStr = "";

		switch (command.charAt(0)) {
			case 'f': f = Face.FRONT; break;
			case 'r': f = Face.RIGHT; break;
			case 't': f = Face.TOP; break;
			case 'k': f = Face.BACK; break;
			case 'l': f = Face.LEFT; break;
			case 'b': f = Face.BOTTOM; break;
		}
		if (command.substring(1,3).compareTo("cw") == 0) {
			d = Dir.CW;
			sliceStr = command.substring(3);
		}
		else {
			d = Dir.CCW;
			sliceStr = command.substring(4);
		}
		if (sliceStr.length() != 0)
			slice = Integer.parseInt(sliceStr);
		else
			slice = 0;
		toy.twist(f, d, slice);
		out.println(toy);
	}

	public void undo(String command, PrintStream out) throws InterpreterException {
		// toy = toy.undo(n); 
	}


	
	public void interpret(String command, PrintStream out) throws InterpreterException {
		command = cleanInput(command);
		for (PatternMethod pm : commands) {
			if (pm.matches(command)) {
				pm.invoke(this, command, out);
					
			}
		}
	}

	


	
	
	private static String cleanInput(String s) {
		
		/* replace abbreviations with long hand */
		for (String key : abbreviations.keySet()) 
			s = s.replaceAll(key, abbreviations.get(key));
		
		/* whitespace only makes parsing more difficult */
		s = s.replaceAll("\\s*", "");
		
		/* commands are case insensitive */
		return s.toLowerCase();

	}

	
	
	// all the strictly recognized interpreter commands
	private static PatternMethod[] commands = new PatternMethod[] {
		new PatternMethod(Pattern.compile("abbr"),  "abbr"),
		new PatternMethod(Pattern.compile("help"),  "help"),
		new PatternMethod(Pattern.compile("print"), "print"),
		new PatternMethod(Pattern.compile("quit"),  "quit"),
		new PatternMethod(Pattern.compile("resize[0-9]*"),"resize"),
		new PatternMethod(Pattern.compile("solve"),"solve"),
		new PatternMethod(Pattern.compile("clean"),"clean"),
		new PatternMethod(Pattern.compile("reset"),"reset"),
		new PatternMethod(Pattern.compile("[frtklb]\\s*(cw|ccw)\\s*[0-9]*"),"twist"),
		new PatternMethod(Pattern.compile("(\\s*spatial\\s*|\\s*colormap\\s*|\\s*reverse\\s*)+"),"showRelated"),
		new PatternMethod(Pattern.compile("(\\s*spatial\\s*|\\s*colormap\\s*|\\s*reverse\\s*)+\\s*[0-9]+"),"getRelated"),
		new PatternMethod(Pattern.compile("(\\s*spatial\\s*|\\s*colormap\\s*|\\s*reverse\\s*)+\\s*[0-9]+"),"getRelated"),
		new PatternMethod(Pattern.compile("undo"),"undo"),
		new PatternMethod(Pattern.compile("redo"),"redo")
	};
	
	// all the abbreviated interpreter commands
	static {
		abbreviations = new HashMap<String,String>();
		abbreviations.put("abbreviations",       "abbr"  );	
		abbreviations.put("counterclockwise",    "ccw"   );	
		abbreviations.put("counter-clockwise",   "ccw"   );	
		abbreviations.put("counter\\sclockwise", "ccw"   );	
		abbreviations.put("clockwise",           "cw"    );	
		abbreviations.put("front",               "f"     );	
		abbreviations.put("right",               "r"     );	
		abbreviations.put("top",                 "t"     );	
		abbreviations.put("bottom",              "b"     );	
		abbreviations.put("left",                "l"     );	
		abbreviations.put("back",                "k"     );	
		abbreviations.put("end",                 "quit"  );	
		abbreviations.put("bye",                 "quit"  );	
		abbreviations.put("goodbye",             "quit"  );	
		abbreviations.put("adios",               "quit"  );	
		abbreviations.put("show",                "print" );	
		abbreviations.put("display",             "print" );	
		abbreviations.put("draw",                "print" );	
		abbreviations.put("redraw",              "print" );
		TreeSet<String> sorted = new TreeSet<String>();
		sorted.addAll(abbreviations.keySet());
		for (String key : sorted) {
			abbr.append(String.format("%-20s : %-20s%n", key, abbreviations.get(key)));
		}	
	}

	// and a long winded help
	static {
		StringBuilder temp = new StringBuilder();
		temp.append("------------------------------------------------------------\n");
		temp.append("HELP\n");
		temp.append("------------------------------------------------------------\n");
		temp.append("\n");
		temp.append("This program simulates a Rubik's Cube toy.  Various commands allow the toy ");
		temp.append("to be twisted, swapped for a related cube, inspected, and resized.\n\n");

		temp.append("--------------\n");
		temp.append("COMMAND BASICS\n");
		temp.append("--------------\n");
		temp.append(
			"All commands are case-insensitive and ignore whitespace.  Various abbreviations " +
			"can be substituted in most commands.  Type ABBREVIATIONS for a list.\n\n"); 
			
		temp.append("--------\n");
		temp.append("TWISTING\n");
		temp.append("--------\n\n");

		temp.append(
			"The toy cube is twisted when a face, a direction, and an optional slice are entered. " +
			"Valid faces: (front, right, top, bottom, left, back) or (f, r, t, b, l, k).  Valid " +
            "directions: (clockwise, counterclockwise) or (cw,ccw).  Slice is an optional numeric " +
            "value that allows middle twists for cubes larger than 2x2.  1 (default) will twist " +
            "the target face.  N (on an NxN cube) will twist the opposite face.  Something in the " +
            "middle will twist the middle sections of the cube.\n\n" +
            "Examples:\n" +
            "(All the following will twist the cube clockwise on its left face.)\n" +
            "LEFT CLOCKWISE          left clockwise 1\n" +  
            "LEFTCLOCKWISE           leftclockwise 1 \n" + 
            "LCLOCKWISE              lcw 1           \n" +  
            "LCW                     lcw1            \n\n"  
		);

		temp.append("-------------\n");
		temp.append("RELATED CUBES\n");
		temp.append("-------------\n\n");

		temp.append(
			"Related cubes are all those cubes related to the current cube using three relationship "+ 
			"algorithms: spatial, colormap, and reverse.\n\n" +
			"Spatial cubes are discovered by rotating the entire cube in space.  This always " +
			"generates a family of 24 cubes (including the original).\n\n" + 
			"ColorMap cubes are discovered " +
			"by \"sticker-swapping.\"  It answers the question of \"What if yellow were instead blue?\" " + 
            "This generates between 1 and 24 cubes (including the original).\n\n" + 
            "Reverse cubes are " +
            "discovered by re-twisting the cube into various reverse permutations.\n\n" +
			"DISPLAYING RELATED CUBES:\n" +
			"Display the related cubes by typing any or all relationship strategies.\n" +
			"Examples:\n" +
			"spatial\n" +
			"spatial colormap\n" +
			"etc.\n\n" +
			"WAPPING FOR A RELATED CUBES:\n" +
			"The current cube can be swapped for a related cube by typing any or all relationship strategies " +
			"and the desired index of the resulting set.\n\n" +
			"Examples:\n" +
            "spatial 1\n" +
            "spatial colormap 5\n" +
			"etc.\n\n" 
		);
		temp.append("\n\n");

		int count = 0;
		int i = 0;
		while (i < temp.length()) {
			char c = temp.charAt(i++);
			if (c == '\n' || c == '\r') {
				help.append(c);
				count = 0;
				continue;
			}
			if (Character.isWhitespace(c) && count == 0) 
				continue;
			
			if (count > 70 & Character.isWhitespace(c)) {
				help.append(String.format("%n"));
				count = 0;
				continue;
			}
			help.append(c);
			count++;
		}
	}
}
