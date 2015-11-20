package precompute;

import data.Url;
import data.Data;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
        URL link;
        try {
            Authenticator.setDefault(
                    new Authenticator() {
                        public PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    "edcguest", "edcguest".toCharArray());
                        }
                    }
            );
            System.setProperty("http.proxyHost", "172.31.103.29");
            System.setProperty("http.proxyPort", "3128");
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
        Jedis jedis = null;
        try {
            Pattern pattern = Pattern.compile("\"url\":\"([^\"]*)");
            BufferedReader r = new BufferedReader(new FileReader(fileName));
            String line;

            jedis = Data.getInstance().getJedisPool().getResource();
            while ((line = r.readLine()) != null) {
                Matcher m = pattern.matcher(line);
                while (m.find()) {
                    // phish_list.add(m.group(1).replaceAll("\\\\", ""));
                    String url_ = m.group(1);
                    Url url = new Url(url_);
                    System.out.println("url: " + url.getUrl());
                    if (!jedis.exists("url:" + url.getCanonical_hash()) || jedis.hget("url:" + url.getCanonical_hash(), "pageHash") == null) {
                        String canonical_hash = url.getCanonical_hash();
                        String page_hash = url.getPage_hash();
                        if (canonical_hash != null) {
                            jedis.hset("url:" + canonical_hash, "url", url.getUrl());
                        }
                        if (page_hash != null) {
                            jedis.hset("url:" + canonical_hash, "pageHash", page_hash);
                            jedis.lpush("phishList:" + page_hash, canonical_hash);
                            jedis.sadd("phishHash", page_hash);
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
