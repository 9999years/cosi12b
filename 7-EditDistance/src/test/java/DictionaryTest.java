package becca.edit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.List;
import java.util.ListIterator;
import java.util.Arrays;

public class DictionaryTest {
	static final String[] threes = new String[] {
		"net", "met", "set", "see", "lee", "leg", "beg", "bel",
		"bed", "bud", "dud", "cud", "bud", "bub", "dub", "cub",
		"dub", "dun", "dug", "tug", "pug", "pus", "pup", "pug",
		"bug", "big", "pig", "pit", "pot", "got", "not", "dot",
		"jot", "lot", "lob", "sob", "soy", "toy", "thy", "shy",
		"she", "see", "bee", "beg", "peg", "pet", "pea", "lea",
		"tea", "ted", "tad", "tid", "rid", "hid", "mid", "mad",
		"mar", "gar", "tar", "tam", "tan", "ran", "tan", "tat",
		"tax", "max", "lax", "tax", "tau", "tag", "gag", "jag",
		"jab", "jar", "gar", "par", "war", "oar", "gar", "bar",
		"gar", "far", "oar", "far", "par", "pan", "man", "mad",
		"man", "may", "fay", "fly", "fay", "say", "may", "gay",
		"gas", "gag", "jag", "nag", "zag", "jag", "wag", "wig",
		"wit", "cit", "cut", "but", "bet", "vet", "vex", "vet",
		"net", "not", "mot", "hot", "not", "got", "mot", "not",
		"cot", "not", "nob", "lob", "lop", "low", "row", "mow",
		"moo", "coo", "coy", "loy", "lop", "pop", "mop", "top",
		"tor", "tog", "jog", "jot", "jog", "fog", "cog", "dog",
		"cog", "cow", "caw", "can", "ran", "tan", "tap", "tip",
		"rip", "nip", "dip", "rip", "rap", "rat", "eat", "eft",
		"aft", "art", "ant", "art", "arm", "ark", "irk", "ark",
		"art", "ant", "ana", "and", "aid", "tid", "tin", "ton",
		"ten", "tee", "fee", "few", "new", "dew", "den", "din",
		"sin", "tin", "tit", "tid", "aid", "lid", "tid", "hid",
		"his", "him", "hem", "her", "hew", "hen", "pen", "men",
		"yen", "yet", "met", "bet", "set", "met", "get", "set",
		"see", "gee", "tee", "fee", "few", "dew", "lew", "lee",
		"tee", "bee", "wee", "nee", "vee", "bee", "tee", "the",
		"thy", "why", "thy", "why", "thy", "the", "toe", "tow",
		"top", "tor", "toy", "loy", "lop", "pop", "pip", "zip",
		"lip", "lid", "lad", "wad", "tad", "tau", "tab", "tam",
		"cam", "gam", "yam", "ram", "gam", "gym", "gyp", "gap",
		"rap", "nap", "hap", "sap", "sag", "sap", "rap", "rep",
		"rap", "tap", "tau", "tag", "jag", "jug", "hug", "pug",
		"pup", "pus", "pup", "pip", "dip", "pip", "zip", "hip",
		"tip", "sip", "nip", "hip", "sip", "sin", "win", "din",
		"dim", "lim", "rim", "rum", "rub", "rob", "rib", "rip",
		"rib", "reb", "rib", "reb"
	};

	void linksTest(
			String[][] expected,
			String word,
			String destination,
			String[] corpus) {
		Dictionary d = new Dictionary();
		d.add(word);
		d.add(destination);
		for(String n : corpus) {
			d.add(n);
		}
		Map<String, String> graph = d.getFlowGraph(word, destination);
		for(String[] pair : expected) {
			assertEquals(pair[1], graph.get(pair[0]),
				"checking node that discovered `"
				+ pair[0] + "`");
		}
	}

	@Test
	void linksTest() {
		//         v-------------\
		// dog <- dot <- cot <- cat
		//  ^---- cog ----^
		linksTest(new String[][] {
				new String[] {"dot", "dog"},
				new String[] {"cog", "dog"},
				new String[] {"cot", "dot"},
				new String[] {"cat", "cot"},
			},
			"dog", "cat",
			new String[] {
				"dog",
				"dot",
				"cot",
				"cog",
				"cat"
			});
	}

	void pathConcisenessTest(String[] path) {
		List<String> pathList = Arrays.asList(path);
		ListIterator<String> A = pathList.listIterator();
		ListIterator<String> B = pathList.listIterator();

		String a = null;
		String b = null;
		int i;
		int j;
		while(A.hasNext()) {
			while(B.hasNext()) {
				i = A.nextIndex();
				j = B.nextIndex();
				a = A.next();
				b = B.next();
				// make sure we only check non-neighbors
				if(Math.abs(j - i) > 1) {
					assertNotEquals(a, b,
						"checking that `"
						+ a + "` and `" + b
						+ "` within a path aren't equal");
					assertFalse(Strings.areNeighbors(a, b),
						"checking that `"
						+ a + "` and `" + b
						+ "` within a path aren't neighbors");
				}
			}
		}
	}

	void integrationTest(
			String[] expected,
			String start,
			String end,
			String[] corpus) {
		Dictionary d = new Dictionary();
		d.add(start);
		d.add(end);
		for(String n : corpus) {
			d.add(n);
		}
		String[] path = d.getPath(start, end).toArray(new String[] {});
		assertEquals(expected.length, path.length, "checking length of path is as expected");
		assertArrayEquals(expected, path, () ->
			"checking path between `" + start + "` and `" + end
			+ "`; length is correct but elements differ; full "
			+ "arrays are: \nexpected: " + Arrays.toString(expected)
			+ "actual:   " + Arrays.toString(path));
		pathConcisenessTest(path);
	}

	@Test
	void integrationTest() {
		integrationTest(new String[] {"net", "pet", "pit", "pip", "hip"},
			"net", "hip", threes);
		integrationTest(new String[] {"tag", "nag"},
			"tag", "nag", threes);
		integrationTest(new String[] {"tag", "tog", "toe", "tee", "see"},
			"tag", "see", threes);
		integrationTest(new String[] {"lop", "loy", "coy"},
			"lop", "coy", threes);
	}

	@Test
	void pathConcisenessTest() {
		pathConcisenessTest(new String[] { "tag", "wag", "wah", "bah",
			"bam", "ram", "ran", "pan", "pap", "map", "mar", "jar",
			"jab", "nab", "nay", "fay", "fat", "hat", "haw", "hew",
			"lew", "let", "pet", "pit", "sit", "sir", "fir", "fib",
			"rib", "rub", "pub", "pus", "bus", "bun", "fun", "fum",
			"gum", "gem", "gel", "eel", "ell", "all", "ale", "awe",
			"owe", "owl", "oil", "til", "tid", "did", "dud", "mud",
			"mug", "lug", "lux", "lax", "lac", "sac", "sad", "gad",
			"god", "gog", "fog", "foe", "hoe", "hob", "lob", "loy",
			"toy", "tow", "vow", "von", "non", "not", "mot", "moo",
			"woo", "who", "why", "wry", "dry", "dey", "key", "keg",
			"beg", "bee", "see" });

		pathConcisenessTest(new String[] { "peg", "pig", "pit", "sit",
			"sir", "fir", "fib", "rib", "rub", "pub", "pus", "bus",
			"bum", "fum", "fun", "sun", "sen", "ten", "tin", "yin",
			"yon", "you", "sou", "soy", "toy", "tow", "wow", "wop",
			"pop", "pap", "map", "mar", "oar" });
	}
}
