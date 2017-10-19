package becca.smp;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

import java.lang.IllegalStateException;

public class NodeSetFactory {
	protected List<IsolatedNode> nodes;
	protected IsolatedNode last;

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

	public NodeSet getSet() {
		NodeSet set = new NodeSet();
		List<Node> promoted = new ArrayList<>(nodes.size());

		for(IsolatedNode n : nodes) {
			promoted.add(new Node(n));
		}

		new BiZip<Node, IsolatedNode>(promoted, nodes)
			.forEachRemaining((node, isolated) -> {
				// make node's set field point to the result
				// set
				node.set = set;
				// isolated.priorities gives indexes to `Node`s
				// in `nodes`; construct a new priority for
				// each inx in isolated.priorities
				for(int i = 0;
					i < isolated.priorities.size();
					i++) {
					// index is priority and an index
					node.priorities.add(new NodePriority(
						i, promoted.get(isolated.priorities.get(i))
					));
				}
			});

		return set;
	}
}
