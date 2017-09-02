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
		String inFilename  = new FileReadPrompter().prompt();
		String outFilename = new FileWritePrompter().prompt();
		KeirseyFileReader reader = new KeirseyFileReader(inFilename);
		try {
			reader.read();
		} catch(FileNotFoundException e) {
			System.err.println("File not found! Deleted between input and computation?");
		}
		reader.writeFile(outFilename);
	}
}
