package becca.edit;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import java.lang.Iterable;
import java.util.Iterator;

import java.lang.StringBuilder;

public class Dictionary {
	/**
	 * maps words onto a set of its neighbors (words with edit distance 1)
	 */
	Map<String, Set<String>> words;

	public static final int DEFAULT_CAPACITY = 128;

	Dictionary() {
		this(DEFAULT_CAPACITY);
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

	protected void add(String word, String neighbor) {
		ensureWord(word);
		ensureWord(neighbor);
		ensureNeighbor(word, neighbor);
		ensureNeighbor(neighbor, word);
	}

	public void add(String word) {
		ensureWord(word);
		words.keySet().forEach(candidate -> {
			if(!words.get(word).contains(candidate) &&
				Strings.areNeighbors(word, candidate)) {
				add(word, candidate);
			}
		});
	}

	public Set<String> neighbors(String word) {
		return words.get(word);
	}

	public boolean contains(String word) {
		return words.containsKey(word);
	}

	protected String getNext(String current, String destination) {
		// for codepoint cur, dest in current, destination
		// if cur != dest and the word with the dest swapped with
		// cur is in the dictionary, return that word
		//
		// so if we want to get from aaaa to bbbb we'll try
		// * baaa
		// * abaa
		// * aaba
		// * aaab
		// checking if each word is in the dictionary along the way
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
						cpDestination)
				);

			if(neighbors.contains(candidate)) {
				return candidate;
			}
		}

		return null;
	}

	public List<String> getPath(String beginning, String destination) {
		if(!Strings.sameLength(beginning, destination)) {
			return null;
		}
		List<String> ret = new LinkedList<>();
		String next = beginning;
		while(!next.equals(destination)) {
			ret.add(next);
			next = getNext(next, destination);
			if(next == null) {
				// no path
				return null;
			}
		}
		// destination
		ret.add(next);
		return ret;
	}

	public String toDot() {
		StringBuilder ret = new StringBuilder("digraph {\n");
		for(String word : words.keySet()) {
			ret.append(word + " -> { ");
			ret.append(String.join(" ", words.get(word))
				+ " };\n");
		}
		ret.append("}\n");
		return ret.toString();
	}
}
