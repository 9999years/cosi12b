/**
 * “dont test that stdin/stdout works” is what everyone says.
 * so i want to clarify that this is *not* doing that; but i have a couple
 * classes that read from stdin, so we use this class to simulate user input
 */
public class STDINTest {
	public void test(String input) {
		System.setIn(new ByteArrayInputStream(data.getBytes()));
	}
}
