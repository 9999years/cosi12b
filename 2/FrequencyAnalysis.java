
// The frequency analysis class attempts to solve substitution ciphers 
// using a frequency analysis. 

import java.util.Arrays;
import java.util.Scanner;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class FrequencyAnalysis {
   /* Characters ordered from most to least frequently used */
    public static final List<Character> ENGLISH_FREQUENCY_ORDER = Arrays.asList(
        'e','t','a','o','i','n','s','h','r','d','l','u','c',
        'm','f','w','y','p','v','b','g','k','j','q','x','z'
    );

    /* The amount two doubles can be apart and still be considered equal. */
    private static final double DELTA = 0.00001;

    /* The threshold used to determine if the percentages are far enough apart
     * to assign a letter or not. */
    private static final double EASY_THRESHOLD = 0.01;

    /* The number of runs of passes through the text on the dictionary the 
       analysis will perform. */
    private static final int NUMBER_DICTIONARY_RUNS = 10;

    /* The ciphertext of the cryptogram. */
    private String cipherText;

    /* The decoded text so far.  Represents undecoded letters with UPPERCASE
     * and decoded letters as LOWERCASE. */
    private String decodedText;

    /* A dictionary of words that we use to complete the cryptogram. */
    private List<String> dictionary; 

    /* The frequency ordering in the ciphertext. */
    private List<Character> frequencyOrdering;

    /* A LetterInventory containing the letters in the cryptogram. */
    private LetterInventory cryptogramInventory;

    /* All of the CIPHERTEXT letters that are currently unassigned.
       We maintain the invariant that these letters are in CIPHERTEXT 
       frequency order. */
    private List<Character> unassignedCipherTextLetters;

    /* All of the PLAINTEXT letters that are currently unassigned.
       We maintain the invariant that these letters are in ENGLISH frequency
       order. */
    private List<Character> unassignedPlainTextLetters;

    /* post: constructs a frequency analysis on the ciphertext given */ 
    public FrequencyAnalysis(String ciphertext) throws FileNotFoundException {
        readCiphertext(ciphertext);
        calculateFrequencyOrdering();

        readDictionary();
    }

    /* post: Attempts to use the most definitive frequencies to fill in letters 
             in the cryptogram. This function will overwrite any previous
             decoding work that was done.  */
    public void decipherEasyLetters() {
        /* Begin with the cipherText we were given.  Since we internally
           represent un-decoded letters with upper case, we also convert
           everything to uppercase as well. */
        this.decodedText = this.cipherText.toUpperCase();

        /* Since we are starting over, we must initialize the unassigned letters 
           variables to contain all of the letters. */
        this.unassignedCipherTextLetters = 
                            new ArrayList<Character>(ENGLISH_FREQUENCY_ORDER);
        this.unassignedPlainTextLetters = 
                            new ArrayList<Character>(this.frequencyOrdering);

        /* Run through all of the letters, and try to assign them based on their 
           frequencies, as compared to English frequencies. */
        for (int i = 0; i < ENGLISH_FREQUENCY_ORDER.size(); i++) {
            char englishLetter = ENGLISH_FREQUENCY_ORDER.get(i);
            char cipherLetter = frequencyOrdering.get(i);
    
            double freq = cryptogramInventory.getLetterPercentage(cipherLetter);
            double previousFreq = 1;
            if (i > 0) {
                previousFreq = cryptogramInventory.getLetterPercentage(
                        frequencyOrdering.get(i-1));
            }

            /* If the current frequency is far enough from the previous one,
               then we assume the letter is in English frequency order.  Go
               ahead and update the output. */
            if (previousFreq - freq > EASY_THRESHOLD) {
                this.decodedText = this.decodedText.replace(
                           Character.toUpperCase(cipherLetter), englishLetter);
                unassignedCipherTextLetters.remove(new Character(cipherLetter));
                unassignedPlainTextLetters.remove(new Character(englishLetter));
            }
        }
    }

    /* post: Attempts to use existing partial translations with a dictionary to 
       translate the ciphertext */
    public void decipherWithDictionary() {
        for (int x = 0; x < NUMBER_DICTIONARY_RUNS; x++) {
            /* Get all of the words with one letter missing. */
            String[] words = this.decodedText
                .replaceAll("[,.?!\"-]", "").split(" ");

            List<Character> newlyAssigned = new ArrayList<Character>();

            for (char toReplace : unassignedCipherTextLetters) {
                toReplace = Character.toUpperCase(toReplace);
                List<Character> possibleChoices = findPossibleChoices(
                        words, toReplace);

                /* If we have narrowed down the possibilities for the
                   translation to a single choice, assign that translation, and
                   decode it in the text. */
                if (possibleChoices.size() == 1) {
                    char choice = possibleChoices.get(0);

                    newlyAssigned.add(
                            new Character(Character.toLowerCase(toReplace)));
                    unassignedPlainTextLetters.remove(new Character(choice));

                    this.decodedText = this.decodedText
                        .replace(toReplace, choice);
                }
            }

            unassignedCipherTextLetters.removeAll(newlyAssigned);
        }
    }

    /* post: returns a string representation of result of the frequency
     *       analysis on the ciphertext; indicates that a letter is
     *       untranslated with a '*' */
    public String toString() {
        return this.decodedText.replaceAll("[A-Z]", "*");
    }

    /* post: returns a list of all unassigned characters that results in a
     *       plausible translation after replacing the specified character. */
    private List<Character> findPossibleChoices(String[] words, char toReplace) {
        String pattern = "[a-z-]*(" + toReplace + "+[a-z-]*)+";
        List<Character> possibleChoices = 
                      new ArrayList<Character>(unassignedPlainTextLetters);

        /* Run through the words with ONLY toReplace untranslated in them.
         * Find all the possible valid translations, and if we end up with a 
         * single choice, then we assume that it is the correct translation 
         * for that character. */
        for (int word = 0; word < words.length; word++) {
            /* Only worry about this word if it has the character we're 
               concerned with. */
            if (words[word].matches(pattern)) {
                List<Character> workingChoices = new ArrayList<Character>();
                for (char choice : possibleChoices) {
                    String possible = words[word].replace(toReplace, choice);
                    if (dictionary.contains(possible)) {
                        workingChoices.add(choice);
                    }
                }

                /* Ignore the case where none of the given possibilities
                   work -- this case likely means a word is missing from
                   the dictionary. */
                if (workingChoices.size() > 0) {
                    possibleChoices.retainAll(workingChoices);
                }
            }
        }

        return possibleChoices;
    }
    
    /* post: frequencyOrdering is populated with a list of all lowercase
     *       letters ordered from most to least frequent in the ciphertext;
     *       cryptogramInventory is populated with a letter inventory
     *       corresponding to the ciphertext */
    private void calculateFrequencyOrdering() {
        this.cryptogramInventory = new LetterInventory(this.cipherText);

        this.frequencyOrdering = this.getFrequencyOrder();
    
        /* Reverse the frequencyOrdering, because getFrequencyOrder() returns a
         * list from least-to-most frequent, and we want the opposite. */
        Collections.reverse(this.frequencyOrdering);
    }

    /* throws a FileNotFoundException if file does not exist
     * post: cipherText is populated with the contents of file, and 
     *       decodedText is initialized to an all uppercase version of
     *       the ciphertext */
    private void readCiphertext(String file) throws FileNotFoundException {
        Scanner input = null;
        input = new Scanner(new File(file));

        StringBuilder originalText = new StringBuilder();
        while (input.hasNext()) {
            originalText.append(input.nextLine());
        }

        this.cipherText = originalText.toString().toLowerCase();
        this.decodedText = this.cipherText.toUpperCase();
    }

    /* post: populates dictionary with the English words listed in the provided
     *       dictionary text file */
    private void readDictionary() {
        Scanner input = null;
        try {
            input = new Scanner(new File("dictionary.txt"));
        } catch (FileNotFoundException e) {
            System.out.println(
                    "You must copy dictionary.txt to this directory" +
                    " before running the frequency analysis.");
            System.exit(1);
        }

        List<String> dictionary = new ArrayList<String>();
        while (input.hasNext()) {
            dictionary.add(input.next());
        }
        this.dictionary = dictionary;
    }

    /* post: returns a list of the characters in the ciphertext in frequency
     *       order from least-to-most frequent */
    private List<Character> getFrequencyOrder() {
        List<Character> order = new ArrayList<Character>();

        List<Double> sortedFrequencies = new ArrayList<Double>();

        for (char c = 'a'; c < ('z' + 1); c++) {
            double freq = this.cryptogramInventory.getLetterPercentage(c);
            sortedFrequencies.add(freq);
        }

        /* Sort the list of frequencies */
        Collections.sort(sortedFrequencies);

        /* goes through the list of sorted counts */
        for (int i = 0; i < sortedFrequencies.size(); i++) {
            /* go through all of the possible letters in the inventory, and 
             * append each letter to the order (but only if it's not already
             * in the list) */
            for (char c = 'a'; c < ('z' + 1); c++) {
                double freq = this.cryptogramInventory.getLetterPercentage(c);
                double difference = Math.abs(freq - sortedFrequencies.get(i));
                if (!order.contains(c) && difference < DELTA) {
                    order.add(c);
                }
            }
        }

        return order;
    }

    public static void main(String[] args) throws FileNotFoundException {
        FrequencyAnalysis freq = null;

        System.out.println("This program makes decodes a cryptogram.");
        System.out.println();

        // get file names from user
        Scanner console = new Scanner(System.in);
        System.out.print("cryptogram file name? ");
        String inFile = console.nextLine();
        System.out.print("plaintext output file name? ");
        String plaintextFile = console.nextLine();
        try {
            freq = new FrequencyAnalysis(inFile);
        } catch (FileNotFoundException e) {
            System.out.println(
                    "You must copy cryptogram.txt to this directory" +
                    " before running the frequency analysis.");
            System.exit(1);
        }

        /* Do the frequency analyses. */
        freq.decipherEasyLetters();
        freq.decipherWithDictionary();

        /* See the result! */
        PrintStream output = new PrintStream(new File(plaintextFile));
        output.println(freq);
    }
}

