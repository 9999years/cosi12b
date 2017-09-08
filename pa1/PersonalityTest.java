/**
 * Parses Keirsey (KTS) personality data from a file and writes output to a
 * file
 * @author Rebecca Turner
 * @version 1.1.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

// import one by one to avoid cluttering the namespace
import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.function.ToIntFunction;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.Arrays;

public class PersonalityTest {

	/**
	 * index of a and b question counts in array
	 * ignore blank answers
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

	/**
	 * a bunch of these fields *should* be enums but enums are a weird
	 * beast in Java distanced from their C counterparts and using them as
	 * array indicies is a hassle; most of the arrays exist because i can't
	 * create other classes anyways, so here we are...
	 */
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
	 * @param checkFailText the string to print if the user enters a
	 * filename that fails the checker
	 * @param checker a function returning a boolean to check if the
	 * filename is valid; checkWriteFilename and checkReadFilename are
	 * examples
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
	 * adjust a keirsey record group to include a new answer
	 * @param record one element of a keirsey record; see
	 * {@link #formatKeirseyRecord(int[][])}
	 * @param answer the answer int ({@link #ANSWER_BLANK},
	 * {@link #ANSWER_A}, or {@link #ANSWER_B})
	 * @return the updated record element
	 */
	public static int[] parseQuestion(int[] record, int answer) {
		if(answer == ANSWER_A) {
			record[A_INDEX] += 1;
		} else if(answer == ANSWER_B) {
			record[B_INDEX] += 1;
		}
		return record;
	}

	/**
	 * Parse a kts data string to an int array.
	 * Generally not directly useful.
	 *
	 * @param input a string representing the kts data in the format
	 * /[AaBb-]{70}/
	 * @return an int array {@link #TEST_LENGTH} long corresponding to the
	 * parsed data
	 * @throws IllegalArgumentException if the input data has any
	 * characters not in AaBb-, or isn't exactly 70 characters long
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
					+ "` in test data! Test data: `"
					+ input
					+ "`"
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
				"Invalid test data; not 70 characters long. Test data: `"
				+ input
				+ "`"
			);
		}

		return data;
	}

	/**
	 * using data from {@link #parseKeirseyStringToData(String)}, create a
	 * keirsey record for result formatting; see
	 * {@link #formatKeirseyRecord(int[][])} for details of the record
	 * format
	 * @param data an int-array returned by or compatible with
	 * {@link #parseKeirseyStringToData(String)}
	 * @return a keirsey record; see {@link #formatKeirseyRecord(int[][])}
	 * for details
	 */
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
	 * parses a string of input to a keirsey record; see
	 * {@link #formatKeirseyRecord(int[][])} for details of the return
	 * format.
	 * @param input the string of kts data, /[AaBb-]{70}/
	 * @return a keirsey record, see {@link #formatKeirseyRecord(int[][])}
	 * for format details
	 */
	public static int[][] parseKeirseyRecord(String input) {
		return reduceKeirseyDataToResult(
			parseKeirseyStringToData(input));
	}

	/**
	 * get the A and B answers for a question, like 9A-1B
	 * @param sums one element of a keirsey record; see
	 * {@link #formatKeirseyRecord(int[][])}
	 * @return a string in the format NA-MB, where N and M are integers
	 */
	public static String questionString(int[] sums) {
		return String.format("%dA-%dB", sums[A_INDEX], sums[B_INDEX]);
	}

	/**
	 * gets the mtbi-like type represented by the result
	 * @return the type represented by the result as an uppercase string
	 * like ENFP or IXTJ
	 * @param record a keirsey record; see
	 * {@link #formatKeirseyRecord(int[][])}
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
	 * get nearest percent of B answers for each axis in a kts record
	 * @param record a keirsey record; see
	 * {@link #formatKeirseyRecord(int[][])}
	 * @return an int array of the nearest percent values of each group
	 */
	public static int[] getPercents(int[][] record) {
		return Arrays.stream(record)
			.mapToInt(k -> (int) Math.round(100.0d * ((double)
				k[B_INDEX] / (k[A_INDEX] + k[B_INDEX])))
			).toArray();
	}

	/**
	 * format a keirsey record array to a COSI12B-compliant string
	 * @param record a 2-d int array containing {@link #GROUP_COUNT}
	 * elements of {@link #ANSWER_TYPES} ints each. the indexes of the top
	 * array correspond to {@link #GROUP_IE} through {@link #GROUP_JP}, and
	 * the indexes of the lower array correspond to {@link #A_INDEX} and
	 * {@link #B_INDEX}. the int values themselves correspond to the counts
	 * of A and B answers from the given test
	 * @return a COSI12B-compliant kts record string
	 */
	public static String formatKeirseyRecord(int[][] record) {
		// get percent values, map to nearest whole percent as an int
		int[] percents = getPercents(record);

		return String.format(
			"%s %s %s %s\n[%d%%, %d%%, %d%%, %d%%] = %s\n",
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

	/**
	 * attempt to open a scanner pointing towards the given file; halt
	 * execution if unable to open
	 * @param fname the file to attempt to open
	 * @return the resulting scanner
	 */
	public static Scanner attemptOpenScanner(String fname) {
		try {
			return new Scanner(new File(fname));
		} catch(FileNotFoundException e) {
			// race condition!
			// nothing to do if we can't read
			System.err.println(
				"File"
				+ fname
				+ "not found; deleted between typing input and writing?"
			);
			System.exit(-1);
			// empty object --- never created, just satisfies
			// return type requirement
			return new Scanner(System.in);
		}
	}

	/**
	 * attempt to open a printstream pointing towards the given file; halt
	 * execution if unable to open
	 * @param fname the file to attempt to open
	 * @return the resulting PrintStream
	 */
	public static PrintStream attemptPrintStream(String fname) {
		try {
			return new PrintStream(fname);
		} catch(FileNotFoundException e) {
			// nothing to do if we can't write
			System.err.println(
				"File `"
				+ fname
				+ "` could not be opened; do you have write permissions?"
			);
			System.exit(-1);
			// empty object --- never created, just satisfies
			// return type requirement
			return new PrintStream(System.out);
		}
	}

	/**
	 * transform a keirsey data file to processed records
	 * @param inFilename the filename of a kts data file to read from
	 * @return a string of all the processed records in the input file,
	 * double-newline separated
	 * @throws IllegalArgumentException when input file has an odd number of lines
	 */
	public static String formatKeirseyFile(String inFilename)
		throws IllegalArgumentException {
		Scanner fileIn = attemptOpenScanner(inFilename);

		StringBuilder ret = new StringBuilder();
		while(fileIn.hasNextLine()) {
			// consume two lines at once, but make sure we can actually get both lines
			ret.append(fileIn.nextLine() + ":\n");
			try {
				ret.append(
					formatKeirseyRecord(
					parseKeirseyRecord(fileIn.nextLine())));
			} catch(NoSuchElementException e) {
				throw new IllegalArgumentException(
					"Invalid test file `"
					+ inFilename
					+ "` has an odd number of lines"
				);
			}
			ret.append("\n");
		}

		return ret.toString();
	}

	/**
	 * attempt to open a file and write to it.
	 * just completely quit if we get an error
	 * @param fname the filename to write to
	 * @param text the text to write to the file
	 */
	public static void writeFile(String fname, String text) {
		attemptPrintStream(fname).print(text);
	}

	/**
	 * prompts for an input and output filename, reads the file and writes
	 * processed data to the output file
	 * @param args completely ignored; a different spec might read
	 * input/output filenames from it though
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
		writeFile(outFilename, formatKeirseyFile(inFilename));
	}
}
