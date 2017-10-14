package becca.markov;

public class CharGenerator extends MarkovGenerator<Integer> {
	CharGenerator(String source, int k, int seed) {
		this(source, k);
		super.seed(seed);
	}

	CharGenerator(String source, int k) {
		super(source.codePoints().boxed().toArray(Integer[]::new), k);
	}

	int getNextChar() {
		int n = super.next();
		System.out.println("seed is: "
			+ new String(
				corpus.subList(inx - length, inx)
					.stream()
					.mapToInt(i -> i)
					.toArray(),
				0, length)
		);
		return n;
	}
}
