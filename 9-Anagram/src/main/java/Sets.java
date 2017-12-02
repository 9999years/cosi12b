import java.lang.Comparable;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

public class Sets {
	public static <T extends Comparable<T>, U extends Iterable<T>>
			SortedSet<U> sortedIterableSet() {
		return new TreeSet<>(new IterableComparator<>());
	}

	/**
	 * a SortedSet of Iterables of a Comparable; each element is compared
	 * in order like alphabetizing a string; ie [a, b, c] is less than [a,
	 * c, d], [a, b, c] and [a, b, c] are equal, etc etc
	 */
	public static <T extends Comparable<T>, U extends Iterable<T>>
			SortedSet<U> sortedIterableSet(
			Collection<U> collection) {
		SortedSet<U> ret = new TreeSet<>(new IterableComparator<>());
		ret.addAll(collection);
		return ret;
	}
}
