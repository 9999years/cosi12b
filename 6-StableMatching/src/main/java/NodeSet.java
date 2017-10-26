package becca.smp;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Function;

import java.lang.StringBuilder;

/**
 * a set of nodes; generally created by a NodeSetFactory
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class NodeSet {
	public static final int DEFAULT_CAPACITY = 10;
	public static final String TABLE_NAME_HEADER = "Name";
	public static final String TABLE_PRIORITY_HEADER = "Choice";
	public static final String TABLE_MATCH_HEADER = "Partner";

	/**
	 * tinker with this responsibly!
	 */
	List<Node> nodes;
	protected NodeSet other;
	protected boolean matched;

	public NodeSet getOtherSet() {
		return other;
	}

	public boolean isMatched() {
		return matched;
	}

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
		return new NodeIterator(nodes,
			n -> !n.isMatched() && n.getPriorities().size() > 0);
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
			// get a's top choice
			Node topChoice = a.getTopChoice().getNode();
			// unmatches and then matches
			topChoice.match(a);
			// doubly-remove links
			topChoice.removePrioritiesAfter(a);
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
		return (double) totalPriority / matchedNodes;
	}

	/**
	 * precondition: nodes is not empty
	 */
	protected String getLongestName() {
		Function<Node, Integer> nodeLength =
			n -> n.getName().toString().length();
		return nodes.stream()
			.max((a, b) ->
				nodeLength.apply(a) - nodeLength.apply(b))
			.get()
			.getName()
			.toString();
	}

	/**
	 * a table displaying the id, match id, match priority, and array of
	 * priority ids
	 */
	public String getMatchStatus() {
		int nameWidth = Math.max(
			TABLE_NAME_HEADER.length(),
			getLongestName().length());

		int choiceWidth = (int) Math.max(
			TABLE_PRIORITY_HEADER.length(),
			// number of DIGITS in largest possible choice
			// field (max size of node list).
			// really this is only relevant with >=100,000
			// nodes.... but better safe than sorry!!
			Math.floor(Math.log10(nodes.size())) + 1);

		String formatString =
			"%"
			+ nameWidth
			+ "s   %"
			// length of "Choice" OR
			+ choiceWidth
			+ "s   %s\n";

		String header = String.format(
			formatString,
			TABLE_NAME_HEADER,
			TABLE_PRIORITY_HEADER,
			TABLE_MATCH_HEADER);

		// i had a generic repeatString function that i immediately
		// applied with a "-" to get a repeatDash but it seemed like
		// overkill
		Function<Integer, String> repeatDash = count ->
			String.join("", Collections.nCopies(count, "-"));

		StringBuilder builder = new StringBuilder();

		builder.append(header);
		builder.append(repeatDash.apply(header.length()) + "\n");

		for(Node n : nodes) {
			if(n.isMatched()) {
				builder.append(String.format(
					formatString,
					n.getName(),
					n.getMatchPriority(),
					n.getMatch().getName()));
			} else {
				builder.append(String.format(
					formatString,
					n.getName(),
					"--",
					"nobody"));
			}
		}

		builder.append("\n");
		builder.append(String.format("Mean choice = %f\n",
			getMeanPriority()));

		return builder.toString();
	}
}
