package becca.markov;

public class RandomWriter {
	public static void main(String[] args) {
		Object o = new MarkovGenerator<Integer>(
			new Integer[] {1, 2, 3, 4, 5, 6}, 5);
	}
}
