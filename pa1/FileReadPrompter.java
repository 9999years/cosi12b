import java.io.File;

class FileReadPrompter extends FilePrompter {
	public String promptText = "Input filename: ";
	protected boolean check(String filename) {
		return new File(filename).exists();
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
