import java.awt.Point;
import java.awt.Color;

public interface BirdBehavior extends AviaryConstants {
	Color getColor();
	Point getPosition();
	void fly();
}
