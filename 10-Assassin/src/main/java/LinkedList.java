public class LinkedList<E>
	implements Serializable, Cloneable, Iterable<E>, Collection<E>,
		Deque<E>, List<E>, Queue<E> {
	protected class Node<E> {
		E value;
		Node<E> previous;
		Node<E> next;

		Node(Node<E> previous, E value, Node<E> next) {
			this.value = value;
			this.previous = previous;
			this.next = next;
		}
	}

	protected Node<E> head;
	protected Node<E> tail;
	protected int size = 0;

	LinkedList() {
	}

	LinkedList(Collection<? extends E> c) {
		addAll(c);
	}

	boolean add(E e) {
		if(size == 0) {
			// empty list
			head = tail = new Node(null, e, null);
		} else {
			tail.next = new Node(tail, e, null);
			tail = tail.next;
		}
		size++;
		return true;
	}

	void add(int index, E element) {
	}

	boolean addAll(Collection<? extends E> c) {
		for(E e : c) {
			add(e);
		}
	}

	void addLast(E e) {
		add(e);
	}

	void clear() {
		head = tail = null;
		size = 0;
	}
}
