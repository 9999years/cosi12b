package becca.smp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

public class StableMarriageTest {
	public static final Predicate<String> NAME_TESTER =
			StableMarriage.NAME_PATTERN.asPredicate();
	
	void patternTest(boolean expected, String pat) {
		assertEquals(expected, NAME_TESTER.test(pat),
			"checking \"" + pat + "\" "
			+ (expected ? "matches" : "doesn't match")
			+ " the name pattern");
	}

	@Test
	void patternTest() {

		patternTest(true,  "Joe: ");
		patternTest(true,  "Kevin: ");
		patternTest(true,  "Mary-Beth: ");
		patternTest(true,  "Man 0: ");
		patternTest(true,  "Woman 3: ");
		patternTest(true,  "rebecca: ");
		patternTest(true,  "rebecca2: ");
		patternTest(true,  ".2398948234)@*##)(!): ");
		patternTest(true,  "man 2: ");
		patternTest(true,  "man #283298: ");
		patternTest(false, "rebecca2:: ");
		patternTest(false, "Joe");
		patternTest(false, ":reb:");
		patternTest(false, ":");
		patternTest(false, "END");
	}
}
