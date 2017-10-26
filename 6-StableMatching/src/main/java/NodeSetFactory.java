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
	protected List<IsolatedNode> nodes;
	protected IsolatedNode last;
	protected NodeSet set;
	protected boolean complete;

	NodeSetFactory() {
		reset();
	}

	public void reset() {
		complete = false;
		nodes = new ArrayList<>();
		set = new NodeSet();
		last = null;
	}

	/**
	 * add a node to the set
	 * @return id of new element
	 */
	public int add(Object name) {
		// index is a fine id more or less
		last = new IsolatedNode(name, nodes.size());
		nodes.add(last);
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
		last.priorities.add(id);
	}

	public IsolatedNode getLast() {
		return last;
	}

	protected void preCalc() {
		set = new NodeSet(nodes.size());
	}

	protected void calcSet() {
		for(IsolatedNode n : nodes) {
			set.nodes.add(new Node(n, set));
		}
	}

	public NodeSet getSet() {
		if(complete) {
			return set;
		}
		throw new IllegalStateException(
			"Factories must be linked before their sets can be "
			+ "retrieved!");
	}

	protected static void link(Node node, IsolatedNode isolated) {
		node.promotePriorities(isolated.priorities);
	}

	protected static void link(
			List<Node> nodes, List<IsolatedNode> isolated) {
		new BiZip<Node, IsolatedNode>(nodes, isolated)
			.forEachRemaining(NodeSetFactory::link);

	}

	protected static void link(NodeSetFactory A) {
		link(A.set.nodes, A.nodes);
		A.complete = true;
	}

	public static void link(NodeSetFactory A, NodeSetFactory B) {
		A.preCalc();
		B.preCalc();

		A.set.other = B.set;
		B.set.other = A.set;

		A.calcSet();
		B.calcSet();

		link(A);
		link(B);
	}
}
