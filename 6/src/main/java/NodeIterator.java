package becca.smp;

import java.util.List;
import java.util.Iterator;
import java.util.Predicate;
import java.util.NoSuchElementException;

public class NodeIterator implements Iterator<Node> {
	NodeSet nodes;
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

	NodeIterator(NodeSet nodes, Predicate<Node> pred) {
		this.nodes = nodes;
		this.pred = pred;
		itr = this.nodes.iterator();
	}

	protected void ensureIterator() {
		if(!itr.hasNext()) {
			// get a fresh copy if we're at the end
			itr = nodes.iterator();
		}
	}

	/**
	 * finds and caches the next element in this.next if it exists
	 */
	boolean hasNext() {
		ensureIterator();
		next = rawNext();
		while(!pred.test(n)) {
			if(travelled == nodes.size()) {
				// we've travelled the whole list and found no
				// new elements
				next = null;
				return false;
			}
			next = rawNext();
		}
		return true;
	}

	protected Node rawNext() {
		travelled++;
		return itr.next();
	}

	Node next() {
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

	void remove() {
		itr.remove();
		travelled--;
	}
}
