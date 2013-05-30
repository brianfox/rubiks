package fox.brian.storage;

public abstract class Node {

	public static final int DEFAULT_SIZE_LIMIT = 5000;

	public abstract boolean add(byte[] array);
	public abstract boolean find(byte[] array);
	public abstract int size();
	public abstract int depth();

}
