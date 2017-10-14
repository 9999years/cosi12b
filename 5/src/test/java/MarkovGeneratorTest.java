package becca.markov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MarkovGeneratorTest {
	void matchTest(Integer[] arr, int inx1, int inx2, boolean expected) {
		MarkovGenerator m = new MarkovGenerator<Integer>(arr, 3);
		assertEquals(expected, m.matches(inx1, inx2),
			"ensuring "
			+ m
			+ " matches at "
			+ inx1 + " and " + inx2);
	}

	@Test
	void matchTest() {
		Integer[] a = new Integer[] {1, 2, 3, 1, 2, 3};
		matchTest(a, 2, 5, true);
		matchTest(a, 0, 3, true);
		matchTest(a, 1, 4, true);
		matchTest(a, 5, 2, true);
		matchTest(a, 3, 0, true);
		matchTest(a, 4, 1, true);
		matchTest(a, 0, 0, true);
		matchTest(a, 5, 5, true);

		// <0 inx
		matchTest(a, 5, -1, false);
	}
}
