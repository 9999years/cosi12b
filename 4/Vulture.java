import java.awt.Color;
import java.awt.Point;

/**
 * vulture class; moves counter-clockwise in a square of 1-unit sides
 * colored black
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class Vulture extends Bird {
	Vulture(int x, int y) {
		super(x, y);
		setColor(Color.BLACK);
		setMovement(1, 0);
	}

	protected void turn() {
		if(delta.y != 0) {
			delta.x = delta.y;
			delta.y = 0;
		} else if(delta.x != 0) {
			delta.y = -delta.x;
			delta.x = 0;
		}
	}
}
