/*
 * $Id: Units.java,v 1.3 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Units.java,v $
 * Revision 1.3  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package com.seefoxrun.visualization.measurement;

public enum Units {
	MM    ("MM",     Conversion.MM_TO_MM,     Conversion.MM_TO_INCHES,     Conversion.MM_TO_DOTS    ),
	INCHES("INCHES", Conversion.INCHES_TO_MM, Conversion.INCHES_TO_INCHES, Conversion.INCHES_TO_DOTS),
	POINTS("POINTS", Conversion.DOTS_TO_MM,   Conversion.DOTS_TO_INCHES,   Conversion.DOTS_TO_DOTS);
	
	
	double mmConv;
	double inConv; 
	double dpiConv;
	
	String text;
	
	Units(String text, double mmConv, double inConv, double dpiConv) {
		this.mmConv = mmConv;
		this.inConv = inConv;
		this.dpiConv = dpiConv;
		this.text = text;
		
	}
	
	
	public double convertTo(double value, Units units) {
		switch (units) {
			case MM:	  return value * mmConv;
			case INCHES:  return value * inConv;
			case POINTS:  return value * dpiConv;
		}
		return 0;
	}


	public static Units parseUnit(String string) {
		string = string.toLowerCase();
		for (Units u : Units.values())
			if (u.text.compareTo(string) == 0)
				return u;
		throw new IllegalArgumentException();
	}

	public String toString() {
		return text;
	}

}

