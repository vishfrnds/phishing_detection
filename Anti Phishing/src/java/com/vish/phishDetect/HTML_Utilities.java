/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vish.phishDetect;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author vishwas
 */
public class HTML_Utilities {
    ArrayList<Element> loginForms = new ArrayList<Element>();
    
    int checkHTML(URL url, Document doc)
    {
        int ans = 0;
        int multplier = 1;
        if(hasLoginForm(doc))
        {
            multplier = 5;
            ans += 20;            
        }
        if(badActionField(url))
        {
                ans += 5 * multplier;
        }
        ans += nonMatchingUrls(url, doc) * multplier/3;
        return ans;
    }
    boolean hasLoginForm(Document doc)
    {
        Elements foms = doc.select("form");
        System.out.println("\nTEXT : " + foms.size());
        for (Element fom : foms) 
        {             
            
            int ctr = 0;
            // get the value from href attribute
            //System.out.println("\nTEXT : " + fom.text());
            Elements inputs = fom.getElementsByTag("input");
            
            for(Element input : inputs)
            {
                if(input.hasAttr("type"))
                {
                    if(input.attr("type").compareToIgnoreCase("password") == 0)
                    {
                        loginForms.add(fom);
                        ctr++;
                    }
                }
            }
            if(ctr >= 1 && ctr != 2)
                return true;
        }
        return false;
    }
    
    boolean badActionField(URL url)
    {
        System.out.println("jkj"+loginForms.size());
        for (Element fom : loginForms) 
        {   
            String action = fom.attr("action");
            if(action == null)
                return true;
            try
            {
                URL _action = new URL(action);
                if(!(_action.getHost().equalsIgnoreCase(url.getHost())))
                    return true;
            }
            catch(MalformedURLException e)
            {
                return true;
            }
        }  
        return false;
    }
    
    int nonMatchingUrls(URL url, Document doc)
    {
        Elements links = doc.select("a[href]");
        int invalid = 0, total = 0, crossdomain = 0;
        for (Element link : links) 
        {
            total++;
            String attr = link.attr("href");
            System.out.println("\nlink : " + link.attr("href"));
            System.out.println("text : " + link.text()); 
            
            if(attr == null || attr.equalsIgnoreCase("") || attr.charAt(0) == '#' || attr.equalsIgnoreCase("index.html"))
            {
                invalid++;   
            }
            else if(attr.charAt(0) != '/' )                
            {
                attr = attr.replaceFirst("://www.", "://");
                try 
                {
                    URL _attr = new URL(attr);
                    if(!(_attr.getHost().equalsIgnoreCase(url.getHost())))
                        crossdomain++;
                        
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }                
            }
            
        }
        return (crossdomain+2*invalid)*100/total;
    }
}
