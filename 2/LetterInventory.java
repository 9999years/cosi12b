/**
 * Keeps track of character frequencies in Latin text. Potentially useful for
 * cryptogram frequency analysis.
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

import java.text.Normalizer;

public class LetterInventory {
	/**
	 * index of ascii a
	 */
	private static final int ALPHABET_START  = 0x61;
	/**
	 * index of ascii b
	 */
	private static final int ALPHABET_END    = 0x7a;
	/**
	 * ALPHABET_END - ALPHABET_START + 1
	 */
	private static final int ALPHABET_LENGTH = 26;
	// use compatibility decomposition to match by letter rather than
	// codepoint
	private static final Normalizer.Form form = Normalizer.Form.NFKD;
	private int[] counts;
	private int corpusSize;

	LetterInventory() {
		this("");
	}

	/**
	 * initializes the object to include the values of a given corpus string
	 */
	LetterInventory(String corpus) {
		this.counts = new int[ALPHABET_LENGTH];
		if(corpus.length() > 0) {
			absorb(corpus);
		}
	}

	/**
	 * get the normalized, lower-case value of the first letter in a string
	 * (or of a char/int)
	 * @param letter a letter or letters to be normalized
	 * @return -1 if letter is an empty string, value of the first unicode
	 * codepoint otherwise
	 */
	private int normalize(String letter) {
		// make sure we don't do a bunch of processing to nothing
		if(letter.length() > 0) {
			// normalize -> lowercase -> get first codepoint
			return Character.toLowerCase(
				Normalizer.normalize(letter, this.form)
					.codePointAt(0));
		} else {
			// empty string
			return -1;
		}
	}

	private int normalize(char letter) {
		return normalize(new String(new char[] {letter}));
	}

	/**
	 * @param letter the codepoint to be normalized
	 */
	private int normalize(int letter) {
		return normalize(new String(Character.toChars(letter)));
	}

	/**
	 * check if a given codepoint is valid for insertion into the matrix
	 * used to verify insertion validity
	 * much of the same logic as {@link #getIndex(int)}
	 * @param letter the codepoint of a character to check
	 * @return true if the codepoint is valid, false otherwise
	 */
	private boolean checkValid(int letter) {
		// make sure codepoint isnt a surrogate and its normalized
		// value is in the alphabet
		letter = normalize(letter);
		return !Character.isSurrogate((char) letter)
			&& (letter >= ALPHABET_START && letter <= ALPHABET_END);
	}

	/**
	 * @param letter a normalized letter (otherwise will unnecessarily
	 * error on, e.g. upper-case letters)
	 * @return the index in {@link #counts} for the given letter
	 */
	private int getIndex(int letter) throws IllegalArgumentException {
		if(Character.isSurrogate((char) letter)) {
			// not actually a character
			throw new IllegalArgumentException(
				"Surrogate codepoints cannot be passed to get()!"
			);
		} else if(letter < ALPHABET_START || letter > ALPHABET_END) {
			// not in the alphabet
			throw new IllegalArgumentException(
				"Normalized codepoint "
				+ String.format("U+%X", letter)
				+ " isn't in alphabet,"
				+ " presumably due to being outside of the"
				+ " Latin alphabet."
			);
		}

		return letter - ALPHABET_START;
	}

	public void absorb(int letter) {
		counts[getIndex(normalize(letter))]++;
		corpusSize++;
	}

	public void absorb(char letter) {
		absorb((int) letter);
	}

	/**
	 * add data to the internal character-set from a string, char, or
	 * codepoint integer
	 * @param corpus the text to process
	 */
	public void absorb(String corpus) {
		corpus.codePoints().forEach(cp -> {
			// ignore if invalid codepoint
			// we normalize twice, essentially --- once to make
			// sure we *can* insert the codepoint, once to actually
			// insert it (absorb will throw an
			// IllegalArgumentException if called with a single
			// invalid char otherwise) --- but that's ok bc
			// normalization overhead is low
			if(checkValid(cp)) { absorb(cp); }
		});
	}

	/**
	 * get the frequency of a given character
	 *
	 * this agrees with wider java conventions (see {@link
	 * java.lang.Character} class docs) and provide an override for an int
	 * instead of a char to access all possible codepoints (java's 2-byte
	 * utf-16 encoded char data-type can't represent "astral" codepoints
	 * [above U+FFFF, most notably emoji and many han characters but also
	 * the several mathematical latin alphabets, often used for stylistic
	 * purposes])
	 *
	 * the {@link FrequencyAnalysis} class isn't written to accommodate
	 * non-normalized text so it'll crash when fed codepoints outside of (i
	 * think?) ASCII with an {@link java.lang.IndexOutOfBoundsException},
	 * but it's good practice to write flexible software either way
	 *
	 * @param letter the codepoint of a letter to fetch the frequencies of;
	 * please note that get() normalizes text so e.g. U+72 r and U+211D
	 * DOUBLE-STRUCK CAPITAL R will compare the same; as this class is
	 * designed for use in frequency analysis, differentiating different
	 * representations of the same letter is unnecessary
	 *
	 * @return the amount of that character fed to the object
	 */
	public int get(int letter) throws IllegalArgumentException {
		letter = normalize(letter);
		return counts[getIndex(letter)];
	}

	public int get(char letter) {
		// plain cast to int is OK because we check for surrogates in
		// get(int)
		return get((int) letter);
	}

	/**
	 * get percent of database represented by a letter
	 * 
	 * @param letter the codepoint of a letter to count
	 * @return the percentage of letters in the object that are the given
	 * letter
	 */
	public double getLetterPercentage(int letter) {
		// be wary of divide by zero
		return corpusSize == 0
			? 0.0
			: (double) get(letter) / corpusSize;
	}

	public double getLetterPercentage(char letter) {
		return getLetterPercentage((int) letter);
	}

	/**
	 * set a letter to a value
	 * generally not useful; try one of the {@link #absorb(int)} methods
	 *
	 * @param letter the codepoint of a letter to count
	 * @param value the value to set that letter's frequency to
	 */
	public void set(int letter, int value) {
		if(value < 0) {
			throw new IllegalArgumentException(
				"Cannot set a frequency to be less than 0!"
			);
		}
		int inx = getIndex(normalize(letter));
		corpusSize += value - counts[inx];
		counts[inx] = value;
	}

	public void set(char letter, int value) {
		set((int) letter, value);
	}

	/**
	 * how much data is in this object?
	 * @return the number of characters represented in the frequency
	 * counts; NOT the number of characters processed overall with the
	 * constructor or {@link #absorb(String)}
	 */
	public int size() {
		return corpusSize;
	}

	/**
	 * does the object have any data?
	 * @return true if the object represents frequencies of one or more
	 * characters; will return false if the object has been fed entirely
	 * non-represented characters (e.g. feeding "../92!@#(*$ {}{}{[]8"
	 * would still lead to an empty object).
	 */
	public boolean isEmpty() {
		return corpusSize == 0;
	}

	public String toString() {
		StringBuilder ret = new StringBuilder("[");
		int amt = 0;
		for(int i = ALPHABET_START; i <= ALPHABET_END; i++) {
			amt = get(i);
			for(int j = 0; j < amt; j++) {
				ret.append((char) i);
			}
		}
		ret.append(']');
		return ret.toString();
	}

	/**
	 * get the union of two LetterInventory objects; does not mutate the
	 * current object
	 *
	 * @param other the object to add with this one's data
	 * @return a new LetterInventory object
	 */
	public LetterInventory add(LetterInventory other) {
		LetterInventory ret = new LetterInventory();
		for(int i = ALPHABET_START; i < ALPHABET_END; i++) {
			ret.set(i, get(i) + other.get(i));
		}
		return ret;
	}

	/**
	 * get the value of removing another LetterInventory object's data from
	 * this one's; does not mutate the current object
	 *
	 * @param other the object to add with this one's data
	 * @return a new LetterInventory object
	 */
	public LetterInventory subtract(LetterInventory other) {
		LetterInventory ret = new LetterInventory();
		int val;
		for(int i = ALPHABET_START; i < ALPHABET_END; i++) {
			val = get(i) - other.get(i);
			if(val < 0) { return null; }
			ret.set(i, val);
		}
		return ret;
	}
}
