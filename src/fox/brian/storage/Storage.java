package fox.brian.storage;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

public class Storage implements SortedSet<byte []> {

	private Node root;
	private int sizeLimit = Node.DEFAULT_SIZE_LIMIT;
	
	public Storage() {
		root = new Metadata();

	}

	@Override
	public boolean add(byte[] array) {
		return root.add(array);
	}

	@Override
	public boolean addAll(Collection<? extends byte[]> collection) {
		boolean ret = true;
		for (byte[] b : collection)
			ret = ret && root.add(b);
		return ret;
	}
	
	@Override
	public int size() {
		return root.size();	
	}

	
	
	
	
	
	////////////////////////////////////////////////
	//
	//   Unimplemented

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<byte[]> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<? super byte[]> comparator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] first() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<byte[]> headSet(byte[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] last() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<byte[]> subSet(byte[] arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<byte[]> tailSet(byte[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
