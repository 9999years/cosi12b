import static org.junit.Assert.*;
import org.junit.Test;

public class LetterInventoryTest {
	public static final double DELTA = 0.00001;

	@Test
	public void percentTest() {
		LetterInventory a = new LetterInventory("abc");
		assertEquals(0.33333333333333,
			a.getLetterPercentage('a'), DELTA);
		assertEquals(0.33333333333333,
			a.getLetterPercentage('b'), DELTA);
		a = new LetterInventory("Hillary Clinton");
		assertEquals(0.142857,
			a.getLetterPercentage('n'), DELTA);
		assertEquals(0.214286,
			a.getLetterPercentage('l'), DELTA);
		assertEquals(0.071429,
			a.getLetterPercentage('y'), DELTA);
		a = new LetterInventory("ooooooooo");
		assertEquals(1.0, a.getLetterPercentage('o'), DELTA);
		a = new LetterInventory("z");
		assertEquals(1.0, a.getLetterPercentage('z'), DELTA);
		assertEquals(0.0, a.getLetterPercentage('o'), DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void percentExceptionTest() {
		LetterInventory a = new LetterInventory("abc");
		a.getLetterPercentage('-');
	}
}
