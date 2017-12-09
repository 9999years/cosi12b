import java.util.List;
import java.util.Objects;
import java.util.ListIterator;
import java.text.Normalizer;

import java.lang.StringBuilder;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;

public class AssassinManager {
	protected LinkedList<AssassinPlayer> killRing;
	protected LinkedList<AssassinPlayer> graveyard;

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
	}

	protected String listString(LinkedList<AssassinPlayer> list,
			final String infix) {
		StringBuilder ret = new StringBuilder();
		final String prefix = ">>    ";
		ListIterator<AssassinPlayer> itr = killRing.listIterator();
		for(AssassinPlayer n : killRing) {
			if(itr.hasPrevious()) {
				// X
				ret.append(n.name + "\n");
			}
			if(itr.hasNext()) {
				// X is stalking
				ret.append(
					prefix
					+ n.name
					+ infix);
			}
		}
		return ret.toString();
	}

	public String killRingString() {
		return listString(killRing, " is stalking ");
	}

	public String graveyardString() {
		return listString(killRing, " was killed by ");
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
		System.out.println(killRingString());
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
		System.out.println(graveyardString());
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
		Parameters.validate(null, n -> isGameOver(), () -> "Game is over!");
		// remove NAME from killRing
		// IF someone was removed, set their killer and add them to
		// the front of the graveyard
		//Iterator<AssassinPlayer> itr = killRing.iterator();
		//for(AssassinPlayer p : itr) {
		//}
		//AssassinPlayer killed = killRing.removeFirstOccurrence(name);
		//if(graveyard.offerFirst(killed)) {
			////killed.kiler = 
		//} else {
			//// not in ring; killed is null
			//throw new IllegalArgumentException();
		//}
	}
}
