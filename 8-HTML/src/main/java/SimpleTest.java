/**
 * JUnit Lite (tm).
 * all the functionality i need, presumably with a lot less polish than JUnit has
 */
public class SimpleTest {
	public static void assertArrayEquals(Object[] expected, Object[] actual) {
		if(expected.length != actual.length) {
			System.err.println("\nArrays are different lengths! "
				+ "Expected: <" + expected + "> but found <"
				+ actual + ">");
			return;
		}
		for(int i = 0; i < expected.length; i++) {
			if(!expected[i].equals(actual[i])) {
				System.err.println("\nArrays differ starting at "
					+ "index " + i + "; Expected <"
					+ expected[i] + "> but found <"
					+ actual[i] + ">");
				return;
			}
		}
		// success!
		System.out.print(".");
	}
}
