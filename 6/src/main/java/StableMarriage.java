package becca.smp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;

public class StableMatching {
	static void verifyMatching(NodeSet a) {
		//Collections.sort(discardedPriorities);
		// dig through disarded and priorities to see if (from
		// wikipedia) for each element A in the first set
		// 1. A prefers some given element B of the second matched set
		//    over the element to which A is already matched, and
		// and
		// 2.  B also prefers A over the element to which B is already
		//     matched.
		for(Node A : a.nodes) {
			for(Node B : a.other.nodes) {
				// stuff
			}
		}
	}

	static void assertSetMatches(NodeSet setA, int[][] matches) {
		for(int match[] : matches) {
			assert setA.get(match[0]).id == match[0];
			assert match[1] == setA.get(match[1]).id;
			assert setA.get(match[0]).match == setA.get(match[1]);
			assert setA.get(match[0]) == setA.get(match[1]).match;
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

	static void verifySets(
			int[][] datA,
			int[][] datB,
			int[][] matches) {
		NodeSetFactory A = constructSet(datA);
		NodeSetFactory B = constructSet(datB);
		NodeSetFactory.link(A, B);

		NodeSet setA = A.getSet();
		NodeSet setB = B.getSet();

		NodeSet.match(setA, setB);
		System.out.println("matches:");
		System.out.println(setA.getMatchStatus());
		System.out.println("expected:");
		for(int[] line : matches) {
			System.out.println(line[0] + " matched with " + line[1]);
		}
		assertSetMatches(setA, matches);
	}

	static void integrationTest() {
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
				{3, 0, 1, 2},
				{1, 2, 0, 3},
				{1, 3, 2, 0},
				{2, 0, 3, 1}
			},
			new int[][] {
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

	public static void main(String args[]) {
		integrationTest();
	}
}
