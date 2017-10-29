package becca.edit;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.List;

/**
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class EditDistance {
	public static String requestAndProcess(Dictionary dict) {
		WordPair words =
			new WordPair("Enter two words separated by a space: ")
			.get();

		if(!dict.contains(words.first) || !dict.contains(words.second)) {
			return "Word does not exist";
		}

		List<String> path = dict.getPath(words.first, words.second);
		if(path.size() > 0) {
			return "Path = "
				+ String.join(", ", path)
				+ "\nEdit distance = "
				+ Strings.editDistance(words.first, words.second);
		} else {
			return "No solution";
		}
	}

	public static Dictionary readDictionaryFile(File f)
			throws FileNotFoundException {
		Scanner input = new Scanner(f);
		Dictionary dict = new Dictionary();
		while(input.hasNext()) {
			dict.add(input.next());
		}
		return dict;
	}

	public static void main(String args[]) throws FileNotFoundException {
		File dictionaryFile =
			new ReadableFile("Enter name of dictionary file: ")
			.getFile();
		Dictionary dict = readDictionaryFile(dictionaryFile);
		while(true) {
			System.out.println(requestAndProcess(dict));
		}
	}
}

class WordPair {
	String first;
	String second;
	String prompt;

	WordPair() {
		this("");
	}

	WordPair(String prompt) {
		this.prompt = prompt;
	}

	public WordPair get() {
		Scanner input = new Scanner(System.in);
		System.out.print(prompt);
		first  = input.next();
		second = input.next();
		return this;
	}
}
