/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package phishDetect;

import data.Url;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author vishwas
 */
public class HTML_Utilities {
    private final static Logger logger = Logger.getLogger(HTML_Utilities.class);
    ArrayList<Element> loginForms = new ArrayList<>();
    Url url;
    public HTML_Utilities(Url url) {
        this.url = url;
    }

    double checkHTML(URL url, Document doc) {
        int login_form = 0;
        if (hasLoginForm(doc)) {
            login_form = 1;
        }
        int bad_action_field = 0;
        if (badActionField(url)) {
            bad_action_field = 1;
        }
        int non_matching_url = nonMatchingUrls(url, doc);
        double ans = login_form * 0.4498 + bad_action_field * 0.4418 + non_matching_url * 0.01820;
        System.out.println("html" + ans);
        logger.debug(login_form + "~" + bad_action_field + "~" + non_matching_url + "~");
        return ans;
    }

    boolean hasLoginForm(Document doc) {
        Elements foms = doc.select("form");
        System.out.println("\nTEXT : " + foms.size());
        for (Element fom : foms) {

            int ctr = 0;
            // get the value from href attribute
            //System.out.println("\nTEXT : " + fom.text());
            Elements inputs = fom.getElementsByTag("input");

            for (Element input : inputs) {
                if (input.hasAttr("type")) {
                    if (input.attr("type").compareToIgnoreCase("password") == 0) {
                        loginForms.add(fom);
                        ctr++;
                    }
                }
            }
            if (ctr >= 1 && ctr != 2)
                return true;
        }
        return false;
    }

    boolean badActionField(URL url) {
        System.out.println("jkj" + loginForms.size());
        for (Element fom : loginForms) {
            String action = fom.attr("action");
            if (action == null)
                return true;
            try {
                URL _action = new URL(action);
                if (!(_action.getHost().equalsIgnoreCase(url.getHost())))
                    return true;
            } catch (MalformedURLException e) {
                return true;
            }
        }
        return false;
    }

    int nonMatchingUrls(URL url, Document doc) {
        Elements links = doc.select("a[href]");
        int invalid = 0, total = 0, crossdomain = 0;
        for (Element link : links) {
            total++;
            String attr = link.attr("href");
            System.out.println("\nlink : " + link.attr("href"));
            System.out.println("text : " + link.text());

            if (attr == null || attr.equalsIgnoreCase("") || attr.charAt(0) == '#' || attr.equalsIgnoreCase("index.html")) {
                invalid++;
            } else if (attr.charAt(0) != '/') {
                attr = attr.replaceFirst("://www.", "://");
                try {
                    URL _attr = new URL(attr);
                    if (!(_attr.getHost().equalsIgnoreCase(url.getHost())))
                        crossdomain++;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return crossdomain + invalid;
    }
}
