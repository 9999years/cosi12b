import java.util.Queue;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.List;

public class HTMLTags {
	public static HTMLTag fromString(String tag) {
		HTMLLexer lexer = new HTMLLexer(tag);
		if(lexer.hasNext()) {
			return lexer.next();
		} else {
			return null;
		}
	}

	public static HTMLTag[] stringsToTags(String[] tags) {
		return Arrays.stream(tags)
			.map(HTMLTags::fromString)
			.toArray(HTMLTag[]::new);
	}

	public static boolean matchesAnyTag(HTMLTag potentialMatch,
		List<HTMLTag> tags) {
		for(HTMLTag tag : tags) {
			if(tag.matches(potentialMatch)) {
				return true;
			}
		}
		return false;
	}
}
