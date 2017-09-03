/**
 * class for prompting the user for a filename to write
 * @author Rebecca Turner
 * @version 0.0.1
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

import java.io.File;

public class FileWritePrompter extends FilePrompter {
	protected boolean check(String filename) {
		// make sure we can actually write the file!
		File f = new File(filename);
		return filename.length > 0 && f.canWrite() == f.isFile();
	}

	FileWritePrompter(String promptText, String checkFailText) {
		super(promptText, checkFailText);
	}

	FileWritePrompter(String promptText) {
		super(promptText);
	}

	/**
	 * better default than FilePrompter
	 */
	FileWritePrompter() {
		this.promptText = "Output filename: ";
	}
}
