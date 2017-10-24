package becca.smp;

import java.lang.Comparable;

public class NodePriority implements Comparable<NodePriority> {
	int priority;
	Node node;

	NodePriority(int priority, Node node) {
		this.priority = priority;
		this.node = node;
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

	/**
	 * compares *priorities* only, in contrast with equals
	 *
	 * sorry!!
	 *
	 * here's the reasoning: we want to be able to sort lists of
	 * `NodePriority`s by *priority*, but when we need equality we only
	 * care about the node
	 */
	public int compareTo(NodePriority o) {
		return new Integer(priority).compareTo(o.priority);
	}

	public String toString() {
		return Integer.toString(node.id);
		//return "becca.smp.NodePriority[priority="
			//+ priority
			//+ ", node="
			//+ node
			//+ "]";
	}
}
