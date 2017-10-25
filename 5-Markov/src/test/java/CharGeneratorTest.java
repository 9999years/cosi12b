package becca.markov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CharGeneratorTest {
	@Test
	void integrationTest() {
		String src = "Now imagine doing a level k analysis by"
			+ " determining the probability with which each"
			+ " character follows every possible sequence of"
			+ " characters of length k. A level 5 analysis of"
			+ " Tom Sawyer for example, would reveal that 'r'"
			+ " follows ''Sawye'' more frequently than any"
			+ " other character. After a level k analysis,"
			+ " you'd be able to produce random Tom Sawyer by"
			+ " always choosing the next character based on"
			+ " the previous k characters (the seed) and the"
			+ " probabilities revealed by the analysis.";

		assertEquals(
			"yets  aoapyhpbligeitAe dtpaerslvittlac )",
			new CharGenerator(src, 0, 9320493).next(40),
			"k=0 integration test"
		);
		assertEquals(
			"o blelly e Tobarabye w r' chee Sanil ote",
			new CharGenerator(src, 1, 9320493).next(40),
			"k=1 integration test"
		);
		assertEquals(
			"ossing andoine prevel k character. After",
			new CharGenerator(src, 2, 5).next(40),
			"k=2 integration test"
		);
		// corpus isn't long enough to make these useful
		// buuuut they test the index generation i guess
		assertEquals(
			"ties reveal that 'r' follows ''Sawyer fo",
			new CharGenerator(src, 5, 9320492).next(40),
			"k=5 integration test"
		);
		assertEquals(
			"racters (the seed) and the probability w",
			new CharGenerator(src, 5, 9320493).next(40),
			"k=5 integration test"
		);
		assertEquals(
			"acter follows every possible sequence of",
			new CharGenerator(src, 10, 9320493).next(40),
			"k=10 integration test"
		);
	}
}
