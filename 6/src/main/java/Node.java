package becca.smp;

import java.util.ArrayList;
import java.util.List;

public class Node {
	/**
	 * which set this node belongs to
	 */
	public int set;
	/**
	 * id of this node
	 * TODO determine if id is per-set or for all nodes
	 */
	public int id;
	/**
	 * name of this node; purely cosmetic
	 * TODO maybe support object descriptors?
	 */
	public String name;
	/**
	 * total amount of nodes; used for determining id
	 */
	protected static int nodeCount = 0;
	/**
	 * list of all the nodes
	 * TODO determine per-set or for all
	 */
	protected static List<Node> allNodes;
	/**
	 * ranked list of node preferences in opposite set; stores IDs
	 */
	protected List<Integer> preferences;
	/**
	 * id of best match
	 */
	protected int match;

	Node(String name, List<Integer> preferences) {
	}

	protected void initId() {
		id = ++nodeCount;
	}
}
