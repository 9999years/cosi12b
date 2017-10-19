package becca.smp;

public class NodePriority {
	int priority;
	Node node;

	NodePriority(int priority, Node node) {
		this.priority = priority;
		this.node = node;
	}

	equals(Object o) {
		return o instanceof NodePriority
			&& node.equals(((NodePriority) o).node);
	}
}
