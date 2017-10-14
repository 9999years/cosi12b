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
		System.out.println(getPossibilities);
		return super.next();
	}
}
