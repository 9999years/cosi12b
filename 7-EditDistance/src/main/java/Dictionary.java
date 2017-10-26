package becca.edit;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class Dictionary {
	/**
	 * maps words onto a set of its neighbors (words with edit distance 1)
	 */
	Map<String, Set<String>> words;

	Dictionary() {
		this(0);
	}

	Dictionary(int initialCapacity) {
		words = new HashMap<>(initialCapacity);
	}

	protected void ensureWord(String word) {
		words.putIfAbsent(word, new HashSet<>());
	}

	protected void ensureNeighbor(String word, String neighbor) {
		words.get(word).add(neighbor);
	}

	public void add(String word, String neighbor) {
		ensureWord(word);
		ensureNeighbor(word, neighbor);
	}

	protected String getNext(String current, String destination) {
	}

	public List<String> getPath(String beginning, String destination) {
		List<String> ret = new LinkedList<>();
		ret.add(beginning);
	}
}
