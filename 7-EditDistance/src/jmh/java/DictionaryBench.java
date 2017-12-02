package becca.edit;

import org.openjdk.jmh.annotations.*;

import java.util.Random;

public class DictionaryBench {
	static final int ITERATIONS = 1000;

	public int randLower(Random rand) {
		// random lowercase ascii
		return 0x61 + rand.nextInt(25);
	}

	public String randString(int length, Random rand) {
		String ret = "";
		while(length --> 0) {
			ret += (char) randLower(rand);
		}
		return ret;
	}

	@Benchmark
	public Dictionary addBenchmark() {
		Dictionary ret = new Dictionary((int) (ITERATIONS * 0.8));
		Random rand = new Random();
		int i = ITERATIONS;
		while(i --> 0) {
			ret.add(randString(4, rand));
		}
		return ret;
	}
}
