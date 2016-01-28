/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package phishDetect;

import data.Url;
import data.Data;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author vishwas
 */
public class GiveFinalRating {
    private Url url;
    private final static Logger logger = Logger.getLogger(GiveFinalRating.class);
    public GiveFinalRating(String _url) {
        url = new Url(_url);
    }

    public static void main(String args[]) {
        int ans = 0;
        //for(int i = 0; i < l; i +=100)
        {
            double x = new GiveFinalRating("http://academics.mnnit.ac.in").run();
            //System.out.println("http://acade");
            ans += x;
        }
        System.out.println(ans);
    }

    public int run() {

        double match = 0;

        try {
            logger.debug("\n");
            //logger.debug("canonicalHash:" + url.getCanonical_hash());

            URL_Utilities url_utilities = new URL_Utilities(url);
            HTML_Utilities html_utilities = new HTML_Utilities(url);
            Web_Utilities web_utilities = new Web_Utilities(url);
            int nearHashBasedDuplicate = premilinary_check();
            match += nearHashBasedDuplicate;
            if (match == 1) {
                return 10;
            }
            //logger.debug("nearHashBasedDuplicate:" + match);
            //if (match >= 80)
            //    return match;
            //match = match / 5;    // 80 -> 8;
            double url_utilities_score = url_utilities.checkURL();
            match += url_utilities_score;

            System.out.println(url + "\n" + match);
            //if (match > 100)
            //    return match;
            //match = match / 3;   // 100->33
            if (url.getUrl() != null) {
                if (url.getPage() != null) {
                    //match = match / 3;
                    System.out.println(url + "\n got doc");
                    double html_utilities_score = 0;
                    try {
                        html_utilities_score = html_utilities.checkHTML(new URL(url.toString()), url.getPage());
                        match += html_utilities_score;
                        //logger.debug("htmlUtilities" + match);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    System.out.println(url + "\n" + html_utilities_score);
                    double web_utilities_score = web_utilities.checkWEB(url.toString());
                    match += web_utilities_score;
                    logger.debug(url.getUrl() + "~" + url.getCanonical_hash() + "~" + nearHashBasedDuplicate + "~" + html_utilities_score + "~" + web_utilities_score + "~" + match);
                    //logger.debug("webUtilities" + match);
                    System.out.println(url + "\n" + web_utilities_score);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println (match);
        
        //match /= 2;
        return (int)match;
    }

    private int premilinary_check() {

        return (hasDuplicateHash())?1:0;
    }
    private boolean hasDuplicateHash () {
        return url.getPage_hash() != null && Data.getInstance().getPhish_list().hasPage(url.getPage_hash());
    }
    /*
    public int percentageMatch()// to find percentage match with given list of URL
    {
        String _url = url.getUrl();
        int l = _url.length(), high = Data.phish_list.size() - 1, low = 0, mid, i, flag, ans = 0, maxi = 0;
        while (high >= low) {
            flag = 0;
            mid = (high + low) / 2;
            //System.out.println(phish_list[mid]+" "+high+" "+" "+mid +" "+low);
            int len = Data.phish_list.get(mid).length();
            for (i = 0; i < Math.min(l, len); i++) {
                if (_url.charAt(i) != Data.phish_list.get(mid).charAt(i)) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                if (i > maxi) {
                    ans = mid;
                    maxi = i;
                }
                if (_url.charAt(i) > Data.phish_list.get(mid).charAt(i))
                    low = mid + 1;
                else
                    high = mid - 1;
            } else {
                if (i > maxi) {
                    ans = mid;
                    maxi = i;
                }
                if (i == len)
                    low = mid + 1;
                else
                    high = mid - 1;
            }
        }
        //System.out.println(phish_list[ans]+"  "+_url +maxi+" "+Math.min(l, phish_list[ans].length()));
        return (int) (maxi * 100.0 / Data.phish_list.get(ans).length());
    }
    */
}
