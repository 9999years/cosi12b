package becca.edit;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Deque;
import java.util.ListIterator;
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
		ensureWord(word);
		return words.get(word);
	}

	public boolean contains(String word) {
		return words.containsKey(word);
	}

	/**
	 * get a digraph of nodes pointing towards the beginning node
	 * not guaranteed to include all nodes in the graph, but guaranteed to
	 * include all nodes between beginning and destination
	 */
	protected Map<String, String> getFlowGraph(
			String beginning, String destination) {
		if(!Strings.sameLength(beginning, destination)) {
			return null;
		}

		// maps a node to the node that first visited it
		Map<String, String> visitedBy = new HashMap<>();

		LinkedList<String> path = new LinkedList<>();
		path.add(beginning);
		while(path.size() > 0) {
			String head = path.pop();
			if(head.equals(destination)) {
				// done!
				break;
			}
			for(String word : neighbors(head)) {
				if(!visitedBy.containsKey(word)) {
					path.push(word);
					visitedBy.put(word, head);
				}
			}
		}

		return visitedBy;
	}

	/**
	 * a breadth-first search on the internal graph of words
	 */
	public List<String> getPath(String beginning, String destination) {
		// maps a node to the node that first visited it
		Map<String, String> visitedBy =
			getFlowGraph(beginning, destination);

		LinkedList<String> path = new LinkedList<>();
		// now we have our tree in visitedBy
		// next, find the route from the destination to the beginning
		String head = destination;
		path.push(head);
		while(!head.equals(beginning)) {
			head = visitedBy.get(head);
			path.push(head);
		}

		return path;
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
