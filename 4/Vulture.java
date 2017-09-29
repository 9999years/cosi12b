import java.awt.Color;
import java.awt.Point;

public class Vulture extends Bird {
	protected Color color = Color.BLACK;
	protected Point delta = new Point(0, -1);

	Vulture(int x, int y) { super(x, y); }

	protected void turn() {
		if(delta.y != 0) {
			delta.x = delta.y;
			delta.y = 0;
		} else if(delta.x != 0) {
			delta.y = -delta.y;
			delta.x = 0;
		}
	}
}
