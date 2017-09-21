import static org.junit.Assert.*;
import org.junit.Test;

public class DateTest {
	@Test
	public void equals() {
		assertEquals(new Date(1998, 4, 30), new Date(1998, 4, 30));
		assertEquals(new Date(2017, 11, 1), new Date(2017, 11, 1));
		assertNotEquals(new Date(2017, 11, 1), new Date(2017, 11, 2));
	}

	@Test
	public void constructors() {
		assertEquals(new Date(17430), new Date(2017, 9, 21));
		assertEquals(new Date(365),   new Date(1971, 1, 1));
		assertEquals(new Date(0),   new Date(1970, 1, 1));
		assertEquals(new Date(10346), new Date(1998, 4, 30));
		assertEquals(new Date(16283), new Date(2014, 8, 1));
	}

	@Test
	public void leapYearTest() {
		assertTrue(new Date(1756, 1, 1).isLeapYear());
		assertTrue(new Date(1952, 1, 1).isLeapYear());
		assertTrue(new Date(2004, 1, 1).isLeapYear());
		assertTrue(new Date(2400, 1, 1).isLeapYear());
		assertTrue(new Date(2000, 1, 1).isLeapYear());

		assertFalse(new Date(1753, 1, 1).isLeapYear());
		assertFalse(new Date(2005, 1, 1).isLeapYear());
		assertFalse(new Date(2100, 1, 1).isLeapYear());
		assertFalse(new Date(1900, 1, 1).isLeapYear());
	}

	@Test
	public void leapDayTest() {
		assertTrue(new Date(2000, 2, 29).isLeapDay());
		assertTrue(new Date(2004, 2, 29).isLeapDay());

		assertFalse(new Date(2000, 2, 28).isLeapDay());
		assertFalse(new Date(2000, 12, 29).isLeapDay());
	}

	@Test
	public void toStringTest() {
		assertEquals("2004/5/24", new Date(2004, 5, 24).toString());
		assertEquals("1756/12/1", new Date(1756, 12, 1).toString());
		assertEquals("1999/11/30", new Date(1999, 11, 30).toString());
		assertEquals("1998/4/30", new Date(1998, 4, 30).toString());
		assertEquals("10998/1/20", new Date(10998, 1, 20).toString());
	}

	@Test
	public void dayOfWeekTest() {
		assertEquals("Sunday", new Date(2005, 8, 7).getDayOfWeek());
		assertEquals("Monday", new Date(1753, 1, 1).getDayOfWeek());
		assertEquals("Monday", new Date(2017, 9, 11).getDayOfWeek());
		assertEquals("Tuesday", new Date(2017, 9, 12).getDayOfWeek());
		assertEquals("Thursday", new Date(2017, 9, 21).getDayOfWeek());

		assertEquals("Sunday",    new Date(2017, 9, 17).getDayOfWeek());
		assertEquals("Monday",    new Date(2017, 9, 18).getDayOfWeek());
		assertEquals("Tuesday",   new Date(2017, 9, 19).getDayOfWeek());
		assertEquals("Wednesday", new Date(2017, 9, 20).getDayOfWeek());
		assertEquals("Thursday",  new Date(2017, 9, 21).getDayOfWeek());
		assertEquals("Friday",    new Date(2017, 9, 22).getDayOfWeek());
		assertEquals("Saturday",  new Date(2017, 9, 23).getDayOfWeek());
	}

	public void assertNextDay(Date d1, Date d2) {
		d1.nextDay();
		assertEquals(d2, d1);
	}

	@Test
	public void nextDayTest() {
		assertNextDay(new Date(2017, 12, 31), new Date(2018, 1, 1));
		assertNextDay(new Date(2005, 2, 28), new Date(2005, 3, 1));
		assertNextDay(new Date(2004, 2, 28), new Date(2004, 2, 29));
	}
}
