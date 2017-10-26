package becca.edit;

import java.util.function.Function;
import java.util.function.Consumer;

class CodePoint {
	int value;
	int index;

	CodePoint(int value, int index) {
		this.value = value;
		this.index = index;
	}

	/**
	 * @param func a curried codePointA -> codePointB -> Index
	 */
	public void apply(
			//       cp                index
			Function<Integer, Consumer<Integer>> func) {
		func
			.apply(value)
			.accept(index);
	}

	/**
	 * @param func a curried codePointA -> codePointB -> Index
	 */
	public <T> T extract(
			//       cp                index
			Function<Integer, Function<Integer, T>> func) {
		return func
			.apply(value)
			.apply(index);
	}
}
