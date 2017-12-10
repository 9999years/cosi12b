import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class AssassinManagerTest {
	final List<String> names = Arrays.asList("Joe", "Sally", "Jim", "Carol", "Chris");

	String[] playersToStrings(Collection<?> c) {
		return c.stream()
			.map(t -> t.toString())
			.toArray(String[]::new);
	}

	String[] playerArray(AssassinManager m) {
		return playersToStrings(m.killRing);
	}

	String[] deadArray(AssassinManager m) {
		return playersToStrings(m.graveyard);
	}

	@Test
	void setupTest() {
		AssassinManager manager = new AssassinManager(names);
		assertArrayEquals(names.toArray(), playerArray(manager));
		assertArrayEquals(new String[] {}, deadArray(manager));
		assertFalse(manager.isGameOver());
		assertEquals(null, manager.winner());
	}

	@Test
	void simpleTest() {
		List<String> names = new ArrayList<>(Arrays.asList(
			"Joe", "Sally", "Jim", "Carol", "Chris"));
		AssassinManager manager = new AssassinManager(names);
		manager.kill("jim");
		names.remove("Jim");
		assertArrayEquals(names.toArray(), playerArray(manager));
		assertArrayEquals(new String[] { "Jim" }, deadArray(manager));
	}

	@Test
	void killRingStringTest() {
		AssassinManager manager = new AssassinManager(names);
		assertEquals(
			  ">>    Joe is stalking Sally\n"
			+ ">>    Sally is stalking Jim\n"
			+ ">>    Jim is stalking Carol\n"
			+ ">>    Carol is stalking Chris\n"
			+ ">>    Chris is stalking Joe\n",
			manager.killRingString());
	}

	@Test
	void graveyardStringTest() {
		AssassinManager manager = new AssassinManager(names);
		manager.kill("jim");
		manager.kill("CHRIS");
		manager.kill("CaRoL");
		assertEquals(
			  ">>    Carol was killed by Sally\n"
			+ ">>    Chris was killed by Carol\n"
			+ ">>    Jim was killed by Sally\n",
			manager.graveyardString());
		assertFalse(manager.isGameOver());
		manager.kill("joe");
		assertEquals("Sally", manager.winner().toString());
		assertEquals(
			  ">>    Joe was killed by Sally\n"
			+ ">>    Carol was killed by Sally\n"
			+ ">>    Chris was killed by Carol\n"
			+ ">>    Jim was killed by Sally\n",
			manager.graveyardString());
		assertTrue(manager.isGameOver());
	}

	@Test
	void containsTest() {
		AssassinManager manager = new AssassinManager(names);
		String[] kills = new String[] {
			"jim", "chris", "carol", "joe"
		};
		for(String kill : kills) {
			assertTrue(manager.killRingContains(kill));
			assertFalse(manager.graveyardContains(kill));
			manager.kill(kill);
			assertFalse(manager.killRingContains(kill));
			assertTrue(manager.graveyardContains(kill));
		}
	}
}
