import java.awt.Color;
import java.awt.Point;

public class Bluebird extends Bird {
	protected Point delta = new Point(1, -1);

	Bluebird(int x, int y) {
		super(x, y);
		setColor(Color.BLUE);
	}

	protected void turn() {
		delta.y *= -1;
		if(position.onHorizontalEdge()) {
			delta.x *= -1;
		}
	}
}
