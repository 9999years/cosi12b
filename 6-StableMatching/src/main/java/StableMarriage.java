package becca.smp;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class StableMarriage {
	/**
	 * exit codes from Linux sysexits.h
	 *
	 * successful termination
	 */
	public static final int EXIT_OK = 0;
	/**
	 * command line usage error
	 */
	public static final int EXIT_USAGE = 64;
	/**
	 * data format error
	 */

	public static final int EXIT_DATAERR = 65;
	/**
	 * input/output error
	 */
	public static final int EXIT_IOERR = 74;

	/**
	 * a number of words followed by a : and possibly some space
	 * like "Joe: "
	 */
	public static final Pattern NAME_PATTERN = Pattern.compile(
		"\\b(?<name>[^:]+)(?<extra>:\\p{Zs}+)");
	public static final String END_STRING = "END";

	public static final int MIN_ARGS = 1;
	public static final int MAX_ARGS = 1;
	public static final int INPUTFILE_INDEX = 0;
	public static final String USAGE =
		"Usage: java StableMarriage INPUTFILE";

	protected static void parseNameLine(
			NodeSetFactory factory, String line) {
		Matcher matcher = NAME_PATTERN.matcher(line);
		if(matcher.lookingAt()) {
			// get name
			factory.add(matcher.group("name"));
			Scanner input = new Scanner(
				line.substring(matcher.end()));
			// get preferences
			while(input.hasNextInt()) {
				factory.addPref(input.nextInt());
			}
		} else {
			error("Invalid input file format; Name must be"
				+ " non-empty and followed by a colon, and"
				+ " section-end lines must contain ONLY the"
				+ " word \"END\". Invalid line:\n" + line);
			usage(EXIT_DATAERR);
		}
	}

	/**
	 * takes the next portion of lines beginning with a name followed by a
	 * list of preference indicies until a line consisting of only "END"
	 * and adds them to a given factory
	 */
	protected static NodeSetFactory getNextSegment(Scanner input) {
		NodeSetFactory factory = new NodeSetFactory();
		while(input.hasNextLine()) {
			String line = input.nextLine();
			if(line.equals(END_STRING)) {
				break;
			} else {
				parseNameLine(factory, line);
			}
		}
		return factory;
	}

	public static NodeSet parse(Scanner input) {
		NodeSetFactory A = getNextSegment(input);
		NodeSetFactory B = getNextSegment(input);
		NodeSetFactory.link(A, B);
		return A.getSet();
	}

	/**
	 * checks that the file is readable and a file
	 * @param filename a filename to test
	 * @return true if filename is OK, false if not
	 */
	public static boolean checkReadFilename(String filename) {
		// no check for length > 0 b/c isFile accounts for that
		// (empty string is not a file)
		File f = new File(filename);
		return f.isFile() && f.canRead();
	}

	/**
	 * checks that the file is writeable and a valid path
	 * @param filename a filename to test
	 * @return true if filename is OK, false if not
	 */
	public static boolean checkWriteFilename(String filename) {
		File f = new File(filename);
		return filename.length() > 0 && f.canWrite() == f.isFile();
	}

	public static void usage(int code) {
		System.out.println(USAGE);
		System.exit(code);
	}

	public static void error(String msg) {
		System.err.println(msg);
	}

	public static void exitIllegalFilename() {
		error("Illegal filename! Cannot be read or doesn't exist.");
		usage(EXIT_IOERR);
	}

	public static void main(String args[]) {
		// ensure we have one argument
		if(args.length < MIN_ARGS
			|| args.length > MAX_ARGS) {
			error("Illegal argument count! Exactly 1 argument required.");
			usage(EXIT_USAGE);
		}

		String inputFile = args[INPUTFILE_INDEX];

		// check if user wants help
		if(inputFile.equals("-?")
			|| inputFile.equals("-h")
			|| inputFile.equals("--help")
			) {
			usage(EXIT_OK);
		}

		// check file exists / is readable
		if(!checkReadFilename(inputFile)) {
			exitIllegalFilename();
		}

		NodeSet men = null;
		try {
			men = parse(new Scanner(new File(inputFile)));
		} catch(FileNotFoundException e) {
			// bogus
			// guaranteed to not be thrown by checkReadFilename
			 exitIllegalFilename();
		}

		NodeSet women = men.other;

		//for(Node n : men.nodes) {
			//System.out.println(n);
			//System.out.println(n.priorities);
		//}

		NodeSet.match(men, women);
		System.out.println("Matches with men prioritized:");
		System.out.println(men.getMatchStatus());
		NodeSet.match(women, men);
		System.out.println("Matches with women prioritized:");
		System.out.println(women.getMatchStatus());
	}
}
