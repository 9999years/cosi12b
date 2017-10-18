package becca.smp;

import java.util.List;

public class NodeMatcher {
	/**
	 * return type ??
	 */
	public static void match(NodeSet A, NodeSet B) {
		List<Node> unmatchedANodes = A.getUnmatchedNodes();
		while(unmatchedANodes.size() > 0) {
			for(Node a : unmatchedANodes) {
				//topChoice = B.get(a.topChoice().id);
				topChoice = a.topChoice().node;
				if(topChoice.isMatched()) {
					// unmatches both
					topChoice.unmatch();
				}
				topChoice.match(a);
				// doubly-remove links
				topChoice.removePreferencesAfter(a.id);
			}
			unmatchedANodes = a.getUnmatchedNodes();
		}
	}
}
