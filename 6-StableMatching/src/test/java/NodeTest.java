package becca.smp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class NodeTest {
	Node createNodeWithPriorities(int[] priorities) {
		// stub node
		Node n = new Node(null, null, 0);
		// create priorities
		for(int i = 0; i < priorities.length; i++) {
			n.priorities.add(
				new NodePriority(i, new Node(
					null, null, priorities[i]))
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

	void removePrioritiesAfterTest(
			int[] expected, int[] priorities, int removeAfter) {
		Node n = createNodeWithPriorities(priorities);
		// remove
		n.removePrioritiesAfter(new Node(null, null, removeAfter));
		// check
		checkPriorities(expected, n);
	}

	@Test
	void removePrioritiesAfterTest() {
		removePrioritiesAfterTest(
			new int[] {0, 1, 2, 3, 4},
			new int[] {0, 1, 2, 3, 4, 5, 6},
			4
		);
		removePrioritiesAfterTest(
			new int[] {0},
			new int[] {0, 1, 2, 3, 4, 5, 6},
			0
		);
		removePrioritiesAfterTest(
			new int[] {0, 1, 2, 3, 4, 5, 6},
			new int[] {0, 1, 2, 3, 4, 5, 6},
			9
		);
	}

	void removePriorityTest(
			int[] expected, int[] priorities, int remove) {
		Node n = createNodeWithPriorities(priorities);
		n.removePriority(new Node(null, null, remove));
		checkPriorities(expected, n);
	}

	@Test
	void removePriorityTest() {
		removePriorityTest(
			new int[] {0, 1, 2, 3, 5, 6},
			new int[] {0, 1, 2, 3, 4, 5, 6},
			4
		);
		removePriorityTest(
			new int[] {1, 2, 3, 4, 5, 6},
			new int[] {0, 1, 2, 3, 4, 5, 6},
			0
		);
		removePriorityTest(
			new int[] {0, 1, 2, 3, 4, 5, 6},
			new int[] {0, 1, 2, 3, 4, 5, 6},
			9
		);
	}

	void getTopChoiceTest(int expected, int[] priorities) {
		Node n = createNodeWithPriorities(priorities);
		assertEquals(expected, n.getTopChoice().node.id);
	}

	@Test
	void getTopChoiceTest() {
		getTopChoiceTest(0, new int[] {0, 1, 2, 3});
		getTopChoiceTest(9, new int[] {9, 8, 129});
		getTopChoiceTest(3, new int[] {3});
	}

	void equalsTest(Node a, Node b, boolean expected) {
		if(expected) {
			assertEquals(a, b);
		} else {
			assertNotEquals(a, b);
		}
	}

	@Test
	void equalsTest() {
		equalsTest(
			new Node(null, null, 0),
			new Node(null, null, 0),
			true);
		equalsTest(
			new Node(null, null, 1),
			new Node(null, null, 0),
			false);
		equalsTest(
			new Node(null, null, 999),
			new Node(null, null, 999),
			true);
		equalsTest(
			new Node("name1", new NodeSet(), 0),
			new Node("name2", new NodeSet(), 0),
			true);
		equalsTest(
			new Node("name1", new NodeSet(), 1),
			new Node("name2", new NodeSet(), 0),
			false);
	}
}
