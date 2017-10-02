import java.awt.Color;
import java.awt.Point;

/**
 * Bluebird class; zig-zags up and down by 1 unit and bounces left and right
 * across the arena
 * colored blue
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class Bluebird extends Bird {
	Bluebird(int x, int y) {
		super(x, y);
		setColor(Color.BLUE);
		setMovement(1, 1);
	}

	protected void turn() {
		delta.y *= -1;
		if(position.onLeftEdge()) {
			delta.x = 1;
		} else if(position.onRightEdge()) {
			delta.x = -1;
		}
	}
}
