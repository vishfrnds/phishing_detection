package phishDetect;

import data.Data;
import data.Url;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vishwas on 19/11/15.
 * Copyright (C) 2015  Vishwas Tripathi
 */
public class GiveFinalRatingTest {
    public static void  main (String[] args) {
        //new GiveFinalRating("http://www.icicibank.com/safe-online-banking/safe-online-banking.page").run();
        new GiveFinalRatingTest().readGood();
    }
    void readGood () {
        Jedis jedis = null;
        File csv = new File ("output.csv");
        File html = new File ("Html");
        int offset = 150;

        String fileName = "good_sites.txt";
        try {

            BufferedReader r = new BufferedReader(new FileReader(fileName));
            String line;

            jedis = Data.getInstance().getJedisPool().getResource();
            while ((line = r.readLine()) != null) {
               // while (m.find()) {
                    // phish_list.add(m.group(1).replaceAll("\\\\", ""));

                    System.out.println(line);
                line = "http://" + line;
                   // Url url = new Url(line);
                    new GiveFinalRating(line).run();
                    /*

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
                    */
                }
            //}
            r.close();
            Data.getInstance().getJedisPool().returnResource(jedis);
        } catch (Exception e) {
            if (jedis != null)
                Data.getInstance().getJedisPool().returnBrokenResource(jedis);
            e.printStackTrace();
        }

    }
    void read () {
        Jedis jedis = null;
        File csv = new File ("output.csv");
        File html = new File ("Html");
        int offset = 150;

        String fileName = "online-valid.json";
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
                    if (offset  > 0) {
                        offset--;
                        continue;
                    }
                    Url url = new Url(url_);
                    new GiveFinalRating(url_).run();
                    /*

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
                    */
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
    void write () {

    }
}
