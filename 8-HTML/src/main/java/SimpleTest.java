/**
 * JUnit Lite (tm).
 * all the functionality i need, presumably with a lot less polish than JUnit has
 *
 * i'll be frank; this is untested. you can actually redirect standard output
 * (or standard error in this case) to a java printstream to compare it to
 * known values, but.... i'm not reinventing the wheel here. JUnit is good.
 * everybody uses it. one thing i know is that coding your own testing
 * framework is very silly and a huge waste of time. because someone's already
 * done it better.
 */
public class SimpleTest {
	protected int testCount = 0;
	protected int successCount = 0;
	protected int failureCount = 0;

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
		return "Expected <" + expected + "> but found <"
			+ actual + ">";
	}

	public static void success() {
		System.out.print(".");
	}

	public boolean richAssertEquals(Object expected, Object actual) {
		return(richTest(assertEquals(expected, actual)));
	}

	public static boolean assertEquals(Object expected, Object actual) {
		if(expected.equals(actual)) {
			success();
			return true;
		} else {
			System.err.println("\nObjects unequal; "
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
			System.err.println("\nArrays are different lengths! "
				+ expectedActual(expected, actual));
			return false;
		}
		for(int i = 0; i < expected.length; i++) {
			if(!expected[i].equals(actual[i])) {
				System.err.println("\nArrays differ starting at "
					+ "index " + i + "; "
					+ expectedActual(expected[i], actual[i]));
				return false;
			}
		}
		success();
		return true;
	}
}
