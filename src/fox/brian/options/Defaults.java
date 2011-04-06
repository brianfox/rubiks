package fox.brian.options;

import java.util.Iterator;

public class Defaults extends OptionList {
	
	static OptionList defaults_ = new OptionList();

	static {
		defaults_.add("Options","ProgramName", new OptionString("Options"));
		defaults_.add("Options","Version", new OptionString("0.1"));
		defaults_.add("Options","CopyrightNotice", new OptionString("Copyright 2010 by Brian Fox"));
		defaults_.add("Options","Author", new OptionString("Brian Fox"));
	}

	@Override
	public Iterator<OptionList.Entry> iterator() {
		return defaults_.iterator();
	}


}

