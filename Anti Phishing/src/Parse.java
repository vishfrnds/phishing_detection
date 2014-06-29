    /*
    * To change this license header, choose License Headers in Project Properties.
    * To change this template file, choose Tools | Templates
    * and open the template in the editor.
    */

    /**
    *
    * @author vishwas
    */

    import java.io.*;    
    import java.net.*;
    import org.jsoup.Jsoup;
    import org.jsoup.nodes.Document;
    import org.jsoup.nodes.Element;
    import org.jsoup.select.Elements;
    import com.vish.hash.JenkinsHash;
    
    public class Parse {

    private String _url, raw;
    private Document doc;
    
    Parse(String _url)
    {
        this._url = _url;        
    }
    public static void main(String[] args)
    {
       Parse p = new Parse("https://www.facebook.com");// try www.gmail.com it has been moved BUG
       p.get_page();
       p.parse_page();
       System.out.println(p.hasLoginForm());
       System.out.println(p.getPageRank());
    }
    
    void get_page()
    {
        try
        {
             URL url = new URL(_url);
             URLConnection urlConnection = url.openConnection();
             HttpURLConnection connection = null;
             if(urlConnection instanceof HttpURLConnection)
             {
                connection = (HttpURLConnection) urlConnection;
             }
             else
             {
                System.out.println("Please enter an HTTP URL.");
                return;
             }
             BufferedReader in = new BufferedReader(
             new InputStreamReader(connection.getInputStream()));
             raw = "";
             String current;
             while((current = in.readLine()) != null)
             {
                raw += current;
             }
             //System.out.println(raw);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    void parse_page()
    {
        doc = Jsoup.parse(raw);        
    }
    
    Elements getLinks()
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
    
    boolean hasLoginForm()
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
    
    
     /**
      * @return google page rank of given url if it exists else returns -1
      */
    public int getPageRank() 
    {
        String domain = _url;
	String result = "";
 
	JenkinsHash jenkinsHash = new JenkinsHash();
	long hash = jenkinsHash.hash(("info:" + domain).getBytes());
 
	//Append a 6 in front of the hashing value.
	String url = "http://toolbarqueries.google.com/tbr?client=navclient-auto&hl=en&"
	   + "ch=6" + hash + "&ie=UTF-8&oe=UTF-8&features=Rank&q=info:" + domain;
 
	//System.out.println("Sending request to : " + url);
 
	try {
		URLConnection conn = new URL(url).openConnection();
 
		BufferedReader br = new BufferedReader(new InputStreamReader(
			conn.getInputStream()));
 
		String input;
		while ((input = br.readLine()) != null) {
 
			// What Google returned? Example : Rank_1:1:9, PR = 9
			//System.out.println(input);
 
			result = input.substring(input.lastIndexOf(":") + 1);
		}
 
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
 
	if ("".equals(result)) {
		return -1;
	} else {
		return Integer.valueOf(result);
	}
 
  }
 
}
