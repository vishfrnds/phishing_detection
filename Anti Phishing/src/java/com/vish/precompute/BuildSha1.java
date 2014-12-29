/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vish.precompute;

import com.vish.hash.SHA1Hash;
import com.vish.phishDetect.Parse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities.EscapeMode;

/**
 *
 * @author vishwas
 */
public class BuildSha1 {
    private final String _url;
    String doc;

    public BuildSha1(String url) {
        this._url = url;
    }
    public static void main(String args[])
    {
        new BuildSha1("http://www.academics.mnnit.ac.in").generate();
    }
    public String generate()
    {
        try
        {


            Parse p = new Parse(_url);
            p.run();
            doc = p.getDoc().toString();
            clean();
            return (new SHA1Hash().toSHA1(doc));
        }
        catch(Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    private void clean() 
    {
       // System.out.println(doc);
        doc = doc.replaceAll(" ", "");
        doc = doc.replaceAll("\n", "");
        Pattern p = Pattern.compile("=\"[^\"]*");
       // get a matcher object
       Matcher m = p.matcher(doc); 
       doc = m.replaceAll("=\"\"");
        System.out.println(doc);
    }
    
    
}
