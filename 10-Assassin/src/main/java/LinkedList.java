import java.lang.IndexOutOfBoundsException;
import java.lang.NoSuchElementException;
import java.lang.Exception;
import java.lang.Iterable;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Collection;
import java.util.Deque;

/**
 * Linked list
 */
public class LinkedList<E> implements Iterable<E>, Deque<E> {
	protected class Node<E> {
		E value;
		Node<E> previous;
		Node<E> next;

		Node(Node<E> previous, E value, Node<E> next) {
			this.value    = value;
			this.previous = previous;
			this.next     = next;
		}
	}

	protected class LinkedListIterator<E> implements Iterator<E> {
		protected Node<E> current;
		protected int inx;

		/**
		 * startingElement is where the iterator starts, advancer is
		 * a function that takes a node and returns the next node
		 */
		LinkedListIterator(E startingElement, int startingIndex) {
			current = startingElement;
			inx = startingIndex;
		}

		public boolean hasNext() {
			if(current == null) {
				return false;
			}
			return current.next != null;
		}

		public boolean hasPrevious() {
			if(current == null) {
				return false;
			}
			return current.previous != null;
		}

		public void add(E e) {
			Node<E> n = new Node<>(null, e, null);
			if(hasPrevious()) {
				n.previous = previous();
				previous().next = n;
			}
			if(hasNext()) {
				n.next = next();
				next().previous = n;
			}
		}

		public E next() {
			// hasNext call ensures next is updated
			if(!hasNext()) {
				throw NoSuchElementException();
			}
			current = next;
			return current.value;
		}

		public void remove() {
			if(current.previous == null) {
				// start of list
				if(current.next == null) {
					// single-element list
					current = next = null;
				}
				LinkedList.this.pollFirst();
			} else if(current.next == null) {
				// end of list
				LinkedList.this.pollLast();
			} else {
				// middle of list
				LinkedList.this.remove(current);
			}
		}
	}

	protected Node<E> head = null;
	protected Node<E> tail = null;
	protected int size = 0;

	LinkedList() {
	}

	LinkedList(Collection<? extends E> c) {
		addAll(c);
	}

	public Iterator<E> iterator() {
		return new LinkedListIterator(head, n -> n.next);
	}

	public Iterator<E> descendingIterator() {
		return new LinkedListIterator(tail, n -> n.previous);
	}

	protected void remove(Node<E> n) {
		n.previous.next = n.next;
		n.next.previous = n.previous;
		// clean up for the gc
		n.value = null;
		size--;
	}

	public boolean remove(Object o) {
		Iterator<Node<E>> itr = iterator();
		for(E e : itr) {
			if(e.equals(o)) {
				itr.remove();
				return true;
			}
		}
		return false;
	}

	/**
	 * returns the element if removed, null otherwise
	 *
	 * i know this isn't the signature the *real* java.util.LinkedList
	 * has, it's fine
	 */
	public E removeFirstOccurrence(Object o) {
		Iterator<Node<E>> itr = iterator();
		for(E e : itr) {
			if(e.equals(o)) {
				itr.remove();
				return e;
			}
		}
		return null;
	}

	/**
	 * precondition: size == 0
	 */
	protected addEmpty(E e) {
		head = tail = new Node(null, e, null);
	}

	/**
	 * actually just addLast(e)
	 */
	public boolean add(E e) {
		addLast(e);
		return true;
	}

	public boolean addAll(Collection<? extends E> c) {
		for(E e : c) {
			add(e);
		}
	}

	public void clear() {
		head = tail = null;
		size = 0;
	}

	public void contains(Object o) {
		for(E e : this) {
			if(e.equals(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * optional operation; if o is in this list, applies operation on it
	 * @return true if operation was performed
	 */
	public boolean operateOnFirst(Object o, Consumer<E> operation) {
		for(E e : this) {
			if(o.equals(e)) {
				operation.accept(e);
				return true;
			}
		}
		return false;
	}

	protected void throwIfEmpty(Supplier<Exception> e) {
		if(size == 0) {
			throw e.get();
		}
	}

	protected void throwIfEmpty() {
		throwIfEmpty(() -> new NoSuchElementException());
	}

	/**
	 * precondition: size &gt; 0
	 */
	public E removeFirstNonEmpty() {
		E oldHead = head.value;
		head = head.next;
		size--;
		return oldHead;
	}

	// DEQUE METHODS:

	// HEAD OPERATIONS

	// insertion:

	/**
	 * never throws an exception
	 */
	public void addFirst(E e) {
		if(size == 0) {
			addEmpty(element);
		} else {
			// add front
			head.previous = new Node(null, element, head);
			head = head.previous;
			size++;
		}
	}

	/**
	 * always returns true
	 */
	public boolean offerFirst(E e) {
		addFirst(e);
		return true;
	}

	// removing:

	/**
	 * throws NoSuchElementException if empty
	 */
	public E removeFirst() {
		throwIfEmpty();
		return removeFirstNonEmpty();
	}

	/**
	 * returns null if empty
	 */
	public E pollFirst() {
		if(size == 0) {
			return null;
		}
		return removeFirstNonEmpty();
	}

	// examining:

	/**
	 * throws NoSuchElementException if empty
	 */
	public E getFirst() {
		throwIfEmpty();
		return head.value;
	}

	/**
	 * returns null if empty
	 */
	public E peekFirst() {
		return size == 0 ? null : head.value;
	}

	// TAIL OPERATIONS

	// insertion

	/**
	 * never throws an exception
	 */
	public void addLast(E e) {
		if(size == 0) {
			// empty list
			addEmpty(e);
		} else {
			tail.next = new Node(tail, e, null);
			tail = tail.next;
			size++;
		}
	}

	/**
	 * always true
	 */
	public boolean offerLast(E e) {
		addLast(e);
		return true;
	}

	// removal

	/**
	 * precondition: tail is non-null / size &gt; 0
	 *
	 * removes last element and returns its value
	 */
	protected E removeLastNonEmpty() {
		E oldTail = tail.value;
		tail = tail.previous;
		return oldTail;
	}

	/**
	 * throws NoSuchElementException if empty
	 */
	public E removeLast() {
		throwIfEmpty();
		return removeLastNonEmpty();
	}

	/**
	 * returns null if empty
	 */
	public E pollLast() {
		if(size == 0) {
			return null;
		}
		return removeLastNonEmpty();
	}

	// examining

	/**
	 * throws NoSuchElementException if empty
	 */
	public E getLast() {
		throwIfEmpty();
		return tail.value;
	}

	/**
	 * returns null if empty
	 */
	public E peekLast() {
		return size == 0 ? null : tail.value;
	}

	public int size() {
		return size;
	}
}
