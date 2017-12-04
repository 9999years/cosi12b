import java.lang.Comparable;
import java.lang.Iterable;

import java.util.Comparator;

/**
 * compares two Iterables of a Comparable; each element is compared
 * in order like alphabetizing a string; ie [a, b, c] is less than [a,
 * c, d], [a, b, c] and [a, b, c] are equal, etc etc
 */
public class IterableComparator<T extends Comparable<T>>
		implements Comparator<Iterable<T>> {
	public int compare(Iterable<T> A, Iterable<T> B) {
		BiZip<T, T> itr = new BiZip<>(A, B);
		for(Tuple<T, T> pair : itr) {
			int compared = pair.t.compareTo(pair.u);
			if(compared != 0) {
				// pair unequal
				return compared;
			}
		}

		if(itr.tHasNext()) {
			// A is longer than B
			return 1;
		} else if(itr.tHasNext()) {
			// B longer than A
			return -1;
		}
		// every pair is equal
		return 0;
	}
}
