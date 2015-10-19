package phishDetect;

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
    
    public Parse(String _url)
    {
        this._url = _url;        
    }
    public void run()
    {
       // get_page();
       parse_page();
    }
    
    private void get_page()
    {
        try
        {
             final URL website = new URL(_url); // The website you want to connect

        // -- Setup connection through proxy
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8080)); // set proxy server and port
           
            HttpURLConnection httpUrlConnetion = (HttpURLConnection) website.openConnection(proxy);
            httpUrlConnetion.connect();

// -- Download the website into a buffer
            BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnetion.getInputStream()));
            StringBuilder buffer = new StringBuilder();
            String str;

            while( (str = br.readLine()) != null )
            {
                buffer.append(str);
            }

            // -- Parse the buffer with Jsoup
            doc = Jsoup.parse(buffer.toString());
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
            Authenticator.setDefault(
               new Authenticator() {
                  public PasswordAuthentication getPasswordAuthentication() {
                     return new PasswordAuthentication(
                           "edcguest", "edcguest".toCharArray());
                  }
               }
            );
            System.setProperty("http.proxyHost", "172.31.100.26");
            System.setProperty("http.proxyPort", "3128");
            //System.setProperty("http.proxyUser", "edcguest");
            //System.setProperty("http.proxyPassword", "edcguest");
            //System.out.println(_url);
            doc = Jsoup.connect(_url).get();
        }
        catch(Exception e)
        {
            doc = null;
            e.printStackTrace();
        }
        //doc = Jsoup.parse(raw);        
    }
    
    
    
    
}
