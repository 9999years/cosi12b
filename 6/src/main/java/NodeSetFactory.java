package becca.smp;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class NodeSetFactory {
	protected List<IsolatedNode> nodes = new ArrayList<>();

	/**
	 * add a node to the set
	 */
	public void add(String name) {
		// index is a fine id more or less
		nodes.add(new IsolatedNode(name, nodes.size()));
	}

	/**
	 * add a preference to the end of the pref list of the last node added
	 */
	public void addPref(int id) {
		nodes.get(nodes.size()).priorities.add(id);
	}

	public void getSet() {
		NodeSet set = new NodeSet();
		List<Node> promoted = new ArrayList<>(nodes.size());

		for(IsolatedNode n : nodes) {
			promoted.add(new Node(n));
		}

		new BiZip<Node, IsolatedNode>(nodes, promoted)
			.forEachRemaining((node, isolated) -> {
				// make node's set field point to the result
				// set
				node.set = set;
				// isolated.priorities gives indexes to `Node`s
				// in `nodes`; construct a new priority for
				// each inx in isolated.priorities
				for(int i = 0;
					i < isolated.priorities.length;
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
