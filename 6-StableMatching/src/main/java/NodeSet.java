package becca.smp;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.lang.StringBuilder;

public class NodeSet {
	public static final int DEFAULT_CAPACITY = 10;

	List<Node> nodes;
	NodeSet other;

	protected boolean matched;

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
		matched = false;
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

		matched = false;
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

		A.matched = B.matched = true;
	}

	public String toString() {
		return "becca.smp.NodeSet[nodes="
			+ nodes
			+ "]";
	}

	public double getMeanPriority() {
		if(!matched) {
			throw new IllegalStateException("NodeSet must be matched to have a mean priority!");
		}
		int totalPriority = 0;
		int matchedNodes = 0;
		for(Node n : nodes) {
			if(n.isMatched()) {
				totalPriority += n.getMatchPriority();
				matchedNodes++;
			}
		}
		return 1.0 + (double) totalPriority / matchedNodes;
	}

	/**
	 * a table displaying the id, match id, match priority, and array of
	 * priority ids
	 */
	public String getMatchStatus() {
		StringBuilder builder = new StringBuilder();
		builder.append(
			"Name        | Choice | Partner\n" +
			"------------+--------+------------\n");

		for(Node n : nodes) {
			if(n.isMatched()) {
				builder.append(String.format(
					"%11s | %6d | %s\n",
					n.name,
					n.getMatchPriority(),
					n.match.name));
			} else {
				builder.append(String.format(
					"%11s |   --   | nobody\n",
					n.name));
			}
		}

		builder.append("\n");
		builder.append(String.format("Mean choice = %f\n",
			getMeanPriority()));

		return builder.toString();
	}
}
