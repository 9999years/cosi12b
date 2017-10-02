import java.awt.Color;
import java.awt.Point;

/**
 * Cardinal that flies vertically up and down; turning at the aviary edges
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class Cardinal extends Bird {
	Cardinal(int x, int y) {
		super(x, y);
		setColor(Color.RED);
		setMovement(0, -1);
	}

	protected void turn() {
		if(position.onVerticalEdge()) {
			delta.y *= -1;
		}
	}
}
