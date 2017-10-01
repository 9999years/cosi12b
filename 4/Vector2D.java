public class Vector2D {
	public int x;
	public int y;

	Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	Vector2D(Vector2D v) {
		this(v.x, v.y);
	}

	public Vector2D add(int dx, int dy) {
		return new Vector2D(x + dx, y + dy);
	}

	public Vector2D add(Vector2D v) {
		return add(v.x, v.y);
	}

	public Vector2D subtract(int dx, int dy) {
		return new Vector2D(x - dx, y - dy);
	}

	public Vector2D subtract(Vector2D v) {
		return subtract(v.x, v.y);
	}

	public void translate(int dx, int dy) {
		x += dx;
		y += dy;
	}

	public void translate(Vector2D v) {
		translate(v.x, v.y);
	}
}
