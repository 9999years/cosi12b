import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class HTMLTagsTest {
	void fromStringTest(String expectedElement, HTMLTagType expectedType,
		String str) {
		assertEquals(new HTMLTag(expectedElement, expectedType),
			HTMLTags.fromString(str));
	}

	@Test
	void fromStringTest() {
		fromStringTest("br", HTMLTagType.SELF_CLOSING, "<br/>");
		fromStringTest("html", HTMLTagType.OPENING, "<html></html>");
		fromStringTest("i", HTMLTagType.CLOSING, "</i>");
	}
}
