package becca.smp;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Objects;

import java.lang.IllegalStateException;

public class NodeSetFactory {
	protected List<IsolatedNode> nodes;
	protected IsolatedNode last;
	NodeSet set;

	NodeSetFactory() {
		reset();
	}

	public void reset() {
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
		return last.id;
	}

	/**
	 * add a preference to the end of the pref list of the last node added
	 */
	public void addPref(int id) {
		Objects.requireNonNull(last, "add() not called!");
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
		return set;
	}

	protected static void link(Node node, IsolatedNode isolated) {
		node.promotePriorities(isolated.priorities);
	}

	protected static void link(List<Node> nodes, List<IsolatedNode> isolated) {
		new BiZip<Node, IsolatedNode>(nodes, isolated)
			.forEachRemaining(NodeSetFactory::link);

	}

	protected static void link(NodeSetFactory A) {
		link(A.set.nodes, A.nodes);
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
