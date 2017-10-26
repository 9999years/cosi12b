package becca.edit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EditDistanceTest {
	void computeTest(int expected, String A, String B) {
		assertEquals(expected, EditDistance.compute(A, B),
			"checking distance between `" + A + "` and `" + B
			+ "`");
	}

	@Test
	void computeTest() {
		computeTest(3, "dog", "cat");
		computeTest(1, "dog", "dod");
		computeTest(0, "dog", "dog");
	}
}
