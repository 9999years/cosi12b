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
		NodeSet setA = constructSet(datA);
		NodeSet.match(setA, constructSet(datB));
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
				{3, 2, 4, 1},
				{2, 3, 1, 4},
				{3, 1, 2, 4},
				{3, 2, 4, 1}
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
				{1, 3, 4, 2, 1, 6, 7, 5},
				{2, 6, 4, 2, 3, 5, 1, 7},
				{3, 6, 3, 5, 7, 2, 4, 1},
				{4, 1, 6, 3, 2, 4, 7, 5},
				{5, 1, 6, 5, 3, 4, 7, 2},
				{6, 1, 7, 3, 4, 5, 6, 2},
				{7, 5, 6, 2, 4, 3, 7, 1}
			},
			new int[][] {
				{1, 4, 5, 3, 7, 2, 6, 1},
				{2, 5, 6, 4, 7, 3, 2, 1},
				{3, 1, 6, 5, 4, 3, 7, 2},
				{4, 3, 5, 6, 7, 2, 4, 1},
				{5, 1, 7, 6, 4, 3, 5, 2},
				{6, 6, 3, 7, 5, 2, 4, 1},
				{7, 1, 7, 4, 2, 6, 5, 3}
			},
			new int[][] {
				{2, 5},
				{3, 1},
				{4, 3},
				{5, 7},
				{6, 6},
				{7, 2}
			}
		);
	}
}
