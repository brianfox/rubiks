package fox.brian.options;

public interface OptionType extends Comparable<OptionType> {
	String toOptionString();
	void fromOptionString(String s) throws OptionException;
	Object getValue();
}
