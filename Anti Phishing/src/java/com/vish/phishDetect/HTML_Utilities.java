/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vish.phishDetect;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author vishwas
 */
public class HTML_Utilities {
    static boolean hasLoginForm(Document doc)
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
                        ctr++;
                }
            }
            if(ctr == 1)
                return true;
        }
        return false;
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
    
    
     
 
}
