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

	void editDistanceTest(int expected, String A, String B) {
		assertEquals(expected, Strings.editDistance(A, B),
			"checking distance between `" + A + "` and `" + B
			+ "`");
	}

	@Test
	void editDistanceTest() {
		editDistanceTest(3, "dog", "cat");
		editDistanceTest(1, "dog", "dod");
		editDistanceTest(0, "dog", "dog");
	}

	void neighborsTest(boolean expected, String A, String B) {
		assertEquals(expected, Strings.areNeighbors(A, B),
			"checking neighbor status of `"
			+ A + "` and `" + B + "`");
	}

	@Test
	void neighborsTest() {
		neighborsTest(true, "aaa", "baa");
		neighborsTest(true, "aaa", "aba");
		neighborsTest(true, "aaa", "aab");

		// length
		neighborsTest(false, "aaa", "aaaa");

		// equality
		neighborsTest(false, "aaa", "aaa");

		// doubles
		neighborsTest(false, "aaa", "abb");
		neighborsTest(false, "aaa", "bbb");
		neighborsTest(false, "aaa", "bba");
		neighborsTest(false, "aaa", "bab");

		neighborsTest(true, "¶", "§");
		neighborsTest(true, "👌", ".");
		neighborsTest(true, "👌👌👌", "👌a👌");
		neighborsTest(false, "👌👌👌", "🙏a👌");
	}
}
