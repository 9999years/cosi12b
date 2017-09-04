/**
 * class for parsing files into data
 * @author Rebecca Turner
 * @version 0.0.1
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

import java.util.Scanner;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Actual class to use; takes a filename and returns a KeirseyResult
 * use the constructor with a filename, .read, and then .toString or .writeFile
 */
public class KeirseyFileReader {
	String filename;
	KeirseyResult[] data;

	private void reader(String fname) throws FileNotFoundException {
		Scanner fileIn = new Scanner(new File(this.filename));
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

		// we need this a couple times, no need to re-calculate
		int recordCount = lines.size() / 2;

		// 2 lines per record so we only need half the amount
		// we've already checked for an even line-count so no need for
		// wiggle room or anything
		this.data = new KeirseyResult[recordCount];
		for(int i = 0; i < recordCount; i++) {
			// add new record, pop twice
			this.data[i] = new KeirseyEvaluator().parse(
				lines.removeLast(),
				lines.removeLast());
		}
	}

	/**
	 * Reads and parses the pre-configured file, checks for correctness
	 * @param fname a string to override this.filename if necessary
	 * @throws FileNotFoundException when a file object for fname
	 * cannot be opened
	 */
	public void read(String fname) throws FileNotFoundException {
		this.reader(fname);
	}

	/**
	 * Reads and parses the pre-configured file, checks for correctness
	 * @throws FileNotFoundException when a file object for this.filename
	 * cannot be opened
	 */
	public void read() throws FileNotFoundException {
		this.reader(this.filename);
	}

	/**
	 * format each record in the file into a string and return it
	 * @return a String representing the processed data for each record in
	 * the input file
	 */
	public String toString() {
		StringBuilder ret = new StringBuilder();
		for(int i = 0; i < this.data.length; i++) {
			ret.append(this.data[i].toString());
		}
		return ret.toString();
	}

	/**
	 * format to a string and then write to a file
	 *
	 * i'm gonna be honest this only exists because of the 'no io in
	 * main()' restriction
	 *
	 * @param outFilename the file to write out to
	 */
	public void writeFile(String outFilename) {
		try {
			PrintStream outFile = new PrintStream(outFilename);
			outFile.print(this.toString());
		} catch(FileNotFoundException e) {
			System.err.println(
				"File not found; deleted between typing input and writing?"
			);
			System.exit(-1);
		}
	}

	KeirseyFileReader(String filename) {
		this.filename = filename;
	}
}
