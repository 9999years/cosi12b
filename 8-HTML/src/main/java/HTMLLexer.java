/**
 * HTMLLexer extracted from HTMLParser
 *
 * @author NOT Rebecca Turner
 */

import java.util.Iterator;

/**
 * HTMLLexer to parse a string of tags into HTMLTags. Iterates
 * over the given source String
 *
 * taken from the inside of the HTMLParser class
 *
 * presumably by dr. papaemannouil or some ta of years passed
 */
public class HTMLLexer implements Iterator<HTMLTag> {
	private String page;
	private int index;
	private boolean inString;

	/**
	 * Creates an HTMLLexer based off the given source String
	 */
	public HTMLLexer(String page) {
		this.page = page;
		this.index = 0;
		this.inString = false;
	}

	/**
	 * Returns the next HTMLTag in the source String
	 */
	public HTMLTag next() {
		int begin = this.index;

		/* If we've found an HTML comment... (that isn't a DOCTYPE...) */
		if (this.page.substring(begin + 1).startsWith("!--")) {
			this.index = this.page.indexOf("-->", this.index);
			begin = begin + 2;
			int end = this.index - 1;
			String element = this.page.substring(begin, end + 1);
			return new HTMLTag(element, HTMLTagType.SELF_CLOSING);
		}
		movePastString('>');
		int end = this.index;

		String contents = "";

		movePastString('<');

		if (end + 1 < this.index) {
			contents = this.page.substring(end + 1, this.index);
		}

		if (this.page.charAt(begin + 1) == '/') {
			String element = this.page.substring(begin + 2, end);
			return new HTMLTag(element, HTMLTagType.CLOSING, contents);
		}
		else if (this.page.charAt(end - 1) == '/') {
			String element = this.page.substring(begin + 1, end - 1);
			return new HTMLTag(element, HTMLTagType.SELF_CLOSING, contents);
		}
		else {
			/* If we've found a script element... */
			if (this.page.substring(begin + 1, end).startsWith("script")) {
				this.index = this.page.indexOf("</script>", begin + 1);
			}

			this.index = this.page.indexOf("<", this.index);
			contents = "";

			if (this.index > -1) {
				contents = this.page.substring(end + 1, this.index);
			}
			String elements = this.page.substring(begin + 1, end);
			return new HTMLTag(elements, HTMLTagType.OPENING, contents);
		}
	}

	/**
	 * Returns true if there is another HTMLTag in the source String
	 * returns false otherwise.
	 */
	public boolean hasNext() {
		int potentialNextIndex = this.page.indexOf("<", this.index);
		if (potentialNextIndex != -1) {
			this.index = potentialNextIndex;
			return true;
		}
		return false;
	}

	/**
	 * Moves the current index in the source String up to the next
	 * needle not contained in the middle of a String
	 */
	private boolean movePastString(char needle) {
		int potentialNextIndex = this.page.indexOf(needle, this.index);

		if (potentialNextIndex == -1) {
			return false;
		}

		int nextSingleQuote = this.page.indexOf("'", this.index);
		if (nextSingleQuote != -1 && nextSingleQuote < potentialNextIndex) {
			this.inString = !this.inString;
			this.index = nextSingleQuote + 1;
			return movePastString(needle);
		}

		int nextDoubleQuote = this.page.indexOf("\"", this.index);
		if (nextDoubleQuote != -1 && nextDoubleQuote < potentialNextIndex) {
			this.inString = !this.inString;
			this.index = nextDoubleQuote + 1;
			return movePastString(needle);
		}

		if (this.inString) {
			this.index++;
			return movePastString(needle);
		}

		this.index = potentialNextIndex;
		return true;
	}
}
