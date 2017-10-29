package becca.edit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DictionaryTest {
	void getNextTest(
			String expected,
			String word,
			String destination,
			String[] neighbors) {
		Dictionary d = new Dictionary();
		for(String n : neighbors) {
			d.add(word, n);
		}
		assertEquals(expected, d.getNext(word, destination),
			"Checking path from `"
			+ word + "` to `"
			+ destination + "`");
	}

	@Test
	void getNextTest() {
		getNextTest("cog", "dog", "cat",
			new String[] {
				"dot",
				"cog" // <-
			});
		getNextTest("dot", "dog", "cat",
			new String[] {
				"dot",
			});
		getNextTest("abaa", "aaaa", "bbbb",
			new String[] {
				"abcd",
				"upyp",
				"bbab",
				"baab",
				"..fu",
				"abaa" // <-
			});
	}
}
