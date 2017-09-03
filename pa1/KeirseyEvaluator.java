/**
 * class for parsing strings into data
 * @author Rebecca Turner
 * @version 0.0.1
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

/**
 * uses KeirseyLineParser to parse a String of data (AAaaABbBB-AAabbaba...)
 * into a KeirseyResult, which can then format itself
 */
public class KeirseyEvaluator {
	private byte[] dat;
	private int INDEX = 0;
	private int GROUPS = 10;
	private int QUESTIONS_PER_GROUP = 7;
	public String name;

	/** 10 chunks of 7 questions
	 */
	private void parseChunk(KeirseyResult result) {
		int limit = INDEX + 10;
		int i = INDEX;
		// 2 × each call of parseQuestion (except for the IE group);
		// not worth using a loop for a trivial function call
		result.parseQuestion(KeirseyResult.IE, dat[i++]);
		result.parseQuestion(KeirseyResult.SN, dat[i++]);
		result.parseQuestion(KeirseyResult.SN, dat[i++]);
		result.parseQuestion(KeirseyResult.TF, dat[i++]);
		result.parseQuestion(KeirseyResult.TF, dat[i++]);
		result.parseQuestion(KeirseyResult.JP, dat[i++]);
		result.parseQuestion(KeirseyResult.JP, dat[i++]);

		INDEX = i;
	}

	private KeirseyResult parseDat(byte[] data) {
		KeirseyResult result = new KeirseyResult(this.name);
		this.dat = data;
		for(int i = 0; i < GROUPS; i++) {
			this.parseChunk(result);
		}
		return result;
	}

	/**
	 * parses a string of input with KeirseyLineParser.
	 */
	public KeirseyResult parse(String name, String input) {
		this.name = name;
		return this.parseDat(KeirseyLineParser.parse(input));
	}

	/**
	 * parses a string of input with KeirseyLineParser, but doesn't set the
	 * output name as well
	 */
	public KeirseyResult parse(String input) {
		return this.parseDat(KeirseyLineParser.parse(input));
	}

	KeirseyEvaluator(String name) {
		this.name = name;
	}

	KeirseyEvaluator() {}
}
