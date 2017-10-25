import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.Color;

public class BirdTest {

	void positionExceptionTest(int x, int y) {
		assertThrows(IllegalArgumentException.class, () -> {
			Bird b = new RealBird(x, y);
		});
	}

	@Test
	void positionExceptionTest() {
		positionExceptionTest(20, 0);
		positionExceptionTest(0, 20);
		positionExceptionTest(-23, -20);
		positionExceptionTest(100, 2838);
		positionExceptionTest(-2, -19);
		positionExceptionTest(-2, 1);
	}

	@Test
	public void validPositionTest() {
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
