import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Deque;

public class LinkedListTest {
	void addTest(int[] expected, int[] input) {
		LinkedList<Integer> list = new LinkedList<>();
		for(int i : input) {
			list.add(i);
		}
		for(int e : expected) {
			assertEquals((Object) e, list.poll());
		}
	}

	void addTest() {
		addTest(
			new int[] { 0, 1, 2, 9, -1929 },
			new int[] { 0, 1, 2, 9, -1929 }
		);
	}

	@Test
	void simpleTest1() {
		LinkedList<Integer> list = new LinkedList<>();
		assertEquals((Object) 0, list.size());
		assertEquals(null, list.peek(), "peek (null)");
		list.add(100);
		assertEquals((Object) 1, list.size(), "size");
		assertEquals((Object) 100, list.head.next.value, "head.next");
		assertEquals((Object) 100, list.tail.previous.value, "tail.next");
		assertEquals((Object) 100, list.peek(), "peek");
	}
}
