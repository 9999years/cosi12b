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
 * Linked list class; see java.util.Deque for details
 *
 * note: all add methods never fail (throw exceptions or return false)
 *
 * note: retainAll, removeAll, and containsAll just throw a
 * java.lang.UnsupportedOperationException
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
		/**
		 * our "cursor" is between current and current.next
		 *
		 * before the list: current == head
		 * after list end: current == tail.previous
		 */
		protected Node<E> current;
		/**
		 * inx of current
		 */
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
			return current != tail.previous;
		}

		public boolean hasPrevious() {
			// we must be able to get the ListIterator to the
			// position "before" the first element, which is, in
			// our case, where curent == head
			return current != head;
		}

		/**
		 * cursor is in front of current, so we advance the cursor
		 * to the next value and then return the current value
		 */
		public E next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			current = current.next;
			inx++;
			return current.value;
		}

		/**
		 * cursor is in front of current, so we take our value at
		 * current (to return) and then rewind the cursor
		 */
		public E previous() {
			if(!hasPrevious()) {
				throw new NoSuchElementException();
			}
			E ret = current.value;
			current = current.previous;
			inx--;
			return ret;
		}

		public int nextIndex() {
			return inx + 1;
		}

		public int previousIndex() {
			return inx;
		}

		public void add(E e) {
			Node<E> n = new Node<>(e);
			LinkedList.this.addBefore(current, e);
		}

		public void remove() {
			LinkedList.this.remove(current);
		}

		public void set(E e) {
			current.value = e;
		}
	}

	protected class DescendingLinkedListIterator implements Iterable<E>, ListIterator<E> {
		LinkedListIterator itr;

		DescendingLinkedListIterator(
			Node<E> startingElement, int startingIndex) {
			itr = new LinkedListIterator(startingElement, startingIndex);
		}

		public boolean hasNext() {
			return itr.hasPrevious();
		}

		public boolean hasPrevious() {
			return itr.hasNext();
		}

		public E next() {
			return itr.previous();
		}

		public E previous() {
			return itr.next();
		}

		public int nextIndex() {
			return itr.previousIndex();
		}

		public int previousIndex() {
			return itr.nextIndex();
		}

		public Iterator<E> iterator() {
			return this;
		}

		public void add(E e) {
			itr.add(e);
		}

		public void remove() {
			itr.remove();
		}

		public void set(E e) {
			itr.set(e);
		}
	}

	protected Node<E> head;
	protected Node<E> tail;
	protected int size;

	LinkedList() {
		clear();
	}

	LinkedList(Collection<? extends E> c) {
		this();
		addAll(c);
	}

	LinkedList(Iterable<? extends E> c) {
		this();
		addAll(c);
	}

	/**
	 * optional operation; if o is in this list, applies operation on it
	 *
	 * kind of a combination .contains / .get
	 *
	 * @return true if operation was performed, false otherwise
	 */
	public boolean operateOnFirst(Object o, Consumer<E> operation) {
		for(E e : this) {
			if(equals(e, o)) {
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
		Objects.requireNonNull(n);
		Node<E> insert = new Node<E>(n, e, n.next);
		n.next.previous = insert;
		n.next = insert;
		size++;
	}

	protected void addBefore(Node<E> n, E e) {
		Objects.requireNonNull(n);
		addAfter(n.previous, e);
	}

	/**
	 * gets the first instance of o in the list
	 */
	public E get(Object o) {
		for(E e : this) {
			if(equals(e, o)) {
				return e;
			}
		}
		return null;
	}

	// DEQUE METHODS:

	public Iterator<E> iterator() {
		return listIterator();
	}

	public ListIterator<E> descendingIterator() {
		return new DescendingLinkedListIterator(tail.previous, size - 1);
	}

	public ListIterator<E> listIterator() {
		return new LinkedListIterator(head, -1);
	}

	protected boolean equals(E e, Object o) {
		return e == null
			? o == null
			: e.equals(o);
	}

	public boolean contains(Object o) {
		for(E e : this) {
			if(equals(e, o)) {
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
		for(E e : this) {
			try {
				a[i] = (T) e;
			} catch(ClassCastException err) {
				throw new ArrayStoreException();
			}
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
		for(E e : c) {
			add(e);
		}
		return true;
	}

	public void clear() {
		head = new Node<E>();
		tail = new Node<E>();
		head.next = tail;
		tail.previous = head;
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
			if(equals(e, o)) {
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

	public void addFirst(E e) {
		addAfter(head, e);
	}

	public boolean offerFirst(E e) {
		addFirst(e);
		return true;
	}

	public void push(E e) {
		addFirst(e);
	}

	// removing:

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

	public E pollFirst() {
		return nullIfEmpty(() -> remove(head.next));
	}

	public E poll() {
		return pollFirst();
	}

	// examining:

	public E getFirst() {
		throwIfEmpty();
		return head.next.value;
	}

	public E element() {
		return getFirst();
	}

	public E peekFirst() {
		return nullIfEmpty(() -> head.next.value);
	}

	public E peek() {
		return peekFirst();
	}

	// TAIL OPERATIONS

	// insertion

	public void addLast(E e) {
		addBefore(tail, e);
	}

	public boolean add(E e) {
		addLast(e);
		return true;
	}

	public boolean offerLast(E e) {
		addLast(e);
		return true;
	}

	public boolean offer(E e) {
		return offerLast(e);
	}

	// removal

	public E removeLast() {
		throwIfEmpty();
		return remove(tail.previous);
	}

	public E pollLast() {
		return nullIfEmpty(this::removeLast);
	}

	// examining

	public E getLast() {
		throwIfEmpty();
		return tail.previous.value;
	}

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
