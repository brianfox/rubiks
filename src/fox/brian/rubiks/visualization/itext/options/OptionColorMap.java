/*
 * $Id: OptionColorMap.java,v 1.4 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: OptionColorMap.java,v $
 * Revision 1.4  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package fox.brian.rubiks.visualization.itext.options;

import fox.brian.options.OptionType;

public class OptionColorMap implements OptionType {

	private ColorMap  cm;

	public OptionColorMap(ColorMap cm) {
		this.cm = cm;
	}

	@Override
	public void fromOptionString(String s) {
		this.cm = ColorMap.parseColorMap(s);
	}

	@Override
	public String toOptionString() {
		return ColorMap.toString(cm);
	}
	
	public ColorMap getValue() {
		return cm;
	}
	
	@Override
	public int compareTo(OptionType o) {
		if (this.getClass() != o.getClass())
			return -1;
		// TODO
		return 0;
	}


}
