package becca.smp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class NodePriorityTest {
	void equalsTest(int pA, int pB, int idA, int idB, boolean expected) {
		NodePriority a = new NodePriority(pA, new Node(null, null, idA));
		NodePriority b = new NodePriority(pB, new Node(null, null, idB));
		if(expected) {
			assertEquals(a, b);
		} else {
			assertNotEquals(a, b);
		}
	}

	@Test
	void equalsTest() {
		equalsTest(0, 0, 0, 0, true);
		equalsTest(1, 5, 0, 0, true);
		equalsTest(-12901, 5, 0, 0, true);
		equalsTest(3, 1, 9, 9, true);

		equalsTest(3, 1, 9, 8, false);
		equalsTest(0, 0, 0, 1, false);
		equalsTest(10, 10, 10, 11, false);
	}
}
