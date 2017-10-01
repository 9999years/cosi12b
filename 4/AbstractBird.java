import java.awt.Point;
import java.awt.Color;

public abstract class AbstractBird implements BirdBehavior {
	protected Point position;
	protected Point delta = new Point(0, 0);
	protected Color color;
	protected int movement_speed;

	public Point getPosition()               { return position;            }
	protected void setPosition(Point p)      { setPosition(p.x, p.y);      }
	protected void setPosition(int x, int y) { position.setLocation(x, y); }
	public Color getColor()                  { return color;               }
	public void setColor(Color c)            { color = c;                  }
	public void setMovement(int dx, int dy)  { setMovement(new Point(dx, dy)); }
	public void setMovement(Point d)         { delta = d;                  }

	AbstractBird() { }

	AbstractBird(int x, int y) {
		position = new Point(x, y);
		setPosition(x, y);
	}

	public void fly() {
		turn();
		move();
	}

	// some birds don't turn
	// this method can be used to modify the delta
	protected void turn() { }

	// some birds don't move with a regular or patterned delta
	protected void move() {
		position.translate(delta.x, delta.y);
	}
}
