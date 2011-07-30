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
