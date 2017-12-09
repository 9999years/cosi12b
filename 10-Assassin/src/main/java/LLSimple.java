import java.util.*;
import java.util.function.*;

public class LLSimple {
	static void removerTest(Integer[] expected, Integer[] input, Function<LinkedList, Integer> remover) {
		LinkedList<Integer> list = new LinkedList<>();
		list.addAll(Arrays.asList(input));
		for(Integer e : expected) {
			remover.apply(list);
		}
	}

	static void removerTest(Integer[] expected, Integer[] input) {
		removerTest(expected, input, LinkedList<Integer>::pop);
		removerTest(expected, input, LinkedList<Integer>::remove);
		removerTest(expected, input, LinkedList<Integer>::removeFirst);
		removerTest(expected, input, LinkedList<Integer>::poll);
		removerTest(expected, input, LinkedList<Integer>::pollFirst);
	}

	static void removerTest() {
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
	public static void main(String[] args) {
		removerTest();
	}
}
