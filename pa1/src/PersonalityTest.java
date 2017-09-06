/**
 * Parses Keirsey (KTS) personality data from a file and writes output to a
 * file
 * @author Rebecca Turner
 * @version 0.0.1
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

// import one by one to avoid cluttering the namespace
import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.util.stream.Stream;
import java.util.function.ToIntFunction;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.Arrays;

public class PersonalityTest {

	/**
	 * index of a and b question counts in array
	 */
	public static int A_INDEX               = 0;
	public static int B_INDEX               = 1;
	public static int ANSWER_TYPES          = 2;
	/**
	 * questions in a whole test
	 */
	public static int TEST_LENGTH           = 70;
	/**
	 * there are 10 sections of 7 questions each in a repeating pattern
	 */
	public static int SECTION_COUNT         = 10;
	public static int QUESTIONS_PER_SECTION = 7;

	public static int ANSWER_A     = 0;
	public static int ANSWER_B     = 1;
	public static int ANSWER_BLANK = 2;

	/**
	 * a group is an axis like the extroversion/introversion axis
	 * there are four groups; not to be confused with *sections*, which are
	 * the units of 7 questions in the test data
	 */
	public static int GROUP_COUNT = 4;
	public static int GROUP_IE    = 0;
	public static int GROUP_SN    = 1;
	public static int GROUP_TF    = 2;
	public static int GROUP_JP    = 3;

	/**
	 * checks that the file is readable and a file
	 * @param filename a filename to test
	 * @return true if filename is OK, false if not
	 */
	public static boolean checkReadFilename(String filename) {
		// no check for length > 0 b/c isFile accounts for that
		// (empty string is not a file)
		File f = new File(filename);
		return f.isFile() && f.canRead();
	}

	/**
	 * checks that the file is writeable and a valid path
	 * @param filename a filename to test
	 * @return true if filename is OK, false if not
	 */
	public static boolean checkWriteFilename(String filename) {
		File f = new File(filename);
		return filename.length() > 0 && f.canWrite() == f.isFile();
	}

	/**
	 * Prompt the user for a filename and handle bad ones by asking again.
	 *
	 * In an ideal world the user would handle those errors but if stdin
	 * ends the whole game is off anyways, and also I really don't want to
	 * do a ton of error handling in main().
	 *
	 * @throws NoSuchElementException when stdin ends unexpectedly
	 * @param promptText the string to print before prompting the user
	 * @param checkFailText the string to print if the user enters a filename that fails the checker
	 * @param checker a function to check if the filename is valid
	 * @return user-entered filename
	 */
	public static String promptFilename(
			String promptText, String checkFailText, Predicate<String> checker)
			throws NoSuchElementException {
		Scanner stdin = new Scanner(System.in);
		String filename = "";

		// prompt and loop
		System.out.print(promptText);
		while(stdin.hasNextLine()) {
			filename = stdin.nextLine();
				//System.err.println(
					//"Unexpected end of input stream! Exiting."
				//);
				//System.exit(-1);
				//return "";
			// does the file pass some check?
			if(checker.test(filename)) {
				break;
			} else {
				// retry
				System.out.print(checkFailText);
				System.out.print(promptText);
			}
		}
		return filename;
	}

	/**
	 * adds an answer to the result's internal dataset
	 * @param group the group id (IE, SN, TF, or JP)
	 * @param answer the answer byte (KeirseyLineParser.ANSWER.BLANK,
	 * ANSWER.A, or ANSWER.B)
	 */
	public static int[] parseQuestion(int[] record, int answer) {
		if(answer == ANSWER_A) {
			record[A_INDEX] += 1;
		} else if(answer == ANSWER_B) {
			record[B_INDEX] += 1;
		}
		return record;
	}

	public static int[][] reduceKeirseyDataToResult(int[] data) {
		int[][] ret = new int[GROUP_COUNT][ANSWER_TYPES];
		int[] groupOrder = {
			GROUP_IE,
			GROUP_SN, GROUP_SN,
			GROUP_TF, GROUP_TF,
			GROUP_JP, GROUP_JP
		};
		/**
		* there are 10 sections of 7 questions.
		* this parses the next 7 questions, and is called 10 times by parseDat
		*/
		for(int i = 0; i < TEST_LENGTH; i += QUESTIONS_PER_SECTION) {
			for(int j = 0; j < QUESTIONS_PER_SECTION; j++) {
				ret[groupOrder[j]] =
					parseQuestion(ret[groupOrder[j]], data[i + j]);
			}
		}
		return ret;
	}

	/**
	 * Takes a string and returns a KeirseyLineParser.ANSWER array of
	 * personality data.
	 * Generally not directly useful.
	 *
	 * @param input a string representing the kts data in the format
	 * /[AaBb-]{70}/
	 * @return a KeirseyLineParser.ANSWER[] of the processed data
	 */
	public static int[] parseKeirseyStringToData(String input)
			throws IllegalArgumentException {
		int[] data = new int[TEST_LENGTH];
		int i;
		for(i = 0; i < input.length(); i++) {
			// don't bother with `.toLowerCase` to avoid overhead
			// for Unicode / locale normalization --- not in the
			// spec
			char c = input.charAt(i);
			if(c == 'A' || c == 'a') {
				data[i] = ANSWER_A;
			} else if(c == 'B' || c == 'b') {
				data[i] = ANSWER_B;
			} else if (c == '-') {
				data[i] = ANSWER_BLANK;
			} else {
				// while it's true that java's internal utf-16
				// string encoding means that an astral
				// codepoint in test data would get split
				// resulting in only a meaningless half of a
				// character being printed if this exception is
				// hit, the 'illegal character' part is more
				// important than printing the actual character
				throw new IllegalArgumentException(
					"Illegal character `"
					+ c
					+ "` in test data!"
				);
			}
		}

		// error handling after iteration because codepoints over
		// U+FFFF are split into surrogate pairs that take up two java
		// `char`s; If we validated before-hand an otherwise fine
		// string with one 'astral' codepoint (such as any emoji) would
		// cause an invalid *length* error rather than an invalid
		// *character* error
		if(i != TEST_LENGTH) {
			throw new IllegalArgumentException(
				"Invalid test data; not 70 characters long"
			);
		}

		return data;
	}

	/**
	 * parses a string of input with KeirseyLineParser.
	 * @param input the string of kts data, /[AaBb-]{70}/
	 * @return a KeirseyResult representing the kts data from data
	 */
	public static int[][] parseKeirseyRecord(String input) {
		return reduceKeirseyDataToResult(
			parseKeirseyStringToData(input));
	}

	/**
	 * get the A and B answers for a question
	 * like 9A-1B
	 * @param sums the a and b answer counts for a group as a 2-element int
	 * array
	 */
	public static String questionString(int[] sums) {
		return String.format("%dA-%dB", sums[A_INDEX], sums[B_INDEX]);
	}

	/**
	 * gets the mtbi-like type represented by the result
	 * @return the type represented by the result as an uppercase string
	 * like ENFP or IXTJ
	 */
	public static String getMBTI(int[][] record) {
		StringBuilder ret = new StringBuilder();
		char[][] groupLetters = {
			{'E', 'I'},
			{'S', 'N'},
			{'T', 'F'},
			{'J', 'P'},
		};
		for(int i = 0; i < GROUP_COUNT; i++) {
			ret.append(
				record[i][A_INDEX] == record[i][B_INDEX]
				? "X" : record[i][A_INDEX] > record[i][B_INDEX]
				? groupLetters[i][A_INDEX] : groupLetters[i][B_INDEX]);
		}
		return ret.toString();
	}

	/**
	 */
	public static String formatKeirseyRecord(int[][] record) {
		// get percent values, map to nearest whole percent as an int
		int[] percents = Arrays.stream(record)
			.mapToInt(k -> (int) Math.round(
				100.0d * ((double) k[B_INDEX]
				/ (k[A_INDEX] + k[B_INDEX])))
			).toArray();

		return String.format(
			"%s %s %s %s\n[%d%%, %d%%, %d%%, %d%%] = %s\n\n",
			questionString(record[GROUP_IE]),
			questionString(record[GROUP_SN]),
			questionString(record[GROUP_TF]),
			questionString(record[GROUP_JP]),
			percents[GROUP_IE],
			percents[GROUP_SN],
			percents[GROUP_TF],
			percents[GROUP_JP],
			getMBTI(record)
		);
	}

	public static Scanner attemptOpenScanner(String fname) {
		try {
			return new Scanner(new File(fname));
		} catch(FileNotFoundException e) {
			// race condition!
			// nothing to do if we can't read
			System.err.println(
				"File not found; deleted between typing input and writing?"
			);
			System.exit(-1);
			// empty object --- never created, just satisfies
			// return type requirement
			return new Scanner(System.in);
		}
	}

	public static PrintStream attemptPrintStream(String fname) {
		try {
			return new PrintStream(fname);
		} catch(FileNotFoundException e) {
			// nothing to do if we can't write
			System.err.println(
				"File could not be opened; do you have write permissions?"
			);
			System.exit(-1);
			// empty object --- never created, just satisfies
			// return type requirement
			return new PrintStream(System.out);
		}
	}

	/**
	 * @param fname the filename of a kts data file
	 * @return a 3d array with an element for each record in the input
	 * file, each containing 4 elements, each containing an a-count and a
	 * b-count element. better as an object but i'm restricted to one class
	 */
	public static void transformKeirseyFile(String inFilename, String outFilename) {
		Scanner fileIn = attemptOpenScanner(inFilename);

		// growable data-type, no need for indexed access, only
		LinkedList<String> lines = new LinkedList<String>();
		while(fileIn.hasNextLine()) {
			lines.push(fileIn.nextLine());
		}

		if(lines.size() % 2 != 0) {
			throw new IllegalArgumentException(
				"Invalid test file; not an even number of lines"
			);
		}

		// we need this a couple times, no need to re-calculate multiple times
		int recordCount = lines.size() / 2;

		PrintStream streamOut = attemptPrintStream(outFilename);

		// 2 lines per record so we only need half the amount
		// we've already checked for an even line-count so no need for
		// wiggle room or anything
		int[][][] ret = new int[recordCount][][];
		for(int i = 0; i < recordCount; i++) {
			streamOut.println(lines.removeLast() + ":");
			// add new record, pop twice
			streamOut.println(
				formatKeirseyRecord(
				parseKeirseyRecord(lines.removeLast())));
		}
	}


	/**
	 * prompts for an input and output filename, reads the file and writes
	 * processed data to the output file
	 * @param args completely ignored; in a better program it might read
	 * input/output filenames
	 */
	public static void main(String[] args) {
		// get input filename
		String inFilename  = promptFilename(
			"Input filename: ",
			"File doesn't exist or isn't readable! Try again?\n",
			f -> checkReadFilename(f));
		// get output filename
		String outFilename = promptFilename(
			"Output filename: ",
			"Invalid path or un-writable file! Try again?\n",
			f -> checkWriteFilename(f));
		// parse and write
		transformKeirseyFile(inFilename, outFilename);
	}
}
