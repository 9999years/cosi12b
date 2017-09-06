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
public abstract class FilePrompter {
	public String promptText = "Input filename: ";
	public String checkFailText = "File doesn't exist! Try again?\n";

	/**
	 * Override this in sub-classes for conditional rejection of certain
	 * strings; by default we just make sure the user has entered *something*
	 * @param filename a filename to validate
	 * @return true if filename is valid, false if not
	 */
	protected boolean check(String filename) {
		return filename.length() > 0;
	}

	/**
	 * Prompt the user for a filename and handle bad ones by asking again.
	 *
	 * In an ideal world the user would handle those errors but if stdin
	 * ends the whole game is off anyways, and also I really don't want to
	 * do a ton of error handling in main().
	 *
	 * @throws NoSuchElementException when stdin ends unexpectedly
	 * @return user-entered filename
	 */
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
