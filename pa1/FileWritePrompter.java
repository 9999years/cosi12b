class FileWritePrompter extends FilePrompter {
	FileWritePrompter(String promptText, String checkFailText) {
		super(promptText, checkFailText);
	}

	FileWritePrompter(String promptText) {
		super(promptText);
	}

	FileWritePrompter() {
		this.promptText = "Output filename: ";
	}
}
