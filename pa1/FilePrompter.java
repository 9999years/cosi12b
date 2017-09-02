import java.util.Scanner;

class FilePrompter {
	String promptText = "Input filename: ";
	String notExistsText = "File doesn't exist! Try again?\n";

	/**
	 * Override this in sub-classes for conditional rejection of certain
	 * strings
	 */
	private boolean check(String filename) {
		return true;
	}

	public String prompt() {
		Scanner stdin = new Scanner(System.in);
		String filename;
		while(true) {
			System.out.print(promptText);
			filename = stdin.nextLine();
			if(this.check(filename)) {
				break;
			} else {
				System.out.print(notExistsText);
			}
		}
		return filename;
	}

	FilePrompter(String promptText, String notExistsText) {
		this.promptText = promptText;
		this.notExistsText = notExistsText;
	}

	FilePrompter() {
	}
}
