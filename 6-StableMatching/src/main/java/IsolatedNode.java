package becca.smp;

import java.util.ArrayList;
import java.util.List;

/**
 * a node isolated from a greater context; has no set it belongs to or
 * knowledge of other nodes. contains an int id, object name, and list of
 * priorities specified as ids, with the index implicitly being the priority;
 * essentially a specialized 3-tuple class
 *
 * @See Node
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
protected class IsolatedNode {
	public int id;
	public Object name;
	/**
	 * ranked list of node preferences in opposite set; stores IDs. index
	 * is implicitly the priority
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
