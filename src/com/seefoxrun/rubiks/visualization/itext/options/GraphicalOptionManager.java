/*
 * $Id: GraphicalOptionManager.java,v 1.6 2010/05/28 22:31:56 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: GraphicalOptionManager.java,v $
 * Revision 1.6  2010/05/28 22:31:56  bfox
 * Code cleanup - removed unnecessary imports.
 *
 * Revision 1.5  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package com.seefoxrun.rubiks.visualization.itext.options;

import java.awt.Color;
import java.io.IOException;

import com.seefoxrun.options.OptionException;
import com.seefoxrun.options.OptionList;
import com.seefoxrun.options.OptionManager;
import com.seefoxrun.visualization.measurement.Value;

public class GraphicalOptionManager extends OptionManager {
	
	public GraphicalOptionManager() {
		super();
	}

	
	public GraphicalOptionManager(String filename) throws IOException, OptionException {
		super(filename);
	}

	public GraphicalOptionManager(OptionList list) {
		super(list);
	}

	public Color getColor(String scope, String key) {
		return (Color)(getOption(scope, key).getValue());
	}

	public ColorMap getColorMap(String scope, String key) {
		return (ColorMap)(getOption(scope, key).getValue());
	}


	public Value getValue(String scope, String key) {
		return (Value)(getOption(scope, key).getValue());
	}
	

}
