import java.awt.Color;
import java.awt.Point;

/**
 * vulture class; moves counter-clockwise in a square of 1-unit sides
 * colored black
 * valid starting positions are not less than 1, 1
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class Vulture extends Bird {
	static final Point MINIMUM_START = new Point(1, 1);
	Vulture(int x, int y) {
		super(x, y);
		if(x < MINIMUM_START.x || y < MINIMUM_START.y) {
			throw new IllegalArgumentException(
				"starting coordinates of a vulture cannot be "
				+ "less than " + MINIMUM_START + "!"
			);
		}

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
