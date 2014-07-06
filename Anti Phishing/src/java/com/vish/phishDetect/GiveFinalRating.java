/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vish.phishDetect;

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
        for(int i = 0; i < l; i ++)
        {
            int x = new GiveFinalRating().run(phish_list[i]);
            System.out.println(phish_list[i]+"\n" + x);
            ans += x;
        }
        System.out.println("answer "+ ans/l);
    }
            
    public int run(String _url)
    {
        
        int match = 0;//Utilities.perMatch(_url);
        if(match >= 80)
            return match;   // if it matches to prev stored urls
        match = match/5;    // 80 -> 8;
        match += URL_Utilities.checkURL(_url);
        if(match > 105)
            return 90;
        match = match/3;   // 100->33
        Parse p = new Parse(_url);
        p.run();
        if(p.getDoc() != null)
        {
            
        }
        match = match/5;
        
        return match;
        
        
        
        /*
        
        
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
