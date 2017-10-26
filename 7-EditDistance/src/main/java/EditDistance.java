package becca.edit;

public class EditDistance {
	public static int compute(String A, String B) {
		int dist = 0;
		Iterable<Tuple<Integer, Integer>> codepoints =
			new BiZip<>(A.codePoints(), B.codePoints());
		for(Tuple<Integer, Integer> tuple : codepoints) {
			if(tuple.t != tuple.u) {
				dist++;
			}
		}
		return dist;
	}
}
