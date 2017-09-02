import java.util.Scanner;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;

class KeirseyFileReader {
	String filename;
	KeirseyResult[] data;

	public void read() throws FileNotFoundException {
		Scanner fileIn = new Scanner(new File(this.filename));
		LinkedList<String> lines = new LinkedList<String>();
		int lineCount = 0;
		while(true) {
			try {
				lines.push(fileIn.nextLine());
				lineCount++;
			} catch(NoSuchElementException e) {
				break;
			}
		}

		if(lineCount % 2 != 0) {
			throw new IllegalArgumentException(
				"Invalid test file; not an even number of lines"
			);
		}

		this.data = new KeirseyResult[lineCount / 2];
		for(int i = 0; i < lineCount / 2; i++) {
			// add new record with the name
			this.data[i] = new KeirseyEvaluator().parse(
				lines.removeLast(),
				lines.removeLast());
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
			System.err.println(
				"File not found; deleted between typing input and writing?"
			);
			System.exit(-1);
		}
	}

	KeirseyFileReader(String filename) {
		this.filename = filename;
	}
}
