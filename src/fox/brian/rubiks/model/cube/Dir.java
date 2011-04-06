/*
 * $Id: Dir.java,v 1.1 2010/01/01 08:28:44 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Dir.java,v $
 * Revision 1.1  2010/01/01 08:28:44  bfox
 * Rearranged packages.
 *
 * Revision 1.4  2009/03/18 23:07:29  bfox
 * Broken spatial cube routine.  Searching for clues in previous commits.
 *
 * Revision 1.3  2009/03/02 19:13:03  bfox
 * *** empty log message ***
 *
 * Revision 1.2  2008/10/27 15:50:39  bfox
 * Added copyright and cvs logging.
 *
 */

package fox.brian.rubiks.model.cube;

public enum Dir {
	CW  ("clockwise",        "CW",  (byte)0),
	CCW ("counterclockwise", "CCW", (byte)1);

	private String description;
	private String abbreviation;
	private byte byteval;
	
	private Dir(String description, String abbreviation, byte byteval) {
		this.description = description;
		this.abbreviation = abbreviation;
		this.byteval = byteval;
	}
	
	public Dir getOpposite() {
		switch (byteval) {
			case 0: return CCW;
			case 1: return CW;
		}
		return CW;
	}
	
	public static Dir getDir(int d) {
		switch (d) {
			case 0: return CW;
			case 1: return CCW;
		}
		return CW;
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