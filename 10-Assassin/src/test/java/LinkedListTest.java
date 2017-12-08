import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.unit.Dequeue;

public class LinkedListTest {
	@Test
	void addTest() {
		Deque<Integer> list = new LinkedList<>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(9);
		list.add(-1929);
		assertEquals(0, list.removeFirst());
		assertEquals(1, list.removeFirst());
		assertEquals(2, list.removeFirst());
		assertEquals(9, list.removeFirst());
		assertEquals(-1929, list.removeFirst());
	}
}
