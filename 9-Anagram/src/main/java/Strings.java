import java.lang.Iterable;
import java.lang.StringBuilder;

import java.util.stream.Stream;
import java.util.Collection;
import java.util.Collections;
import java.util.function.IntFunction;

/**
 * utility class
 */
public class Strings {
	public static int minLength(Collection<String> strings) {
		return Collections.min(strings,
			(a, b) -> a.length() - b.length())
			.length();
	}

	public static int totalLength(Stream<String> strings) {
		return strings
			.collect(
				MutableInt::new,
				(i, s) -> i.val += s.length(),
				(i, j) -> i.val += j.val
			).val;
	}

	/**
	 * total length of all the strings in strings combined
	 */
	public static int totalLength(Collection<String> strings) {
		return totalLength(strings.stream());
	}

	public static int totalLength(String... strings) {
		return totalLength(Stream.of(strings));
	}
}

/**
 * mutable int wrapper class
 */
class MutableInt {
	int val;
	
	MutableInt(int i) {
		val = i;
	}

	MutableInt() {
		this(0);
	}
}
