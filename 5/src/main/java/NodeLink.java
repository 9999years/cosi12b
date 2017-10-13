public class NodeLink<T> {
	Node<T> to;
	/**
	 * don't make this negative
	 */
	double weight;

	NodeLink<T>() { }

	NodeLink<T>(Node<T> to, double weight) {
		this.to     = to;
		this.weight = weight;
	}
}
