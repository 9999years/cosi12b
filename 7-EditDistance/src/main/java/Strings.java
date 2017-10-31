package becca.edit;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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

	public static String fromIntArray(int[] a) {
		return new String(a, 0, a.length);
	}

	public static int[] toIntArray(String A) {
		return A.codePoints().toArray();
	}

	public static List<Integer> toIntegerList(String A) {
		return A.codePoints().boxed().collect(Collectors.toList());
	}

	public static Iterator<Integer> iterator(String A) {
		return A.codePoints().boxed().iterator();
	}

	public static int length(String A) {
		return toIntArray(A).length;
	}

	public static boolean sameLength(String A, String B) {
		return length(A) == length(B);
	}

	public static int editDistance(String A, String B) {
		int[] a = toIntArray(A);
		int[] b = toIntArray(B);

		if(a.length != b.length) {
			throw new IllegalArgumentException(
				"Strings must be the same size!");
		}

		int dist = 0;
		for(int i = 0; i < a.length; i++) {
			if(a[i] != b[i]) {
				dist++;
			}
		}
		return dist;
	}

	public static boolean areNeighbors(String A, String B) {
		int[] a = toIntArray(A);
		int[] b = toIntArray(B);

		if(a.length != b.length) {
			return false;
		}

		int dist = 0;
		for(int i = 0; i < a.length; i++) {
			if(a[i] != b[i]) {
				dist++;
			}
			if(dist > 1) {
				return false;
			}
		}
		return dist == 1;
	}
}
