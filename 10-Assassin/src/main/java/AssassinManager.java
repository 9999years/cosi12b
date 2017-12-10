import java.util.List;
import java.util.Objects;
import java.util.ListIterator;
import java.util.Iterator;
import java.text.Normalizer;

import java.lang.StringBuilder;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;

public class AssassinManager {
	// not the java.util.LinkedList, don't worry!
	protected LinkedList<AssassinPlayer> killRing  = new LinkedList<>();
	protected LinkedList<AssassinPlayer> graveyard = new LinkedList<>();

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

	public String killRingString() {
		if(killRing.size() == 0) {
			return "";
		}

		StringBuilder ret = new StringBuilder();
		final String prefix = ">>    ";
		final String infix = " is stalking ";
		Iterator<AssassinPlayer> itr = killRing.iterator();

		// list has at least 1 person in it
		AssassinPlayer p = itr.next();
		ret.append(prefix + p.name + infix);
		while(itr.hasNext()) {
			p = itr.next();
			ret.append(p + "\n" + prefix + p + infix);
		}
		ret.append(killRing.peekFirst().name + "\n");

		return ret.toString();
	}

	public String graveyardString() {
		StringBuilder ret = new StringBuilder();
		final String prefix = ">>    ";
		final String infix = " was killed by ";
		for(AssassinPlayer p : graveyard) {
			ret.append(prefix + p + infix + p.killer + "\n");
		}
		return ret.toString();
	}

	public void printKillRing() {
		System.out.print(killRingString());
	}

	public void printGraveyard() {
		System.out.print(graveyardString());
	}

	public boolean killRingContains(String name) {
		return killRing.contains(name);
	}

	public boolean graveyardContains(String name) {
		return graveyard.contains(name);
	}

	public boolean isGameOver() {
		return killRing.size() == 1;
	}

	public String winner() {
		return isGameOver() ? killRing.peekFirst().name : null;
	}

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
