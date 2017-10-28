package becca.smp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.Objects;

import java.lang.IllegalStateException;

/**
 * a node in a stable matching problem; has a name, a unique id, a NodeSet it
 * belongs to, and a list of priorities which wrap other nodes it would like to
 * be matched with
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class Node {
	/**
	 * unique per-set id of this node
	 */
	protected int id;
	/**
	 * display name of this node; purely cosmetic
	 */
	protected Object name;
	/**
	 * which set this node belongs to
	 */
	protected NodeSet set;
	/**
	 * ranked list of node priorities in opposite set; stores IDs
	 */
	protected List<NodePriority> priorities;
	/**
	 * the node's original priorities; sorted by priority and containing a
	 * node id. used for regenerating the priorities field
	 */
	protected List<Integer> originalPriorities;
	/**
	 * current match
	 */
	protected Node match = null;

	public int getID() {
		return id;
	}

	public Object getName() {
		return name;
	}

	public NodeSet getSet() {
		return set;
	}

	public List<NodePriority> getPriorities() {
		return priorities;
	}

	/**
	 * precondition: id doesn't collide with any other node ids in
	 * parent.nodes
	 */
	Node(Object name, NodeSet parent, int id) {
		this.name = name;
		this.id = id;
		this.set = parent;
		originalPriorities = new ArrayList<Integer>();
		initPriorities();
	}

	protected void initPriorities() {
		priorities = new LinkedList<NodePriority>();
	}

	/**
	 * precondition: other.nodes is fully constructed, ie has references to
	 * all nodes which will exist in the set
	 */
	public void resetPriorities() {
		Objects.requireNonNull(set.getOtherSet().nodes);
		initPriorities();
		Iterator<Integer> itr = originalPriorities.iterator();
		for(int i = 0; itr.hasNext(); i++) {
			int nodeID = itr.next();
			priorities.add(new NodePriority(
				i, // = priority
				set.other.nodes.get(nodeID)
			));
		}
		set.matched = false;
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
				return np.getPriority() + 1;
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

	public void removePriority(Node n) {
		Objects.requireNonNull(priorities, "priorities field null!");
		priorities.remove(n);
	}

	public void removePrioritiesAfter(Node n) {
		Objects.requireNonNull(priorities, "priorities field null!");

		boolean removing = false;
		for(Iterator<NodePriority> itr = priorities.iterator();
				itr.hasNext(); ) {
			NodePriority np = itr.next();
			if(!removing && np.getNode().equals(n)) {
				removing = true;
				continue;
			}
			if(removing == true) {
				np.getNode().removePriority(this);
				itr.remove();
			}
		}
	}

	public boolean equals(Object o) {
		if(o instanceof Node) {
			return ((Node) o).getID() == getID();
		} else if(o instanceof NodePriority) {
			// lets a more natural correspondence work between
			// nodes and priorities, which are *essentially*
			// wrappers around a node
			return equals(((NodePriority) o).node);
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
