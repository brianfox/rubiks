package com.seefoxrun.options;


public class OptionInt implements OptionType {

	public final static int DEFAULT = 0;
	private int i = DEFAULT;

	public OptionInt() {
		
	}

	public OptionInt(String s) {
		fromOptionString(s);
	}

	public OptionInt(int i) {
		this.i = i;
	}

	@Override
	public void fromOptionString(String s) {
		this.i = Integer.parseInt(s);
	}

	@Override
	public String toOptionString() {
		return Integer.toString(i);
	}
	
	@Override
	public Integer getValue() {
		return i;
	}

	@Override
	public int compareTo(OptionType o) {
		if (this.getClass() != o.getClass())
			return -1;
		return i - ((OptionInt)o).i;
	}


}
