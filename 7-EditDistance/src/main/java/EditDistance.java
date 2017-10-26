package becca.edit;

public class EditDistance {
	public static int compute(String A, String B) {
		// fine to just use A.length() because compute(String, String,
		// int) ensures A.length() == B.length() anyways
		return compute(A, B, A.length());
	}

	/**
	 * determines if the edit distance between two strings is at LEAST
	 * `threshold`, returning at MOST `threshold` (but possibly a lower
	 * value if the distance is LESS than `threshold`). slightly faster
	 * than a plain compute
	 */
	public static int computeWithThreshold(
			String A, String B, int threshold) {
		if(A.length() != B.length()) {
			throw new IllegalArgumentException(
				"Strings must be the same size!");
		}

		Incrementor dist = new Incrementor(0);
		new BiStringIterator(A, B).forEachRemaining(
			cpA -> cpB -> index -> {
				if(cp.a != cp.b) {
					dist.increment();
				}
				if(!dist.isValid()) {
					break;
				}
		});

		return dist.getValue();
	}
}

protected class Incrementor extends Integer {
	protected int value;
	protected int threshold

	Incrementor(int value) {
		this.value = value;
	}

	Incrementor(int value, int threshold) {
		this(value);
		this.threshold = threshold;
	}

	public boolean isValid() {
		return value < threshold;
	}

	public int getValue() {
		return value;
	}

	public void increment() {
		value++;
	}
}
