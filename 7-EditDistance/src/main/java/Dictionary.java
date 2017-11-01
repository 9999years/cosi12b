/**
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

package becca.edit;

/**
 * dictionary class to represent a graph of neighboring words
 *
 * public api is mostly {@link becca.edit.Graph#getPath Graph.getPath} and
 * {@link becca.edit.Graph#add Graph.add}
 */
public class Dictionary extends Graph<String> {
	Dictionary() {
		super(DEFAULT_CAPACITY);
	}

	Dictionary(int initialCapacity) {
		super(initialCapacity);
	}

	protected boolean areNeighbors(String a, String b) {
		return Strings.areNeighbors(a, b);
	}

	protected boolean pathMayExist(String a, String b) {
		return Strings.sameLength(a, b);
	}
}
