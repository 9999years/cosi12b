import java.awt.Point;
import java.awt.Color;

/**
 * a bird with an aviary it's confined to
 */
public abstract class Bird extends AbstractBird {
	protected WorldPoint position;
	public static final Point AviarySize = new Point(
		AviaryConstants.SIZE,
		AviaryConstants.SIZE);

	public Point getPosition() { return position.toPoint(); }
	protected void setPosition(int x, int y) {
		setPosition(new Point(x, y));
	}
	protected void setPosition(Point p) { position.setPosition(p); }

	// new method
	public WorldPoint getRichPosition() { return position; }

	Bird(int x, int y) {
		position = new WorldPoint(new Point(x, y), AviarySize);
	}

	protected void move() {
		position.move(delta);
	}
}
