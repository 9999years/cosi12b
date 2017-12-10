import java.util.List;
import java.util.Objects;
import java.util.ListIterator;
import java.text.Normalizer;

import java.lang.StringBuilder;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;

public class AssassinManager {
	protected LinkedList<AssassinPlayer> killRing  = new LinkedList<>();
	protected LinkedList<AssassinPlayer> graveyard = new LinkedList<>();

	/**
	 * This constructor should initialize a new assassin manager over the
	 * given list of people. Note that you should not save the list
	 * parameter itself as a field, nor modify the list. Instead, you
	 * should build your own kill ring of list nodes that contains these
	 * names in the same order. If the list is null or empty, you should
	 * throw an IllegalArgumentException.
	 *
	 * For example, if the given list contains ["John", "Sally", "Fred"],
	 * your initial kill ring should represent that John is stalking Sally
	 * who is stalking Fred who is stalking John (in that order).  You may
	 * assume that the names are non-empty, non-null strings and that
	 * there are no duplicates.
	 */
	AssassinManager(List<String> names) {
		Objects.requireNonNull(names);
		Parameters.validate(names, l -> l.size() > 0);
		addAll(names);
	}

	protected void addAll(List<String> names) {
		for(String name : names) {
			killRing.add(new AssassinPlayer(name));
		}
	}

	protected String listString(LinkedList<AssassinPlayer> list,
			final String infix) {
		if(list.size() == 0) {
			return "";
		}

		StringBuilder ret = new StringBuilder();
		final String prefix = ">>    ";
		ListIterator<AssassinPlayer> itr = killRing.listIterator();

		// list always has at least 1 person in it
		AssassinPlayer p = itr.next();
		ret.append(prefix + p.name + infix);
		while(itr.hasNext()) {
			p = itr.next();
			ret.append(p + "\n" + prefix + p + infix);
		}
		ret.append(killRing.peekFirst().name + "\n");

		return ret.toString();
	}

	public String killRingString() {
		return listString(killRing, " is stalking ");
	}

	public String graveyardString() {
		StringBuilder ret = new StringBuilder();
		final String prefix = ">>    ";
		for(AssassinPlayer p : graveyard) {
			ret.append(prefix + p + " was killed by " + p.killer + "\n");
		}
		return ret.toString();
	}

	/**
	 * This method should print the names of the people in the kill ring,
	 * one per line, indented by four spaces, as "X is stalking Y ". If
	 * the game is over, then instead print "X is stalking X".  For
	 * example, using the names in the example game above, the output is:
	 *     Joe is stalking Sally
	 *     Sally is stalking Jim
	 *     Jim is stalking Carol
	 *     Carol is stalking Chris
	 *     Chris is stalking Joe
	 * If the game is over and Chris is the winner, so Chris is the only
	 * name in the kill ring, the output is:
	 *     Chris is stalking Chris
	 */
	public void printKillRing() {
		System.out.print(killRingString());
	}

	/**
	 * This method should print the names of the people in the graveyard,
	 * one per line, with each line indented by four spaces, with output
	 * of the form "name was killed by name". It should print the names in
	 * the opposite of the order in which they were killed (most recently
	 * killed first, then next more recently killed, and so on). It should
	 * produce no output if the graveyard is empty.
	 *
	 * For example, using the names from above, if Jim is killed, then
	 * Chris, then Carol, the output is:
	 *     Carol was killed by Sally
	 *     Chris was killed by Carol
	 *     Jim was killed by Sally
	 */
	public void printGraveyard() {
		System.out.print(graveyardString());
	}

	/**
	 * This method should return true if the given name is in the current
	 * kill ring and false otherwise. It should ignore case in comparing
	 * names; so, "salLY" should match a node with a name of "Sally".
	 */
	public boolean killRingContains(String name) {
		return killRing.contains(name);
	}

	/**
	 * This method should return true if the given name is in the current
	 * graveyard and false otherwise. It  should ignore case in comparing
	 * names; so, "CaRoL" should match a node with a name of "Carol".
	 */
	public boolean graveyardContains(String name) {
		return graveyard.contains(name);
	}

	/**
	 * This method should return true if the game is over (the kill ring
	 * has one person) and false otherwise.
	 */
	public boolean isGameOver() {
		return killRing.size() == 1;
	}

	/**
	 * This method should return the name of the winner of the game, or
	 * null if the game is not over.
	 */
	public String winner() {
		return isGameOver() ? killRing.peekFirst().name : null;
	}

	/**
	 * This method should record the assassination of the person with the
	 * given name, transferring the person from the kill ring to the front
	 * of the graveyard. This operation should not change the relative
	 * order of the kill ring (i.e. the links of who is killing whom
	 * should stay the same other than the person who is being killed).
	 * This method should ignore case in comparing names.
	 *
	 * A node remembers who killed the person in its killer field, and you
	 * must set the value of this field. You should throw an
	 * IllegalStateException if the game is over, or throw an
	 * IllegalArgumentException if the given name is not part of the kill
	 * ring. If both of these conditions are true, the
	 * IllegalStateException takes precedence.
	 */
	public void kill(String name) throws IllegalArgumentException, IllegalStateException {
		Parameters.validate(() -> !isGameOver(), () -> "Game is over!");
		// remove NAME from killRing
		// IF someone was removed, set their killer and add them to
		// the front of the graveyard
		ListIterator<AssassinPlayer> itr = killRing.listIterator();
		while(itr.hasNext()) {
			AssassinPlayer person = itr.next();
			if(person.equals(name)) {
				itr.remove();
				// reverse so we don't get the same person
				// twice
				itr.previous();
				if(itr.hasPrevious()) {
					// mid list or end of list
					person.killer = itr.previous();
				} else {
					// front of list, being stalked by
					// end
					person.killer = killRing.peekLast();
				}
				graveyard.offerFirst(person);
				// done
				return;
			}
		}

		// name not found
		throw new IllegalArgumentException();
	}
}
