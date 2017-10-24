package becca.smp;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.Objects;

import java.lang.IllegalStateException;

public class Node {
	/**
	 * unique per-set id of this node
	 */
	public int id;
	/**
	 * display name of this node; purely cosmetic
	 */
	public Object name;
	/**
	 * which set this node belongs to
	 */
	public NodeSet set;
	/**
	 * ranked list of node preferences in opposite set; stores IDs
	 */
	protected List<NodePriority> priorities;
	/**
	 * current match
	 */
	protected Node match = null;

	/**
	 * precondition: n is not null
	 * @param n the IsolatedNode to construct a real node from
	 */
	Node(IsolatedNode n, NodeSet parent) {
		this(n.name, parent, n.id);
		set.nodes.add(this);
	}

	/**
	 * precondition: id doesn't collide with any other node ids in
	 * parent.nodes
	 */
	Node(Object name, NodeSet parent, int id) {
		this.name = name;
		this.id = id;
		this.set = parent;
		init();
	}

	protected void init() {
		priorities = new LinkedList<NodePriority>();
	}

	/**
	 * precondition: other.nodes is fully constructed, ie has references to
	 * all nodes which will exist in the set
	 */
	protected void promotePriorities(
			List<Integer> priorityIndices, NodeSet other) {
		Iterator<Integer> itr = priorityIndices.iterator();
		for(int i = 0; itr.hasNext(); i++) {
			// index in B.set.nodes
			int indexB = itr.next();
			priorities.add(
				new NodePriority(
					i, // priority
					other.nodes.get(indexB)
				)
			);
		}
	}

	public NodePriority getTopChoice() {
		Objects.requireNonNull(priorities, "priorities field null!");
		return priorities.get(0);
	}

	public int getMatchPriority() {
		if(match == null) {
			return -1;
		}

		for(NodePriority np : priorities) {
			if(np.equals(match)) {
				return np.priority;
			}
		}

		return -1;
	}

	public Node getMatch() {
		return match;
	}

	public boolean isMatched() {
		return getMatch() != null;
	}

	public void match(Node n) {
		unmatch();
		match = n;
		n.match = this;
	}

	public void unmatch() {
		if(match != null) {
			match.match = null;
			match = null;
		}
	}

	public void removePreference(Node n) {
		Objects.requireNonNull(priorities, "priorities field null!");
		priorities.remove(n);
	}

	public void removePreferencesAfter(Node n) {
		Objects.requireNonNull(priorities, "priorities field null!");

		boolean removing = false;
		for(Iterator<NodePriority> itr = priorities.iterator();
				itr.hasNext(); ) {
			NodePriority np = itr.next();
			//System.out.println("Examining " + np);
			if(!removing && np.node.equals(n)) {
				removing = true;
				continue;
			}
			if(removing == true) {
				//System.out.println("(removing)");
				np.node.removePreference(this);
				itr.remove();
			}
		}
	}

	public boolean equals(Object o) {
		if(o instanceof Node) {
			return ((Node) o).id == id;
		} else if(o instanceof NodePriority) {
			// lets a more natural correspondence work between
			// nodes and priorities, which are *essentially*
			// wrappers around a node
			return ((NodePriority) o).node.id == id;
		} else {
			return false;
		}
	}

	public String toString() {
		return "becca.smp.Node[id="
			+ id
			+ (isMatched()
				?  ", matchId=" + match.id
				: "")
			+ "]";
	}
}
