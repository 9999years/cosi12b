package becca.edit;

import java.util.Arrays;

public class Strings {
	/**
	 * returns a string identical to A with the indexth codepoint inserted
	 * from B
	 *
	 * precondition: A and B are at least index codepoints long
	 */
	public static String swapN(int index, String A, String B) {
		// offset of n-th codepoint in the underlying
		// char[]
		return swapN(index, A,
			B.codePointAt(B.offsetByCodePoints(0, index)));
	}

	/**
	 * returns a string identical to A with the indexth codepoint replaced
	 * with `newCodepoint`
	 *
	 * precondition: A is at least index codepoints long
	 */
	public static String swapN(int index, String A, int newCodepoint) {
		return swapN(index, A.codePoints().toArray(), newCodepoint);
	}

	/**
	 * @param A a codepoint-array corresponding to a string
	 */
	public static String swapN(int index, int[] A, int newCodepoint) {
		// make sure we don't mess up the user's array
		int[] a = Arrays.copyOf(A, A.length);
		a[index] = newCodepoint;
		return new String(a, 0, a.length);
	}
}
