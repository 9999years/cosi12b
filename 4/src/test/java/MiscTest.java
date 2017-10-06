import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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

	public void flyTest(AbstractBird b, int x, int y) {
		b.fly();
		assertEquals(new Point(x, y), b.getPosition());
	}

	@Test
	public void cardinalMovementTest() {
		Cardinal c = new Cardinal(0, 3);
		flyTest(c, 0, 2);
		flyTest(c, 0, 1);
		flyTest(c, 0, 0);
		flyTest(c, 0, 1);
		flyTest(c, 0, 2);

		c = new Cardinal(0, 17);
		c.setMovement(new Point(0, 1));
		flyTest(c, 0, 18);
		flyTest(c, 0, 19);
		flyTest(c, 0, 18);
		flyTest(c, 0, 17);
	}

	@Test
	public void bluebirdMovementTest() {
		Bluebird b = new Bluebird(0, 3);
		flyTest(b, 1, 2);
		flyTest(b, 2, 3);
		flyTest(b, 3, 2);
		flyTest(b, 4, 3);
		flyTest(b, 5, 2);

		b = new Bluebird(17, 1);
		flyTest(b, 18, 0);
		flyTest(b, 19, 1);
		flyTest(b, 18, 0);
		flyTest(b, 17, 1);
	}

	@Test
	public void vultureMovementTest() {
		Vulture v = new Vulture(5, 5);
		flyTest(v, 5, 4);
		flyTest(v, 4, 4);
		flyTest(v, 4, 5);
		flyTest(v, 5, 5);
		flyTest(v, 5, 4);
	}
}
