/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package phishDetect;

import data.Url;
import data.Data;
import util.Parse;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author vishwas
 */
public class GiveFinalRating {
    private Url url;

    public GiveFinalRating(String _url) {
        url = new Url(_url);
    }

    public static void main(String args[]) {
        int ans = 0;
        int l = Data.phish_list.size();
        //for(int i = 0; i < l; i +=100)
        {
            int x = new GiveFinalRating("http://academics.mnnit.ac.in").run();
            //System.out.println("http://acade");
            ans += x;
        }
        System.out.println(ans);
    }

    public int run() {
        //int match = URL_Utilities.perMatch(_url);
        int match = 0;
        System.out.println(url + "\n" + match);
        if (match >= 80)
            return match;
        match = match / 5;    // 80 -> 8;
        match += URL_Utilities.checkURL(url.toString());
        System.out.println(url + "\n" + match);
        if (match > 105)
            return match;
        match = match / 3;   // 100->33


        if (url != null) {
            if (url.getPage() != null) {
                match = match / 3;
                System.out.println(url + "\n got doc");
                try {
                    match += new HTML_Utilities().checkHTML(new URL (url.toString()), url.getPage());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                System.out.println(url + "\n" + match);
                match += new Web_Utilities().checkWEB(url.toString());
                System.out.println(url + "\n" + match);
            }
        }
        match /= 2;
        return match;
    }

}
