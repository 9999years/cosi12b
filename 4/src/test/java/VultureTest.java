import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.Color;

public class VultureTest {
	void positionExceptionTest(int x, int y) {
		assertThrows(IllegalArgumentException.class, () -> {
			Vulture v = new Vulture(x, y);
		});
	}

	@Test
	void positionExceptionTest() {
		positionExceptionTest(0, 0);
		positionExceptionTest(0, 1);
		positionExceptionTest(1, 0);
		positionExceptionTest(0, 15);
	}

	@Test
	public void validPositionTest() {
		Bird b;
		b = new RealBird(1, 19);
		b = new RealBird(1, 18);
		b = new RealBird(3, 4);
	}
}
