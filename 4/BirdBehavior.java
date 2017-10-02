import java.awt.Point;
import java.awt.Color;

/**
 * Assignment spec; what a bird should do
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public interface BirdBehavior {
	/**
	 * fetch the bird's color as a java.awt.Color
	 * @return a Color representing the bird's color
	 */
	Color getColor();

	/**
	 * fetch the bird's position to integer position as a java.awt.Point
	 * @return a Point representing the bird's position
	 */
	Point getPosition();

	/**
	 * move the bird "once"; as many units as it should move per-frame;
	 * typical implementations provide several abstract,
	 * individually-overridable units to build a fly() function
	 */
	void fly();
}
