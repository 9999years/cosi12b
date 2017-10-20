package becca.smp;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

import java.lang.IllegalStateException;

// make nodeCount an array which matches to different sets
// so it can still be static and set variable isnt really relevant
// input file indexes per-set, not globally

public class Node extends IsolatedNode {
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

	Node(IsolatedNode n) {
		this(n.name);
		id = n.id;
	}

	Node(Object name) {
		super(name);
		init();
	}

	protected void init() {
		priorities = new LinkedList<NodePriority>();
	}

	public NodePriority getTopChoice() {
		if(priorities == null) {
			throw new IllegalStateException("priorities field null!");
		}
		return priorities.get(0);
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
		if(priorities == null) {
			throw new IllegalStateException("priorities field null!");
		}
		priorities.remove(n);
	}

	public void removePreferencesAfter(Node n) {
		if(priorities == null) {
			throw new IllegalStateException("priorities field null!");
		}
		boolean removing = false;
		for(Iterator<NodePriority> itr = priorities.iterator();
				itr.hasNext(); ) {
			NodePriority np = itr.next();
			System.out.println("Examining/removing " + np);
			if(!removing && np.node.equals(n)) {
				removing = true;
			}
			if(removing == true) {
				itr.remove();
			}
		}
	}

	public boolean equals(Object o) {
		return o instanceof Node
			&& ((Node) o).id == id;
	}

	public String toString() {
		return "becca.smp.Node[id="
			+ id
			+ ", matchId="
			+ (match == null ? -1 : match.id)
			+ "]";
	}
}
