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
		/*
		 * If you're reading carefully, you might notice this strays
		 * from the supplied implementation guidelines. Wondering why?
		 * Read that huge block of text below. Or skip it if you don't
		 * care. I'm not your boss.
		 */
		inx = rand.nextInt(corpus.size() - length) + length;

		/*
		 * Okay, so next we immediately throw away the next `length`
		 * random ints from the generator. This seems strange, and is,
		 * so it warrants some explanation.
		 *
		 * The "implementation approach" section in pa5.pdf on page 3
		 * suggests (verbatim):
		 *
		 * 1. To select the initial seed, choose a random position P in
		 *    the source string between L - k, where L is the length of
		 *    the text source. Then, use the source characters between
		 *    (P, P+k) as the random seed.
		 * 2. To choose the next character, find each occurrence of the
		 *    seed in the source and store the character that follows
		 *    it into an ArrayList.
		 *
		 * However, if this is actually implemented, you'll find that
		 * the test suggested on page 4 (roughly called with `java
		 * RandomWriter 15 50 hamlet.txt out.txt 3`) will fail.
		 * Although it produces the correct output, it starts 15
		 * characters (`length`) earlier than it should, implying the
		 * reference implementation used to generate the output
		 * actually compares (or was designed to compare) (P - k, P) as
		 * the source seed, rather than (P, P + k) as the
		 * implementation approach section suggests. The seed matching
		 * the reference implementation can be found by simply adding
		 * `length` to the random seed. However, then we run into
		 * another problem: our output is different! You see, the
		 * pre-computed output requires that output is generated from
		 * the original seed, and that the first `length` elements are
		 * simply thrown out! Rather than actually do this, it works
		 * fine to just generate `length` random integers, advancing
		 * the internal state of our random object just enough to match
		 * the pre-computed output. This is all very silly and, quite
		 * frankly, rather confusing. But it works, and, despite my
		 * best efforts, I cannot find a better way. So here you go.
		 * The tests pass.
		 *
		 * Here are some things that don't work:
		 * 1. Returning the [inx + length]th element instead of the
		 *    [inx]th element
		 * 2. Using (P - k, P) as the seed and checking those elements
		 *    as the sample output implies might be happening
		 *
		 * Side-note: a large portion of the time (about 88% in the
		 * supplied test), there's only one potential possibility to
		 * choose from; calls to the random object can be greatly
		 * reduced by selecting the singular element when only one is
		 * available, but this too creates a different state in the
		 * random object than the test requires.
		 *
		 * IN CONCLUSION: This is bad. I think it's bad. I don't like
		 * it. But it's the only way I can find. Please email if you
		 * know of a better way.
		 *
		 * rebeccaturner@brandeis.edu
		 */

		// skip next `length` ints
		rand.ints(length).forEach(i -> {});
	}

	protected void ensureIndexValid() {
		if(inx < 0 || inx >= corpus.size() - length) { newStartingIndex(); }
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
