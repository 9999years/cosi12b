/**
 * Parses Keirsey (KTS) personality data from files and writing output to a
 * file
 * @author Rebecca Turner
 * @version 0.0.1
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */
class PersonalityTest {
	public static void main(String[] args) {
		byte[] dat = KeirseyLineParser.parse(
			"BABA-AABAAAAAAABAAAABBAAAAAABAAAABABAABAAABABABAABAAAAAABAAAAAABAAAAAA"
		);
		for(int i = 0; i < dat.length; i++) {
			System.out.println(dat[i]);
		}
	}
}
