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
		for(Tuple<T, T> pair : new BiZip<T, T>(A, B)) {
			int compared = pair.t.compareTo(pair.u);
			if(compared != 0) {
				// pair unequal
				return compared;
			}
		}
		// every pair is equal
		return 0;
	}
}
