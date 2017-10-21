package becca.smp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NodeTest {
	void removePreferencesAfterTest(
			int[] expected, int[] priorities, int removeAfter) {
		Node n = new Node("");
		for(int i = 0; i < priorities.length; i++) {
			n.priorities.add(
				new NodePriority(i, new Node("", priorities[i]))
			);
		}
		n.removePreferencesAfter(new Node("", removeAfter));
		for(int i = 0; i < n.priorities.size(); i++) {
			assertEquals(expected[i], n.priorities.get(i).node.id,
				"checking inx " + i + " matches");
		}
	}

	@Test
	void removePreferencesAfterTest() {
		removePreferencesAfterTest(
			new int[] {0, 1, 2, 3, 4},
			new int[] {0, 1, 2, 3, 4, 5, 6},
			4
		);
		removePreferencesAfterTest(
			new int[] {0},
			new int[] {0, 1, 2, 3, 4, 5, 6},
			0
		);
		removePreferencesAfterTest(
			new int[] {0, 1, 2, 3, 4, 5, 6},
			new int[] {0, 1, 2, 3, 4, 5, 6},
			9
		);
	}
}
