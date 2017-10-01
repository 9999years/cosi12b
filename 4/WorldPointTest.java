import static org.junit.Assert.*;
import org.junit.Test;

import java.awt.Point;

public class WorldPointTest {
	WorldPoint make(int x, int y) {
		return new WorldPoint(new Point(x, y), Bird.AviarySize);
	}

	@Test
	public void edgesTest() {
		WorldPoint p;
		p = make(0, 0);
		assertTrue(p.onEdge());
		assertTrue(p.onTopEdge());
		assertTrue(p.onLeftEdge());
		assertTrue(p.onHorizontalEdge());
		assertTrue(p.onVerticalEdge());
		assertFalse(p.onRightEdge());
		assertFalse(p.onBottomEdge());
		assertTrue(p.onCorner());

		p = make(19, 19);
		assertTrue(p.onEdge());
		assertFalse(p.onTopEdge());
		assertFalse(p.onLeftEdge());
		assertTrue(p.onHorizontalEdge());
		assertTrue(p.onVerticalEdge());
		assertTrue(p.onRightEdge());
		assertTrue(p.onBottomEdge());
		assertTrue(p.onCorner());

		p = make(1, 1);
		assertFalse(p.onEdge());
		assertFalse(p.onTopEdge());
		assertFalse(p.onLeftEdge());
		assertFalse(p.onHorizontalEdge());
		assertFalse(p.onVerticalEdge());
		assertFalse(p.onRightEdge());
		assertFalse(p.onBottomEdge());
		assertFalse(p.onCorner());

		p = make(5, 0);
		assertTrue(p.onEdge());
		assertTrue(p.onTopEdge());
		assertFalse(p.onLeftEdge());
		assertFalse(p.onHorizontalEdge());
		assertTrue(p.onVerticalEdge());
		assertFalse(p.onRightEdge());
		assertFalse(p.onBottomEdge());
		assertFalse(p.onCorner());

		p = make(0, 10);
		assertTrue(p.onEdge());
		assertFalse(p.onTopEdge());
		assertTrue(p.onLeftEdge());
		assertTrue(p.onHorizontalEdge());
		assertFalse(p.onVerticalEdge());
		assertFalse(p.onRightEdge());
		assertFalse(p.onBottomEdge());
		assertFalse(p.onCorner());
	}
}
