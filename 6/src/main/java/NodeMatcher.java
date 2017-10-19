package becca.smp;

import java.util.List;

public class NodeMatcher {
	/**
	 * return type ??
	 */
	public static void match(NodeSet A, NodeSet B) {
		Iterator<Node> unmatchedANodes = A.getUnmatchedNodes();
		unmatchedANodes.forEachRemaining(a -> {
			//topChoice = B.get(a.topChoice().id);
			topChoice = a.getTopChoice().node;
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
