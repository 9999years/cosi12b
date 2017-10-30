package becca.edit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class DictionaryTest {
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
				new String[] {"cot", "cog"},
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
		assertArrayEquals(expected, d.getPath(start, end).toArray(),
			"checking path between `"
			+ start + "` and `" + end + "`");
	}

	@Test
	void integrationTest() {
		String[] threes = new String[] {
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

		integrationTest(new String[] {"net", "nee", "lee", "lew",
			"hew", "hem", "him", "hip"},
			"net", "hip", threes);
		integrationTest(new String[] {"tag", "nag"},
			"tag", "nag", threes);
		integrationTest(new String[] {"tag", "wag", "wad", "lad",
			"lid", "tid", "tit", "cit", "cot", "cog", "dog", "dug",
			"dun", "den", "ten", "tee", "see"},
			"tag", "see", threes);
		integrationTest(new String[] {"lop", "mop", "moo", "coo",
			"coy"},
			"lop", "coy", threes);
	}
}
