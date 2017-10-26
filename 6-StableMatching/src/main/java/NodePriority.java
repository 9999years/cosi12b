package becca.smp;

/**
 * a 2-tuple of an integer priority (how important this match is to the node it
 * belongs to) and a node (what node the node this NodePriority belongs to
 * would like to be matched to)
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class NodePriority {
	protected int priority;
	protected Node node;

	NodePriority(int priority, Node node) {
		this.priority = priority;
		this.node = node;
	}

	public int getPriority() {
		return priority;
	}

	public Node getNode() {
		return node;
	}

	/**
	 * compares nodes only, not priorities
	 */
	public boolean equals(Object o) {
		if(o instanceof NodePriority) {
			return node.equals(((NodePriority) o).node);
		} else if(o instanceof Node) {
			return node.equals((Node) o);
		} else {
			return false;
		}
	}

	public String toString() {
		return "becca.smp.NodePriority[priority="
			+ priority
			+ ", node="
			+ node
			+ "]";
	}
}
