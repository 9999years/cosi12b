import static org.junit.Assert.*;
import org.junit.Test;

public class LetterInventoryTest {
	public static final double DELTA = 0.00001;

	@Test
	public void percentTest() {
		LetterInventory a = new LetterInventory("abc");
		assertEquals(
			0.33333333333333,
			a.getLetterPercentage('a'),
			DELTA);
	}
}
