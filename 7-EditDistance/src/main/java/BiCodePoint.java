package becca.edit;

import java.util.function.Function;
import java.util.function.Consumer;


class BiCodePoint {
	int a;
	int b;
	int index;

	BiCodePoint(int a, int b, int index) {
		this.a = a;
		this.b = b;
		this.index = index;
	}

	/**
	 * @param func a curried codePointA -> codePointB -> Index
	 */
	public void apply(
		//       cpA      ->       cpB      ->       index
		Function<Integer, Function<Integer, Consumer<Integer>>>
		func) {
		func
			.apply(a)
			.apply(b)
			.accept(index);
	}

	/**
	 * @param func a curried codePointA -> codePointB -> Index
	 */
	public <T> T extract(
		//       cpA      ->       cpB      ->       index
		Function<Integer, Function<Integer, Function<Integer, T>>>
		func) {
		return func
			.apply(a)
			.apply(b)
			.apply(index);
	}
}
