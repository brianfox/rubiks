package fox.brian.matrix;

public class ChainedMatrix {

	class Link {
		Matrix m;
		String note;

		Link previous;
		Link next;
		
		Link(Matrix m, String note) {
			this.m = m;
			this.note = note;
			previous = next = null;
		}
	}
	
	
	private Link head;
	private Link tail;
	
	public ChainedMatrix() {
		head = null;
	}
	
	public void addHead(Matrix m, String note) {
		Link temp = new Link(m,note);
		if (head == null) { 
			head = temp;
			tail = temp;
		}
		else {
			head.previous=temp;
			temp.next = head;
			head = temp;
		}
	}

	public void addTail(Matrix m, String note) {
		Link temp = new Link(m,note);
		if (tail == null) { 
			head = temp;
			tail = temp;
		}
		else {
			tail.next = temp;
			temp.previous=tail;
			tail = temp;
		}
	}

	public Matrix multiply() {
		if (head == null)
			return null;
		
		Matrix ret = head.m;
		Link temp = head.next;
		while (temp != null) {
			ret = ret.multiply(temp.m);
			temp = temp.next;
		}
		return ret;
	}


	@Override
	public String toString() {
		if (head == null)
			return "Empty";

		StringBuilder sb = new StringBuilder();
		Link temp = head;
		while (temp != null) {
			sb.append(String.format("%s: %s (%s)", temp.m.getClass().getSimpleName(), temp.m, temp.note));
			temp = temp.next;
			if (temp != null)
				sb.append(String.format("%n"));
		}
		return sb.toString();
	}

}
