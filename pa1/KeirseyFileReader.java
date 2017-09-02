class KeirseyFileReader {
	String filename;

	public void read() {
		Scanner fileIn = new Scanner(this.filename);
		String[] lines = new String[];
		while(true) {
			try {
				lines.append(fileIn.nextLine());
			} catch(NoSuchElementException e) {
				break;
			}
		}

		if(lines.length % 2 != 0) {
			throw new IllegalArgumentException(
				"Invalid test file; not an even number of lines"
			);
		}

		KeirseyData[] ret = new KeirseyData[];
		for(int i = 0; i < lines.length / 2; i++) {
			// add new record with the name
			ret.append(new KeirseyData(lines[i]));
		}
	}

	public void KeirseyFileReader(String filename) {
		this.filename = filename;
	}
}
