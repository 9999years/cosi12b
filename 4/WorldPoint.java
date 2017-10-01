import java.awt.Point;

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
	public boolean onVerticalEdge() {
		return vertical != Side.None;
	}

	public boolean onLeftEdge() {
		return horizontal == Side.Left;
	}
	public boolean onRightEdge() {
		return horizontal == Side.Right;
	}
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

	public void setPosition(Point l) throws IllegalArgumentException {
		position = l;
		updateEdges();
	}

	public void setPosition(int x, int y) throws IllegalArgumentException {
		setPosition(new Point(x, y));
	}

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

	public int getX() {
		return position.x;
	}

	public int getY() {
		return position.y;
	}

	public Point toPoint() {
		return new Point(
			worldOrigin.x + position.x,
			worldOrigin.y + position.y
		);
	}

	/**
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
