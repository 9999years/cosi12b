import java.util.function.Consumer;
import java.util.Arrays;

public class HTMLManagerTest {
	public static SimpleTest tester = new SimpleTest();

	/**
	 * takes a string of html, returns an array of tags, applies the
	 * lambda after parsing
	 *
	 * @param sideEffects post-parsing-pre-return side-effects; something like
	 * manager -&gt; manager.removeAll(tag)
	 */
	static HTMLTag[] htmlToTags(
			String html, Consumer<HTMLManager> sideEffects) {
		HTMLManager manager = new HTMLManager(html);
		sideEffects.accept(manager);
		return manager.getTags().toArray(new HTMLTag[] {});
	}

	/**
	 * takes a string of html, returns an array of tags
	 */
	static HTMLTag[] htmlToTags(String html) {
		return htmlToTags(html, m -> {});
	}

	static void expectedTest(String html, String... expected) {
		HTMLTag[] expectedRich = HTMLTags.stringsToTags(expected);
		tester.richAssertArrayEquals(expectedRich, htmlToTags(html));
	}

	static void expectedTest() {
		expectedTest("<b>whatever</b>", "<b>", "</b>");
		expectedTest("<b><i></b></i>", "<b>", "<i>", "</b>", "</i>");
	}

	static void removeAllTest(String expected, String html, String remove) {
		HTMLTag removeTag = HTMLTags.fromString(remove);
		tester.richAssertArrayEquals(htmlToTags(expected),
			htmlToTags(html, m -> m.removeAll(removeTag)));
	}

	static void removeAllTest() {
		removeAllTest("<a><html></html></a>",
			"<br><a><html><br /><br/></html></a>",
			"<br>");
		removeAllTest("<a></html></b>",
			"<b><a></html></b>",
			"<b>");
		removeAllTest("<b><a></html></b>",
			"<b><a></html></b>",
			"<html>");
		removeAllTest("<b><a></b>",
			"<b><a></html></b>",
			"</html>");
	}

	static void fixHTMLTest(String expected, String original) {
		HTMLTag[] expectedA = htmlToTags(expected);
		HTMLTag[] fixedA = htmlToTags(original, m -> m.fixHTML());
		tester.richAssertArrayEquals(expectedA, fixedA);
	}

	static void fixHTMLTest() {
		// test1
		fixHTMLTest("<b><i><br/></i></b>","<b><i><br/></b></i>");
		// test2
		fixHTMLTest("<a><a><a></a></a></a>","<a><a><a></a>");
		// test3
		fixHTMLTest("<br/>","<br/></p></p>");
		// test4
		fixHTMLTest("<div><div><ul><li></li>"
			+"<li></li><li></li></ul></div></div>",
			"<div><div><ul><li></li>"
			+"<li></li><li></ul></div>");

		// test5
		fixHTMLTest("<div><h1></h1><div><img/><p><br/><br/><br/></p>"
			+ "</div></div>",
			"<div><h1></h1><div><img/><p><br/><br/><br/></div>"
			+ "</div></table>");

		// test6
		fixHTMLTest("<ul><li></li><li><div></div></li><ul><span><a>"
			+ "<test></test><ul><li></li><span></span><li></li>"
			+ "</ul></a></span><a><a></a></a></ul></ul>",
			"<ul><li></li><li><div></div></li><ul><span><a><test>"
			+ "</test><ul><li></li><span></p></span><li></li>"
			+ "</span><a><a></span></a></a></span>");

		// test7
		fixHTMLTest("<div> <h1>CSE 007</h1> </div> <div id="
			+ "\"container\"> <div id=\"header\"> <div class="
			+ "\"titles\"> <hr /> <h3>Spring 2015</h3> <h4>James "
			+ "(<span>jamesbond</span>) Office: CSE 007</h4> </div> "
			+ "</div> <div class=\"sidebar\"> <h2>Course Info</h2> "
			+ "<ul> <li><a href=\"lectures.shtml#today\"> Lectures</"
			+ "a></li> <li><a href=\"homework.shtml\"> Homework</a> "
			+ "<li><a href=\"sections.shtml\"> Sections</a></li> "
			+ "<li><a href=\"labs.shtml\"> Labs</a></li> <li><a href"
			+ "=\"exams.shtml\"> Exams</a></li> <li><a href=\"textbook."
			+ "shtml\"> Textbook</a></li> <li><a href="
			+ "\"working_at_home.shtml\"> Work at Home</a></li><!--"
			+ "<li><a href=\"http://staff.washington.edu/oterod/help"
			+ "/eclipse/index.html\"> Eclipse Tutorial</a></li>--> "
			+ "<li><a href=\"jgrasp.shtml\"> jGRASP Tutorial</a></"
			+ "li></li> </ul> <h2>Check Scores</h2> <ul> <li><a href"
			+ "=\"gradeit.shtml\"><img src=\"images/gradeit_icon."
			+ "png\" class=\"sidebaricon\" alt=\"grade-it\" /> Grade"
			+ " Sheets</a></li> <li><a href=\"gradenator.shtml\">"
			+ "<img src=\"images/calculator_icon.png\" class="
			+ "\"sidebaricon\" alt=\"gradenator\" /> Grade-a-nator</"
			+ "a></li> <li><a href=\"myuw.shtml\"><img src=\"images/"
			+ "myuw_icon.png\" class=\"sidebaricon\" alt=\"myuw\" />"
			+ "MyUW</a></li> </ul> </div> <div class=\"centerpane\">"
			+ "<div class=\"excitingnews\"> Please <a href=\"staff."
			+ "shtml\">e-mail us</a> with questions or feedback.<div"
			+ " id=\"announcementsarea\"><h3>Announcements</h3><ul "
			+ "class=\"announcements\"> <li>Homework <a href="
			+ "\"homework.shtml\">posted</a>.</li> <li>Welcome to "
			+ "CSE 007!</li> </ul> </div> </div> </div> </div>",

			"<div><h1>CSE 007</h1></div><div id=\"container\"><div "
			+ "id=\"header\"><div class=\"titles\"><hr /><h3>Spring "
			+ "2015</h3><h4>James (<span>jamesbond</span>) Office: "
			+ "CSE 007</div></div><div class=\"sidebar\"><h2>Course "
			+ "Info</h2><ul><li><a href=\"lectures."
			+ "shtml#today\">Lectures</a></li><li><a href=\"homework"
			+ ".shtml\">Homework</a><li><a href=\"sections."
			+ "shtml\">Sections</a></li><li><a href=\"labs."
			+ "shtml\">Labs</a></li><li><a href=\"exams."
			+ "shtml\">Exams</li><li><a href=\"textbook."
			+ "shtml\">Textbook</a></li><li><a href="
			+ "\"working_at_home.shtml\">Work at Home</a></li><!--"
			+ "<li><a href=\"http://staff.washington.edu/oterod/help"
			+ "/eclipse/index.html\"> Eclipse Tutorial</a></li>-->"
			+ "<li><a href=\"jgrasp.shtml\">jGRASP Tutorial</a></"
			+ "ul><h2>Check Scores</h2><ul><li><a href=\"gradeit."
			+ "shtml\"><img src=\"images/gradeit_icon.png\" class="
			+ "\"sidebaricon\" alt=\"grade-it\" />Grade Sheets</a></"
			+ "li><li><a href=\"gradenator.shtml\"><img src=\"images"
			+ "/calculator_icon.png\" class=\"sidebaricon\" alt="
			+ "\"gradenator\" />Grade-a-nator</a></li><li><a href="
			+ "\"myuw.shtml\"><img src=\"images/myuw_icon.png\" "
			+ "class=\"sidebaricon\" alt=\"myuw\" />MyUW</ul></b></"
			+ "div><div class=\"centerpane\"><div class="
			+ "\"excitingnews\">Please<a href=\"staff.shtml\">e-mail"
			+ " us</a>with questions or feedback.<div id="
			+ "\"announcementsarea\"><h3>Announcements</h3><ul class"
			+ "=\"announcements\"><li>Homework<a href=\"homework."
			+ "shtml\">posted</a>.</li><li>Welcome to CSE 007!</li>"
			+ "</ul></div></div>");
	}

	static void fromStringTest(String expectedElement, HTMLTagType expectedType,
		String str) {
		tester.richAssertEquals(new HTMLTag(expectedElement, expectedType),
			HTMLTags.fromString(str));
	}

	static void fromStringTest() {
		fromStringTest("br", HTMLTagType.SELF_CLOSING, "<br/>");
		fromStringTest("html", HTMLTagType.OPENING, "<html></html>");
		fromStringTest("i", HTMLTagType.CLOSING, "</i>");
	}

	public static void main(String[] args) {
		System.out.println("Testing! Printing a dot for each success.");
		expectedTest();
		removeAllTest();
		fixHTMLTest();
		fromStringTest();
		System.out.println();
		System.out.println(tester.summary());
	}
}
