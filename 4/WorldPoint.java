import java.awt.Point;

public class WorldPoint {
	/**
	 * None, Top, or Bottom
	 */
	Side vertical;
	/**
	 * None, Left, or Right
	 */
	Side horizontal;

	Point position;
	Point worldSize;
	/**
	 * hypothetically could be overridden for world that doesnt start at
	 * 0, 0
	 */
	protected Point worldOrigin = new Point(0, 0);

	WorldPoint(Point position) {
		setPosition(position);
		updateEdges();
	}

	WorldPoint(Point position, Point worldSize) {
		setPosition(position);
		this.worldSize = worldSize;
		updateEdges();
	}

	public boolean onTopEdge() {
		return position.y == worldOrigin.y;
	}
	public boolean onBottomEdge() {
		return position.y == worldOrigin.y + worldSize.y - 1;
	}
	public boolean onVerticalEdge() {
		return vertical != Side.None;
	}

	public boolean onLeftEdge() {
		return position.x == worldOrigin.x;
	}
	public boolean onRightEdge() {
		return position.x == worldOrigin.x + worldSize.x - 1;
	}
	public boolean onHorizontalEdge() {
		return horizontal != Side.None;
	}

	public boolean onEdge() {
		return onHorizontalEdge() || onVerticalEdge();
	}

	/**
	 * makes sure edge variables correctly reflect position
	 */
	protected void updateEdges() {
		if(onLeftEdge()) {
			horizontal = Side.Left;
		} else if(onRightEdge()) {
			horizontal = Side.Right;
		} else {
			horizontal = Side.None;
		}

		if(onTopEdge()) {
			horizontal = Side.Top;
		} else if(onBottomEdge()) {
			horizontal = Side.Bottom;
		} else {
			horizontal = Side.None;
		}
	}

	public void setPosition(Point l) {
		position = l;
	}

	public void setPosition(int x, int y) {
		setPosition(new Point(x, y));
	}

	public void move(Point delta) {
		setPosition(
			position.x + delta.x,
			position.y + delta.y);
	}

	public void move(int x, int y) {
		move(new Point(x, y));
	}

	public Point toPoint() {
		return new Point(
			worldOrigin.x + position.x,
			worldOrigin.y + position.y
		);
	}
}
