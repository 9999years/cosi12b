public class HTMLParserTest {
	public static void printParsed(String... documents) {
		for(String page : documents) {
			System.out.println("Attempting to parse `" + page + "`");
			try {
				System.out.println("Parsed: "
					+ new HTMLParser(page).parse());
			} catch(Exception e) {
				System.err.println("Error: " + e.toString());
			}
		}
	}

	public static void main(String[] args) {
		printParsed(
			// PARSES CORRECTLY:
			// correct html
			"<html></html>",
			"<!doctype html><html></html>",
			// more closing tags than opening tags
			"</i></b>",
			// unmatched tags
			"<html></b>",
			// mismatched closing / opening tags but balanced count
			"<html><i><b></i></b></html>",

			// StringIndexOutOfBoundsException
			"<",

			// NEVER TERMINATES:
			// balanced html! (self-closing tags)
			"<img>",
			"<!doctype html>",
			// more opening than closing tags
			// this one isn't really a tag but the parser doesn't
			// complain
			"<>",
			"<i>",
			"<i><b></b>");
	}
}
