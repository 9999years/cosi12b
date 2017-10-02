/**
 * a modified AbstractBird confined to a certain aviary (in this case, 20x20
 * units); guarenteed to not exceed [0, AviarySize)
 * position field (accessable as a WorldPoint with getRichPosition()) can
 * detect edge status of bird
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

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
