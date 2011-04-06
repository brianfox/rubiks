/*
 * $Id: Path.java,v 1.2 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Path.java,v $
 * Revision 1.2  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package fox.brian.rubiks.model.stateful.experimental;

import java.util.ArrayList;
import java.util.Iterator;

import fox.brian.rubiks.model.cube.Cube;
import fox.brian.rubiks.model.operators.Twist;

public class Path implements Cloneable, Iterable<Twist>, Comparable<Path> {
	private Cube source;
	private ArrayList<Twist> path;
	
	public Path(Cube source) {
		this.source = source;
		path = new ArrayList<Twist>();
	}
	
	public void add(Twist op) {
		path.add(op);
	}

	public Twist get(int index) {
		return path.get(index);
	}
	
	public int size() {
		return path.size();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Twist t : path) {
			sb.append(String.format("%s ", t));
		}
		return sb.toString();
	}
	
	@Override 
	public Path clone() {
		Path ret = new Path(source);
		for (Twist op : path)
			ret.path.add(op);
		return ret;
	}

	@Override
	public Iterator<Twist> iterator() {
		return path.iterator();
	}

	@Override
	public int compareTo(Path o) {
		/* first compare source cubes */
		int srcdiff = source.compareTo(o.source);
		if (srcdiff != 0)
			return srcdiff;

		/* now compare path sizes */
		int sizediff = path.size() - o.path.size();
		if (sizediff != 0)
			return sizediff;
		
		/* finally, all the path entries */
		for (int i=0; i < path.size(); i++) {
			int tdiff = path.get(i).compareTo(o.path.get(i));
			if (tdiff != 0)
				return tdiff;
		}
			
		return 0;
	}


}
