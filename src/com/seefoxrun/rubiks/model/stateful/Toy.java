/*
 * $Id: Toy.java,v 1.1 2010/01/01 08:28:49 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Toy.java,v $
 * Revision 1.1  2010/01/01 08:28:49  bfox
 * Rearranged packages.
 *
 * Revision 1.4  2009/08/15 16:01:31  bfox
 * Removed Twist constructor calls in favor of createTwist method.
 *
 * Revision 1.3  2009/06/08 16:26:49  bfox
 * *** empty log message ***
 *
 * Revision 1.2  2009/06/02 22:46:21  bfox
 * Took a new direction with serialization.
 *
 * The Java interface tries to keep too many references going at once which gobbles memory.  Since a WalkingTree is acyclic, these redundant references just get in the way, causes disk thrashing, and eventually exhausts all memory.
 *
 * It looks like the best approach is to implement serialization by hand for node or node related classes.  This has produced very encouraging results.  But it's unfortunately less clean as well.
 *
 * Revision 1.1  2009/05/07 18:49:13  bfox
 * *** empty log message ***
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

package com.seefoxrun.rubiks.model.stateful;

import java.util.ArrayList;
import java.util.TreeSet;

import com.seefoxrun.rubiks.model.cube.*;
import com.seefoxrun.rubiks.model.operators.*;

public class Toy {
	
	private Cube toy;
	private Cube origin;
	private ArrayList<Twist> moves;
	private ArrayList<Twist> undos;
	
	
	public Toy(int size) {
		toy = Cube.createCube(size);
		origin = toy;
		moves = new ArrayList<Twist>();
		undos = new ArrayList<Twist>();
	}

	
	

	@Override
	public String toString() {
		
		TreeSet<Cube> unique = new TreeSet<Cube>();
		Cube reenact = origin;
		unique.add(reenact);
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s%n",toy));
		sb.append(String.format("==HISTORY==(MAP=%s)===============%n", toy.getFaceMap()));
		sb.append(String.format("%3d. %s%n", 0, origin));
		for (int i = 0; i < moves.size(); i++) {
			Twist o = moves.get(i);
			reenact = o.apply(reenact);
			sb.append(String.format("%3d. %s %s%n", i + 1, o, unique.contains(reenact) ? "LOOP" : ""));			
			unique.add(reenact);
		}
		if (undos.size() > 0) 
			sb.append(String.format("--UNDO STACK-----------------------%n"));
		for (int i = 0; i < undos.size(); i++) {
			Twist o = undos.get(i);
			sb.append(String.format("%3d. %s%n", i, o));			
		}

		sb.append(String.format("===================================%n"));
		return sb.toString();
	}


	public void reset() {
		toy = origin;
		moves = new ArrayList<Twist>();
		undos = new ArrayList<Twist>();
	}

	public void resize(int size) {
		toy = Cube.createCube(size);
		origin = toy;
		moves = new ArrayList<Twist>();
		undos = new ArrayList<Twist>();
	}

	public String twist(Face f, Dir d, int s) {
		// TODO: Input validation
		toy = toy.twist(f, d, s);
		moves.add(Twist.createTwist(f, d, s));
		return null;
	}

}
