/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vish.phishDetect;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author vishwas
 */
public class HTML_Utilities {
    ArrayList<Element> loginForms = new ArrayList<Element>();
    URL url;
    int checkHTML(String _url, Document doc)
    {
        int ans = 0;
        
        if(hasLoginForm(doc))
        {
            ans += 10;
            if(badActionField())
            {
                ans += 50;
            }
            ans += NonMatchingUrls(doc);
            
        }
        return ans;
    }
    boolean hasLoginForm(Document doc)
    {
        Elements foms = doc.select("form");
        //System.out.println("\nTEXT : " + foms.size());
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
    
    boolean badActionField()
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
    
    int NonMatchingUrls(Document doc)
    {
        Elements links = doc.select("a[href]");
        for (Element link : links) 
        {
            // get the value from href attribute
            System.out.println("\nlink : " + link.attr("href"));
            System.out.println("text : " + link.text()); 
        }
        return 0;
    }
             
             /*
                     Element loginform = doc.getElementById("your_form_id");
             System.out.println(loginform.text()+"dsd\n");
	Elements inputElements = loginform.getElementsByTag("input");
 
	List<String> paramList = new ArrayList<String>();
	for (Element inputElement : inputElements) {
		String key = inputElement.attr("name");
		String value = inputElement.attr("value");
                     
	}
         */
    static Elements getLinks(Document doc)
    {
        Elements links = doc.select("a[href]");
        return links;
        /*
        for (Element link : links) 
        {
            // get the value from href attribute
            System.out.println("\nlink : " + link.attr("href"));
            System.out.println("text : " + link.text()); 
        }
        */
    }
    
     
 
}
