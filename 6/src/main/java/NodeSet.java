package becca.smp;

import java.util.List;
import java.util.Iterator;

public class NodeSet; {
	List<Node> nodes;

	NodeSet() {
		set = new ArrayList<>();
	}

	NodeSet(List<Node> nodes) {
		this.nodes = nodes;
	}

	public Iterator<Node> getUnmatchedNodes() {
		return new NodeIterator(nodes, n -> !n.isMatched());
	}

	public Node get(int id) {
		return nodes.get(id);
	}

	//protected void ensureInvariants() {
	//}
}
