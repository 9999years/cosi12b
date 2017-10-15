package becca.markov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MarkovGeneratorTest {
	void matchTest(Integer[] arr, int length, int inx1, int inx2, boolean expected) {
		MarkovGenerator m = new MarkovGenerator<Integer>(arr, length);
		assertEquals(expected, m.matches(inx1, inx2),
			"ensuring "
			+ m
			+ " matches at "
			+ inx1 + " and " + inx2);
	}

	@Test
	void matchTest() {
		//                           0  1  2  3  4  5
		Integer[] a = new Integer[] {1, 2, 3, 1, 2, 3};
		matchTest(a, 3, 0, 3, true);
		matchTest(a, 3, 3, 0, true);
		matchTest(a, 3, 1, 4, true);

		matchTest(a, 3, 0, 0, true);
		matchTest(a, 3, 3, 3, true);

		// <0 inx
		matchTest(a, 3, 5, -1, false);
		matchTest(a, 3, 3, 4, false);
		matchTest(a, 3, 1, 3, false);

		//a = new Integer[] {1, 2, 3, 6, 8, 9, 1, 2, 3};
	}
}
