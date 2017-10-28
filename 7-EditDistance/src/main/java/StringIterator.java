package becca.edit;

import java.util.Iterator;
import java.lang.Iterable;

import java.util.stream.IntStream;
import java.util.function.Function;
import java.util.function.Consumer;

public class StringIterator
		implements Iterable<CodePoint>, Iterator<CodePoint> {
	protected Iterator<Integer> iter;
	protected int index;

	StringIterator(String source) {
		iter = source.codePoints().iterator();
	}

	public boolean hasNext() {
		return iter.hasNext();
	}

	public CodePoint next() {
		return new CodePoint(iter.next(), index++);
	}

	public Iterator<CodePoint> iterator() {
		return this;
	}

	/**
	 * @param func a curried codePointA -> codePointB -> Index
	 */
	public void forEachRemaining(
			//       cp                index
			Function<Integer, Consumer<Integer>> func) {
		while(hasNext()) {
			next().apply(func);
		}
	}
}
