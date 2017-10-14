package becca.markov;

import java.io.File;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileNotFoundException;

public class RandomWriter {
	public static void main(String[] args) {
		MarkovArguments arguments = new MarkovArguments();
		arguments.absorb(args);
		CharGenerator generator = null;
		if(arguments.seedSet) {
			generator = new CharGenerator(
				arguments.inputFileText(),
				arguments.length,
				arguments.seed
			);
		} else {
			generator = new CharGenerator(
				arguments.inputFileText(),
				arguments.length
			);
		}

		PrintStream stream = arguments.getOutputStream();
		for(int i = 0; i < arguments.outputLength; i++) {
			stream.print(Character.toChars(generator.getNextChar()));
		}
	}
}

class MarkovArguments {
	static final int INX_LENGTH       = 0;
	static final int INX_OUTPUTLENGTH = 1;
	static final int INX_INPUT        = 2;
	static final int INX_OUTPUT       = 3;
	static final int INX_SEED         = 4;

	static final int NUM_ARGS_MIN = 4;
	static final int NUM_ARGS_MAX = 4;

	static final String usage =
		"usage: java RandomWriter k len in out [seed]\n"
		+ "    k:    Markov analysis (trail / memory) length\n"
		+ "    len:  num. of output characters\n"
		+ "    in:   source filename\n"
		+ "    out:  output filename\n"
		+ "    seed: random seed (int)\n";

	int length;
	int outputLength;
	int seed;
	File inputFile;
	File outputFile;
	boolean seedSet;

	MarkovArguments() { }
	MarkovArguments(String[] args) {
		absorb(args);
	}

	public void absorb(String[] args) {
		if(!validateCount(args.length)) {
			System.out.println(usage);
			System.exit(-1);
		}

		length       = new Integer(args[INX_LENGTH]);
		outputLength = new Integer(args[INX_OUTPUTLENGTH]);
		inputFile    = new File(args[INX_INPUT]);
		outputFile   = new File(args[INX_OUTPUT]);

		if(args.length >= INX_SEED + 1) {
			seed = new Integer(args[INX_SEED]);
			seedSet = true;
		}
	}

	public boolean validateLength() {
		return length > 0;
	}

	public boolean validateOutputLength() {
		return outputLength > 0;
	}

	public boolean validateSource() {
		return outputLength > 0;
	}

	public boolean validateInputFile() {
		// no check for length > 0 b/c isFile accounts for that
		// (empty string is not a file)
		return inputFile != null
			&& inputFile.isFile()
			&& inputFile.canRead()
			&& inputFile.length() > length;
	}

	public boolean validateOutputFile() {
		return outputFile != null
			&& outputFile.canWrite() == outputFile.isFile();
	}

	public static boolean validateCount(int n) {
		return n >= NUM_ARGS_MIN
			&& n <= NUM_ARGS_MAX;
	}

	public String inputFileText() {
		try {
			return new String(
				Files.readAllBytes(inputFile.toPath()),
				StandardCharsets.UTF_8);
		} catch(IOException e) {
			return null;
		}
	}

	public PrintStream getOutputStream() {
		try {
			if(outputFile.toString().equals("-")) {
				return System.out;
			} else {
				return new PrintStream(outputFile);
			}
		} catch(FileNotFoundException e) {
			return null;
		}
	}
}
