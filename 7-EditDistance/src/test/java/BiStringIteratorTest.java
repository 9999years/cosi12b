package becca.edit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BiStringIteratorTest {
	void forEachRemainingTest(
			String a, String b,
			int[] expectedCPA, int[] expectedCPB) {
		new BiStringIterator(a, b).forEachRemaining(
			cpA -> cpB -> inx -> {
				assertEquals(expectedCPA[inx], cpA.intValue(),
					"ensuring string a `"
					+ a
					+ "` matches at index "
					+ inx);
				assertEquals(expectedCPB[inx], cpB.intValue(),
					"ensuring string b `"
					+ b
					+ "` matches at index "
					+ inx);
			}
		);
	}

	@Test
	void forEachRemainingTest() {
		forEachRemainingTest(
			"abc", "*%(",
			new int[] {0x61, 0x62, 0x63},
			new int[] {0x2a, 0x25, 0x28}
		);
		forEachRemainingTest(
			"abc", "*%(ayrunft;wfunp;twanufty;un",
			new int[] {0x61, 0x62, 0x63},
			new int[] {0x2a, 0x25, 0x28}
		);
		forEachRemainingTest(
			"ℕ", "🤙",
			new int[] {0x2115},
			new int[] {0x1f919}
		);
	}
}
