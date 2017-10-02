import java.awt.Point;

/**
 * a point confined to a certain area, capable of detecting when it's on an
 * edge or a corner
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
public class WorldPoint {
	enum Side {
		None,
		Top,
		Right,
		Bottom,
		Left;
	}

	/**
	 * None, Top, or Bottom
	 */
	protected Side vertical;
	/**
	 * None, Left, or Right
	 */
	protected Side horizontal;

	protected Point position;

	/**
	 * hypothetically could be overridden for world that doesnt start at
	 * 0, 0
	 */
	protected Point worldOrigin = new Point(0, 0);
	/**
	 * where the world ends
	 */
	protected Point worldEnd;

	/**
	 * @param position the point's starting position
	 * @param worldSize the world's size; coordinates up to worldSize.x - 1,
	 * worldSize.y - 1 are legal values
	 * @throws IllegalArgumentException when position is beyond the worldSize
	 */
	WorldPoint(Point position, Point worldSize)
			throws IllegalArgumentException {
		// end = orig + size - <1, 1>
		// subtract <1, 1> to correct for an obo error
		// (for a world of size <20, 20> only coords up to <19, 19> are
		// valid)
		worldEnd = new Point(worldOrigin);
		worldEnd.translate(
			worldSize.x - 1,
			worldSize.y - 1);
		setPosition(position);
	}

	public boolean onTopEdge() {
		return vertical == Side.Top;
	}
	public boolean onBottomEdge() {
		return vertical == Side.Bottom;
	}

	/**
	 * on the top or the bottom; not a vertical-facing (eg up-down) edge
	 * like the left and right edges
	 */
	public boolean onVerticalEdge() {
		return vertical != Side.None;
	}

	public boolean onLeftEdge() {
		return horizontal == Side.Left;
	}
	public boolean onRightEdge() {
		return horizontal == Side.Right;
	}

	/**
	 * on the left or the right; not a horizontal-facing (eg left-right) edge
	 * like the top and bottom edges
	 */
	public boolean onHorizontalEdge() {
		return horizontal != Side.None;
	}

	public boolean onEdge() {
		return onHorizontalEdge() || onVerticalEdge();
	}
	public boolean onCorner() {
		return onHorizontalEdge() && onVerticalEdge();
	}

	/**
	 * makes sure edge variables correctly reflect position
	 * throws IllegalArgumentException which bubbles up call stack if
	 * necessary for error handling
	 */
	protected void updateEdges() throws IllegalArgumentException {
		if(
			   position.x < worldOrigin.x
			|| position.y < worldOrigin.y
			|| position.x > worldEnd.x
			|| position.y > worldEnd.y) {
			throw new IllegalArgumentException(
				"Illegal movement; "
				+ position
				+ " is not in the world extending from "
				+ worldOrigin
				+ " to "
				+ worldEnd
				+ " inclusively"
			);
		}

		if(position.x == worldOrigin.x) {
			horizontal = Side.Left;
		} else if(position.x == worldEnd.x) {
			horizontal = Side.Right;
		} else {
			horizontal = Side.None;
		}

		if(position.y == worldOrigin.y) {
			vertical = Side.Top;
		} else if(position.y == worldEnd.y) {
			vertical = Side.Bottom;
		} else {
			vertical = Side.None;
		}
	}

	/**
	 * changes the point's position entirely
	 * @param l the new location
	 */
	public void setPosition(Point l) throws IllegalArgumentException {
		position = l;
		updateEdges();
	}

	public void setPosition(int x, int y) throws IllegalArgumentException {
		setPosition(new Point(x, y));
	}

	/**
	 * moves the point's position relative to its current position via a delta
	 * @param delta how much to move the point relative to its current position
	 */
	public void move(Point delta) throws IllegalArgumentException {
		setPosition(
			position.x + delta.x,
			position.y + delta.y);
	}

	public void move(int x, int y) throws IllegalArgumentException {
		move(new Point(x, y));
	}

	public Point getPosition() {
		return position;
	}

	public Point toPoint() {
		return new Point(
			position.x,
			position.y
		);
	}

	/**
	 * something like:
	 * java.awt.Point[x=0,y=0] between java.awt.Point[x=0,y=0] and
	 * java.awt.Point[x=19,y=19]
	 */
	public String toString() {
		return position
			+ " between "
			+ worldOrigin
			+ " and "
			+ worldEnd;
	}
}
