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
	NodeSet other;

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
	 */
	public void add(Object name) {
		last = new IsolatedNode(name, nodes.size());
		// index is a fine id more or less
		nodes.add(last);
	}

	/**
	 * add a preference to the end of the pref list of the last node added
	 */
	public void addPref(int id) {
		if(last == null) {
			throw new IllegalStateException("No last element!");
		}
		last.priorities.add(id);
	}

	public void addPrefs(int[] ids) {
		if(last == null) {
			throw new IllegalStateException("No last element!");
		}
		last.priorities.addAll(
			// int[] -> Integer[]
			IntStream
				.of(ids)
				.boxed()
				.collect(Collectors.toList())
		);
	}

	public IsolatedNode getLast() {
		return last;
	}

	protected void calcSet() {
		Objects.requireNonNull(other);
		List<Node> promoted = new ArrayList<>(nodes.size());

		for(IsolatedNode n : nodes) {
			promoted.add(new Node(n, set));
		}

		set = new NodeSet(promoted);
	}

	public NodeSet getSet() {
		return set;
	}

	protected static void link(
			Node node, IsolatedNode isolated, NodeSet other) {
		node.promotePriorities(isolated.priorities, other);
	}

	protected static void link(
			List<Node> nodes, List<IsolatedNode> isolated,
			NodeSet other) {
		new BiZip<Node, IsolatedNode>(nodes, isolated)
			.forEachRemaining((n, i) -> link(n, i, other));

	}

	public static void link(NodeSetFactory A, NodeSetFactory B) {
		A.other = B.set;
		B.other = A.set;
		A.calcSet();
		B.calcSet();

		link(A.set.nodes, A.nodes, B.set);
		link(B.set.nodes, B.nodes, A.set);
	}
}
