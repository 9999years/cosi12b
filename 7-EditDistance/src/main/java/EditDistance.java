package becca.edit;

public class EditDistance {
	public static int compute(String A, String B) {
		// fine to just use A.length() because compute(String, String,
		// int) ensures A.length() == B.length() anyways
		return computeWithThreshold(A, B, A.length());
	}

	/**
	 * determines if the edit distance between two strings is at LEAST
	 * `threshold`, returning at MOST `threshold` (but possibly a lower
	 * value if the distance is LESS than `threshold`). slightly faster
	 * than a plain compute
	 */
	public static int computeWithThreshold(
			String A, String B, int threshold) {
		if(A.length() != B.length()) {
			throw new IllegalArgumentException(
				"Strings must be the same size!");
		}

		int dist = 0;
		Iterable<BiCodePoint> codepoints = new BiStringIterator(A, B);
		for(BiCodePoint cp : codepoints) {
			if(cp.a != cp.b) {
				dist++;
			}
			if(dist == threshold) {
				break;
			}
		}
		return dist;
	}

	/**
	 * really a comparator
	 */
	public static int compareThreshold(
			String A, String B, int threshold) {
		if(A.length() != B.length()) {
			throw new IllegalArgumentException(
				"Strings must be the same size!");
		}

		int dist = 0;
		Iterable<BiCodePoint> codepoints = new BiStringIterator(A, B);
		for(BiCodePoint cp : codepoints) {
			if(cp.a != cp.b) {
				dist++;
			}
			if(dist > threshold) {
				return 1;
			}
		}
		// uhhh
		return dist - threshold;
	}
}
