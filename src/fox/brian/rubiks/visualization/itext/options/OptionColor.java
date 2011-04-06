/*
 * $Id: OptionColor.java,v 1.3 2010/05/28 22:31:56 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: OptionColor.java,v $
 * Revision 1.3  2010/05/28 22:31:56  bfox
 * Code cleanup - removed unnecessary imports.
 *
 * Revision 1.2  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package fox.brian.rubiks.visualization.itext.options;

import java.awt.Color;

import fox.brian.options.OptionType;
import fox.brian.options.OptionException;


public class OptionColor implements OptionType {

	public static final Color DEFAULT = Color.BLACK;
	
	private Color myColor = DEFAULT;
	
	public OptionColor(int red, int green, int blue) {
		myColor = new Color(red, green, blue);
	}

	public OptionColor() {
		myColor = DEFAULT;
	}

	public OptionColor(String s) throws OptionException {
		fromOptionString(s);
	}

	@Override
	public void fromOptionString(String s) throws OptionException{
		if (s == null || s.length() != 7 || s.charAt(0) != '#')
			throw new OptionException("String must be of the hexideciml format #RRGGBB");
		int hex = Integer.parseInt(s.substring(1),16);
		int red  =  (hex & 0xFF0000) >> 16;
		int green = (hex & 0x00FF00) >> 8;
		int blue =  (hex & 0x0000FF);
		myColor = new Color(red, green, blue);
		
	}

	@Override
	public String toOptionString() {
		int hex = (myColor.getRed() << 16) + (myColor.getGreen() << 8) + myColor.getBlue();
		String t = ("000000" + Integer.toHexString(hex));
		t = t.substring(t.length() - 6);
		return "#" + t;
	}
	
	public Color getValue() {
		return myColor;
	}

	@Override
	public int compareTo(OptionType o) {
		if (this.getClass() != o.getClass())
			return -1;
		OptionColor bc = (OptionColor)o;
		int hex1 = myColor.getRed() << 16 + myColor.getGreen() << 8 + myColor.getBlue();
		int hex2 = bc.myColor.getRed() << 16 + bc.myColor.getGreen() << 8 + bc.myColor.getBlue();
		return hex1 - hex2;
	}


}
