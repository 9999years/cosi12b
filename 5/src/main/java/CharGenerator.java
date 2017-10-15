package becca.markov;

import java.lang.Math;
import java.lang.StringBuilder;

public class CharGenerator extends MarkovGenerator<Integer> {
	CharGenerator(String source, int k, int seed) {
		this(source, k);
		super.seed(seed);
	}

	CharGenerator(String source, int k) {
		super(source.codePoints().boxed().toArray(Integer[]::new), k);
	}

	int getNextChar() {
		return super.next();
	}

	/**
	 * get the string representation of the next n characters from the
	 * generator
	 */
	String next(int n) {
		StringBuilder ret = new StringBuilder();
		for(; n > 0; n--) {
			ret.append(Character.toChars(getNextChar()));
		}
		return ret.toString();
	}
}
