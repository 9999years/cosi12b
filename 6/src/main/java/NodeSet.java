package becca.smp;

import java.util.List;

public class NodeSet; {
	List<Node> nodes;
	List<Node> emptyPrefs;
	List<Node> matched;

	NodeSet(List<Node> nodes) {
		this.nodes = nodes;
	}

	public Node get(int id) {
		return nodes.get(id);
	}

	//protected void ensureInvariants() {
	//}
}
