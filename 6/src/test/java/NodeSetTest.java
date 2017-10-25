package becca.smp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NodeSetTest {
	static void assertSetMatches(NodeSet setA, int[][] matches) {
		for(int match[] : matches) {
			assertEquals(
				setA.get(match[0]).id,
				match[0],
				"ensuring ids are correct"
			);
			assertEquals(
				match[1],
				setA.get(match[1]).id,
				"ensuring ids are correct"
			);
			assertEquals(
				setA.get(match[0]).match,
				setA.get(match[1]),
				"ensuring a's match is b"
			);
			assertEquals(
				setA.get(match[0]),
				setA.get(match[1]).match,
				"ensuring b's match is a"
			);
		}
	}

	static NodeSetFactory constructSet(int[][] dat) {
		NodeSetFactory factory = new NodeSetFactory();

		for(int i = 0; i < dat.length; i++) {
			factory.add(i);
			for(int p : dat[i]) {
				factory.addPref(p);
			}
		}

		return factory;
	}

	static NodeSet verifySets(
			int[][] datA,
			int[][] datB,
			int[][] matches) {
		NodeSetFactory A = constructSet(datA);
		NodeSetFactory B = constructSet(datB);
		NodeSetFactory.link(A, B);

		NodeSet setA = A.getSet();
		NodeSet setB = B.getSet();

		NodeSet.match(setA, setB);
		assertSetMatches(setA, matches);

		return setA;
	}

	static void verifySets(
			int[][] datA,
			int[][] datB,
			int[][] matches,
			double abPriority,
			double baPriority) {
		NodeSet setA = verifySets(datA, datB, matches);
		assertEquals(abPriority, setA.getMeanPriority(), 0.0001);
		NodeSet.match(setA.other, setA);
		assertEquals(baPriority, setA.getMeanPriority(), 0.0001);
	}

	static void integrationTest() {

		// short.dat
		verifySets(
			new int[][] { // men
				{3, 0, 1, 2},
				{1, 2, 0, 3},
				{1, 3, 2, 0},
				{2, 0, 3, 1}
			},
			new int[][] { // women
				{3, 0, 2, 1},
				{0, 2, 1, 3},
				{0, 1, 2, 3},
				{3, 0, 2, 1},
			},
			new int[][] {
				{0, 3},
				{1, 2},
				{2, 1},
				{3, 0}
			},
			1.5, // men favored
			1.75 // women favored
		);

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
				{3, 3}
			}
		);

		verifySets(
			new int[][] {
				{3, 4, 2, 6, 1, 5, 0},
				{4, 5, 3, 6, 2, 1, 0},
				{0, 5, 4, 3, 2, 6, 1},
				{2, 4, 5, 6, 1, 3, 0},
				{0, 6, 5, 3, 2, 4, 1},
				{5, 2, 6, 4, 1, 3, 0},
				{0, 6, 3, 1, 5, 4, 2}
			},
			new int[][] {
				{2, 3, 1, 0, 5, 6, 4},
				{5, 3, 1, 2, 4, 0, 6},
				{5, 2, 4, 6, 1, 3, 0},
				{0, 5, 2, 1, 3, 6, 4},
				{0, 5, 4, 2, 3, 6, 1},
				{0, 6, 2, 3, 4, 5, 1},
				{4, 5, 1, 3, 2, 6, 0}
			},
			new int[][] {
				{0, 3},
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
