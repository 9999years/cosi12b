import static org.junit.Assert.*;
import org.junit.Test;

import java.awt.Point;
import java.awt.Color;

public class BirdTest {

	@Test(expected=IllegalArgumentException.class)
	public void positionTest1() {
		Bird b = new RealBird(20, 0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void positionTest2() {
		Bird b = new RealBird(0, 20);
	}

	@Test(expected=IllegalArgumentException.class)
	public void positionTest3() {
		Bird b = new RealBird(100, 2838);
	}

	@Test(expected=IllegalArgumentException.class)
	public void positionTest4() {
		Bird b = new RealBird(-2, -19);
	}

	@Test(expected=IllegalArgumentException.class)
	public void positionTest5() {
		Bird b = new RealBird(-23, -20);
	}

	@Test
	public void positionTest6() {
		Bird b;
		b = new RealBird(0, 0);
		b = new RealBird(1, 19);
		b = new RealBird(1, 18);
		b = new RealBird(19, 0);
		b = new RealBird(3, 4);
	}
}

// wrapper class so it can be instantiated
class RealBird extends Bird {
	RealBird(int x, int y) { super(x, y); }
}
