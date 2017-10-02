import static org.junit.Assert.*;
import org.junit.Test;

import java.awt.Point;
import java.awt.Color;

public class MiscTest {
	void colorTest(AbstractBird b, Color c) {
		assertEquals(c, b.getColor());
	}

	@Test
	public void colorTest() {
		colorTest(new Cardinal(0, 0), Color.RED);
	}
}
