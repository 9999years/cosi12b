import java.awt.Color;
import java.awt.Point;

public class Cardinal extends Bird {
	protected Color color = Color.RED;
	protected Point delta = new Point(0, -1);

	Cardinal(int x, int y) { super(x, y);
		System.out.println("color is " + color);
		System.out.println("color is " + getColor());
		System.out.println("delta is " + delta);
	}

	protected void turn() {
		if(position.onVerticalEdge()) {
			delta.y *= -1;
		}
	}
}
