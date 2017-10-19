package becca.smp;

import java.util.ArrayList;
import java.util.List;

// make nodeCount an array which matches to different sets
// so it can still be static and set variable isnt really relevant
// input file indexes per-set, not globally

public class Node extends IsolatedNode {
	/**
	 * which set this node belongs to
	 */
	public NodeSet set;
	/**
	 * list of all the nodes
	 * TODO determine per-set or for all
	 */
	//protected static List<Node> allNodes;
	/**
	 * ranked list of node preferences in opposite set; stores IDs
	 */
	protected List<NodePriority> priorities;
	/**
	 * current match
	 */
	protected Node match = null;

	Node(IsolatedNode n) {
		this(n.name);
		id = n.id;
	}

	Node(Object name) {
		super(name);
	}

	protected void init() {
		id = ++nodeCount;
		//allNodes.add(this);
	}

	public int getMatchId() {
		return match;
	}

	public boolean isMatched() {
		return getMatch() != -1;
	}

	public boolean equals(Object o) {
		return o instanceof Node
			&& ((Node) o).id == id;
	}
}
