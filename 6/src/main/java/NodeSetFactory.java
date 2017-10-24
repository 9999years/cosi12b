package becca.smp;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

import java.lang.IllegalStateException;

public class NodeSetFactory {
	protected List<IsolatedNode> nodes;
	/** index = node id
	 * each element is a list of ids ordered by priority
	 */
	//protected List<List<Integer>> nodePriorities;
	protected IsolatedNode last;
	NodeSet set;

	NodeSetFactory() {
		reset();
	}

	public void reset() {
		nodes = new ArrayList<>();
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
		set = new NodeSet();
		List<Node> promoted = new ArrayList<>(nodes.size());
		set.nodes = promoted;

		for(IsolatedNode n : nodes) {
			Node node = new Node(n);
			node.set = set;
			promoted.add(node);
		}
	}

	public NodeSet getSet() {
		return set;
	}

	public static void link(NodeSetFactory A, NodeSetFactory B) {
		A.calcSet();
		B.calcSet();

		new QuadZip<Node, Node, IsolatedNode, IsolatedNode>(
			A.set.nodes, // real
			B.set.nodes,
			A.nodes, // isolated
			B.nodes
		).forEachRemaining(Na -> Nb -> Ia -> Ib -> {
			// Na is real node in A.set.nodes
			// Ia is isolated node in A.nodes
			Iterator<Integer> itr = Ia.priorities.iterator();
			for(int i = 0; itr.hasNext(); i++) {
				// index in B.set.nodes
				int indexB = itr.next();
				Na.priorities.add(
					new NodePriority(
						i, // priority
						B.set.nodes.get(indexB)
					)
				);
			}
		});
	}
}
