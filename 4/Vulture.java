import java.awt.Color;
import java.awt.Point;

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
