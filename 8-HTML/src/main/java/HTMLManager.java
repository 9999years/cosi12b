/**
 * class to manage lists of html tags; can add, remove, or fix the internal html
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.function.Consumer;

import java.lang.StringBuilder;
import java.lang.Iterable;

/**
 * class to manage lists of html tags; can add, remove, or fix the internal html
 *
 * this is basically a lie, ie completely broken and unable to serve the
 * purpose of managing html in a real-world scenario. really, only the parser
 * is bad; the html spec is 49,000 words long so it may not surprise you that
 * a 6,000 byte parser doesn't conform. the lexer can't eg. deal with 
 */
public class HTMLManager {
	protected Queue<HTMLTag> tags;

	HTMLManager(Iterable<HTMLTag> page) {
		replace(page);
	}

	HTMLManager(Queue<HTMLTag> page) {
		replace(page);
	}

	HTMLManager(String page) {
		tags = new HTMLParser(page).parse();
	}

	/**
	 * adds a tag to the end of the page
	 */
	public void add(HTMLTag tag) {
		tags.add(tag);
	}

	/**
	 * removes all instances of a given tag from the page; note that this
	 * will only remove half of matching tag pairs; use removeAllPairs for
	 * that.
	 */
	public void removeAll(HTMLTag tag) {
		tags.removeIf(t -> t.equals(tag));
	}

	/**
	 * removes all *pairs* of a given tag from the page; ie if you want to
	 * remove both <b> and </b> tags from a page, use this method
	 */
	public void removeAllPairs(HTMLTag tag) {
		removeAll(tag);
		removeAll(tag.getMatching());
	}

	public List<HTMLTag> getTags() {
		return new LinkedList<>(tags);
	}

	/**
	 * resets internal tag list and replaces it with the given list of tags
	 * it would be very silly to pass an un-ordered iterable (like a
	 * HashSet) but you could, hypothetically, do it
	 */
	protected void replace(Iterable<HTMLTag> newTags) {
		tags.clear();
		for(HTMLTag t : newTags) {
			add(t);
		}
	}

	/**
	 * From PA8.pdf:
	 *
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

		Consumer<HTMLTag> consumeNonClosingTag = tag -> {
			// we assume the author meant to include it
			corrected.push(tag);
			if(tag.isOpening()) {
				// not self-closing or comment tags; we
				// need to include these in the stack
				// so we know what needs to be closed
				// later
				opened.push(tag);
			}
		};

		Consumer<HTMLTag> consumeClosingTag = tag -> {
			// if there are no opened tags, we discard this
			// tag
			Stack<HTMLTag> closed = new Stack<>();
			while(!opened.empty()) {
				closed.push(opened.pop());
				// close the top tag
				corrected.push(closed.peek().getMatching());
				// finish if the current tag matches
				// the top of the opened stack;
				// otherwise we just close everything
				// that's been open
				if(tag.matches(closed.peek())) {
					// tag matches; we're finished
					return;
				}
			}
			// if we've gotten here, our tag didn't match anything
			// on the opened stack
			//
			// SO we undo everything we just did; we've just pushed
			// closed.size() tags onto corrected, so we pop those
			// off
			//
			// this lets us skip iterating over the opened stack
			// without doing anything before starting, just to see
			// if anything in it matches our tag. if the input html
			// has a bunch of unmatched closing tags, this is
			// slower than checking for a match beforehand. if the
			// input html has more unordered or correctly-matching
			// closing tags, this is faster
			for(int i = 0; i < closed.size(); i++) {
				opened.push(corrected.pop().getMatching());
			}
		};

		for(HTMLTag tag : tags) {
			if(!tag.isClosing()) {
				// comments, opening tags, and self-closing tags
				consumeNonClosingTag.accept(tag);
			} else {
				// tag is closing
				consumeClosingTag.accept(tag);
			}
		}

		// close extra unclosed tags
		while(!opened.empty()) {
			corrected.push(opened.pop().getMatching());
		}

		// replace internal html with fixed tags
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
