import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class HTMLManager {
	private Queue<HTMLTag> tags = new LinkedList<>();

	HTMLManager(Queue<HTMLTag> page) {
		for(HTMLTag t : page) {
			add(t);
		}
	}

	public void add(HTMLTag tag) {
		tags.add(tag);
	}

	public void removeAll(HTMLTag tag) {
		tags.removeIf(t -> t.equals(tag));
	}

	public List<HTMLTag> getTags() {
		return null;
	}

	public void fixHTML() {
	}
}
