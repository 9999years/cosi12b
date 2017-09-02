import java.util.Scanner;
import java.util.NoSuchElementException;

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
		System.out.print(this.promptText);
		while(true) {
			filename = stdin.nextLine();
			if(this.check(filename)) {
				break;
			} else {
				System.out.print(this.checkFailText);
				System.out.print(this.promptText);
			}
		}
		return filename;
	}

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
