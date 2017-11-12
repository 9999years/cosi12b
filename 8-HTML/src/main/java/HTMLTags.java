public class HTMLTags {
	public static HTMLTag fromString(String tag) {
		return new HTMLParser(tag).parse().poll();
	}
}
