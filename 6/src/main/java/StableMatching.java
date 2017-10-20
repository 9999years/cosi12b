package becca.smp;

public class StableMatching {
	static void assertSetMatches(NodeSet setA, int[][] matches) {
		//for(int match[] : matches) {
			//assertEquals(
				//setA.get(match[0]).id,
				//match[0],
				//"ensuring ids are correct"
			//);
			//assertEquals(
				//match[1],
				//setA.get(match[1]).id,
				//"ensuring ids are correct"
			//);
			//assertEquals(
				//setA.get(match[0]).match,
				//setA.get(match[1]),
				//"ensuring a's match is b"
			//);
			//assertEquals(
				//setA.get(match[0]),
				//setA.get(match[1]).match,
				//"ensuring b's match is a"
			//);
		//}
	}

	static NodeSet constructSet(int[][] dat) {
		NodeSetFactory factory = new NodeSetFactory();

		for(int i = 0; i < dat.length; i++) {
			factory.add(i);
			factory.addPrefs(dat[i]);
		}

		return factory.getSet();
	}

	static void verifySets(
			int[][] datA,
			int[][] datB,
			int[][] matches) {
		System.out.println("Constructing first set");
		NodeSet setA = constructSet(datA);
		System.out.println("Constructing second set");
		NodeSet setB = constructSet(datB);
		System.out.println("Matching");
		NodeSet.match(setA, setB);
		System.out.println("Verifying matches");
		System.out.println("set A is " + setA);
		System.out.println("set B is " + setB);
		assertSetMatches(setA, matches);
	}

	public static void main(String args[]) {
		verifySets(
			new int[][] {
				{3, 2, 0, 1}, // 0
				{1, 0, 2, 3}, // 1
				{0, 2, 3, 1}, // 2
				{3, 2, 0, 1}  // 3
			},
			new int[][] {
				{2, 1, 3, 0},
				{1, 2, 0, 3},
				{2, 0, 1, 3},
				{2, 1, 3, 0}
			},
			new int[][] {
				{0, 2},
				{1, 1},
				{2, 0},
				{3, 3},
				{0, 3}
			}
		);

		verifySets(
			new int[][] {
				{0, 2, 3, 1, 0, 5, 6, 4},
				{1, 5, 3, 1, 2, 4, 0, 6},
				{2, 5, 2, 4, 6, 1, 3, 0},
				{3, 0, 5, 2, 1, 3, 6, 4},
				{4, 0, 5, 4, 2, 3, 6, 1},
				{5, 0, 6, 2, 3, 4, 5, 1},
				{6, 4, 5, 1, 3, 2, 6, 0}
			},
			new int[][] {
				{0, 3, 4, 2, 6, 1, 5, 0},
				{1, 4, 5, 3, 6, 2, 1, 0},
				{2, 0, 5, 4, 3, 2, 6, 1},
				{3, 2, 4, 5, 6, 1, 3, 0},
				{4, 0, 6, 5, 3, 2, 4, 1},
				{5, 5, 2, 6, 4, 1, 3, 0},
				{6, 0, 6, 3, 1, 5, 4, 2}
			},
			new int[][] {
				{1, 4},
				{2, 0},
				{3, 2},
				{4, 6},
				{5, 5},
				{6, 1}
			}
		);
	}
}
