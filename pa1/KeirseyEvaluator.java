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
		// 2 × SN; not worth using a loop for a trivial function call
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
		return answer == KeirseyLineParser.ANSWER_A ? 1 : 0;
	}

	private int bQuestion(int answer) {
		return answer == KeirseyLineParser.ANSWER_B ? 1 : 0;
	}

	public void parseQuestion(int group, int answer) {
		if(group == IE) {
			E_AMOUNT += aQuestion(answer);
			I_AMOUNT += bQuestion(answer);
		} else if(group == SJ) {
			S_AMOUNT += aQuestion(answer);
			N_AMOUNT += bQuestion(answer);
		} else if(group == TF) {
			T_AMOUNT += aQuestion(answer);
			F_AMOUNT += bQuestion(answer);
		} else if(group == JP) {
			J_AMOUNT += aQuestion(answer);
			P_AMOUNT += bQuestion(answer);
		} else {
			throw new IllegalArgumentException("Invalid group ID");
		}
	}
}
