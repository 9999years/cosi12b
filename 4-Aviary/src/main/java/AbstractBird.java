import java.awt.Point;
import java.awt.Color;

/**
 * a bird-like object with a color, position, and movement vector
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public abstract class AbstractBird implements BirdBehavior {
	protected Point position;
	protected Point delta = new Point(0, 0);
	protected Color color;

	// getters and setters
	public Point getPosition() {
		 return position;
	}
	protected void setPosition(Point p) {
		 setPosition(p.x, p.y);
	}
	protected void setPosition(int x, int y) {
		 position.setLocation(x, y);
	}
	public Color getColor() {
		 return color;
	}
	protected void setColor(Color c) {
		 color = c;
	}
	protected void setMovement(int dx, int dy) {
		 setMovement(new Point(dx, dy));
	}
	protected void setMovement(Point d) {
		 delta = d;
	}

	AbstractBird() { }

	/**
	 * creates a new bird
	 * @param x the bird's starting x coordinate
	 * @param y the bird's starting y coordinate
	 */
	AbstractBird(int x, int y) {
		position = new Point(x, y);
		setPosition(x, y);
	}

	public void fly() {
		turn();
		move();
	}

	/**
	 * some birds don't turn
	 * this method can be used to modify the delta
	 */
	protected void turn() { }

	/**
	 * some birds dont have a regularly patterned movement, eg hummingbirds
	 * which move to a completely random point every step
	 */
	protected void move() {
		position.translate(delta.x, delta.y);
	}
}
