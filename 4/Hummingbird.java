import java.awt.Color;
import java.util.Random;

/**
 * hummingbird class; moves to a random location within the arena every turn
 * colored magenta
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class Hummingbird extends Bird {
	protected Random generator = new Random();

	Hummingbird(int x, int y) {
		super(x, y);
		setColor(Color.MAGENTA);
	}

	public void fly() {
		position.setPosition(
			generator.nextInt(AviarySize.x),
			generator.nextInt(AviarySize.y));
	}
}
