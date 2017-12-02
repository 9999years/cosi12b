/**
  AnagramMain is a client program that prompts a user for the name of a
  dictionary file and then gives the user the opportunity to find anagrams of
  various phrases.  It constructs an Anagrams object to do the actual
  search for anagrams that match the user's phrases.
*/

import java.io.*;
import java.util.*;

public class AnagramMain {
	// dictionary file to use for input (change to dict2, dict3)
	private static final String DICTIONARY_FILE = "dict1.txt";

	// set to true to test runtime and # of letter inventories created
	private static final boolean TIMING = true;

	public static void main(String[] args) throws FileNotFoundException {
		String dict_file = "dict1.txt";
		if(args.length > 0) {
			dict_file = args[0];
		}
		System.out.println("Welcome to the CSE 143 anagram solver.");
		System.out.println("Using dictionary file " + dict_file + ".");

		// read dictionary into a set
		Scanner input = new Scanner(new File(dict_file));
		Set<String> dictionary = new TreeSet<String>();
		while (input.hasNextLine()) {
			dictionary.add(input.nextLine());
		}
		dictionary = Collections.unmodifiableSet(dictionary);   // read-only

		// create Anagrams object for, well, solving anagrams
		Anagrams solver = new Anagrams(dictionary);

		// get first phrase to solve
		Scanner console = new Scanner(System.in);
		String phrase = getPhrase(console);

		// loop to get/solve each phrase
		while (phrase.length() > 0) {
			System.out.println("All words found in \"" + phrase + "\":");
			Set<String> allWords = solver.getWords(phrase);
			System.out.println(allWords);
			System.out.println();

			System.out.print("Max words to include (Enter for no max)? ");
			String line = console.nextLine().trim();

			long startTime = System.currentTimeMillis();
			if (line.length() > 0) {
				// use a max
				int max = new Scanner(line).nextInt();
				solver.print(phrase, max);   // print all anagrams of phrase
			} else {
				// no max
				solver.print(phrase);        // print all anagrams of phrase
			}
			long endTime = System.currentTimeMillis();
			System.out.println();

			// 12247 ms elapsed, 2594392 unique LetterInventory object(s) created
			if (TIMING) {
				long elapsed = endTime - startTime;
				int inventories = LetterInventory.getInstanceCount();
				System.out.println(elapsed + " ms elapsed, " + inventories + 
						" unique LetterInventory object(s) created");
				LetterInventory.resetInstanceCount();
			}

			// get next phrase to solve
			phrase = getPhrase(console);
		}
	}

	// Helper to prompt for a phrase to generate anagrams.
	public static String getPhrase(Scanner console) {
		System.out.println();
		System.out.print("Phrase to scramble (Enter to quit)? ");
		return console.nextLine().trim();
	}
}
