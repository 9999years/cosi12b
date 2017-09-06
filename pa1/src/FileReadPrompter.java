/**
 * class for prompting the user for a filename to read
 * @author Rebecca Turner
 * @version 0.0.1
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

import java.io.File;

public class FileReadPrompter extends FilePrompter {
	public String promptText = "Input filename: ";

	/**
	 * checks that the file is readable and a file
	 */
	protected boolean check(String filename) {
		File f = new File(filename);
		return f.isFile() && f.canRead();
	}

	FileReadPrompter(String promptText, String checkFailText) {
		super(promptText, checkFailText);
	}

	FileReadPrompter(String promptText) {
		super(promptText);
	}

	FileReadPrompter() {
		super();
	}
}
