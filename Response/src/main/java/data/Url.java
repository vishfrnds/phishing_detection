package data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import util.BuildSha1;
import util.URLNormalizer;
import util.hash.SHA1Hash;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vishwas on 23/10/15.
 * Copyright (C) 2015  Vishwas Tripathi
 */
public class Url {
    // does not follow all java-bean rules
    // use Gson for serialization
    private String url;
    private String canonical_hash;
    private transient Document page;
    private String page_hash;

    public Url(String url) {
        super();
        System.out.println ("URL" + url);
        this.url = url.replaceAll("\\\\", "").replaceFirst("://www.", "://"); // remove www. from domain name
        canonical_hash = null;
        page = null;
        page_hash = null;
    }

    public String getUrl() {
        return url;
    }

    public String getCanonical_hash() {
        if (canonical_hash == null) {
            try {
                canonical_hash = URLNormalizer.getCanonicalUrlHash(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return canonical_hash;
    }

    public Document getPage() {
        if (page == null) {

            System.setProperty("java.net.useSystemProxies", "true");

            System.setProperty("http.proxyHost", "172.31.100.14");
            System.setProperty("http.proxyPort", "3128");
            System.setProperty("http.proxyUser","edcguest");
            System.setProperty("https.proxyPassword","edcguest");
            System.setProperty("https.proxyHost", "172.31.100.14");
            System.setProperty("https.proxyPort", "3128");
            System.setProperty("https.proxyUser","edcguest");
            System.setProperty("https.proxyPassword","edcguest");
            try {
            Authenticator.setDefault(
                    new Authenticator() {
                        public PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    "edcguest", "edcguest".toCharArray());
                        }
                    }
            );
          //  System.setProperty("http.proxyHost", "172.31.102.14");
          //  System.setProperty("http.proxyPort", "3128");

                //System.out.println(_url)

              //  System.setProperty("java.net.useSystemProxies", "true");
/*
            System.setProperty("http.proxyHost", "172.31.102.14");
            System.setProperty("http.proxyPort", "" + "3128");
            System.setProperty("http.proxyUser","edcguest");
            System.setProperty("http.proxyPassword","edcguest");
*/
                page = Jsoup.connect(url).get();
            } catch (Exception e) {
                page = null;
                e.printStackTrace();
            }
        }
        //System.out.println (page);
        return page;
    }

    public String getPage_hash() {
        if (page_hash == null) {
            try {
                page_hash = SHA1Hash.toSHA1(clean(getPage().toString()));
            }catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return page_hash;
    }
    private String clean(String doc) {
        //
        doc = doc.replaceAll(" ", "");
        doc = doc.replaceAll("\n", "");
        doc = doc.replaceAll("\r", "");
        Pattern p = Pattern.compile("=\"[^\"]*");
        // get a matcher object
        Matcher m = p.matcher(doc);
        doc = m.replaceAll("=\"\"");
        System.out.println(doc);
        return doc;
    }

    public String toString () {
        return url;
    }

}
