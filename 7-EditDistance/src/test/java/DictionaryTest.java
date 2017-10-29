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
}
