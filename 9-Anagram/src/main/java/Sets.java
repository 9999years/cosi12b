import java.lang.Comparable;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Sets {
	//public static <T> TreeSet<T> treeSet(
		//Collection<? extends T> collection,
		//Comparator<? super T> comparator) {
		//TreeSet<T> ret = new TreeSet<>(comparator);
		//if(collection != null) {
			//ret.addAll(collection);
		//}
		//return ret;
	//}

	//public static <T extends Comparable<?>>
		//TreeSet<TreeSet<T>> alphabeticSet(
		//Collection<? extends TreeSet<T>> collection) {
		//return treeSet(collection, (x, y) -> {
			//for(Tuple<TreeSet<T>, TreeSet<T>> pair :
				//new BiZip<TreeSet<T>, TreeSet<T>>(x, y)) {
				//int compared = pair.t.compareTo(pair.u);
				//if(compared != 0) {
					//return compared;
				//}
			//}
			//return 0;
		//});
	//}

	//public static <T> TreeSet<T> alphabeticSet() {
		//return alphabeticSet(null);
	//}
}
