import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.ListIterator;

public class LinkedListTest {
	// super low quality random numbers
	int[] numbers1 = new int[] {
		46, 82, 11, 81, 33, 55, 97, 48, 75, 5, 70, 16, 1, 13, 45,
		69, 97, 81, 7, 87, 99, 87, 92, 67, 11, 97, 2, 32,
		79, 2, 60, 26, 42, 0, 53, 9, 28, 70, 93, 72, 92, 59
	};

	int[] numbers2 = new int[] {
		46, 82, 11, 81, 33, 55, 97, 48, 75, 5, 70, 16, 1, 13, 45,
		69, 97, 81, 7, 87, 99, 87, 92, 67, 11, 97, 2, 32, 79, 2, 60,
		26, 42, 0, 53, 9, 28, 70, 93, 72, 92, 59, 68, 6, 74, 20, 42,
		35, 2, 39, 77, 97, 65, 0, 21, 53, 80, 77, 40, 14, 32, 42,
		45, 34, 70, 16, 26, 97, 0, 58, 21, 22, 79, 96, 77, 60, 5,
		62, 40, 22, 33, 22, 39, 53, 88, 82, 70, 84, 24, 1, 95, 31,
		19, 52, 7, 14, 89, 52, 2, 98, 88, 60, 56, 17, 76, 23, 13,
		53, 93, 66, 49, 8, 57, 21, 61, 47, 73, 19, 27, 45, 99, 28,
		49, 11, 79, 18, 90, 84, 72, 96, 13, 52, 13, 51, 22, 5, 1,
		85, 11, 31, 95, 18, 99, 75, 44, 44, 17, 17, 86, 12, 38, 21,
		87, 38, 36, 4, 20, 88, 53, 9, 15, 40, 34, 64, 75, 11, 43,
		98, 31, 80, 18, 6, 69, 84, 77, 80, 90, 29, 42, 50, 25, 10,
		34, 17, 93, 37, 64, 40, 72, 28, 88, 96, 14, 65, 50, 35, 0,
		1, 1, 9, 71, 23, 33, 61, 17, 38, 88, 35, 35, 9, 20, 64, 73,
		77, 63, 75, 7, 57, 51, 62, 28, 28, 12, 10, 61, 42, 27, 1, 2,
		8, 1, 85, 52, 3, 60, 40, 18, 37, 50, 62, 95, 9, 32, 92, 75,
		68, 49, 4, 43, 50, 76, 87, 10, 2, 87, 97, 10, 54, 62, 73,
		47, 47, 0, 30, 66, 20, 67, 30, 80, 42, 69, 4, 18, 91, 73,
		29, 81, 9, 31, 27, 23, 40, 84, 89, 24, 74, 27, 56, 41, 38,
		64, 79, 14, 48, 76, 37, 55, 46, 94, 53, 62, 65, 41, 55, 50,
		72, 51, 30, 93, 4, 35, 72, 58, 6, 85, 83, 0, 78, 46, 45, 45,
		17, 16, 34, 52, 19, 97, 26, 51, 24, 96, 89, 89, 72, 97, 54,
		71, 45, 33, 26, 25, 61, 62, 60, 54, 36, 61, 59, 59, 41, 25,
		31, 69, 64, 16, 20, 90, 13, 7, 3, 6, 61, 23, 17, 57, 65, 34,
		55, 62, 94, 83, 70, 52, 74, 84, 72, 97, 16, 49, 95, 23, 83,
		63, 54, 3, 27, 0, 10, 81, 2, 48, 87, 81, 94, 0, 0, 70, 74,
		79, 1, 90, 38, 15, 76, 23, 49, 29, 99, 82, 10, 69, 20, 9,
		21, 13, 76, 55, 28, 49, 7, 5, 19, 65, 51, 31, 36, 1, 44, 82,
		40, 6, 28, 76, 92, 49, 98, 55, 69, 30, 92, 7, 82, 74, 89,
		66, 35, 86, 76, 0, 71, 71, 24, 74, 66, 44, 81, 64, 61, 15
	};

	int[] numbers3 = new int[] {
		70, 31, 99, 55, 31, 50, 68, 0, 43, 33, 60, 75, 97, 60, 37,
		61, 77, 54, 68, 74, 73, 65, 94, 35, 76, 93, 78, 95, 91, 61,
		98, 47, 65, 67, 57, 26, 79, 68, 29, 66, 60, 72, 87, 39, 2,
		36, 38, 76, 2, 55, 9, 72, 45, 30, 60, 5, 82, 11, 61, 63, 65,
		11, 51, 13, 28, 15, 62, 29, 0, 17, 12, 36, 87, 19, 87, 58,
		53, 30, 51, 86, 99, 49, 39, 70, 21, 75, 98, 46, 99, 36, 38,
		63, 20, 97, 17, 41, 50, 99, 49, 86, 76, 12, 86, 86, 86, 31,
		69, 45, 71, 8, 81, 72, 6, 65, 17, 31, 14, 7, 51, 49, 11, 44,
		48, 68, 8, 61, 91, 85, 65, 98, 97, 22, 5, 80, 37, 86, 46,
		85, 75, 38, 57, 30, 36, 8, 61, 15, 67, 10, 18, 5, 75, 32,
		56, 55, 90, 79, 71, 31, 62, 48, 5, 10, 6, 29, 16, 23, 16,
		63, 91, 86, 41, 32, 87, 11, 41, 65, 93, 97, 3, 99, 34, 41,
		65, 94, 25, 83, 6, 74, 27, 41, 77, 62, 17, 44, 69, 72, 34,
		34, 35, 92, 93, 1, 50, 90, 26, 95, 81, 71, 24, 78, 76, 91,
		8, 61, 34, 20, 81, 6, 50, 17, 61, 90, 56, 59, 14, 55, 74,
		25, 49, 60, 68, 24, 30, 57, 51, 31, 25, 85, 14, 57, 75, 24,
		70, 13, 95, 12, 36, 64, 47, 33, 13, 76, 94, 55, 26, 28, 5,
		60, 73, 46, 39, 68, 85, 33, 73, 33, 20, 81, 42, 1, 83, 98,
		59, 24, 25, 83, 82, 12, 20, 12, 2, 76, 10, 83, 32, 59, 1,
		87, 77, 63, 70, 59, 28, 81, 46, 42, 55, 38, 49, 10, 66, 98,
		68, 11, 45, 17, 30, 91, 14, 84, 5, 52, 21, 77, 98, 44, 11,
		44, 26, 80, 19, 7, 49, 16, 82, 61, 25, 86, 70, 92, 31, 12,
		84, 47, 41, 35, 48, 77, 87, 10, 72, 45, 91, 68, 11, 95, 7,
		66, 10, 79, 83, 37, 20, 17, 23, 10, 70, 20, 88, 99, 80, 80,
		22, 9, 0, 16, 41, 73, 94, 41, 38, 22, 51, 58, 4, 20, 89, 97,
		43, 56, 86, 70, 36, 80, 43, 20, 33, 66, 2, 36, 78, 64, 46,
		70, 89, 9, 43, 95, 12, 65, 60, 72, 30, 30, 68, 21, 49, 1,
		11, 19, 42, 12, 71, 7, 47, 60, 30, 95, 89, 33, 10, 84, 71,
		65, 43, 73, 84, 18, 80, 33, 44, 86, 21, 4, 55, 1, 41, 46,
		13, 34, 6, 75, 30, 23, 73, 74, 82, 86, 78, 88, 46, 44, 38,
		68, 88, 30, 56, 9, 17, 84, 26, 96, 71, 78, 66, 1, 87, 55,
		51, 21, 50, 16, 0, 60, 29, 26, 19, 49, 88, 16, 74, 76, 14,
		66, 92, 24, 24, 72, 36, 25, 33, 34, 93, 20, 71, 12, 80, 6,
		23, 97, 13, 14, 73, 66, 71, 50, 15, 42, 41, 16, 76, 96, 80,
		76, 60, 67, 52, 43, 95, 7, 33, 17, 22, 55, 5, 45, 87, 68,
		78, 84, 3, 2, 59, 86, 26, 44, 82, 87, 21, 26, 71, 78
	};

	int[] numbers4 = new int[] { };
	int[] numbers5 = new int[] { 0, 1, 2, 9, -1929 };
	int[] numbers6 = new int[] { 4574 };

	// large
	int[] numbers7 = new int[] {
		-47213, -2720, -70973, -13345, 78590, 24782, 95603, -47864,
		-8482, 33537, 48241, -26363, 31811, -79587, 89727, 92298,
		-75600, -28021, 15496, 49950, -14363, -55650, 24166, -97619,
		-31501, -39970, -16778, -78949, 53814, -69167, 2867, 20262,
		12979, 96138, -28348, 37226, -93433, 50414, -59573, 82982,
		-12107, -50518, -55096, 90316, 92861, -30850, -34047,
		-18991, -4661, -25659, -5689, 78391, -1030, -27725, 50057,
		-267, -10340, -83383, -48850, -24068, -51806, 73706, -67109,
		15743, 13615, 15688, 46265, -68742, -17744, -62110, -73090,
		21011, 42262, -97074, 22240, -68063, -84325, -82278, -41580,
		-88011, 67423, -74362, -37781, -38371, -71326, 69009,
		-62642, 89723, -87227, 4306, 73450, 99339, 75052, 26276,
		9195, -83976, -66808, 37444, 40150, 36455, -63222
	};

	// HUGE
	int[] numbers8 = new int[] {
		2141189727, 1963605963, 783498319, 1701260636, 1765489994,
		1933443289, 258168141, 1975481639, 418860235, 1017993252,
		1501404128, 227608783, 1022964812, 521546890, 97948355,
		1407375095, 549721842, 310321518, 1975664599, 282684906,
		534392402, 1228024760, 1765035350, 1193337125, 1902366823,
		389091032, 1950610549, 1257321748, 1923648557, 370327224,
		950394968, 1287301870, 64725458, 1579091381, 22437409,
		1817697516, 79444263, 1829667893, 977305537, 1964229413,
		1116992745, 807022108, 52159024, 1087620160, 802096366,
		1422548673, 1448413065, 1082077687, 1985041078, 526196629,
		1841046260, 1812339384, 778222411, 345280994, 52399747,
		987592604, 1779483268, 1513898461, 328094725, 1106035409,
		1161073780, 1192399790, 990124797, 120020062, 1195314168,
		1754331054, 902822527, 11144133, 579047341, 1330176141,
		867741962, 1714018658, 498889357, 1685336103, 678785932,
		1475166189, 1080501132, 1674181138, 1833677920, 334634367,
		1833262237, 223944852, 1381156224, 468362721, 161299519,
		336284340, 1041007786, 889856896, 673050037, 1644203650,
		52480184, 1234267267, 883347602, 634827384, 1529746549,
		1062700404, 660063993, 1781938295, 209945370, 2066704996,
		594492302
	};

	int[][] numbers = new int[][] {
		numbers1,
		numbers2,
		numbers3,
		numbers4,
		numbers5,
		numbers6,
		numbers7,
		numbers8
	};

	Integer[] boxed(int[] ints) {
		return IntStream.of(ints).boxed().toArray(Integer[]::new);
	}

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

	void removerTest(int[] expected, int[] input, Function<LinkedList, Integer> remover) {
		LinkedList<Integer> list = new LinkedList<>();
		list.addAll(Arrays.asList(boxed(input)));
		for(int e : expected) {
			assertEquals((Object) e, remover.apply(list));
		}
	}

	void removerTest(int[] expected, int[] input) {
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
		for(int[] nrs : numbers) {
			removerTest(nrs, nrs);
		}
	}

	void peekLastTest(LinkedList<Integer> list, int expected, Function<LinkedList, Integer> peeker) {
		assertEquals((Object) expected, peeker.apply(list));
	}

	void peekLastTest(LinkedList<Integer> list, int expected) {
		peekLastTest(list, expected, LinkedList<Integer>::getLast);
		peekLastTest(list, expected, LinkedList<Integer>::peekLast);
	}

	void peekTest(LinkedList<Integer> list, int expected, Function<LinkedList, Integer> peeker) {
		assertEquals((Object) expected, peeker.apply(list));
	}

	void peekTest(LinkedList<Integer> list, int expected) {
		peekTest(list, expected, LinkedList<Integer>::getFirst);
		peekTest(list, expected, LinkedList<Integer>::element);
		peekTest(list, expected, LinkedList<Integer>::peek);
		peekTest(list, expected, LinkedList<Integer>::peekFirst);
	}

	void adderTest(int[] expected, int[] input, BiConsumer<LinkedList, Integer> adder) {
		LinkedList<Integer> list = new LinkedList<>();
		for(int i : input) {
			adder.accept(list, i);
			peekLastTest(list, i);
		}
		for(int e : expected) {
			peekTest(list, e);
			assertEquals((Object) e, list.removeFirst());
		}
	}

	void adderTest(int[] expected, int[] input) {
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
		for(int[] nrs : numbers) {
			adderTest(nrs, nrs);
		}
	}

	void pusherTest(int[] expected, int[] input, BiConsumer<LinkedList, Integer> pusher) {
		LinkedList<Integer> list = new LinkedList<>();
		for(int i : input) {
			pusher.accept(list, i);
		}
		for(int e : expected) {
			assertEquals((Object) e, list.removeLast());
		}
	}

	void pusherTest(int[] expected, int[] input) {
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
		for(int[] nrs : numbers) {
			pusherTest(nrs, nrs);
		}
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
		for(int[] nrs : numbers) {
			addRemoveTest(nrs, nrs);
		}
	}

	void listIteratorTest(int[] expected) {
		LinkedList<Integer> list = new LinkedList<>();
		list.addAll(Arrays.asList(boxed(expected)));
		ListIterator<Integer> itr = list.listIterator();
		assertFalse(itr.hasPrevious(), "no hasPrevious at start");
		assertEquals(-1, itr.previousIndex(), "previousIndex at start");
		assertEquals(0, itr.nextIndex(), "nextIndex at start");

		if(list.size() > 0) {
			assertTrue(itr.hasNext(), "hasNext at start (size > 0)");
		} else {
			assertFalse(itr.hasNext(), "no hasNext at start (size == 0)");
			// quit early; nothing to do
			return;
		}

		// traverse forwards
		int i = 0;
		while(itr.hasNext()) {
			assertEquals(i, itr.nextIndex(),
				"index (forward traversal)");
			assertEquals((Object) expected[i], itr.next(),
				"element (forward traversal)");
			i++;
		}
		// index is actually past the end of the list (last index is
		// list.size() - 1 but we've incremented again)
		assertEquals(list.size(), i, "index == size after forward traversal");
		assertFalse(itr.hasNext(), "no hasNext at end");
		assertTrue(itr.hasPrevious(), "hasPrevious at end");

		// and backwards!
		i = list.size() - 1;
		while(itr.hasPrevious()) {
			assertEquals(i, itr.previousIndex(),
				"index (backwards traversal)");
			assertEquals((Object) expected[i], itr.previous(),
				"element (backward traversal)");
			i--;
		}
		assertEquals(-1, i, "index after backwards traversal");
		assertFalse(itr.hasPrevious(),
			"no hasPrevious at start after backwards traversal");
		assertTrue(itr.hasNext(),
			"hasNext at start after backwards traversal");
	}

	@Test
	void listIteratorTest() {
		for(int[] nrs : numbers) {
			listIteratorTest(nrs);
		}
	}

	// TO TEST:
	// descendingIterator()
	// operateOnFirst(Object o, Consumer<E> operation)
	// iterator()
	// listIterator()
	// contains(Object o)
	// isEmpty()
	// size()
	// toArray(T[] a)
	// toArray()
	// addAll(Iterable<? extends E> c)
	// addAll(Collection<? extends E> c)
	// clear()
	// remove(Object o, Iterator<E> itr)
	// remove(Object o)
	// removeFirstOccurrence(Object o)
	// removeLastOccurrence(Object o)
}
