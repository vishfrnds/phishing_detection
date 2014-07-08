/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vish.phishDetect;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 *
 * @author vishwas
 */
public class GiveFinalRating {
    public static void main(String args[])
    {
        Arrays.sort(URL_Utilities.phish_list);
        String[] phish_list = com.vish.phishDetect.Data.phish_list;
        int ans = 0;
        int l = phish_list.length;
        //for(int i = 0; i < l; i +=100)
        {
            int x = new GiveFinalRating().run("http://academics.mnnit.ac.in/index.jsp");
            //System.out.println("http://acade");
            ans += x;
        }
       System.out.println(ans);
    }
            
    public int run(String _url)
    {
        
        _url = _url.replaceFirst("://www.", "://"); // remove www. from domain name
        int match = URL_Utilities.perMatch(_url);
        if(match >= 80)
            return match;
        match = match / 5;    // 80 -> 8;
        match += URL_Utilities.checkURL(_url);
        if(match > 105)
            return match;           
        match = match/3;   // 100->33
        Parse p = new Parse(_url);
        p.run();
        if(p.getDoc() != null)
        {
           // System.out.println(_url+"\n"+p.getRaw());
            match += new HTML_Utilities().checkHTML(_url, p.getDoc());
           
            
        }
        return match;
        /*
        URL url;
        String domainName ="";
        try
        {
            url = new URL(_url);
            domainName = url.getHost();
            Web_Utilities.domainAge(domainName);
        }
        catch(MalformedURLException e)
        {
            url = null;
        }
        return 0;
         
        
           // if it matches to prev stored urls
            
        match = match / 5;    // 80 -> 8;
        match += URL_Utilities.checkURL(_url);
       // if(match > 105)
            return match;
           
        match = match/3;   // 100->33
        Parse p = new Parse(_url);
        p.run();
        if(p.getDoc() != null)
        {
            
        }
        match = match/5;
        
        return match;
        
        
        
        
        
        
            return 404;
        //System.out.println(p.getDoc());
        //
            match = match / 5 + URL_Utilities.checkURL(_url);
            return match;
        }
        //else
        //    return match/2;
                */
    }
    
}
