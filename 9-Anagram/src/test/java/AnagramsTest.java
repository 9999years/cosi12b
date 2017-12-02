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

	void getAnagramsTest(String phrase, String[]... expected) {
		assertArrayEquals(expected,
			new Anagrams(dict)
				.getAnagrams(phrase)
				.stream()
				.map(t -> t.toArray(new String[0]))
				.toArray(String[][]::new)
		);
	}

	@Test
	void getAnagramsTest() {
		getAnagramsTest("barbara bush",
			new String[] { "abash", "bar", "rub" },
			new String[] { "abash", "barb" },
			new String[] { "abash", "bar", "rub" },
			new String[] { "aura", "bar" },
			new String[] { "aura", "barb" },
			new String[] { "abash", "bar", "rub" },
			new String[] { "aura", "bar" },
			new String[] { "bar", "barb" },
			new String[] { "bar", "brush" },
			new String[] { "bar", "bus" },
			new String[] { "bar", "hub" },
			new String[] { "abash", "bar", "rub" },
			new String[] { "bar", "shrub" },
			new String[] { "bar", "sub" },
			new String[] { "abash", "barb" },
			new String[] { "aura", "barb" },
			new String[] { "bar", "barb" },
			new String[] { "barb", "brush" },
			new String[] { "barb", "bus" },
			new String[] { "barb", "hub" },
			new String[] { "barb", "rub" },
			new String[] { "barb", "shrub" },
			new String[] { "barb", "sub" },
			new String[] { "bar", "brush" },
			new String[] { "barb", "brush" },
			new String[] { "bar", "bus" },
			new String[] { "barb", "bus" },
			new String[] { "bar", "hub" },
			new String[] { "barb", "hub" },
			new String[] { "abash", "bar", "rub" },
			new String[] { "abash", "bar", "rub" },
			new String[] { "barb", "rub" },
			new String[] { "bar", "shrub" },
			new String[] { "barb", "shrub" },
			new String[] { "bar", "sub" },
			new String[] { "barb", "sub" }
		);
	}
}
