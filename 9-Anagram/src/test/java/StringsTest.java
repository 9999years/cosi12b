import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class StringsTest {
	void totalLengthTest(int expected, String... strings) {
		assertEquals(expected, Strings.totalLength(strings));
	}

	@Test
	void totalLengthTest() {
		totalLengthTest(0, "", "", "");
		totalLengthTest(0, "");
		totalLengthTest(1, "a");
		totalLengthTest(2, "ab");
		totalLengthTest(2, "a", "b");
		totalLengthTest(17, "barbara", "streissand");
	}
}
