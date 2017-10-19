package becca.smp;

import java.util.ArrayList;
import java.util.List;

/**
 * a node isolated from a greater context; has no set it belongs to or
 * knowledge of other nodes
 */
public class IsolatedNode {
	/**
	 * unique per-set id of this node
	 */
	public int id;
	/**
	 * display name of this node; purely cosmetic
	 */
	public Object name;
	/**
	 * ranked list of node preferences in opposite set; stores IDs
	 */
	public List<Integer> priorities;

	IsolatedNode(Object name) {
		this.name = name;
		priorities = new ArrayList<>();
	}

	IsolatedNode(Object name, int id) {
		this(name);
		this.id = id;
	}
}
