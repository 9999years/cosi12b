package becca.edit;

import java.util.Iterator;
import java.lang.Iterable;

import java.util.stream.IntStream;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.Arrays;

public class BiStringIterator
		implements Iterable<BiCodePoint>, Iterator<BiCodePoint> {
	protected Iterator<Integer> iterA;
	protected Iterator<Integer> iterB;
	protected int index;

	BiStringIterator(String A, String B) {
		iterA = A.codePoints().iterator();
		iterB = B.codePoints().iterator();
	}

	BiStringIterator(int[] A, int[] B) {
		iterA = Arrays.stream(A).boxed().iterator();
		iterB = Arrays.stream(B).boxed().iterator();
	}

	public boolean hasNext() {
		return iterA.hasNext() && iterB.hasNext();
	}

	public BiCodePoint next() {
		return new BiCodePoint(iterA.next(), iterB.next(), index++);
	}

	public Iterator<BiCodePoint> iterator() {
		return this;
	}

	/**
	 * @param func a curried codePointA -> codePointB -> Index
	 */
	public void forEachRemaining(
			//       cpA               cpB               index
			Function<Integer, Function<Integer, Consumer<Integer>>>
			func) {
		while(hasNext()) {
			next().apply(func);
		}
	}
}
