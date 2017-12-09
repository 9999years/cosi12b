import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.Arrays;

public class LinkedListTest {
	/**
	 * basic operations
	 */
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

	/**
	 * tests all remove from front methods:
	 * pop, remove, removefirst, poll, and pollfirst
	 */
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
			new Integer[] { 1, 9 },
			new Integer[] { 1, 9 }
		);

		removerTest(
			new Integer[] { 4574 },
			new Integer[] { 4574 }
		);
	}

	void peekLastTest(LinkedList<Integer> list, int expected, Function<LinkedList, Integer> peeker) {
		assertEquals((Object) expected, peeker.apply(expected));
	}

	void peekLastTest(LinkedList<Integer> list, int expected) {
		peekLastTest(list, expected, LinkedList<Integer>::getLast);
		peekLastTest(list, expected, LinkedList<Integer>::peekLast);
	}

	void peekTest(LinkedList<Integer> list, int expected) {
		peekTest(list, expected, LinkedList<Integer>::getFirst);
		peekTest(list, expected, LinkedList<Integer>::element);
		peekTest(list, expected, LinkedList<Integer>::peek);
		peekTest(list, expected, LinkedList<Integer>::peekFirst);
	}

	void adderTest(Integer[] expected, Integer[] input, BiConsumer<LinkedList, Integer> adder) {
		LinkedList<Integer> list = new LinkedList<>();
		for(int i : input) {
			adder.accept(list, i);
			peekLastTest(list, i);
		}
		for(int e : expected) {
			assertEquals((Object) e, list.removeFirst());
			peekTest(list, i);
		}
	}

	void adderTest(Integer[] expected, Integer[] input) {
		adderTest(expected, input, LinkedList<Integer>::add);
		adderTest(expected, input, LinkedList<Integer>::addLast);
		adderTest(expected, input, LinkedList<Integer>::offerLast);
		adderTest(expected, input, LinkedList<Integer>::offer);
	}

	/**
	 * tests all add at end methods:
	 * add, addLast, offer, and offerLast
	 *
	 * ALSO tests the peek at end methods:
	 */
	@Test
	void adderTest() {
		adderTest(
			new Integer[] { 0, 1, 2, 9, -1929 },
			new Integer[] { 0, 1, 2, 9, -1929 }
		);

		adderTest(
			new Integer[] { },
			new Integer[] { }
		);

		adderTest(
			new Integer[] { 1, 9 },
			new Integer[] { 1, 9 }
		);

		adderTest(
			new Integer[] { 4574 },
			new Integer[] { 4574 }
		);
	}

	void pusherTest(Integer[] expected, Integer[] input, BiConsumer<LinkedList, Integer> pusher) {
		LinkedList<Integer> list = new LinkedList<>();
		for(int i : input) {
			pusher.accept(list, i);
		}
		for(int e : expected) {
			assertEquals((Object) e, list.removeFirst());
		}
	}

	void pusherTest(Integer[] expected, Integer[] input) {
		pusherTest(expected, input, LinkedList<Integer>::addFirst);
		pusherTest(expected, input, LinkedList<Integer>::offerFirst);
		pusherTest(expected, input, LinkedList<Integer>::push);
	}

	/**
	 * tests all add at front methods:
	 * addFirst, offerFirst, push
	 */
	@Test
	void pusherTest() {
		pusherTest(
			new Integer[] { 0, 1, 2, 9, -1929 },
			new Integer[] { 0, 1, 2, 9, -1929 }
		);

		pusherTest(
			new Integer[] { },
			new Integer[] { }
		);

		pusherTest(
			new Integer[] { 1, 9 },
			new Integer[] { 1, 9 }
		);

		pusherTest(
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
			peekTest(list, e);
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
