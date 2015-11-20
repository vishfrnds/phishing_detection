/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package phishDetect;

import data.Url;
import org.apache.log4j.Logger;
import util.hash.JenkinsHash;
import org.apache.commons.net.whois.WhoisClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vishwas
 */
public class Web_Utilities {
    private final static Logger logger = Logger.getLogger(Web_Utilities.class);
    public Web_Utilities(Url url) {
        //this.url = url;
    }

    public static int getPageRank(URL Url) {
        String result = "";
        String _url = Url.getHost();
        System.out.println(_url);
        try {
            /*
            Authenticator.setDefault(
                    new Authenticator() {
                        public PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    "edcguest", "edcguest".toCharArray());
                        }
                    }
            );
            System.setProperty("http.proxyHost", "172.31.102.14");
            System.setProperty("http.proxyPort", "3128");
            */
        JenkinsHash jenkinsHash = new JenkinsHash();
        long hash = jenkinsHash.hash(("info:" + _url).getBytes());

        //Append a 6 in front of the hashing value.
        String url = "http://toolbarqueries.google.com/tbr?client=navclient-auto&hl=en&"
                + "ch=6" + hash + "&ie=UTF-8&oe=UTF-8&features=Rank&q=info:" + java.net.URLEncoder.encode(_url);

        System.out.println("Sending request to : " + url);

            URLConnection conn = new URL(url).openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));

            String input;
            while ((input = br.readLine()) != null) {

                // Wh                                                                                                                                                                                                                                                                                           at Google returned? Example : Rank_1:1:9, PR = 9
                System.out.println(input);

                result = input.substring(input.lastIndexOf(":") + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }
        System.out.println("pr " + result);
        if ("".equals(result)) {
            //StringTokenizer st = new StringTokenizer(_url);

            return -1;
        } else {
            return Integer.valueOf(result);
        }

    }

    //not helpful
    public static int domainAge(String domainName) {
        StringBuilder whoisResult = new StringBuilder("");

        WhoisClient crunchifyWhois = new WhoisClient();
        try {
            // The WhoisClient class implements the client side of the Internet
            // Whois Protocol defined in RFC 954. To query a host you create a
            // WhoisClient instance, connect to the host, query the host, and
            // finally disconnect from the host. If the whois service you want
            // to query is on a non-standard port, connect to the host at that
            // port.
            crunchifyWhois.connect(WhoisClient.DEFAULT_HOST);
            String whoisData = crunchifyWhois.query("=" + domainName);
            whoisResult.append(whoisData);
            crunchifyWhois.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String patternString1 = "No\\smatch\\sfor";
        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(whoisResult);
        if (!matcher.find()) {
            System.out.println(domainName + "\n" + whoisResult);
        }

        return 0;//whoisResult.toString();
    }

    double checkWEB(String _url) throws MalformedURLException {
        double ans = 0;
        int pr = getPageRank(new URL(_url));
        ans = (2 - pr) * 2.3122;

        System.out.println ("PageRank" + pr);
        /*
        if (pr >= 2)
            ans -= pr * 5;
        else
            ans += -5 * pr;

        */
        logger.debug(pr + "~");
        return ans;
    }

}
