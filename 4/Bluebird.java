import java.awt.Color;
import java.awt.Point;

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
