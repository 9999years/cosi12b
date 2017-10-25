package becca.smp;

import java.util.List;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

public class NodeIterator implements Iterator<Node> {
	List<Node> nodes;
	Iterator<Node> itr;
	Predicate<Node> pred;

	/**
	 * offset since last yielded element
	 */
	protected int travelled;
	/**
	 * cached node from hasnext
	 */
	protected Node next;

	NodeIterator(List<Node> nodes, Predicate<Node> pred) {
		if(nodes.size() == 0) {
			throw new IllegalArgumentException("Node list is empty!");
		}
		this.nodes = nodes;
		this.pred = pred;
		ensureIterator();
	}

	NodeIterator(NodeSet nodes, Predicate<Node> pred) {
		this(nodes.nodes, pred);
	}

	protected void ensureIterator() {
		if(itr == null || !itr.hasNext()) {
			// get a fresh copy if we're at the end
			itr = nodes.iterator();
		}
	}

	protected Node rawNext() {
		ensureIterator();
		travelled++;
		return itr.next();
	}

	/**
	 * finds and caches the next element in this.next if applicable
	 */
	public boolean hasNext() {
		next = rawNext();
		while(!pred.test(next)) {
			if(travelled >= nodes.size()) {
				// we've travelled the whole list and found no
				// new elements
				next = null;
				return false;
			}
			next = rawNext();
		}
		travelled = 0;
		return true;
	}

	public Node next() {
		if(hasNext()) {
			Node n = next;
			// make sure we dont accidentally use this.next twice
			next = null;
			return n;
		} else {
			// we've travelled the whole list and found no new
			// elements
			throw new NoSuchElementException();
		}
	}

	public void remove() {
		itr.remove();
		travelled--;
	}
}
