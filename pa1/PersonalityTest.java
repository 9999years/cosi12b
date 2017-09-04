/**
 * Parses Keirsey (KTS) personality data from files and writing output to a
 * file
 * @author Rebecca Turner
 * @version 0.0.1
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class PersonalityTest {
	/**
	 * prompts for an input and output filename, reads the file and writes
	 * processed data to the output file
	 */
	public static void main(String[] args) {
		// get input filename
		String inFilename  = new FileReadPrompter().prompt();
		// get output filename
		String outFilename = new FileWritePrompter().prompt();
		// set up the reader
		KeirseyFileReader reader = new KeirseyFileReader(inFilename);
		// try to read, prepare to catch a race condition
		try {
			reader.read();
		} catch(FileNotFoundException e) {
			System.err.println("File not found! Deleted between input and computation?");
			return;
		}
		// write and exit
		reader.writeFile(outFilename);
	}
}
