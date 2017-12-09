import java.lang.Exception;
import java.lang.Iterable;
import java.lang.SuppressWarnings;

//exceptions
import java.lang.UnsupportedOperationException;
import java.lang.ArrayStoreException;
import java.lang.ClassCastException;
import java.lang.IndexOutOfBoundsException;
import java.util.NoSuchElementException;

// functional
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Consumer;

import java.util.Objects;
import java.util.Iterator;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Deque;

/**
 * Linked list
 */
public class LinkedList<E> implements Iterable<E>, Deque<E> {
	protected class Node<E> {
		E value;
		Node<E> previous;
		Node<E> next;

		Node() {
		}

		Node(E value) {
			this(null, value, null);
		}

		Node(Node<E> previous, E value, Node<E> next) {
			this.value    = value;
			this.previous = previous;
			this.next     = next;
		}
	}

	protected class LinkedListIterator implements Iterable<E>, ListIterator<E> {
		protected Node<E> current;
		protected int inx;

		/**
		 * startingElement is where the iterator starts, advancer is
		 * a function that takes a node and returns the next node
		 */
		LinkedListIterator(Node<E> startingElement, int startingIndex) {
			current = startingElement;
			inx = startingIndex;
		}

		public Iterator<E> iterator() {
			return this;
		}

		public boolean hasNext() {
			return current.next != tail;
		}

		public boolean hasPrevious() {
			return current.previous != head;
		}

		public void add(E e) {
			Node<E> n = new Node<>(e);
			LinkedList.this.addBefore(current, e);
		}

		public E next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			current = current.next;
			inx++;
			return current.value;
		}

		public E previous() {
			if(!hasPrevious()) {
				throw new NoSuchElementException();
			}
			current = current.previous;
			inx--;
			return current.value;
		}

		public int nextIndex() {
			return inx + 1;
		}

		public int previousIndex() {
			return inx - 1;
		}

		public void remove() {
			LinkedList.this.remove(current);
		}

		public void set(E e) {
			current.value = e;
		}
	}

	protected class DescendingLinkedListIterator extends LinkedListIterator {
		DescendingLinkedListIterator(Node<E> startingElement, int startingIndex) {
			super(startingElement, startingIndex);
		}

		public boolean hasNext() {
			return super.hasPrevious();
		}

		public boolean hasPrevious() {
			return super.hasNext();
		}

		public E next() {
			return super.previous();
		}

		public E previous() {
			return super.next();
		}

		public int nextIndex() {
			return super.previousIndex();
		}

		public int previousIndex() {
			return super.nextIndex();
		}
	}

	protected Node<E> head = new Node<E>();
	protected Node<E> tail = new Node<E>();
	protected int size = 0;

	LinkedList() {
		head.next = tail;
		tail.previous = head;
	}

	LinkedList(Collection<? extends E> c) {
		this();
		addAll(c);
	}

	LinkedList(Iterable<? extends E> c) {
		this();
		addAll(c);
	}

	public ListIterator<E> descendingIterator() {
		return new DescendingLinkedListIterator(head, size);
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

	protected <E extends Exception> void throwIfEmpty(Supplier<E> e) throws E {
		if(isEmpty()) {
			throw e.get();
		}
	}

	protected void throwIfEmpty() {
		throwIfEmpty(NoSuchElementException::new);
	}

	protected <T> T nullIfEmpty(Supplier<T> t) {
		return isEmpty() ? null : t.get();
	}

	protected void addAfter(Node<E> n, E e) {
		Node<E> insert = new Node<E>(n, e, n.next);
		n.previous.next = insert;
		n.next.previous = insert;
		size++;
	}

	protected void addBefore(Node<E> n, E e) {
		addAfter(n.previous, e);
	}

	// DEQUE METHODS:

	public Iterator<E> iterator() {
		return listIterator();
	}

	public ListIterator<E> listIterator() {
		return new LinkedListIterator(head, -1);
	}

	public boolean contains(Object o) {
		for(E e : this) {
			if(e.equals(o)) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		Objects.requireNonNull(a);

		if(a.length < size) {
			a = (T[]) new Object[size];
		}

		int i = 0;
		Node<E> current = head;
		while(current != null) {
			try {
				a[i] = (T) current.value;
			} catch(ClassCastException e) {
				throw new ArrayStoreException();
			}
			current = current.next;
			i++;
		}

		if(i < a.length - 1) {
			a[i] = null;
		}

		return a;
	}

	@SuppressWarnings("unchecked")
	public E[] toArray() {
		return toArray((E[]) new Object[size]);
	}

	// MUTATORS

	public boolean addAll(Iterable<? extends E> c) {
		for(E e : c) {
			add(e);
		}
		return true;
	}

	public boolean addAll(Collection<? extends E> c) {
		addAll(c);
		return true;
	}

	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}

	protected E remove(Node<E> n) {
		E value = n.value;
		n.previous.next = n.next;
		n.next.previous = n.previous;
		// clean up for the gc
		n.value = null;
		size--;
		return value;
	}

	public boolean remove(Object o, Iterator<E> itr) {
		E e;
		while(itr.hasNext()) {
			e = itr.next();
			if((o == null && e == null) || e.equals(o)) {
				itr.remove();
				return true;
			}
		}
		return false;
	}

	public boolean remove(Object o) {
		return remove(o, iterator());
	}

	public boolean removeFirstOccurrence(Object o) {
		return remove(o);
	}

	public boolean removeLastOccurrence(Object o) {
		return remove(o, descendingIterator());
	}

	// HEAD OPERATIONS

	// insertion:

	/**
	 * never throws an exception
	 */
	public void addFirst(E e) {
		addAfter(head, e);
	}

	/**
	 * always returns true
	 */
	public boolean offerFirst(E e) {
		addFirst(e);
		return true;
	}

	public void push(E e) {
		addFirst(e);
	}

	// removing:

	/**
	 * throws NoSuchElementException if empty
	 */
	public E removeFirst() {
		throwIfEmpty();
		return remove(head.next);
	}

	public E pop() {
		throwIfEmpty();
		return removeFirst();
	}

	public E remove() {
		throwIfEmpty();
		return removeFirst();
	}

	/**
	 * returns null if empty
	 */
	public E pollFirst() {
		return nullIfEmpty(() -> remove(head.next));
	}

	public E poll() {
		return pollFirst();
	}

	// examining:

	/**
	 * throws NoSuchElementException if empty
	 */
	public E getFirst() {
		throwIfEmpty();
		return head.value;
	}

	public E element() {
		return getFirst();
	}

	/**
	 * returns null if empty
	 */
	public E peekFirst() {
		return nullIfEmpty(() -> head.value);
	}

	public E peek() {
		return peekFirst();
	}

	// TAIL OPERATIONS

	// insertion

	/**
	 * never throws an exception
	 */
	public void addLast(E e) {
		addBefore(tail.previous, e);
	}

	/**
	 * actually just addLast(e)
	 */
	public boolean add(E e) {
		addLast(e);
		return true;
	}

	/**
	 * always true
	 */
	public boolean offerLast(E e) {
		addLast(e);
		return true;
	}

	public boolean offer(E e) {
		return offerLast(e);
	}

	// removal

	/**
	 * throws NoSuchElementException if empty
	 */
	public E removeLast() {
		throwIfEmpty();
		return remove(tail.previous);
	}

	/**
	 * returns null if empty
	 */
	public E pollLast() {
		return nullIfEmpty(this::removeLast);
	}

	// examining

	/**
	 * throws NoSuchElementException if empty
	 */
	public E getLast() {
		throwIfEmpty();
		return tail.previous.value;
	}

	/**
	 * returns null if empty
	 */
	public E peekLast() {
		return nullIfEmpty(() -> tail.previous.value);
	}

	// COLLECTION METHODS

	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
}
