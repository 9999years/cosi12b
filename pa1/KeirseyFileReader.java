import java.util.Scanner;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.PrintStream;
import java.io.FileNotFoundException;

class KeirseyFileReader {
	String filename;
	KeirseyResult[] data;

	public void read() {
		Scanner fileIn = new Scanner(this.filename);
		ArrayList<String> lines = new ArrayList<String>();
		while(true) {
			try {
				lines.add(fileIn.nextLine());
			} catch(NoSuchElementException e) {
				break;
			}
		}

		if(lines.size() % 2 != 0) {
			throw new IllegalArgumentException(
				"Invalid test file; not an even number of lines"
			);
		}

		this.data = new KeirseyResult[lines.size() / 2];
		for(int i = 0; i < lines.size(); i++) {
			// add new record with the name
			this.data[i] = new KeirseyEvaluator().parse(lines.get(i + 1));
			this.data[i].name = lines.get(i);
		}
	}

	public String format() {
		StringBuilder ret = new StringBuilder();
		for(int i = 0; i < this.data.length; i++) {
			ret.append(this.data[i].toString());
		}
		return ret.toString();
	}

	public void writeFile(String outFilename) {
		try {
			PrintStream outFile = new PrintStream(outFilename);
			outFile.print(this.format());
		} catch(FileNotFoundException e) {
			System.err.println("File not found; deleted between typing input and writing?");
		}
	}

	KeirseyFileReader(String filename) {
		this.filename = filename;
	}
}
