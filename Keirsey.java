public class KeirseyEvaluator {
	private byte[] dat;
	private int INDEX = 0;

	/** there are 10 chunks of 7 questions
	 */
	private void parseChunk() {
		int limit = INDEX + 10;
		for(int i = INDEX; i < limit; i++) {
		}
		INDEX = i;
	}

	public void KeirseyEvaluator(String input) {
		data = KeirseyLineParser(input);
	}
}

class KeirseyResult {
	// A answers
	private int E_AMOUNT = 0;
	private int S_AMOUNT = 0;
	private int T_AMOUNT = 0;
	private int J_AMOUNT = 0;

	// B answers
	private int I_AMOUNT = 0;
	private int N_AMOUNT = 0;
	private int F_AMOUNT = 0;
	private int P_AMOUNT = 0;

	// axis indexes
	public static int IE = 0x00;
	public static int SJ = 0x01;
	public static int TF = 0x02;
	public static int JP = 0x03;

	private int aQuestion(int answer) {
		return answer 
	}

	public void parseQuestion(int group) {
	}
}
