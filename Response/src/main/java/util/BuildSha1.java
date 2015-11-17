
package util;

import org.apache.log4j.Logger;
import util.hash.SHA1Hash;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuildSha1 {
    private final static Logger logger = Logger.getLogger(BuildSha1.class);
    private final String _url;
    private String doc;

    public BuildSha1(String url) {
        this._url = url;
    }

    public static void main(String args[]) {
        new BuildSha1("http://www.academics.mnnit.ac.in").generate();
    }

    public String generate() {
        try {


            Parse p = new Parse(_url);
            p.run();
            doc = p.getDoc().toString();
            logger.debug(_url +"\n" + doc);
            clean();
            logger.debug("cleaned " + doc);
            return (SHA1Hash.toSHA1(doc));
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    private void clean() {
        // System.out.println(doc);
        doc = doc.replaceAll(" ", "");
        doc = doc.replaceAll("\n", "");
        doc = doc.replaceAll("\r", "");
        Pattern p = Pattern.compile("=\"[^\"]*");
        // get a matcher object
        Matcher m = p.matcher(doc);
        doc = m.replaceAll("=\"\"");
    }


}
