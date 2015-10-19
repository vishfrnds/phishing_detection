/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package phishDetect;

import precompute.Data;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author vishwas
 */
public class GiveFinalRating {
    public static void main(String args[]) {
        int ans = 0;
        int l = Data.phish_list.size();
        //for(int i = 0; i < l; i +=100)
        {
            int x = new GiveFinalRating().run("http://academics.mnnit.ac.in");
            //System.out.println("http://acade");
            ans += x;
        }
        System.out.println(ans);
    }

    public int run(String _url) {

        _url = _url.replaceFirst("://www.", "://"); // remove www. from domain name      
        int match = URL_Utilities.perMatch(_url);
        System.out.println(_url + "\n" + match);
        if (match >= 80)
            return match;
        match = match / 5;    // 80 -> 8;
        match += URL_Utilities.checkURL(_url);
        System.out.println(_url + "\n" + match);
        if (match > 105)
            return match;
        match = match / 3;   // 100->33

        URL url = null;
        try {
            url = new URL(_url);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();

        }
        if (url != null) {
            Parse p = new Parse(_url);
            p.run();
            System.out.println(_url + "\n NO doc");
            if (p.getDoc() != null) {
                match = match / 3;
                System.out.println(_url + "\n got doc");
                match += new HTML_Utilities().checkHTML(url, p.getDoc());
                System.out.println(_url + "\n" + match);
                match += new Web_Utilities().checkWEB(_url);
                System.out.println(_url + "\n" + match);
            }
        }
        match /= 2;
        return match;
    }

}
