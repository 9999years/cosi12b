import java.lang.Comparable;
import java.lang.Iterable;

import java.util.Iterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

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
