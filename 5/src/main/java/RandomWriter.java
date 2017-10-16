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
		if(arguments.isSeedSet()) {
			generator = new CharGenerator(
				arguments.inputFileText(),
				arguments.getLength(),
				arguments.getSeed()
			);
		} else {
			generator = new CharGenerator(
				arguments.inputFileText(),
				arguments.getLength()
			);
		}

		PrintStream stream = arguments.getOutputStream();
		if(stream == null) {
			System.exit(-1);
		}
		stream.print(generator.next(arguments.getOutputLength()));
	}
}

class MarkovArguments {
	static final int INX_LENGTH       = 0;
	static final int INX_OUTPUTLENGTH = 1;
	static final int INX_INPUT        = 2;
	static final int INX_OUTPUT       = 3;
	static final int INX_SEED         = 4;

	static final int NUM_ARGS_MIN = 4;
	static final int NUM_ARGS_MAX = 5;

	static final String usage =
		"usage: java RandomWriter k len in out [seed]\n"
		+ "    k    (int): Markov analysis (trail / memory) length\n"
		+ "    len  (int): Num. of output characters\n"
		+ "    in   (str): Source filename\n"
		+ "    out  (str): Output filename\n"
		+ "    seed (int): Random seed\n";

	private int length;
	private int outputLength;
	private int seed;
	private File inputFile;
	private File outputFile;
	private boolean seedSet;

	private String errorComponent;
	private String errorReason;

	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
		if(!validateLength()) {
			throw new IllegalArgumentException(getErrorText());
		}
	}
	public void setLength(String length) {
		setLength(new Integer(length));
	}

	public int  getOutputLength() {
		return outputLength;
	}
	public void setOutputLength(int outputLength) {
		this.outputLength = outputLength;
		if(!validateOutputLength()) {
			throw new IllegalArgumentException(getErrorText());
		}
	}
	public void setOutputLength(String outputLength) {
		setOutputLength(new Integer(outputLength));
	}

	public File getInputFile() {
		return inputFile;
	}
	public void setInputFile(String f) {
		inputFile = new File(f);
		if(!validateInputFile()) {
			throw new IllegalArgumentException(getErrorText());
		}
	}

	public File getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(String f) {
		outputFile = new File(f);
		if(!validateOutputFile()) {
			throw new IllegalArgumentException(getErrorText());
		}
	}

	public int getSeed() {
		return seed;
	}
	public void setSeed(int s) {
		seed = s;
		seedSet = true;
	}
	public void setSeed(String s) {
		seed = new Integer(s);
		seedSet = true;
	}
	public boolean isSeedSet() {
		return seedSet;
	}

	MarkovArguments() { }
	MarkovArguments(String[] args) {
		absorb(args);
	}

	public void absorb(String[] args) {
		if(!validateCount(args.length)) {
			System.out.println(getErrorText());
			System.exit(-1);
		}

		try {
			// mandatory, ensured by validateCount
			setLength(args[INX_LENGTH]);
			setOutputLength(args[INX_OUTPUTLENGTH]);
			setInputFile(args[INX_INPUT]);
			setOutputFile(args[INX_OUTPUT]);
			// optional
			if(seedPresent(args.length)) {
				setSeed(args[INX_SEED]);
			}
		} catch(IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	public boolean seedPresent(int argsLength) {
		return argsLength >= INX_SEED + 1;
	}

	public boolean validateLength() {
		if(length >= 0) {
			return true;
		} else {
			errorReason = "k must be greater than 0";
			return false;
		}
	}

	public boolean validateOutputLength() {
		if(outputLength > 0) {
			return true;
		} else {
			errorReason = "output length must be greater than 0";
			return false;
		}
	}

	public boolean validateInputFile() {
		// no check for length > 0 b/c isFile accounts for that
		// (empty string is not a file)
		if(inputFile != null
				&& inputFile.isFile()
				&& inputFile.canRead()
				&& inputFile.length() > length) {
			return true;
		} else {
			errorReason = "input file must exist, be readable, "
				+ "and have more than k characters";
			return false;
		}
	}

	public boolean validateOutputFile() {
		if(outputFile != null
			&& outputFile.canWrite() == outputFile.isFile()) {
			return true;
		} else {
			errorReason = "output file must exist and be writable";
			return false;
		}
	}

	private boolean validateCount(int n) {
		if(n >= NUM_ARGS_MIN
			&& n <= NUM_ARGS_MAX) {
			return true;
		} else {
			errorReason = "there must be between "
				+ NUM_ARGS_MIN
				+ " and "
				+ NUM_ARGS_MAX
				+ " arguments";
			return false;
		}
	}

	public String getErrorText() {
		return "Invalid arguments! "
			+ errorReason
			+ "!\n"
			+ usage;
	}

	public String validate() {
		if(
				!(validateLength()
				&& validateOutputLength()
				&& validateInputFile()
				&& validateOutputFile())) {
			return getErrorText();
		} else {
			return null;
		}

	}

	public String inputFileText() {
		try {
			return new String(
				Files.readAllBytes(inputFile.toPath()),
				StandardCharsets.UTF_8);
		} catch(FileNotFoundException e) {
			return "";
		} catch(IOException e) {
			return "";
		}
	}

	public PrintStream getOutputStream() {
		try {
			return new PrintStream(outputFile);
		} catch(FileNotFoundException e) {
			return null;
		}
	}
}
