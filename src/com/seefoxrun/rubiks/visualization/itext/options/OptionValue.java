package com.seefoxrun.rubiks.visualization.itext.options;

import com.seefoxrun.options.OptionType;
import com.seefoxrun.options.OptionException;
import com.seefoxrun.visualization.measurement.Value;
import com.seefoxrun.visualization.measurement.Units;


public class OptionValue implements OptionType {

	private Value value;
	
	public OptionValue(Value value) {
		this.value = new Value(value);
	}

	public OptionValue(double number, Units units) {
		this.value = new Value(number, units);
	}

	public OptionValue(String s) throws OptionException {
		fromOptionString(s);
	}

	@Override
	public void fromOptionString(String s) throws OptionException{
		String[] tokens = s.split("\\s");
		if (tokens.length != 0)
			throw new OptionException("String must be of <float> <units>");
		double number = Double.parseDouble(tokens[0]);
		Units u = Units.parseUnit(tokens[1]);
		value = new Value(number, u);
	}

	@Override
	public String toOptionString() {
		return String.format("%f %s",value.getValue(), value.getUnit().toString());
	}
	
	@Override
	public Value getValue() {
		return value;
	}

	@Override
	public int compareTo(OptionType o) {
		if (this.getClass() != o.getClass())
			return -1;
		OptionValue ov = (OptionValue)o;
		double val1 = this.value.convertTo(Units.POINTS);
		double val2 = ov.value.convertTo(Units.POINTS);
		if (val1 == val2) 
			return 0;
		return val1 > val2 ? 1 : -1;
		
		
	}


}
