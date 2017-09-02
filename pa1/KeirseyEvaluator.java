public class KeirseyEvaluator {
	private byte[] dat;
	private int INDEX = 0;
	private int GROUPS = 10;
	private int QUESTIONS_PER_GROUP = 7;

	/** there are 10 chunks of 7 questions
	 */
	private void parseChunk(KeirseyResult result, byte[] data) {
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

	private KeirseyResult parseDat(byte[] data) {
		KeirseyResult result = new KeirseyResult();
		for(int i = 0; i < GROUPS; i++) {
			this.parseChunk(result, data);
		}
		return result;
	}

	public KeirseyResult parse(String input) {
		return this.parseDat(KeirseyLineParser.parse(input));
	}
}
