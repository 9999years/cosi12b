package becca.markov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MarkovGeneratorTest {
	void matchTest(Integer[] arr, int inx1, int inx2, boolean expected) {
		assertEquals(expected, (new MarkovGenerator<Integer>(arr, 3))
			.matches(inx1, inx2),
			"ensuring "
			+ arr
			+ " matches at "
			+ inx1 + " and " + inx2);
	}

	@Test
	void matchTest() {
		matchTest(new Integer[] {1, 2, 3, 1, 2, 3}, 2, 5, true);
		matchTest(new Integer[] {1, 2, 3, 1, 2, 3}, 0, 3, true);
		matchTest(new Integer[] {1, 2, 3, 1, 2, 3}, 1, 4, true);
	}
}
