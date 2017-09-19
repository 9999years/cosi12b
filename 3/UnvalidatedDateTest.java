import static org.junit.Assert.*;
import org.junit.Test;

public class UnvalidatedDateTest {
	@Test
	public void constructor() {
		// make sure attribs are set correctly and are NOT VALIDATED
		UnvalidatedDate d = new UnvalidatedDate(1, 2, 3);
		assertEquals(d.year, 1);
		assertEquals(d.month, 2);
		assertEquals(d.day, 3);

		d = new UnvalidatedDate(91283, -1239, -4054);
		assertEquals(d.year, 91283);
		assertEquals(d.month, -1239);
		assertEquals(d.day, -4054);

		d = new UnvalidatedDate(new Date(2000, 12, 30));
		assertEquals(d.year, 2000);
		assertEquals(d.month, 12);
		assertEquals(d.day, 30);

		// leap day!
		d = new UnvalidatedDate(new Date(2000, 2, 29));
		assertEquals(d.year, 2000);
		assertEquals(d.month, 2);
		assertEquals(d.day, 29);
	}

	@Test
	public void validateDayTest() {
		assertFalse("day 0 is invalid",
			new UnvalidatedDate(2000, 1, 0).validateDay());
		assertFalse("day <0 is invalid",
			new UnvalidatedDate(2000, 1, -2).validateDay());
		assertFalse("day 32 is invalid in jan.",
			new UnvalidatedDate(2000, 1, 32).validateDay());
		assertTrue("day 31 is valid in jan.",
			new UnvalidatedDate(2000, 1, 31).validateDay());
		assertTrue("day 30 is valid in nov.",
			new UnvalidatedDate(2000, 11, 30).validateDay());
		assertFalse("day 29 is invalid in feb (non leap yr)",
			new UnvalidatedDate(2001, 2, 29).validateDay());
		assertTrue("day 29 is valid in feb (leap yr)",
			new UnvalidatedDate(2000, 2, 29).validateDay());
	}

	@Test
	public void validateMonthTest() {
		assertFalse("month 0 is invalid",
			new UnvalidatedDate(2000, 0, 1).validateMonth());
		assertFalse("month <0 is invalid",
			new UnvalidatedDate(2000, -9, 1).validateMonth());
		assertFalse("month >12 is invalid",
			new UnvalidatedDate(2000, 13, 1).validateMonth());
		assertFalse("month >12 is invalid",
			new UnvalidatedDate(2000, 99999, 1).validateMonth());

		assertTrue("month 1 is valid",
			new UnvalidatedDate(2000, 1, 1).validateMonth());
		assertTrue("month 11 is valid",
			new UnvalidatedDate(2000, 11, 1).validateMonth());
		assertTrue("month 12 is valid",
			new UnvalidatedDate(2000, 11, 1).validateMonth());
	}

	@Test
	public void validateYearTest() {
		assertFalse("year <0 is invalid",
			new UnvalidatedDate(-1, 1, 1).validateYear());
		assertFalse("year 0 is invalid",
			new UnvalidatedDate(0, 1, 1).validateYear());
		assertFalse("year <1753 is invalid",
			new UnvalidatedDate(1600, 1, 1).validateYear());
		assertFalse("year <1753 is invalid",
			new UnvalidatedDate(1752, 1, 1).validateYear());

		assertTrue("year >=1753 is valid",
			new UnvalidatedDate(1753, 1, 1).validateYear());
		assertTrue("year >=1753 is valid",
			new UnvalidatedDate(2000, 1, 1).validateYear());
		assertTrue("year >=1753 is valid",
			new UnvalidatedDate(9999, 1, 1).validateYear());
		assertTrue("5-digit years are valid",
			new UnvalidatedDate(23456, 1, 1).validateYear());
	}
}
