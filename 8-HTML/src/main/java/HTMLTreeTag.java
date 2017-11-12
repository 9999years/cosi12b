public class HTMLTreeTag extends HTMLTag {
	protected List<HTMLTreeTag> children = new LinkedList<>();
	protected HTMLTreeTag parent;
	protected HTMLTag tag;

	public List<HTMLTreeTag> getChildren() {
		return new LinkedList<>(children);
		//<>!==
	}

	public HTMLTreeTag getParent() {
		return parent;
	}

	public HTMLTag getTag() {
		return tag;
	}
}
