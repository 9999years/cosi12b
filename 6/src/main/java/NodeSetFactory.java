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
		Objects.requireNonNull(set.other);
		List<Node> promoted = new ArrayList<>(nodes.size());

		for(IsolatedNode n : nodes) {
			promoted.add(new Node(n, set));
		}

		set = new NodeSet(promoted);
	}

	public NodeSet getSet() {
		return set;
	}

	protected static void link(Node node, IsolatedNode isolated) {
		node.promotePriorities(isolated.priorities);
	}

	/**
	 * links a factory with a non-null other field
	 */
	protected static void link(NodeSetFactory A) {
		Objects.requireNonNull(A.set);
		Objects.requireNonNull(A.set.other);
		A.calcSet();
		new BiZip<Node, IsolatedNode>(A.set.nodes, A.nodes)
			.forEachRemaining(NodeSetFactory::link);
	}

	public static void link(NodeSetFactory A, NodeSetFactory B) {
		Objects.requireNonNull(A.set);
		Objects.requireNonNull(B.set);
		A.set.other = B.set;
		B.set.other = A.set;

		link(A);
		link(B);
	}
}
