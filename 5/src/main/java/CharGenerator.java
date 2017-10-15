package becca.markov;

import java.lang.Math;

public class CharGenerator extends MarkovGenerator<Integer> {
	CharGenerator(String source, int k, int seed) {
		this(source, k);
		super.seed(seed);
	}

	CharGenerator(String source, int k) {
		super(source.codePoints().boxed().toArray(Integer[]::new), k);
	}

	int getNextChar() {

		//String indent = "         ";
		//System.out.println();
		//System.out.println(indent + getPossibilities().size() + " possibilities");
		//System.out.println(indent + "index is: " + inx);
		//if(inx > length) {
			//System.out.println(indent + "context is: "
				//+ new String(
					//corpus.subList(inx - length, inx + 1)
						//.stream()
						//.mapToInt(i -> i)
						//.toArray(),
					//0, length)
			//);
		//}

		return super.next();
	}
}
