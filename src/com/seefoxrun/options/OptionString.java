package com.seefoxrun.options;

public class OptionString implements OptionType {

	public final static String DEFAULT = "";
	private String s = DEFAULT;

	public OptionString() {
	}

	public OptionString(String s) {
		this.s = s == null ? "" : s;
	}
	
	@Override
	public void fromOptionString(String s) {
		this.s = s;
	}

	@Override
	public String toOptionString() {
		return s;
	}

	@Override
	public String getValue() {
		return s;
	}

	@Override
	public int compareTo(OptionType o) {
		if (this.getClass() != o.getClass())
			return -1;
		return s.compareTo(((OptionString)o).s);
	}

}
