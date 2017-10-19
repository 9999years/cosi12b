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

	@Test
	static void integration() {
		NodeSetFactory factory = new NodeSetFactory();

		int[][] datA = new int[][] {
			{3, 2, 0, 1}, // 0
			{1, 0, 2, 3}, // 1
			{0, 2, 3, 1}, // 2
			{3, 2, 0, 1}  // 3
		};

		for(int i = 0; i < datA.length; i++) {
			factory.add(i);
			factory.addPrefs(datA[i]);
		}

		NodeSet setA = factory.getSet();
		factory.reset();

		int[][] datB = new int[][] {
			{3, 2, 4, 1},
			{2, 3, 1, 4},
			{3, 1, 2, 4},
			{3, 2, 4, 1}
		};

		for(int i = 0; i < datB.length; i++) {
			factory.add(i);
			factory.addPrefs(datB[i]);
		}

		NodeSet setB = factory.getSet();

		NodeSet.match(setA, setB);

		assertSetMatches(setA,
			new int[][] {
				{0, 2},
				{1, 1},
				{2, 0},
				{3, 3},
				{0, 3}
			}
		);

		//1, 3, 4, 2, 1, 6, 7, 5
		//2, 6, 4, 2, 3, 5, 1, 7
		//3, 6, 3, 5, 7, 2, 4, 1
		//4, 1, 6, 3, 2, 4, 7, 5
		//5, 1, 6, 5, 3, 4, 7, 2
		//6, 1, 7, 3, 4, 5, 6, 2
		//7, 5, 6, 2, 4, 3, 7, 1

		//1, 4, 5, 3, 7, 2, 6, 1
		//2, 5, 6, 4, 7, 3, 2, 1
		//3, 1, 6, 5, 4, 3, 7, 2
		//4, 3, 5, 6, 7, 2, 4, 1
		//5, 1, 7, 6, 4, 3, 5, 2
		//6, 6, 3, 7, 5, 2, 4, 1
		//7, 1, 7, 4, 2, 6, 5, 3


		//2, 5
		//3, 1
		//4, 3
		//5, 7
		//6, 6
		//7, 2
	}
}
