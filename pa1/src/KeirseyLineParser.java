/**
 * parses a string of kts data into a byte array; not useful for external
 * applications
 * @author Rebecca Turner
 * @version 0.0.1
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

/**
 * Class for parsing kts data expressed as strings to... a similarly useless
 * binary format. You're probably interested in KeirseyEvaluator or
 * KeirseyFileReader
 */
public class KeirseyLineParser {
	public static enum ANSWER {
		BLANK,
		A,
		B
	}
	public static int TEST_LENGTH   = 70;

	/**
	 * Takes a string and returns a KeirseyLineParser.ANSWER array of
	 * personality data.
	 * Generally not directly useful.
	 *
	 * @param input a string representing the kts data in the format
	 * /[AaBb-]{70}/
	 * @return a KeirseyLineParser.ANSWER[] of the processed data
	 */
	public static KeirseyLineParser.ANSWER[] parse(String input)
			throws IllegalArgumentException {
		KeirseyLineParser.ANSWER[] data =
			new KeirseyLineParser.ANSWER[TEST_LENGTH];
		int i;
		for(i = 0; i < input.length(); i++) {
			// don't bother with `.toLowerCase` to avoid overhead
			// for Unicode / locale normalization --- not in the
			// spec
			char c = input.charAt(i);
			if(c == 'A' || c == 'a') {
				data[i] = ANSWER.A;
			} else if(c == 'B' || c == 'b') {
				data[i] = ANSWER.B;
			} else if(c == '-') {
				data[i] = ANSWER.BLANK;
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
