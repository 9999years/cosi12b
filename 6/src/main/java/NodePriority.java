package becca.smp;

public class NodePriority {
	int priority;
	Node node;

	NodePriority(int priority, Node node) {
		this.priority = priority;
		this.node = node;
	}

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
		return Integer.toString(node.id);
		//return "becca.smp.NodePriority[priority="
			//+ priority
			//+ ", node="
			//+ node
			//+ "]";
	}
}
