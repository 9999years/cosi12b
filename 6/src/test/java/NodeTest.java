package becca.smp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class NodeTest {
	Node createNodeWithPriorities(int[] priorities) {
		// stub node
		Node n = new Node(null);
		// create priorities
		for(int i = 0; i < priorities.length; i++) {
			n.priorities.add(
				//               priority        id
				new NodePriority(i, new Node(null, priorities[i]))
			);
		}
		return n;
	}

	void checkPriorities(int[] expected, Node n) {
		Iterator<NodePriority> itr = n.priorities.iterator();
		for(int i = 0; itr.hasNext(); i++) {
			assertEquals(expected[i], itr.next().node.id,
				"checking inx " + i + " matches");
		}
	}

	void removePreferencesAfterTest(
			int[] expected, int[] priorities, int removeAfter) {
		Node n = createNodeWithPriorities(priorities);
		// remove
		n.removePreferencesAfter(new Node(null, removeAfter));
		// check
		checkPriorities(expected, n);
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

	void removePreferenceTest(
			int[] expected, int[] priorities, int remove) {
		Node n = createNodeWithPriorities(priorities);
		System.out.println("BEGIN");
		System.out.println(n.priorities);
		System.out.println("removing " + remove);
		n.removePreference(new Node(null, remove));
		System.out.println(n.priorities);
		checkPriorities(expected, n);
	}

	@Test
	void removePreferenceTest() {
		removePreferenceTest(
			new int[] {0, 1, 2, 3, 5, 6},
			new int[] {0, 1, 2, 3, 4, 5, 6},
			4
		);
		removePreferenceTest(
			new int[] {1, 2, 3, 4, 5, 6},
			new int[] {0, 1, 2, 3, 4, 5, 6},
			0
		);
		removePreferenceTest(
			new int[] {0, 1, 2, 3, 4, 5, 6},
			new int[] {0, 1, 2, 3, 4, 5, 6},
			9
		);
	}

	void getTopChoiceTest(int expected, int[] priorities) {
		Node n = createNodeWithPriorities(priorities);
		assertEquals(expected, n.getTopChoice());
	}

	@Test
	void getTopChoiceTest() {
		getTopChoiceTest(0, new int[] {0, 1, 2, 3});
		getTopChoiceTest(9, new int[] {9, 8, 129});
		getTopChoiceTest(9, new int[] {3});
	}
}
