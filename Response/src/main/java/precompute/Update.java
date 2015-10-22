package precompute;

import data.Url;
import data.Data;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//fix the location of download
public class Update {
    FileOutputStream fos;
    String fileName = "online-valid.json";

    public static void main(String args[]) {
        //new Load().read();

        new Update().read();
    }

    public void download() {
        String key = "f2aadbac4691e264a737b37acf154d278612f54290ab85a56b5859513e1aeb91";

        String url = "http://data.phishtank.com/data/";

        System.out.println(url + key + "/" + fileName);
        URL link = null;
        try {
            link = new URL(url + key + "/" + fileName);
            //(url + key + "/" + fileName); //The file that you want to download

            ReadableByteChannel rbc = Channels.newChannel(link.openStream());
            fos = new FileOutputStream(fileName);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            // System.out.println(fos.toString());
            fos.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void read() {
        //download();
        //ArrayList<String> phish_list = new ArrayList<String>();
        ArrayList<String> phish_list = new ArrayList();
        ArrayList<String> sha = new ArrayList<String>();
        Jedis jedis = null;
        try {
            Pattern pattern = Pattern.compile("\"url\":\"([^\"]*)");
            File f = new File(fileName);
            BufferedReader r = new BufferedReader(new FileReader(fileName));
            String line;

            jedis = Data.getInstance().getJedisPool().getResource();
            while ((line = r.readLine()) != null) {
                Matcher m = pattern.matcher(line);
                while (m.find()) {
                    // phish_list.add(m.group(1).replaceAll("\\\\", ""));
                    String url_ = m.group(1);
                    Url url = new Url(url_);
                    System.out.println(url.getUrl());
                    if (!jedis.exists(url.getCanonical_hash())) {
                        jedis.set("url:" + url.getCanonical_hash(), url.getUrl());
                        if (url.getPage_hash() != null) {
                            jedis.lpush("phishlist:" + url.getPage_hash(), url.getCanonical_hash());
                        }
                    }
                }
            }
            r.close();
            Data.getInstance().getJedisPool().returnResource(jedis);
        } catch (Exception e) {
            if (jedis != null)
                Data.getInstance().getJedisPool().returnBrokenResource(jedis);
            e.printStackTrace();
        }
    }
}
