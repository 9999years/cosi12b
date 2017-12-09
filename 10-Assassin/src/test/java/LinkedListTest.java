import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.Arrays;

public class LinkedListTest {
	@Test
	void simpleTest1() {
		LinkedList<Integer> list = new LinkedList<>();
		assertEquals((Object) 0, list.size());
		assertEquals(list.tail.previous.value, list.head.next.value,
			"asserting head and tail point towards each other");
		assertEquals(null, list.peek(), "peek (null)");
		list.add(100);
		assertEquals((Object) 1, list.size(), "size");
		assertEquals((Object) 100, list.head.next.value, "head.next");
		assertEquals((Object) 100, list.tail.previous.value, "tail.next");
		assertEquals((Object) 100, list.peek(), "peek");
	}

	void removerTest(Integer[] expected, Integer[] input, Function<LinkedList, Integer> remover) {
		LinkedList<Integer> list = new LinkedList<>();
		list.addAll(Arrays.asList(input));
		for(int e : expected) {
			assertEquals((Object) e, remover.apply(list));
		}
	}

	void removerTest(Integer[] expected, Integer[] input) {
		removerTest(expected, input, LinkedList<Integer>::pop);
		removerTest(expected, input, LinkedList<Integer>::remove);
		removerTest(expected, input, LinkedList<Integer>::removeFirst);
		removerTest(expected, input, LinkedList<Integer>::poll);
		removerTest(expected, input, LinkedList<Integer>::pollFirst);
	}

	@Test
	void removerTest() {
		removerTest(
			new Integer[] { 0, 1, 2, 9, -1929 },
			new Integer[] { 0, 1, 2, 9, -1929 }
		);

		removerTest(
			new Integer[] { },
			new Integer[] { }
		);

		removerTest(
			new Integer[] { 1, 9, null, null, null, null },
			new Integer[] { 1, 9 }
		);

		removerTest(
			new Integer[] { 4574 },
			new Integer[] { 4574 }
		);
	}

	/**
	 * also tests the peek methods
	 */
	void addRemoveTest(int[] expected, int[] input) {
		LinkedList<Integer> list = new LinkedList<>();
		for(int i : input) {
			list.add(i);
		}
		for(int e : expected) {
			// WHY DOES THE DEQUE INTERFACE HAVE SO MANY
			// METHODS!!! THAT ALL DO THE SAME THING!!!
			assertEquals((Object) e, list.peek());
			assertEquals((Object) e, list.peekFirst());
			assertEquals((Object) e, list.getFirst());
			assertEquals((Object) e, list.element());
			assertEquals((Object) e, list.poll());
		}
	}

	@Test
	void addRemoveTest() {
		addRemoveTest(
			new int[] { 0, 1, 2, 9, -1929 },
			new int[] { 0, 1, 2, 9, -1929 }
		);

		addRemoveTest(
			new int[] { },
			new int[] { }
		);

		addRemoveTest(
			new int[] { 4574 },
			new int[] { 4574 }
		);
	}
}
