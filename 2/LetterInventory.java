import java.text.Normalizer;
import java.lang.IllegalArgumentException;

public class LetterInventory {
	/**
	 * index of ascii A
	 */
	private final int A_OFFSET = 0x61;
	private final int ALPHABET_LENGTH = 26;
	private int[] counts;
	private int corpusSize;
	private Normalizer normalizer;
	private final Normalizer.form form = Normalizer.Form.NFD;

	LetterInventory() {
		// already all zeroes
		this.counts = new int[ALPHABET_LENGTH];
		this.normalizer = new Normalizer();
	}

	/**
	 * get the normalized, lower-case value of the first letter in a string
	 * @param letter a letter or letters to be normalized
	 * @return -1 if letter is an empty string, value of the first unicode
	 * codepoint otherwise
	 */
	private int normalize(String letter) {
		if(letter.length > 0) {
			return Character.toLowerCase(
				normalizer.normalize(letter, this.form)
					.codePointAt(0));
		} else {
			// empty string
			return -1;
		}
	}

	private int normalize(char letter) {
		return normalize(new char[] {letter});
	}

	private int normalize(int letter) {
		return normalize(new int[] {letter});
	}

	/**
	 * @param letter a NORMALIZED 
	 */
	private int getIndex(int letter) {
		if(Character.isSurrogate((char) letter)) {
			// not actually a character
			throw IllegalArgumentException(
				"Surrogate codepoints cannot be passed to get()!"
			);
		} else if(letter <= A_OFFSET
			|| letter >= A_OFFSET + ALPHABET_LENGTH) {
			throw IllegalArgumentException(
				"Normalized codepoint value isn't in alphabet,"
				+ "presumably due to being outside of the Latin"
				+ "alphabet."
			);
		}

		return letter - A_OFFSET;
	}

	/**
	 * @param letter a normalized int unicode scalar value
	 */
	private int normalizedGet(int letter) {
		return counts[getIndex(letter)];
	}

	/**
	 * agree with wider java conventions (see Character class docs) and
	 * provide an override for an int instead of a char to access all
	 * possible codepoints
	 */
	public int get(int letter) throws IllegalArgumentException {
		letter = normalize(letter);
		return normalizedGet(letter);
	}

	public int get(char letter) {
		// plain cast is OK becuase we check for surrogates in get(int)
		return get((int) letter);
	}

	public double getLetterPercentage(char letter) {
		return get(letter) / corpusSize;
	}

	public void set(int letter, int value) {

	}

	public void set(char letter, int value) {
		set((int) letter, value);
	}

	public int size() {
		return corpusSize;
	}

	public int isEmpty() {
		return corpusSize == 0;
	}

	public String toString() {
	}

	public LetterInventory add(LetterInventory other) {
	}

	public LetterInventory subtract(LetterInventory other) {
	}
}
