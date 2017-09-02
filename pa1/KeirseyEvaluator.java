public class KeirseyEvaluator {
	private byte[] dat;
	private int INDEX = 0;
	private int GROUPS = 10;
	private int QUESTIONS_PER_GROUP = 7;

	/** there are 10 chunks of 7 questions
	 */
	private void parseChunk(result, data) {
		int limit = INDEX + 10;
		int i = INDEX;
		result.parseQuestion(KeirseyResult.IE, dat[i++]);
		// 2 × parseQuestion; not worth using a loop for a trivial
		// function call
		result.parseQuestion(KeirseyResult.SN, dat[i++]);
		result.parseQuestion(KeirseyResult.SN, dat[i++]);

		result.parseQuestion(KeirseyResult.TF, dat[i++]);
		result.parseQuestion(KeirseyResult.TF, dat[i++]);

		result.parseQuestion(KeirseyResult.JP, dat[i++]);
		result.parseQuestion(KeirseyResult.JP, dat[i++]);

		INDEX = i;
	}

	private void parseDat(byte[] data) {
		KeirseyResult result = new KeirseyResult();
		for(int i = 0; i < GROUPS; i++) {
			parseChunk(result, data);
		}
		return result;
	}

	public KeirseyResult parse(String input) {
		return parseDat(KeirseyLineParser(input));
	}
}

/**
 * Takes a string and returns a BitSet of personality data.
 * Generally not directly useful.
 * @throws IllegalArgumentException
 */
class KeirseyLineParser {
	public static byte ANSWER_BLANK = 0x00;
	public static byte ANSWER_A     = 0x01;
	public static byte ANSWER_B     = 0x02;
	public static int TEST_LENGTH   = 70;

	public static byte[] parse(String input) {
		byte[] data = new byte[TEST_LENGTH];
		int i;
		for(i = 0; i < input.length(); i++) {
			// don't bother with `.toLowerCase` to avoid overhead
			// for Unicode / locale normalization --- not in the
			// spec
			char c = input.charAt(i);
			if(c == 'A' || c == 'a') {
				data[i] = ANSWER_A;
			} else if(c == 'B' || c == 'b') {
				data[i] = ANSWER_B;
			} else if(c == '-') {
				data[i] = ANSWER_BLANK;
			} else {
				throw new IllegalArgumentException(
					"Illegal character `"
					+ c
					+ "` in test data!"
				);
			}
		}

		// error handling after iteration because codepoints over
		// U+FFFF are split into surrogate pairs that take up two java
		// `char`s; If we validated before-hand an otherwise fine
		// string with one 'astral' codepoint (such as any emoji) would
		// cause an invalid *length* error rather than an invalid
		// *character* error
		if(i != TEST_LENGTH) {
			throw new IllegalArgumentException(
				"Invalid test data; not 70 characters long"
			);
		}

		return data;
	}

}
