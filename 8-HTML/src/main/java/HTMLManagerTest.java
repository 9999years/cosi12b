/**
 * class for testing the HTMLManager
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

import java.util.Arrays;

public class HTMLManagerTest {
	public static SimpleTest tester = new SimpleTest();

	static void expectedTest(String html, String... expected) {
		HTMLTag[] expectedRich = HTMLTags.stringsToTags(expected);
		tester.richAssertArrayEquals(
			expectedRich, HTMLTags.htmlToTags(html));
	}

	static void expectedTest() {
		expectedTest("<b>whatever</b>", "<b>", "</b>");
		expectedTest("<b><i></b></i>", "<b>", "<i>", "</b>", "</i>");
	}

	static void removeAllTest(String expected, String html, String remove) {
		HTMLTag removeTag = HTMLTags.fromString(remove);
		tester.richAssertArrayEquals(
			HTMLTags.htmlToTags(expected),
			HTMLTags.htmlToTags(html, m -> m.removeAll(removeTag)));
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
		HTMLTag[] expectedA = HTMLTags.htmlToTags(expected);
		HTMLTag[] fixedA = HTMLTags.htmlToTags(
			original, m -> m.fixHTML());
		if(!tester.richAssertArrayEquals(expectedA, fixedA)) {
			tester.err("Failed fixing HTML; original HTML: "
				+ original);
		}
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

		System.out.println("\nJust to demonstrate: Here's what a test failure looks like (a modified .fixHTML() test):");
		fixHTMLTest("<b><i><br/><i></b>","<b><i><br/></b></i>");
	}
}
