package com.seefoxrun.options;

public class OptionBoolean implements OptionType {

	public final static boolean DEFAULT = false;
	private boolean b = DEFAULT;
	
	public OptionBoolean() {
		
	}

	public OptionBoolean(String s) {
		fromOptionString(s);
	}

	public OptionBoolean(boolean b) {
		this.b = b;
	}

	@Override
	public void fromOptionString(String s) {
		this.b = Boolean.parseBoolean(s);
	}

	@Override
	public String toOptionString() {
		return Boolean.toString(b);
	}
	
	public Boolean getValue() {
		return b;
	}

	@Override
	public int compareTo(OptionType o) {
		if (this.getClass() != o.getClass())
			return -1;
		return (b ? 1 : 0) - (((OptionBoolean)o).b ? 1 : 0);
	}

}
