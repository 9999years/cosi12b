/**
 * Class from prompting user for filenames
 * @author Rebecca Turner
 * @version 0.0.1
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 * Base class for prompting users for filenames
 * See FileReadPrompter and FileWritePrompter for specialized sub-classes;
 * FileReadPrompter checks if the file exists and is readable, and
 * FileWritePrompter has a better default prompt string.
 */
abstract class FilePrompter {
	public String promptText = "Input filename: ";
	public String checkFailText = "File doesn't exist! Try again?\n";

	/**
	 * Override this in sub-classes for conditional rejection of certain
	 * strings
	 */
	protected boolean check(String filename) {
		return true;
	}

	public String prompt() throws NoSuchElementException {
		Scanner stdin = new Scanner(System.in);
		String filename;

		// prompt and loop
		System.out.print(this.promptText);
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
			if(this.check(filename)) {
				break;
			} else {
				// retry
				System.out.print(this.checkFailText);
				System.out.print(this.promptText);
			}
		}
		if(filename.equals("")) {
			System.err.println(
				"Empty filename! Exiting."
			);
			System.exit(-1);
		}
		return filename;
	}

	// constructors
	FilePrompter(String promptText, String checkFailText) {
		this.promptText = promptText;
		this.checkFailText = checkFailText;
	}

	FilePrompter(String promptText) {
		this.promptText = promptText;
	}

	FilePrompter() {
	}
}
