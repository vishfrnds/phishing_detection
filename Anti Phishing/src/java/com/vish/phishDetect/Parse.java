package com.vish.phishDetect;

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
    
    public class Parse {

    private String _url, raw;
    private Document doc;

    public String getUrl() {
        return _url;
    }

    public void setUrl(String _url) {
        this._url = _url;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }
    
    Parse(String _url)
    {
        this._url = _url;        
    }
    public void run()
    {
        //get_page();
        parse_page();
    }
    
    private void get_page()
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
    
    private void parse_page()
    {
        try
        {
            
            System.out.println(_url);
        doc = Jsoup.connect(_url).get();
        }
        catch(Exception e)
        {
            //e.printStackTrace();
        }
        //doc = Jsoup.parse(raw);        
    }
    
    
    
    
}
