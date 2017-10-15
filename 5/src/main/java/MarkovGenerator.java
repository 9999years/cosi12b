package becca.markov;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class MarkovGenerator<T> {
	protected ArrayList<T> corpus;
	//protected HashSet<Context<T>> cache;
	protected Random rand = new Random();
	/**
	 * context length
	 */
	int length;
	/**
	 * guaranteed to be in [length, corpus.size)
	 */
	protected int inx = -1;

	MarkovGenerator(int length) {
		this.length = length;
		corpus = new ArrayList<T>();
	}

	MarkovGenerator(int length, int seed) {
		this(length);
		seed(seed);
	}

	MarkovGenerator(T[] corpus, int length) {
		this(length);
		for(T t : corpus) {
			consume(t);
		}
	}

	MarkovGenerator(T[] corpus, int length, int seed) {
		this(corpus, length);
		seed(seed);
	}

	public void seed(long seed) {
		rand.setSeed(seed);
	}

	public void consume(T t) {
		//cache.clear();
		corpus.add(t);
	}

	protected int startingIndex() {
		return rand.nextInt(corpus.size() - length);
	}

	protected boolean matches(int i, int j) {
		if(i < 0 || j < 0) {
			return false;
		} else if(i > j) {
			// ensure i < j
			// swap
			int tmp = i;
			i = j;
			j = tmp;
		} else if(i == j) {
			return true;
		}

		// compare i, j and increment
		// create a limit variable to ensure i < j < corpus.size
		for(int limit = Math.min(j + length, corpus.size());
				j < limit;
				i++, j++) {
			if(!corpus.get(i).equals(corpus.get(j))) {
				return false;
			}
		}

		// all comparisons successful
		return true;
	}

	protected ArrayList<Integer> getPossibilities() {
		if(inx < 0) {
			inx = startingIndex();
		}

		ArrayList<Integer> possibilities = new ArrayList<>();

		for(int i = 0; i < corpus.size() - 1; i++) {
			if(matches(inx, i)) {
				possibilities.add(i + 1);
			}
		}

		return possibilities;
	}

	protected int nextIndex() {
		if(inx < 0) {
			inx = startingIndex();
		}

		ArrayList<Integer> possibilities = getPossibilities();
		if(possibilities.size() == 0) {
			inx = startingIndex();
			return nextIndex();
		}

		inx = possibilities.get(rand.nextInt(possibilities.size()));

		return inx;
	}

	public T next() {
		return corpus.get(nextIndex());
	}

	public String toString() {
		return "becca.markov.MarkovGenerator[{" + corpus + "}]";
	}
}
