/*
 * $Id: Face.java,v 1.1 2010/01/01 08:28:44 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Face.java,v $
 * Revision 1.1  2010/01/01 08:28:44  bfox
 * Rearranged packages.
 *
 * Revision 1.5  2009/03/18 23:07:29  bfox
 * Broken spatial cube routine.  Searching for clues in previous commits.
 *
 * Revision 1.4  2009/03/11 21:07:29  bfox
 * Changing FTR to FRT
 *
 * Revision 1.3  2009/03/02 19:13:03  bfox
 * *** empty log message ***
 *
 * Revision 1.2  2008/10/27 15:50:39  bfox
 * Added copyright and cvs logging.
 *
 */

package fox.brian.rubiks.model.cube;

public enum Face {
	FRONT ("front", "F", (byte)0),
	RIGHT ("right", "R", (byte)1),
	TOP   ("top",   "T", (byte)2),
	BOTTOM("bottom","B", (byte)3),
	LEFT  ("left",  "L", (byte)4),
	BACK  ("back",  "K", (byte)5);
	
	private String description;
	private String abbreviation;
	private byte byteval;
	
	private Face(String description, String abbreviation, byte byteval) {
		this.description = description;
		this.abbreviation = abbreviation;
		this.byteval = byteval;
	}
	
	public Face getOpposite() {
		switch (byteval) {
			case 0: return BACK;
			case 1: return LEFT;
			case 2: return BOTTOM;
			case 3: return TOP;
			case 4: return RIGHT;
			case 5: return FRONT;
		}
		return FRONT;
	}
	
	public static Face getFace(int f) {
		switch (f) {
			case 0: return FRONT;
			case 1: return RIGHT;
			case 2: return TOP;
			case 3: return BOTTOM;
			case 4: return LEFT;
			case 5: return BACK;
		}
		return FRONT;
	}
	
	public byte val() {
		return byteval;
	}
	
	public String getDescription() {
		return description;
	}

	@Override public String toString() {
		return abbreviation;
	}
}


