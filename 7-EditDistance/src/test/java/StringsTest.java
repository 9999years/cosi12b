package becca.edit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class StringsTest {
	void swapNTest(String expected, String a, String b, int n) {
		//.codePoints().toArray()
		assertEquals(
			expected,
			Strings.swapN(n, a, b),
			"swapping the "
			+ n + "th codepoint in `"
			+ a + "` and `" + b + "`"
		);
	}

	@Test
	void swapNTest() {
		swapNTest(" a ", "   ", "aaa", 1);
		swapNTest("xyz", "xyx", "zzz", 2);
		swapNTest("👌🙏👌", "👌a👌", "🙏🙏🙏", 1);
		swapNTest("👌🙏👌", "👌👌👌", "a🙏a", 1);
		swapNTest("a🙏👌", "a👌👌", "👌🙏aa", 1);
	}

	void swapNSideEffectsTest(
			String expected, int inx, int[] str, int newCodepoint) {
		assertEquals(expected, Strings.swapN(inx, str, newCodepoint),
			"checking character swapping (swapping codepoint "
			+ inx + " with U+" + newCodepoint
			+ " in string `"
			+ new String(str, 0, str.length)
			+ "`)");
	}

	@Test
	void swapNSideEffectsTest() {
		// make sure swapN doesn't mess up user data
		String a = "dog";
		swapNTest("cog", a, "cat", 0);
		swapNTest("dag", a, "cat", 1);
		swapNTest("dot", a, "cat", 2);

		int[] b = "dog".codePoints().toArray();
		assertEquals("cog", Strings.swapN(0, b, 0x63)); // c
		assertEquals("dag", Strings.swapN(1, b, 0x61)); // a
		assertEquals("dot", Strings.swapN(2, b, 0x74)); // t
	}
}
