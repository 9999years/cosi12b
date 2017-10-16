package becca.markov;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.BaseStream;
import java.util.Random;
import java.lang.Math;

/**
 * generic markov generator class for arbitrary sequences
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class MarkovGenerator<T> {
	protected ArrayList<T> corpus;
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
		consume(corpus);
	}

	MarkovGenerator(T[] corpus, int length, int seed) {
		this(corpus, length);
		seed(seed);
	}

	public void seed(long seed) {
		rand.setSeed(seed);
	}

	/**
	 * absorb an element T at the end of the corpus
	 */
	public void consume(T t) {
		corpus.add(t);
	}

	public void consume(T[] ts) {
		for(T t : ts) {
			consume(t);
		}
	}

	public void consume(Iterator<T> i) {
		i.forEachRemaining(t -> consume(t));
	}

	public void consume(BaseStream<T, S extends BaseStream<T, S>> s) {
		consume(s.iterator());
	}

	public void consume(List<T> l) {
		consume(l.iterator());
	}

	/**
	 * assign a new starting index to inx
	 */
	protected void newStartingIndex() {
		inx = rand.nextInt(corpus.size() - length);
	}

	protected void ensureIndexValid() {
		if(inx < 0) { newStartingIndex(); }
	}

	/**
	 * do the next `length` elements in the corpus match at locations i and j?
	 */
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

	/**
	 * get list of indices in corpus the next element may be --- in a
	 * better implementation, this method might hit a cache first
	 */
	protected ArrayList<Integer> getPossibilities() {
		ensureIndexValid();

		ArrayList<Integer> possibilities = new ArrayList<>();

		for(int i = 0; i < corpus.size() - 1; i++) {
			if(matches(inx, i)) {
				possibilities.add(i + 1);
			}
		}

		return possibilities;
	}

	/**
	 * get the next index and assign it to the internal inx counter
	 */
	protected int nextIndex() {
		ensureIndexValid();

		ArrayList<Integer> possibilities = getPossibilities();

		if(possibilities.size() == 0) {
			newStartingIndex();
			return nextIndex();
		}

		inx = possibilities.get(rand.nextInt(possibilities.size()));

		return inx;
	}

	/**
	 * get the next T from the corpus with even probability across
	 * locations where the next `length` elements match
	 */
	public T next() {
		return corpus.get(nextIndex());
	}

	public String toString() {
		return "becca.markov.MarkovGenerator[{" + corpus + "}]";
	}
}
