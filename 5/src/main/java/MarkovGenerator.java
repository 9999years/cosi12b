package becca.markov;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;
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
		return length + rand.nextInt(corpus.size() - length);
	}

	protected boolean matches(int inx1, int inx2) {
		if(inx1 < 0 || inx2 < 0) {
			return false;
		}

		if(inx1 > inx2) {
			// swap
			int tmp = inx1;
			inx1 = inx2;
			inx2 = tmp;
		} else if(inx1 == inx2) {
			return true;
		}

		for(int limit = Math.max(0, inx1 - length);
				inx1 > limit; inx1--, inx2--) {
			if(!corpus.get(inx2).equals(corpus.get(inx1))) {
				System.out.println(
					corpus.get(inx1)
					+ " != "
					+ corpus.get(inx2)
				);
				return false;
			}
		}

		return true;
	}

	public T next() {
		if(inx < 0) {
			inx = startingIndex();
		}
		return corpus.get(0);
	}

	public String toString() {
		return "becca.markov.MarkovGenerator[{" + corpus + "}]";
	}
}
