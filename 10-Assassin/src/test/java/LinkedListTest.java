import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Deque;

public class LinkedListTest {
	void addTest(int[] expected, int[] input) {
		Deque<Integer> list = new LinkedList<>();
		for(int i : input) {
			list.add(i);
		}
		for(int e : expected) {
			assertEquals((Object) e, list.removeFirst());
		}
	}

	@Test
	void addTest() {
		addTest(
			new int[] { 0, 1, 2, 9, -1929 },
			new int[] { 0, 1, 2, 9, -1929 }
		);
	}
}
