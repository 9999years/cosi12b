import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;
import java.io.PrintStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;

public class PersonalityTestTest {
	@Test
	public void fnamelengthtest() {
		String msg = "0-length filenames are rejected";
		String param = "";
		assertFalse(msg, PersonalityTest.checkReadFilename(param));
		assertFalse(msg, PersonalityTest.checkWriteFilename(param));
	}

	@Test
	public void fnamedirectorytest() {
		String msg = "directories are not files";
		String param = "./";
		assertFalse(msg, PersonalityTest.checkReadFilename(param));
		assertFalse(msg, PersonalityTest.checkWriteFilename(param));
	}

	@Test
	public void fnameregulartest() {
		String msg = "regular filename (Makefile) is readable";
		String param = "Makefile";
		assertTrue(msg, PersonalityTest.checkReadFilename(param));
		assertTrue(msg, PersonalityTest.checkWriteFilename(param));
	}

	@Test
	public void fnamepermissiontest() {
		String msg = "bad filename fails (system32) --- might fail if test suite run as admin";
		String param = "C:\\WINDOWS\\system32";
		assertFalse(msg, PersonalityTest.checkReadFilename(param));
		assertFalse(msg, PersonalityTest.checkWriteFilename(param));
	}

	@Test
	public void inputtest() {
		StdInTest in = new StdInTest();
		in.nullifyOut();
		in.test("null1\nnull2\r\nnull3\n\rMakefile\n");
		assertEquals(
			"multiple inputs work --- this might fail if the files null1, null2, or null3 exist",
			PersonalityTest.promptFilename(
				"",
				"",
				f -> PersonalityTest.checkReadFilename(f)
			),
			"Makefile");
	}

	@Test(expected = IllegalArgumentException.class)
	public void ktslengthtest() throws IllegalArgumentException {
		PersonalityTest.parseKeirseyStringToData("abcdef");
	}

	@Test(expected = IllegalArgumentException.class)
	public void ktschartest() throws IllegalArgumentException {
		PersonalityTest.parseKeirseyStringToData(
"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaca"
		);
	}

	@Test
	public void ktsregulartest() {
		// pre-computed to be correct
		assertArrayEquals(PersonalityTest.parseKeirseyStringToData(
"BA-ABABBB-bbbaababaaaabbaaabbaaabbabABBAAABABBAAABABAAAABBABAAABBABAAB"
			), new int[] {
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_BLANK, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_BLANK,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B
		});
		assertArrayEquals(PersonalityTest.parseKeirseyStringToData(
"aabaabbabbbaaaabaaaabaaaaababbbaabaaaabaabbbbabaaaabaabaaaaaabbaaaaabb"),
			new int[] {
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_B,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_A, PersonalityTest.ANSWER_A,
			PersonalityTest.ANSWER_B, PersonalityTest.ANSWER_B
		});
	}

	@Test
	public void ktsdataregulartest() {
		assertArrayEquals(
			PersonalityTest.reduceKeirseyDataToResult(
				PersonalityTest.parseKeirseyStringToData(
"aabaabbabbbaaaabaaaabaaaaababbbaabaaaabaabbbbabaaaabaabaaaaaabbaaaaabb")),
			new int[][] {
				{8, 2}, {11, 9}, {17, 3}, {9, 11}
			}
		);
		assertArrayEquals(
			PersonalityTest.reduceKeirseyDataToResult(
				PersonalityTest.parseKeirseyStringToData(
"BABAAAABAAAAAAABAAAABBAAAAAABAAAABABAABAAABABABAABAAAAAABAAAAAABAAAAAA")),
			new int[][] {
				{1, 9}, {17, 3}, {18, 2}, {18, 2}
			}
		);
	}

	@Test
	public void testquestionstring() {
		assertEquals(PersonalityTest.questionString(
			new int[] {8, 2}), "8A-2B");
		// i guess?
		assertEquals(PersonalityTest.questionString(
			new int[] {1, 39302}), "1A-39302B");
	}

	@Test
	public void testmbti() {
		assertEquals(PersonalityTest.getMBTI(
			new int[][] {{8, 2}, {11, 9}, {17, 3}, {9, 11}}),
			"ESTP");
		assertEquals(PersonalityTest.getMBTI(
			new int[][] {{2, 8}, {9, 9}, {11, 9}, {15, 5}}),
			"IXTJ");
	}

	@Test
	public void testpercents() {
		assertArrayEquals(
			PersonalityTest.getPercents(
				new int[][] {{2, 8}, {9, 9}, {11, 9}, {15, 5}}
			),
			new int[] {80, 50, 45, 25}
		);
		assertArrayEquals(
			PersonalityTest.getPercents(
				new int[][] {{1, 8}, {7, 11}, {14, 5}, {15, 5}}
			),
			new int[] {89, 61, 26, 25}
		);
	}

	@Test
	public void testformatrecord() {
		assertEquals(
			PersonalityTest.formatKeirseyRecord(
				new int[][] {{1, 8}, {7, 11}, {14, 5}, {15, 5}}
			),
			"1A-8B 7A-11B 14A-5B 15A-5B\n"
			+ "[89%, 61%, 26%, 25%] = INTJ\n"
		);
	}
}

/**
 * “dont test that stdin/stdout works” is what everyone says.
 * so i want to clarify that this is *not* doing that; but i have a couple
 * classes that read from stdin, so we use this class to simulate user input
 */
class StdInTest {
	public void nullifyOut() {
		System.setOut(new PrintStream(new OutputStream() {
			public void write(int b) {} }));
	}

	public void test(String input) {
		System.setIn(new ByteArrayInputStream(input.getBytes()));
	}
}
