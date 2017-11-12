import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HTMLManagerTest {
	void expectedTest(String html, String[] expected) {
		HTMLManager page = new HTMLManager(html);
		System.out.println("hello");
		HTMLTag[] expectedRich = Arrays.stream(expected)
			.map(HTMLTags::fromString)
			.peek(e -> System.out.println("mapped: " + e))
			.collect(Collectors.toList())
			.toArray(new HTMLTag[] {});
		System.out.println("Goodbye");
		assertEquals(expectedRich,
			page.getTags().toArray(new HTMLTag[] {}));
	}

	@Test
	void expectedTest() {
		expectedTest("<b>whateve</b>", new String[] {
			"<b>", "</b>"
		});
		expectedTest("<b><i></b></i>", new String[] {
			"<b>", "<i>", "</b>", "</i>"
		});
	}
}
