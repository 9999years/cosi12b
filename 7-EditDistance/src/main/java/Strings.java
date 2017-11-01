/**
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

package becca.edit;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * utility class of string methods, specialized for exclusively dealing with
 * (effectively utf-32) codepoints rather than (utf-16) chars
 *
 * due to the upgrade of unicode from 16 to 24 bits a couple decades ago utf-16
 * was "patched" to represent "astral" codepoints (&gt;U+FFFF) as a surrogate
 * pair of two utf-16 character values; this means that String.length can
 * return a different number than the actual length of the string
 *
 * also contains areNeighbors and editDistance
 */
public class Strings {
	public static int[] toIntArray(String A) {
		return A.codePoints().toArray();
	}

	public static List<Integer> toIntegerList(String A) {
		return A.codePoints().boxed().collect(Collectors.toList());
	}

	public static Iterator<Integer> iterator(String A) {
		return A.codePoints().boxed().iterator();
	}

	/**
	 * the number of codepoints in a string; won't report erroneous lengths
	 * for strings with astral codepoints
	 *
	 * not unicode normalized so still flawed but a bit better than the
	 * default
	 */
	public static int length(String A) {
		return toIntArray(A).length;
	}

	public static boolean sameLength(String A, String B) {
		return length(A) == length(B);
	}

	/**
	 * get the edit distance between two strings; only replacements
	 * allowed, insertions / deletions not in spec
	 */
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

	/**
	 * *marginally* faster specialized case of editDistance() which skips
	 * iterating over the last portion of the string if the edit distance
	 * is discovered to be greater than 1
	 */
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
