class FilePrompter {
	String promptText = "Input filename: ";
	String notExistsText = "File doesn't exist! Try again?";
	public String prompt() {
		Scanner stdin = new Scanner(System.in);
		String filename;
		while(true) {
			System.out.print(promptText);
			filename = stdin.nextLine();
			if(File(filename).exists()) {
				break;
			} else {
				System.out.print(notExistsText);
			}
		}
	}

	public void FilePrompter(String promptText, String notExistsText) {
		this.promptText = promptText;
		this.notExistsText = notExistsText;
	}
}
