/**
 * Parses Keirsey (KTS) personality data from files and writing output to a
 * file
 * @author Rebecca Turner
 * @version 0.0.1
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

class PersonalityTest {
	public static void main(String[] args) {
		String inFilename;
		try {
			inFilename = new FileReadPrompter().prompt();
		} catch(NoSuchElementException e) {
			System.err.println("Unexpected end of input stream! Exiting.");
			return;
		}
		String outFilename;
		try {
			outFilename = new FileWritePrompter().prompt();
		} catch(NoSuchElementException e) {
			System.err.println("Unexpected end of input stream! Exiting.");
			return;
		}
		KeirseyFileReader reader = new KeirseyFileReader(inFilename);
		try {
			reader.read();
		} catch(FileNotFoundException e) {
			System.err.println("File not found; deleted between typing input and writing?");
		}
		reader.writeFile(outFilename);
	}
}
