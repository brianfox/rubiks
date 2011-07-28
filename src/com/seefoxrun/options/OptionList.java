package com.seefoxrun.options;

import java.util.ArrayList;
import java.util.Iterator;

public class OptionList implements Iterable<OptionList.Entry> {

	private ArrayList<OptionList.Entry> defaults = new ArrayList<OptionList.Entry>();
	
	public class Entry {
		String scope;
		String key;
		OptionType option;

		public Entry(String scope, String key, OptionType option) {
			this.scope = scope;
			this.key = key;
			this.option = option;
		}
	}
	
	public void add(String scope, String key, OptionType option) {
		defaults.add(new Entry(scope, key, option));
	}
	
	@Override
	public Iterator<OptionList.Entry> iterator() {
		return defaults.iterator();
	}


}
