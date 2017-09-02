class FileWritePrompter extends FilePrompter {
	FileWritePrompter(String promptText, String checkFailText) {
		super(promptText, checkFailText);
	}

	FileWritePrompter(String promptText) {
		super(promptText);
	}

	FileWritePrompter() {
		public String promptText = "Output filename: ";
	}
}
