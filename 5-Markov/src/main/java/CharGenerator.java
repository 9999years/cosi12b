package becca.markov;

import java.lang.StringBuilder;

/**
 * markov chain class for strings; operates on characters internally because
 * java `String`s, due to being encoded in utf-16, encode codepoints above
 * U+FFFF as two 2-byte `char`s; this means that:
 *
 * 1. a String of .length() N is not guarenteed to be N codepoints long
 * 2. the N-th character of a String S is not guarenteed to be retrieved with
 *    S[N]
 * 3. a java `char` cannot represent any Unicode codepoint
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class CharGenerator extends MarkovGenerator<Integer> {
	CharGenerator(String source, int k, int seed) {
		this(source, k);
		seed(seed);
	}

	CharGenerator(String source, int k) {
		// convert source string to Integer array of codepoints
		super(source.codePoints(), k);
	}

	int getNextChar() {
		return super.next();
	}

	/**
	 * get the string representation of the next n codepoints from the
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
