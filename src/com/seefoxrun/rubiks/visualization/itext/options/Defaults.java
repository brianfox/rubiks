/*
 * $Id: Defaults.java,v 1.4 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Defaults.java,v $
 * Revision 1.4  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package com.seefoxrun.rubiks.visualization.itext.options;

import com.seefoxrun.options.OptionException;
import com.seefoxrun.options.OptionList;
import com.seefoxrun.visualization.measurement.Units;


public final class Defaults extends OptionList {

	public final static String RADIAL_SCOPE = "RadialGraph";
	
	public final static String PAPER_WIDTH          = "PaperWidth";
	public final static String PAPER_HEIGHT         = "PaperHeight";
	public final static String SCALE                = "Scale";
	public final static String GUIDE_LINE_COLOR     = "GuideLineColor";
	public final static String GUIDE_FILL_COLOR     = "GuideFillColor";
	public final static String GUIDE_LINE_WIDTH     = "GuideLineWidth";
	public final static String CONNECTOR_LINE_COLOR = "ConnectorLineColor";
	public final static String CONNECTOR_FILL_COLOR = "ConnectorFillColor";
	public final static String CONNECTOR_LINE_WIDTH = "ConnectorLineWidth";
	public final static String NODE_LINE_COLOR      = "NodeLineColor";
	public final static String NODE_FILL_COLOR      = "NodeFillColor";
	public final static String NODE_LINE_WIDTH      = "NodeLineWidth";
	public final static String BORDER_LEFT          = "BorderLeft";
	public final static String BORDER_RIGHT         = "BorderRight";
	public final static String BORDER_TOP           = "BorderTop";
	public final static String BORDER_BOTTOM        = "BorderBottom";
	public final static String WEDGE_LINE_COLOR     = "WedgeLineColor";
	public final static String WEDGE_FILL_COLOR     = "WedgeFillColor";
	

	
	static OptionList defaults_ = new OptionList();

	static {
		defaults_.add(RADIAL_SCOPE, PAPER_WIDTH,  new OptionValue(36.0, Units.INCHES));
		defaults_.add(RADIAL_SCOPE, PAPER_HEIGHT, new OptionValue(36.0, Units.INCHES));

		defaults_.add(RADIAL_SCOPE, BORDER_LEFT,    new OptionValue(1, Units.INCHES));
		defaults_.add(RADIAL_SCOPE, BORDER_RIGHT,   new OptionValue(1, Units.INCHES));
		defaults_.add(RADIAL_SCOPE, BORDER_TOP,     new OptionValue(1, Units.INCHES));
		defaults_.add(RADIAL_SCOPE, BORDER_BOTTOM,  new OptionValue(1, Units.INCHES));

		defaults_.add(RADIAL_SCOPE, SCALE,          new OptionValue(0.95, Units.INCHES));

		defaults_.add(RADIAL_SCOPE, GUIDE_LINE_WIDTH,     new OptionValue(5, Units.POINTS));
		defaults_.add(RADIAL_SCOPE, CONNECTOR_LINE_WIDTH, new OptionValue(0.25, Units.POINTS));
		defaults_.add(RADIAL_SCOPE, NODE_LINE_WIDTH,      new OptionValue(0.25, Units.POINTS));


		try {
			defaults_.add(RADIAL_SCOPE, GUIDE_LINE_COLOR, new OptionColor("#DDDDDD"));
			defaults_.add(RADIAL_SCOPE, GUIDE_FILL_COLOR, new OptionColor("#DDDDDD"));

			defaults_.add(RADIAL_SCOPE, WEDGE_LINE_COLOR, new OptionColor("#EEEEEE"));
			defaults_.add(RADIAL_SCOPE, WEDGE_FILL_COLOR, new OptionColor("#000066"));

			defaults_.add(RADIAL_SCOPE, CONNECTOR_LINE_COLOR, new OptionColor("#6699FF"));
			defaults_.add(RADIAL_SCOPE, CONNECTOR_FILL_COLOR, new OptionColor("#333333"));
			
			defaults_.add(RADIAL_SCOPE, NODE_LINE_COLOR, new OptionColor("#0000DD"));
			defaults_.add(RADIAL_SCOPE, NODE_FILL_COLOR, new OptionColor("#FFFFFF"));
		} catch (OptionException e) {
			System.err.println("Could not load default options.");
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
	
	public static OptionList getList() {
		return defaults_;
	}

	
}


