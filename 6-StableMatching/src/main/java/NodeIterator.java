package becca.smp;

import java.util.List;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

/**
 * an iterator for `NodeSet`s. note that the iteration must "drain" the
 * predicate-satisfying nodes in the set. to find the next node, a NodeIterator
 * iterates over the underlying NodeSet, testing each node against a
 * user-supplied predicate and returning the next one, looping around at the
 * set's end. if the iteration body of the client program doesn't modify the
 * nodes so that they no longer satisfy the predicate, the program will be
 * caught in an infinite loop. Watch out!
 *
 * The main use for this class is to find unmatched nodes, in NodeSet.match,
 * which matches two `NodeSet`s, matching and modifying nodes until no
 * unmatched nodes exist.
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class NodeIterator implements Iterator<Node> {
	protected List<Node> nodes;
	protected Iterator<Node> itr;
	protected Predicate<Node> pred;

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
