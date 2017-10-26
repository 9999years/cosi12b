package becca.edit;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import java.lang.Iterable;
import java.util.Iterator;

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
		// for codepoint cur, dest in current, destination
		// if cur != dest and the word with the dest swapped with
		// cur is in the dictionary, return that word
		//
		// otherwise get a random neighbor???
		int[] currentCodePoints = current.codePoints().toArray();
		Set<String> neighbors = words.get(current);
		Iterable<CodePoint> codePoints =
			new StringIterator(destination);
		for(CodePoint cp : codePoints) {
			String candidate = cp.extract(
				cpDestination -> index ->
					Strings.swapN(index,
						currentCodePoints,
						cpDestination));
			if(neighbors.contains(candidate)) {
				return candidate;
			}
		}
		return null;
	}

	public List<String> getPath(String beginning, String destination) {
		List<String> ret = new LinkedList<>();
		ret.add(beginning);
		return null;
	}
}
