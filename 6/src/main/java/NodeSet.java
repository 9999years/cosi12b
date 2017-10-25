package becca.smp;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.lang.StringBuilder;

public class NodeSet {
	public static final int DEFAULT_CAPACITY = 10;

	List<Node> nodes;
	NodeSet other;

	NodeSet() {
		init(DEFAULT_CAPACITY);
	}

	NodeSet(int size) {
		init(size);
	}

	NodeSet(List<Node> nodes) {
		this.nodes = nodes;
	}

	protected void init(int size) {
		nodes = new ArrayList<>(size);
		other = null;
	}
	
	public void reset() {
		init(DEFAULT_CAPACITY);
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
			// unmatches and then matches
			topChoice.match(a);
			// doubly-remove links
			topChoice.removePreferencesAfter(a);
		});
	}

	public String toString() {
		return "becca.smp.NodeSet[nodes="
			+ nodes
			+ "]";
	}

	/**
	 * a table displaying the id, match id, match priority, and array of
	 * priority ids
	 */
	public String getMatchStatus() {
		StringBuilder builder = new StringBuilder();
		builder.append(
			"id | matchid | priority | priorities\n" +
			"---+---------+----------+-----------\n");

		for(Node n : nodes) {
			builder.append(String.format(
				"%2d | %7s | %8s | %s\n",
				n.id,
				n.isMatched()
					? Integer.toString(n.match.id)
					: "",
				n.isMatched()
					? Integer.toString(n.getMatchPriority())
					: "",
				n.priorities));
		}

		builder.append("\n");
		return builder.toString();
	}
}
