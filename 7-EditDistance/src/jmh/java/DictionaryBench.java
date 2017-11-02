package becca.edit;

import org.openjdk.jmh.annotations.*;

import java.util.Random;

public class DictionaryBench {

	@State(Scope.Thread)
	public static class ThreadState {
		public static Random rand;
		ThreadState() {
			rand = new Random();
		}
	}

	static final int iterations = 20000;

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
	public Dictionary addBenchmark(ThreadState state) {
		Dictionary ret = new Dictionary((int) (iterations * 0.8));
		int i = iterations;
		while(i --> 0) {
			ret.add(randString(4, state.rand));
		}
		return ret;
	}
}
