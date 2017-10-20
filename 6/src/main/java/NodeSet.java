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
		System.out.println(
			"matching set of "
			+ A.nodes.size()
			+ " nodes with a set of "
			+ B.nodes.size()
			+ " nodes");

		Iterator<Node> unmatchedANodes = A.getUnmatchedNodes();

		unmatchedANodes.forEachRemaining(a -> {
			System.out.println(a);
			Node topChoice = a.getTopChoice().node;
			// unmatches and then matches
			topChoice.match(a);
			// doubly-remove links
			topChoice.removePreferencesAfter(a);
			System.out.println("getting next unmatched node");
		});
	}

	public String toString() {
		return "becca.smp.NodeSet[nodes="
			+ nodes
			+ "]";
	}
}
