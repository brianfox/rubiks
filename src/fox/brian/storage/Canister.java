package fox.brian.storage;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.seefoxrun.rubiks.model.cube.Cube;

public class Canister extends Node {

	static int count = 0;
	
	Comparator<byte[]> c = new Comparator<byte[]>() {
	    public int compare(byte[] b1, byte[] b2) {
	    	int comIdx = b1.length > b2.length ? b2.length : b1.length;
	    	for (int i = 0; i < comIdx; i++) {
				if (b1[i] != b2[i])
					return b1[i] - b2[i];
			}
			if (b1.length == b2.length)
				return 0;
			return b1.length - b2.length;
	   }
	};
	
	
	// THE actual heap storage
	TreeSet<byte []> store;

	
	private int sizeLimit = Node.DEFAULT_SIZE_LIMIT;
	Metadata parent;
	int depth;
	
	public Canister(int depth) {
		this.depth = depth;
		store = new TreeSet<byte []>(c);
	}
	
	public Canister(Metadata parent) {
		this(parent.depth() + 1);
		this.parent = parent;
	}
	
	@Override
	public boolean add(byte[] array) {
		boolean ret = store.add(array);
		if (this.size() > sizeLimit && array.length > depth()) 
			parent.splitNode(this);
		return ret;
	}

	@Override
	public boolean find(byte[] array) {
		return false;
	}
	
	public int depth() {
		return depth;
	}
	
	public int size() {
		return store.size();
	}

}
