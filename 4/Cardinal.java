import java.awt.Color;
import java.awt.Point;

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
