package becca.smp;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Objects;

import java.lang.IllegalStateException;

/**
 * class for creating `NodeSet`s from names and lists of indicies
 * useful if node ids and preferences are known ahead of time, and nodes can be
 * added in id-order
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class NodeSetFactory {
	protected Node last;
	protected NodeSet set;
	protected boolean complete;

	NodeSetFactory() {
		reset();
	}

	public void reset() {
		complete = false;
		set = new NodeSet();
		last = null;
	}

	/**
	 * add a node to the set
	 * @return id of new element
	 */
	public int add(Object name) {
		// index is a fine id more or less
		last = new Node(name, set, set.getNodeCount());
		set.add(last);
		complete = false;
		return last.id;
	}

	/**
	 * add a priority to the end of the priority list of the last node
	 * added
	 */
	public void addPriority(int id) {
		Objects.requireNonNull(last, "add() not called!");
		complete = false;
		last.originalPriorities.add(id);
	}

	public Node getLast() {
		return last;
	}

	public NodeSet getSet() {
		if(complete) {
			return set;
		}
		throw new IllegalStateException(
			"Factories must be linked before their sets can be "
			+ "retrieved!");
	}

	protected static void link(NodeSetFactory A) {
		A.set.iterator().forEachRemaining(
			node -> node.resetPriorities());
		A.complete = true;
	}

	public static void link(NodeSetFactory A, NodeSetFactory B) {
		A.set.other = B.set;
		B.set.other = A.set;

		link(A);
		link(B);
	}
}
