package com.seefoxrun.rubiks.visualization.itext.options;

import com.seefoxrun.options.OptionType;

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
	
	@Override
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
