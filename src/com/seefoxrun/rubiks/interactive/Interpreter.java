package com.seefoxrun.rubiks.interactive;

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
