/**
 * utility class to deal with `HTMLTag`s
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

import java.util.Queue;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.List;
import java.util.function.Consumer;

/**
 * utility class for dealing with `HTMLTag`s; this stuff is helpful for testing
 * (especially the fromString method)
 */
public class HTMLTags {
	/**
	 * this is the only place the HTMLLexer, copied from the inside of the
	 * provided HTMLParser class, is used, and it's only used because the
	 * HTMLParser class starts an infinite loop if the input has more
	 * opening than closing tags (???) and we only need to grab one tag....
	 */
	public static HTMLTag fromString(String tag) {
		// alternate implementation, if the HTMLParser class worked:
		// return new HTMLParser(tag).parse().poll();
		// (Queue.poll() returns null for an empty queue so that covers
		// error handling)
		HTMLLexer lexer = new HTMLLexer(tag);
		if(lexer.hasNext()) {
			return lexer.next();
		} else {
			return null;
		}
	}

	/**
	 * maps an array of strings to an array of tags; useful for testing
	 */
	public static HTMLTag[] stringsToTags(String[] tags) {
		return Arrays.stream(tags)
			.map(HTMLTags::fromString)
			.toArray(HTMLTag[]::new);
	}

	/**
	 * takes a string of html, returns an array of tags, applies the
	 * lambda after parsing
	 *
	 * @param sideEffects post-parsing-pre-return side-effects; something
	 * like manager -&gt; manager.removeAll(tag)
	 * @return an array of html tags formed from the input document
	 */
	static HTMLTag[] htmlToTags(
			String html, Consumer<HTMLManager> sideEffects) {
		HTMLManager manager = new HTMLManager(html);
		sideEffects.accept(manager);
		return manager.getTags().toArray(new HTMLTag[] {});
	}

	/**
	 * @param html an html document as a string
	 * @return an array of tags, parsed from the html document
	 */
	static HTMLTag[] htmlToTags(String html) {
		return htmlToTags(html, m -> {});
	}

}
