package becca.smp;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class NodeSet {
	List<Node> nodes;

	NodeSet() {
		nodes = new ArrayList<>();
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

	public void unmatchAll() {
		for(Node n : nodes) {
			n.unmatch();
		}
	}

	/**
	 * MUTABLY modifies BOTH sets IN PLACE
	 */
	public static void match(NodeSet A, NodeSet B) {
		Iterator<Node> unmatchedANodes = A.getUnmatchedNodes();

		unmatchedANodes.forEachRemaining(a -> {
			Node topChoice = a.getTopChoice().node;
			if(topChoice.isMatched()) {
				// unmatches both
				topChoice.unmatch();
			}
			topChoice.match(a);
			// doubly-remove links
			topChoice.removePreferencesAfter(a);
		});
	}
}
