import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.SortedSet;

public class AnagramsTest {
	String[] dict = new String[] {
		"abash", "aura", "bar", "barb", "bee", "beg", "blush", "bog",
		"bogus", "bough", "bow", "brew", "briar", "brow", "brush",
		"bug", "bugs", "bus", "but", "egg", "ego", "erg", "ghost",
		"go", "goes", "gorge", "gosh", "grew", "grow", "grub", "gush",
		"he", "her", "here", "hew", "hog", "hose", "how", "hub", "hug",
		"huh", "hush", "owe", "rub", "sew", "she", "shrub", "shrug",
		"sir", "sub", "surge", "swore", "web", "wee", "were", "whore",
		"whose", "woe", "wore", "worse"
	};

	void getWordsTest(String word, String[] dictionary, String... expected) {
		assertArrayEquals(
			expected,
			new Anagrams(dictionary).getWords(word).toArray()
		);
	}

	void getWordsTest(String word, String... expected) {
		getWordsTest(word, dict, expected);
	}

	@Test
	void getWordsTest() {
		getWordsTest("barbara bush",
			"abash", "aura", "bar", "barb", "brush", "bus", "hub",
			"rub", "shrub", "sub");
		getWordsTest("hello", "he");
		getWordsTest("baggage", "beg", "egg");
	}

	void getAnagramsTest(String phrase, int max, String[]... expected) {
		String[][] found = new Anagrams(dict)
				.getAnagrams(phrase, max)
				.stream()
				.map(t -> t.toArray(new String[0]))
				.toArray(String[][]::new);
		assertArrayEquals(expected,
			found,
			() -> "`" + phrase + "` anagrams to "
				+ Arrays.deepToString(found)
				+ ", expecting "
				+ Arrays.deepToString(expected)
		);
	}

	void getAnagramsTest(String phrase, String[]... expected) {
		getAnagramsTest(phrase, 0, expected);
	}

	@Test
	void getAnagramsTest() {
		getAnagramsTest("barbara bush",
			new String[] { "abash", "bar", "rub" },
			new String[] { "abash", "rub", "bar" },
			new String[] { "bar", "abash", "rub" },
			new String[] { "bar", "rub", "abash" },
			new String[] { "rub", "abash", "bar" },
			new String[] { "rub", "bar", "abash" }
		);
		getAnagramsTest("hairbrush",
			new String[] { "bar", "huh", "sir" },
			new String[] { "bar", "sir", "huh" },
			new String[] { "briar", "hush" },
			new String[] { "huh", "bar", "sir" },
			new String[] { "huh", "sir", "bar" },
			new String[] { "hush", "briar" },
			new String[] { "sir", "bar", "huh" },
			new String[] { "sir", "huh", "bar" }
		);
		System.out.println("-----");
		getAnagramsTest("hairbrush", 2,
			new String[] { "briar", "hush" },
			new String[] { "hush", "briar" }
		);
		// nothin for my boy john
		getAnagramsTest("john kerry");
	}

	void toStringTest(String expected, String phrase, int max) {
		String found = new Anagrams(dict).toString(phrase, max);
		assertEquals(expected, found, () -> "checking the anagrams of `"
			+ phrase + "` print as `" + expected + "`");
	}

	void toStringTest(String expected, String phrase) {
		toStringTest(expected, phrase, 0);
	}

	@Test
	void toStringTest() {
		toStringTest("[bar, huh, sir]\n"
			+ "[bar, sir, huh]\n"
			+ "[briar, hush]\n"
			+ "[huh, bar, sir]\n"
			+ "[huh, sir, bar]\n"
			+ "[hush, briar]\n"
			+ "[sir, bar, huh]\n"
			+ "[sir, huh, bar]\n",
			"hairbrush\n"
		);

		//toStringTest("[bar, huh, sir]\n"
			//+ "[bar, sir, huh]\n",
			//"hairbrush", 2
		//);
	}
}
