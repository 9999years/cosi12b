class KeirseyResult {
	// A answers
	public int E_AMOUNT = 0;
	public int S_AMOUNT = 0;
	public int T_AMOUNT = 0;
	public int J_AMOUNT = 0;

	// B answers
	public int I_AMOUNT = 0;
	public int N_AMOUNT = 0;
	public int F_AMOUNT = 0;
	public int P_AMOUNT = 0;

	// axis indexes
	public static int IE = 0x00;
	public static int SJ = 0x01;
	public static int TF = 0x02;
	public static int JP = 0x03;

	public String name;

	public void KeirseyData(String name, String data) {
		this.name = name;

	}

	public String toString() {
		int[] percents = DoubleStream(this.getPercentages())
			.mapToInt(k -> Math.round(k * 100.0d))
			.toArray();

		return String.format(
			"%s:\n%s %s %s %s\n[%d, %d, %d, %d] = %s\n\n",
			this.name,
			this.questionString(this.IE),
			this.questionString(this.SJ),
			this.questionString(this.TF),
			this.questionString(this.JP),
			percents[this.IE],
			percents[this.SJ],
			percents[this.TF],
			percents[this.JP],
			this.getType()
		);
	}

	private int aQuestion(int answer) {
		return answer == KeirseyLineParser.ANSWER_A ? 1 : 0;
	}

	private int bQuestion(int answer) {
		return answer == KeirseyLineParser.ANSWER_B ? 1 : 0;
	}

	private String questionString(int group) {
		int a_amount, b_amount;
		if(group == this.IE) {
			a_amount = this.E_AMOUNT;
			b_amount = this.I_AMOUNT;
		} else if(group == this.SJ) {
			a_amount = this.S_AMOUNT;
			b_amount = this.N_AMOUNT;
		} else if(group == this.TF) {
			a_amount = this.T_AMOUNT;
			b_amount = this.F_AMOUNT;
		} else if(group == this.JP) {
			a_amount = this.J_AMOUNT;
			b_amount = this.P_AMOUNT;
		} else {
			throw new IllegalArgumentException("Invalid group ID");
		}
		return String.format("%dA-%dB", a_amount, b_amount);
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

	public double[] getPercentages() {
		return new double[] {
			I_AMOUNT / (E_AMOUNT + I_AMOUNT),
			N_AMOUNT / (S_AMOUNT + N_AMOUNT),
			F_AMOUNT / (T_AMOUNT + F_AMOUNT),
			P_AMOUNT / (J_AMOUNT + P_AMOUNT)
		};
	}

	public String getType() {
		StringBuilder ret = new StringBuilder();
		ret.append(E_AMOUNT == I_AMOUNT ? "X" : E_AMOUNT > I_AMOUNT ? "E" : "I");
		ret.append(S_AMOUNT == N_AMOUNT ? "X" : S_AMOUNT > N_AMOUNT ? "S" : "N");
		ret.append(T_AMOUNT == F_AMOUNT ? "X" : T_AMOUNT > F_AMOUNT ? "T" : "F");
		ret.append(J_AMOUNT == P_AMOUNT ? "X" : J_AMOUNT > P_AMOUNT ? "J" : "P");
		return ret.toString();
	}
}

