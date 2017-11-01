/**
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

package becca.edit;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.Optional;

/**
 * class to determine if a filename represents a readable file and convert it
 * to a File if so
 *
 * @see java.io.File
 */
public class ReadableFile {
	protected File file = null;
	String prompt = null;

	ReadableFile() {
		this("");
	}

	ReadableFile(String prompt) {
		this.prompt = prompt;
	}

	/**
	 * checks that the file is readable and a file
	 * @param filename a filename to test
	 * @return true if filename is OK, false if not
	 */
	public static Optional<File> checkReadFilename(String filename) {
		// no check for length > 0 b/c isFile accounts for that
		// (empty string is not a file)
		if(filename != null) {
			Optional<File> ret = Optional.of(new File(filename));
			return ret.filter(
				file -> file.isFile() && file.canRead());
		} else {
			return Optional.empty();
		}
	}

	/**
	 * prints the prompt and reads a filename from the user until they
	 * enter a valid one
	 */
	public File getFile() {
		Scanner input = new Scanner(System.in);
		Optional<File> ret = checkReadFilename(null);
		boolean first = true;
		while(!ret.isPresent()) {
			// not having a filename on the first loop isnt caused
			// by an error
			if(!first) {
				System.err.println("Invalid filename! "
					+ "Doesn't exist or cannot be read. "
					+ "Try again?");
			} else {
				first = false;
			}
			System.out.print(prompt);
			ret = checkReadFilename(input.nextLine());
		}
		return ret.get();
	}
}
