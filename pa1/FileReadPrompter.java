import java.io.File;

class FileReadPrompter extends FilePrompter {
	private boolean check(String filename) {
		return new File(filename).exists();
	}
}
