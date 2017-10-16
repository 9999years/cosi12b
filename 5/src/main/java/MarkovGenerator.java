package becca.markov;

import java.util.ArrayList;
// just for constructors
import java.util.List;
import java.util.stream.BaseStream;
import java.util.Iterator;
import java.util.Random;
// min
import java.lang.Math;

/**
 * generic markov generator class for arbitrary sequences of elements of type
 * T; can be constructed with an array of T[], an Iterator<T>, a Stream<T>
 * (including an IntStream if T is Integer, etc), or a List<T>
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

	/**
	 * approximately one billion constructors so args are:
	 * [U corpus] int length [int seed]
	 * and U = T[]
	 *       | Iterator<T>
	 *       | BaseStream<T, S extends BaseStream<T, S>>
	 *       | List<T>
	 */
	MarkovGenerator(int length) {
		if(length < 0) {
			throw new IllegalArgumentException("Length cannot be less than 0!");
		}
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
		this(length, seed);
		consume(corpus);
	}

	MarkovGenerator(Iterator<T> corpus, int length) {
		this(length);
		consume(corpus);
	}

	MarkovGenerator(Iterator<T> corpus, int length, int seed) {
		this(length, seed);
		consume(corpus);
	}

	<S extends BaseStream<T, S>> MarkovGenerator(BaseStream<T, S> corpus, int length) {
		this(length);
		consume(corpus);
	}

	<S extends BaseStream<T, S>> MarkovGenerator(BaseStream<T, S> corpus, int length, int seed) {
		this(length, seed);
		consume(corpus);
	}

	MarkovGenerator(List<T> corpus, int length) {
		this(length);
		consume(corpus);
	}

	MarkovGenerator(List<T> corpus, int length, int seed) {
		this(length, seed);
		consume(corpus);
	}

	public void seed(long seed) {
		rand.setSeed(seed);
	}

	/**
	 * absorb an element T at the end of the corpus from a singlet or
	 * array/stream/list/iterator
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

	/**
	 * for IntStream and the like, e.g. from String.codePoints()
	 */
	public <S extends BaseStream<T, S>> void consume(BaseStream<T, S> s) {
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
		if(inx < 0 || inx >= corpus.size()) { newStartingIndex(); }
	}

	/**
	 * do the next `length` elements in the corpus match at locations i and
	 * j?
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

		if(j + length > corpus.size() || i + length > corpus.size()) {
			return false;
		}

		// compare i, j and increment
		// create a limit variable to ensure i < j < corpus.size
		for(int limit = j + length;
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

		for(int i = 0; i < corpus.size() - length; i++) {
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
