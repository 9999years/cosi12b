package becca.edit;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Dotter {
	public static final String usage =
		"Usage: java Dotter DICTIONARY_FILE";

	public static Dictionary readDictionaryFile(String f, List<String> words)
			throws FileNotFoundException {
		Scanner input = new Scanner(new File(f));
		Dictionary dict = new Dictionary();
		while(input.hasNext()) {
			String next = input.next();
			words.add(next);
			dict.add(next);
		}
		return dict;
	}

	public static void main(String[] args) throws FileNotFoundException {
		// 0: dictionary file
		// 1: count
		if(args.length != 1) {
			System.err.println("Exactly one argument required!\n"
				+ usage);
			System.exit(-1);
		}

		String dictionaryFile = args[0];
		List<String> words = new ArrayList<>();

		Dictionary dict = readDictionaryFile(dictionaryFile, words);
		System.out.println(dict.toDot());
	}
}
