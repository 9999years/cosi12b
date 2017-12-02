import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;

public class Anagrams {
	SortedSet<String> dictionary;

	/**
	 * @param dictionary MUST be a SortedSet
	 */
	Anagrams(Set<String> dictionary) {
		Objects.requireNonNull(dictionary);
		//AnagramMain uses Collections.unmodifiableSet rather than
		//Collections.unmodifiableSortedSet, so this would fail! but
		//ideally we could use this to validate the parameters (or just
		//change the signature to a SortedSet<String>
		//Parameters.validate(dictionary, s -> s instanceof SortedSet,
			//() -> "dictionary must be a SortedSet!");
		this.dictionary = new TreeSet<String>(dictionary);
	}

	Anagrams(Collection<String> dictionary) {
		this(new TreeSet<String>(dictionary));
	}

	Anagrams(String[] dictionary) {
		this(Arrays.asList(dictionary));
	}

	protected SortedSet<String> getWords(
		String phrase, SortedSet<String> dict) {
		Objects.requireNonNull(phrase);
		Objects.requireNonNull(dict);
		LetterInventory inventory = new LetterInventory(phrase);
		SortedSet<String> choices = new TreeSet<>();
		for(String word : dict) {
			if(inventory.contains(word)) {
				choices.add(word);
			}
		}
		return choices;
	}

	public SortedSet<String> getWords(String phrase) {
		return getWords(phrase, dictionary);
	}

	public String toString(String phrase, int max) {
		Objects.requireNonNull(phrase);
		Parameters.validate(max, i -> i >= 0,
			() -> "Max cannot be negative!");
		return getAnagrams(phrase, max).stream()
			.collect(
				StringBuilder::new,
				(builder, anagram) -> {
					builder.append(anagram);
					builder.append("\n");
				},
				(b1, b2) -> {
					b1.append(b2);
					b1.append("\n");
				}
			).toString();
	}

	public String toString(String phrase) {
		return toString(phrase, 0);
	}

	public void print(String phrase, int max) {
		System.out.print(toString(phrase, max));
	}

	public void print(String phrase) {
		print(phrase, 0);
	}

	public SortedSet<List<String>> getAnagrams(String phrase, int max) {
		SortedSet<List<String>> ret = Sets.sortedIterableSet();
		LetterInventory inventory = new LetterInventory(phrase);
		getAnagrams(inventory, getWords(phrase), new ArrayList<>(),
			ret, max, inventory.size());
		return ret;
	}

	public SortedSet<List<String>> getAnagrams(String phrase) {
		return getAnagrams(phrase, 0);
	}

	/**
	 * @param all mutated to add new anagrams
	 */
	protected void getAnagrams(LetterInventory remaining,
		SortedSet<String> choices, List<String> chosen,
		SortedSet<List<String>> all, int max, int originalLength) {
		// is this a bottom call (eg is our decision tree empty?)
		boolean bottom = true;
		for(String choice : choices) {
			if(remaining.contains(choice)) {
				// we're doing stuff, so we're not at the
				// bottom of the tree yet
				bottom = false;
				// duplicate our sets so we don't mutate the
				// caller's variables
				LetterInventory _remaining =
					new LetterInventory(remaining);
				List<String> _chosen =
					new ArrayList<>(chosen);
				SortedSet<String> _choices =
					new TreeSet<>(choices);
				_remaining.subtract(choice);
				// One difference between this algorithm and
				// other backtracking algorithms is that the
				// same word can appear more than once in an
				// anagram. For example, from  "barbara bush"
				// you might extract the word  "bar" twice.
				//
				// use this line to forbid duplicate words
				// within each anagram
				//_choices.remove(choice);
				_chosen.add(choice);
				getAnagrams(_remaining, _choices, _chosen, all, max, originalLength);
				if(max != 0 && all.size() >= max) {
					// done!
					return;
				}
			}
		}
		if(bottom && !all.contains(chosen)
			&& Strings.totalLength(chosen) == originalLength) {
			all.add(chosen);
		}
	}
}
