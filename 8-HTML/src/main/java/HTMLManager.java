import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.function.Consumer;

import java.lang.StringBuilder;
import java.lang.Iterable;

public class HTMLManager {
	private Queue<HTMLTag> tags = new LinkedList<>();

	HTMLManager(Queue<HTMLTag> page) {
		replace(page);
	}

	HTMLManager(String page) {
		tags = new HTMLParser(page).parse();
	}

	public void add(HTMLTag tag) {
		tags.add(tag);
	}

	public void removeAll(HTMLTag tag) {
		tags.removeIf(t -> t.equals(tag));
	}

	public List<HTMLTag> getTags() {
		return new LinkedList<>(tags);
	}

	protected void replace(Iterable<HTMLTag> tags) {
		for(HTMLTag t : tags) {
			add(t);
		}
	}

	/**
	 * The basic idea of the algorithm is to process the page tag by tag.
	 * For each tag, you will check to see if it has a matching tag later
	 * in the page in the correct place. Since self- closing tags don’t
	 * have to match anything, whenever you see one, you can simply add it
	 * directly to the correct version of the tags. For opening tags, we
	 * assume that the writer of the HTML page intended to actually include
	 * the tag; so, like with the self- closing tag, we add it to the
	 * result. However, we need to keep track of if we have found its
	 * match; so, it should also be added to a Stack. If we find a closing
	 * tag, we must figure out if it is in the right place or not. In
	 * particular:
	 *
	 *   • If the opening tag at the top of the stack matches the closing
	 *     tag we found, then it matches, and you should update the state
	 *     according. (Hint: You probably want to edit the stack and the
	 *     result.)
	 *   • If the opening tag at the top of the stack does not match the
	 *     closing tag we found, then the writer of the HTML page made a
	 *     mistake. To fix the mistake, you should add a new closing tag
	 *     that matches the opening one at the top of the stack (so that it
	 *     remains balanced). You should keep on adding closing tags to
	 *     your result as long as the opening tag on top of the stack does
	 *     not match. If you close all unclosed open tags on the stack,
	 *     then the closing tag we found doesn’t match any open tags and
	 *     should be discarded.
	 *   • If, at any point, you find a closing tag that has no matching
	 *     open tag, just discard it.  At the end of processing all the
	 *     tags, if you have any left over tags that were never closed, you
	 *     should close them. Note that every tag in the page that was ever
	 *     opened must at some point be closed!
	 */
	public void fixHTML() {
		Stack<HTMLTag> corrected = new Stack<>();
		Stack<HTMLTag> opened = new Stack<>();
		Consumer<HTMLTag> fixMatchings = tag -> {
			// discard tags that dont close anything
			//
			// as long as a tag has been opened and not closed, we
			// have a tag to close
			//
			// as long as given tag doesnt match
			// the last unclosed tag
			while(!opened.empty() && !tag.matches(opened.peek())) {
				// close the top tag
				corrected.push(
					opened.pop().getMatching());
			}

			// now we *might* have one last tag to close;
			// if this closing tag was correct, the push
			// above validated the fragment, so we have to
			// check that we still have a mistake
			//if(!opened.empty() && tag.matches(opened.peek())) {
				// insert the users tag
				//corrected.push(tag);
			//}
		};

		for(HTMLTag tag : tags) {
			if(!tag.isClosing()) {
				// equivalent to:
				// tag.isSelfClosing()
				// || tag.isOpening()
				// || tag.isComment()
				corrected.push(tag);
				if(tag.isOpening()) {
					// not self-closing or comment tags
					opened.push(tag);
				}
			} else {
				// tag is closing
				fixMatchings.accept(tag);
			}
		}

		replace(corrected);
	}

	public String toString() {
		StringBuilder ret = new StringBuilder();
		for(HTMLTag tag : tags) {
			ret.append(tag.toString());
		}
		return ret.toString();
	}
}
