import java.lang.Comparable;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Sets {
	public static <T extends Comparable<T>, U extends Iterable<T>>
			SortedSet<U> sortedIterableSet() {
		return new TreeSet<>(new IterableComparator<>());
	}

	public static <T extends Comparable<T>, U extends Iterable<T>>
			SortedSet<U> sortedIterableSet(
			Collection<U> collection) {
		SortedSet<U> ret = new TreeSet<>(new IterableComparator<>());
		ret.addAll(collection);
		return ret;
	}

	//public static <T> TreeSet<T> alphabeticSet() {
		//return alphabeticSet(null);
	//}
}
