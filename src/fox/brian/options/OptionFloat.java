package fox.brian.options;


public class OptionFloat implements OptionType {

	public final static float DEFAULT = 0F;
	private float f = DEFAULT;

	public OptionFloat() {
		
	}

	public OptionFloat(String s) {
		fromOptionString(s);
	}
	
	public OptionFloat(float f) {
		this.f = f;
	}

	@Override
	public void fromOptionString(String s) {
		this.f = Float.parseFloat(s);
	}

	@Override
	public String toOptionString() {
		return Float.toString(f);
	}
	
	public Float getValue() {
		return f;
	}

	@Override
	public int compareTo(OptionType o) {
		if (this.getClass() != o.getClass())
			return -1;
		if (f == ((OptionFloat)o).f)
			return 0;
		return f > ((OptionFloat)o).f ? 1 : -1;
	}

}
