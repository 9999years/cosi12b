package becca.edit;

import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
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

public abstract class Graph <T> {
	/**
	 * maps nodes onto a set of its neighbors (nodes with edit distance 1)
	 */
	Map<T, Set<T>> nodes;

	public static final int DEFAULT_CAPACITY = 128;

	Graph() {
		this(DEFAULT_CAPACITY);
	}

	Graph(int initialCapacity) {
		nodes = new HashMap<>(initialCapacity);
	}

	public Map<T, Set<T>> getNodes() {
		return nodes;
	}

	protected void ensureNode(T node) {
		nodes.putIfAbsent(node, new HashSet<>());
	}

	protected void ensureNeighbor(T node, T neighbor) {
		nodes.get(node).add(neighbor);
	}

	protected void add(T node, T neighbor) {
		ensureNode(node);
		ensureNode(neighbor);
		ensureNeighbor(node, neighbor);
		ensureNeighbor(neighbor, node);
	}

	/**
	 * set addition; also ensures neighbors are properly updated ---
	 * unfortunately that means this runs in O(n), and adding n nodes takes
	 * O(n^2) time :-(
	 */
	public void add(T node) {
		// ensures the node exists
		Set<T> neighbors = neighbors(node);
		Set<T> keys = nodes.keySet();
		for(T candidate : keys) {
			if(!neighbors.contains(candidate) &&
				areNeighbors(node, candidate)) {
				add(node, candidate);
				keys.remove(node);
				keys.remove(candidate);
			}
		}
	}

	/**
	 * function to determine if two Ts are neighbors
	 */
	protected abstract boolean areNeighbors(T a, T b);

	/**
	 * function to determine if a path *may* exist on the graph between two
	 * nodes
	 *
	 * for strings, this function represents being the same length; being
	 * the same length does not *ensure* a path exists between the two
	 * nodes, but being different lengths ensures a path does *not* exist
	 * between the two nodes
	 */
	protected abstract boolean pathMayExist(T a, T b);

	public Set<T> neighbors(T node) {
		ensureNode(node);
		return nodes.get(node);
	}

	public boolean contains(T node) {
		return nodes.containsKey(node);
	}

	/**
	 * get a digraph of nodes pointing towards the start node
	 * not guaranteed to include all nodes in the graph, but guaranteed to
	 * include all nodes between start and end
	 */
	protected Map<T, T> getFlowGraph(
			T start, T end) {
		if(!pathMayExist(start, end)) {
			return null;
		}

		// maps a node to the node that first visited it
		Map<T, T> visitedBy = new HashMap<>();

		LinkedList<T> path = new LinkedList<>();
		path.add(start);
		while(path.size() > 0) {
			T head = path.poll();
			if(head.equals(end)) {
				// done!
				break;
			}
			for(T node : neighbors(head)) {
				if(!visitedBy.containsKey(node)) {
					path.add(node);
					visitedBy.put(node, head);
				}
			}
		}

		return visitedBy;
	}

	/**
	 * a breadth-first search on the internal graph of nodes
	 */
	public List<T> getPath(T start, T end) {
		// maps a node to the node that first visited it
		Map<T, T> visitedBy = getFlowGraph(start, end);

		LinkedList<T> path = new LinkedList<>();
		// now we have our tree in visitedBy
		// next, find the route from the end to the start
		// b/c otherwise we have to reverse the list before returning
		// it
		T head = end;
		path.clear();
		path.push(head);
		while(!head.equals(start)) {
			head = visitedBy.get(head);
			path.push(head);
		}

		return path;
	}

	public String toDot() {
		StringBuilder ret = new StringBuilder("digraph {\n");
		for(T node : nodes.keySet()) {
			ret.append(node + " -> { ");
			Iterable<String> itr =
				nodes.get(node)
				.stream()
				.map(Object::toString)
				::iterator;
			ret.append(String.join(" ", itr) + " };\n");
		}
		ret.append("}\n");
		return ret.toString();
	}
}
