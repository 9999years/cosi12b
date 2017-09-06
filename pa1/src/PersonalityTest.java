/**
 * Parses Keirsey (KTS) personality data from files and writing output to a
 * file
 * @author Rebecca Turner
 * @version 0.0.1
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

// import one by one to avoid cluttering the namespace
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.LinkedList;

import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;


public class PersonalityTest {

	public int KEIRSEY_AXIS_COUNT = 4;
	public int KEIRSEY_A_INDEX    = 0;
	public int KEIRSEY_B_INDEX    = 1;

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
		String filename;

		// prompt and loop
		System.out.print(promptText);
		while(true) {
			try {
				filename = stdin.nextLine();
			} catch(NoSuchElementException e) {
				System.err.println(
					"Unexpected end of input stream! Exiting."
				);
				System.exit(-1);
				return "";
			}
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
	 * @param fname the filename of a kts data file
	 * @return a 3d array with an element for each record in the input
	 * file, each containing 4 elements, each containing an a-count and a
	 * b-count element. better as an object but i'm restricted to one class
	 */
	public int[][][] readKeirseyFile(String fname) throws FileNotFoundException {
		Scanner fileIn = new Scanner(new File(fname));
		// growable data-type, no need for indexed access
		LinkedList<String> lines = new LinkedList<String>();
		while(true) {
			try {
				lines.push(fileIn.nextLine());
			} catch(NoSuchElementException e) {
				break;
			}
		}

		if(lines.size() % 2 != 0) {
			throw new IllegalArgumentException(
				"Invalid test file; not an even number of lines"
			);
		}

		// we need this a couple times, no need to re-calculate multiple times
		int recordCount = lines.size() / 2;

		// 2 lines per record so we only need half the amount
		// we've already checked for an even line-count so no need for
		// wiggle room or anything
		int[][][] ret = new int[KeirseyResult][][] KeirseyResult[recordCount];
		for(int i = 0; i < recordCount; i++) {
			// add new record, pop twice
			this.data[i] = new KeirseyEvaluator().parse(
				lines.removeLast(),
				lines.removeLast());
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
		// set up the reader
		//KeirseyFileReader reader = new KeirseyFileReader(inFilename);
		// try to read, prepare to catch a race condition
		try {
			readKeirseyFile(inFilename);
		} catch(FileNotFoundException e) {
			System.err.println("File not found! Deleted between input and computation?");
			return;
		}
		//// write and exit
		//reader.writeFile(outFilename);
	}
}
