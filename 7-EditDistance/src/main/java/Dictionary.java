package becca.edit;

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
