import static org.junit.Assert.*;
import org.junit.Test;

import java.awt.Point;
import java.awt.Color;

public class AbstractBirdTest {
	public void constructorTest(int x, int y) {
		AbstractBird b = new RealAbstractBird(x, y);
		assertEquals(new Point(x, y), b.getPosition());
	}

	@Test
	public void constructorTest() {
		constructorTest(1, 100);
		constructorTest(-1, -100);
		constructorTest(102830182, -293839249);
	}

	public void colorTest(AbstractBird b, Color c) {
		b.setColor(c);
		assertEquals(c, b.getColor());
	}
	
	@Test
	public void colorTest() {
		AbstractBird b = new RealAbstractBird(0, 0);
		colorTest(b, Color.BLACK);
		colorTest(b, Color.MAGENTA);
		colorTest(b, Color.BLUE);
	}

	public void movementTest(int x, int y) {
		AbstractBird b = new RealAbstractBird(0, 0);
		b.setMovement(x, y);
		// turn and move
		b.fly();
		assertEquals(new Point(x, y), b.getPosition());
	}

	@Test
	public void movementTest() {
		movementTest(0, 0);
		movementTest(1, 1);
		movementTest(-1, -1);
		movementTest(-12930, 138);
		movementTest(-18300001, -389894);
	}
}

// wrapper class so it can be instantiated
class RealAbstractBird extends AbstractBird {
	RealAbstractBird(int x, int y) { super(x, y); }
}
