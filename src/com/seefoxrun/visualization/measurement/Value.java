package com.seefoxrun.visualization.measurement;


public final class Value implements Comparable<Value> {
	
	private double value;
	private Units unit;

	
	public Value(double value, Units unit) {
		this.value = value;
		this.unit = unit;
	}

	
	public Value(Value v) {
		this.value = v.value;
		this.unit = v.unit;
	}

	public Value(Value v, Units u) {
		this(v.convertTo(u),u);
	}

	public Value add(Value v) {
		return new Value(this.value + v.convertTo(this.unit), this.unit);
	}

	public Value sub(com.seefoxrun.visualization.measurement.Value v) {
		return new Value(this.value - v.convertTo(this.unit), this.unit);
	}

	public Value div(double x) {
		return new Value(this.value / x, this.unit);
	}

	public Value mul(double d) {
		return new Value(this.value * d, this.unit);
	}

	public double getValue() {
		return value;
	}

	public Units getUnit() {
		return unit;
	}
	
	public double convertTo(Units target) {
		return unit.convertTo(value, target);
	}


	@Override
	public int compareTo(Value v) {
		double v1 = v.convertTo(unit);
		if (value == v1)
			return 0;
		return this.value < v1 ? -1 : 1;
	}





}
