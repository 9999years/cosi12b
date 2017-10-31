package becca.edit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class StringsTest {
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
