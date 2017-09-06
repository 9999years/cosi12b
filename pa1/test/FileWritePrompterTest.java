import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class FileWritePrompterTest {
	@Test
	public void fnametest() {
		assertFalse("0-length filenames are rejected", new FileWritePrompter().check(""));
		assertFalse("checking regular filename (out.txt) is writeable", new FileWritePrompter().check("out.txt"));
		assertFalse("checking bad filename fails (system32)", new FileWritePrompter().check("C:\\WINDOWS\\system32"));
	}
}
