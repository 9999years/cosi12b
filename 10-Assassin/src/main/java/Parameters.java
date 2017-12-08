import java.lang.Exception;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.Function;

/**
 * class for validating function parameters; some companion functions for
 * Objects.requireNonNull
 */
public class Parameters {
	/**
	 * returns true if parameter passes test
	 */
	public static <T> boolean validateQuiet(T parameter, Predicate<T> test) {
		return test.test(parameter);
	}

	/**
	 * throws an IllegalArgumentException if parameter fails test
	 */
	public static <T> void validate(T parameter, Predicate<T> test) {
		if(!validateQuiet(parameter, test)) {
			// test failed
			throw new IllegalArgumentException();
		}
	}

	/**
	 * throws an IllegalArgumentException with a custom message if
	 * parameter fails test
	 */
	public static <T> void validate(T parameter, Predicate<T> test,
			Supplier<String> msg)
			throws Exception {
		validate(parameter, test, msg, IllegalArgumentException::new);
	}

	public static <T> void validate(T parameter, Predicate<T> test,
			Supplier<String> msg,
			Function<String, Exception> exceptionGenerator)
			throws Exception {
		validate(parameter, test, p -> exceptionGenerator.apply(msg.get()));
	}

	/**
	 * throws an IllegalArgumentException with a custom message in terms of
	 * the parameter if parameter fails test
	 */
	public static <T> void validate(
		T parameter, Predicate<T> test, Function<T, Exception> msg)
		throws Exception {
		if(!validateQuiet(parameter, test)) {
			// test failed
			throw msg.apply(parameter);
		}
	}
}
