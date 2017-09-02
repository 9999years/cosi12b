/**
 * Takes a string and returns a byte array of personality data.
 * Generally not directly useful.
 * @throws IllegalArgumentException
 */
public class KeirseyLineParser {
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
