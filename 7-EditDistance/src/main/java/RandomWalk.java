package becca.edit;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class RandomWalk {
	public static final String usage =
		"Usage: java RandomWalk DICTIONARY_FILE WORD_COUNT";

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
		if(args.length != 2) {
			System.err.println("Exactly two arguments required!\n"
				+ usage);
			System.exit(-1);
		}

		String dictionaryFile = args[0];
		List<String> words = new ArrayList<>();

		Dictionary dict = readDictionaryFile(dictionaryFile, words);
		int amt = new Integer(args[1]);
		Random rand = new Random();
		String word = words.get(rand.nextInt(words.size()));
		words = new LinkedList<>();

		while(amt --> 0) {
			Object[] neighbors = dict.neighbors(word).toArray();
			word = neighbors[rand.nextInt(neighbors.length)].toString();
			words.add(word);
		}

		System.out.println(words
			.stream()
			.collect(Collectors.joining(" -> ")));
	}
}
