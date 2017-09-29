import java.awt.Point;
import java.awt.Color;

public abstract class Bird implements BirdBehavior {
	protected WorldPoint position;
	protected Point AviarySize = new Point(
		AviaryConstants.SIZE,
		AviaryConstants.SIZE);
	protected Point delta = new Point(0, 0);
	protected Color color;
	protected int movement_speed;

	public Point getPosition() {
		return position.toPoint();
	}
	public WorldPoint getRichPosition() {
		return position;
	}
	protected void setPosition(Point p) {
		position.setPosition(p);
	}
	protected void setPosition(int x, int y) {
		setPosition(new Point(x, y));
	}
	public void setColor(Color c)           { color    = c;               }
	public Color getColor()                 { return color;               }
	public void setMovement(int dx, int dy) { delta = new Point(dx, dy);  }
	public void setMovement(Point d)        { delta = d;                  }

	Bird(int x, int y) {
		setPosition(x, y);
	}

	public void fly() {
		turn();
		move();
	}

	protected void turn() { }

	protected void move() {
		position.move(delta);
	}
}
