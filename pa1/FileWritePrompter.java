class FileWritePrompter extends FilePrompter {
	public String promptText = "Output filename: ";

	FileWritePrompter(String promptText, String checkFailText) {
		super(promptText, checkFailText);
	}

	FileWritePrompter(String promptText) {
		super(promptText);
	}

	FileWritePrompter() {
	}
}
