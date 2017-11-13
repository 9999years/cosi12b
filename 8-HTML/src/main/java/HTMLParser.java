// COSI12b: HTML Manager
// Instructor-provided code.  You should not modify this file!
// Resource file for HTMLManager.  Put this file in the same directory
// as HTMlManager.java, HTMLTag.java, HTMLTagType.java and HTMLMain.java.

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.Reader;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Parses a File, String, or URL into a List<HTMLTag>
 */
public class HTMLParser {
    public String unparsedPage;

    /**
     * Parses the given input stream from the source with the given name
     */
    private void parseStream(String name, InputStream stream) {
        try {
            /* Read the HTML */
            Reader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            StringBuilder response = new StringBuilder();
            int c = in.read();
            while (c >= 0) {
                response.append((char)c);
                c = in.read();
            }
            this.unparsedPage = response.toString();
        } catch (IOException e) {
            System.err.println("The " + name + " is invalid.");
            System.exit(1);
        }

    }

    /**
     * Creates a parser based off the HTML at the given source URL
     */
    public HTMLParser(URL url) {
        try {
            /* Create the GET request. */
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            parseStream("URL '" + url.toString() + "'", conn.getInputStream());
        } catch (IOException e) {
            System.err.println("The URL " + url.toString() + " is invalid.");
            System.exit(1);
        }
    }

    /**
     * Creates a parser based off the given source File
     */
    public HTMLParser(File file) {
        String filename = file.toString();
        try {
            parseStream("file " + filename  + "'", new FileInputStream(file));
        } catch (FileNotFoundException ee) {
            System.err.println("The file '" + filename + "' is invalid.");
            System.exit(1);
        }

    }

    /**
     * Creates a parser based off the given source String
     */
    public HTMLParser(String str) {
        this.unparsedPage = str;
    }

    /**
     * Parses the source String and returns the List of HTMLTags
     */
    public Queue<HTMLTag> parse() {
        Queue<HTMLTag> parsed = new LinkedList<HTMLTag>();
        HTMLLexer lexer = new HTMLLexer(this.unparsedPage);
        while (lexer.hasNext()) {
            parsed.add(lexer.next());
        }
        return parsed;
    }
}
