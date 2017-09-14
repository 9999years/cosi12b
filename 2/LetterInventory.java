import java.text.Normalizer;

public class LetterInventory {
	/**
	 * index of ascii A
	 */
	private final int ALPHABET_START = 0x61;
	private final int ALPHABET_LENGTH = 26;
	private final int ALPHABET_END = 0x7a;
	private int[] counts;
	private int corpusSize;
	private final Normalizer.Form form = Normalizer.Form.NFD;

	private void initCommon() {
		this.counts = new int[ALPHABET_LENGTH];
	}

	LetterInventory() {
		initCommon();
	}

	LetterInventory(String corpus) {
		initCommon();
		absorb(corpus);
	}

	/**
	 * get the normalized, lower-case value of the first letter in a string
	 * @param letter a letter or letters to be normalized
	 * @return -1 if letter is an empty string, value of the first unicode
	 * codepoint otherwise
	 */
	private int normalize(String letter) {
		if(letter.length() > 0) {
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

	private int normalize(int letter) {
		return normalize(new String(Character.toChars(letter)));
	}

	private boolean checkValid(int letter) {
		letter = normalize(letter);
		return !Character.isSurrogate((char) letter)
			&& (letter >= ALPHABET_START && letter <= ALPHABET_END);
	}

	/**
	 * @param letter a NORMALIZED letter
	 */
	private int getIndex(int letter) {
		if(Character.isSurrogate((char) letter)) {
			// not actually a character
			throw new IllegalArgumentException(
				"Surrogate codepoints cannot be passed to get()!"
			);
		} else if(letter < ALPHABET_START || letter > ALPHABET_END) {
			throw new IllegalArgumentException(
				"Normalized codepoint value `"
				+ new String(Character.toChars(letter))
				+ "` isn't in alphabet,"
				+ " presumably due to being outside of the Latin"
				+ " alphabet."
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

	public void absorb(String corpus) {
		corpus.codePoints().forEach(cp -> {
			if(checkValid(cp)) { absorb(cp); }
		});
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
		return (double) get(letter) / corpusSize;
	}

	public void set(int letter, int value) {
		int inx = getIndex(normalize(letter));
		corpusSize += value - counts[inx];
		counts[inx] = value;
	}

	public void set(char letter, int value) {
		set((int) letter, value);
	}

	public int size() {
		return corpusSize;
	}

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

	public LetterInventory add(LetterInventory other) {
		LetterInventory ret = new LetterInventory();
		for(int i = ALPHABET_START; i < ALPHABET_END; i++) {
			ret.set(i, get(i) + other.get(i));
		}
		return ret;
	}

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
