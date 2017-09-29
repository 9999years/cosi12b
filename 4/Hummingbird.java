import java.awt.Color;
import java.util.Random;

public class Hummingbird extends Bird {
	protected Color color = Color.MAGENTA;
	protected Random generator = new Random();

	Hummingbird(int x, int y) { super(x, y); }

	public void move() {
		position.setPosition(
			generator.nextInt(AviarySize.x),
			generator.nextInt(AviarySize.y));
	}
}
