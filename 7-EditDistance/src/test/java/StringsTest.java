package becca.edit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

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
}
