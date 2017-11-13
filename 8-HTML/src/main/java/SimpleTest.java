/**
 * simple test framework
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

import java.util.Arrays;

/**
 * JUnit Lite (tm).
 * all the functionality i need, presumably with a lot less polish than JUnit has
 *
 * ironically, this is untested. you can actually redirect standard output
 * (or standard error in this case) to a java printstream to compare it to
 * known values, but.... i'm not reinventing the wheel here. JUnit is good.
 * everybody uses it. one thing i know is that coding your own testing
 * framework is very silly and a huge waste of time. because someone's already
 * done it better.
 *
 * we provide regular static methods for "trivial" tests, but also allow
 * instantiating the class for the matching "rich" methods, which keep track of
 * successes and failures
 *
 * junit avoids that by running tests with a platform (and avoids making a
 * main() function yourself)
 */
public class SimpleTest {
	protected int testCount = 0;
	protected int successCount = 0;
	protected int failureCount = 0;

	public int getSuccesses() {
		return successCount;
	}

	public int getFailures() {
		return failureCount;
	}

	public int getTests() {
		return testCount;
	}

	protected boolean richTest(boolean status) {
		testCount++;
		if(status) {
			successCount++;
		} else {
			failureCount++;
		}
		return status;
	}

	public String summary() {
		return testCount + " tests run; " + successCount + " passed and "
			+ failureCount + " failed ("
			+ (100.0 * successCount / testCount)
			+ "% pass rate).";
	}

	protected static String expectedActual(Object expected, Object actual) {
		return "Expected `" + expected + "` but found `"
			+ actual + "`";
	}

	protected static String expectedActual(
			Object[] expected, Object[] actual) {
		return "Expected: " + Arrays.toString(expected)
			+ "\nFound:    " + Arrays.toString(actual);
	}

	public String toString() {
		return "SimpleTest[tests=" + getTests() + "]";
	}

	/**
	 * indicates a successful test
	 */
	public static void success() {
		System.out.print(".");
	}

	public static void err(Object output) {
		System.err.println(output);
	}

	// ======================================================================
	// ================== ASSERT METHODS FROM HERE DOWN =====================
	// ======================================================================

	public boolean richAssertEquals(Object expected, Object actual) {
		return(richTest(assertEquals(expected, actual)));
	}

	public static boolean assertEquals(Object expected, Object actual) {
		if(expected.equals(actual)) {
			success();
			return true;
		} else {
			err("\nObjects unequal; "
				+ expectedActual(expected, actual));
			return false;
		}
	}

	public boolean richAssertArrayEquals(
			Object[] expected, Object[] actual) {
		return(richTest(assertArrayEquals(expected, actual)));
	}

	public static boolean assertArrayEquals(
			Object[] expected, Object[] actual) {
		if(expected.length != actual.length) {
			err("\nArrays are different lengths! "
				+ expectedActual(expected, actual));
			return false;
		}
		for(int i = 0; i < expected.length; i++) {
			if(!expected[i].equals(actual[i])) {
				err("\nArrays differ starting at"
					+ "index " + i + "; "
					+ expectedActual(expected[i], actual[i])
					+ "\n"
					+ expectedActual(expected, actual));
				return false;
			}
		}
		success();
		return true;
	}
}
