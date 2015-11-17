package data;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;

/**
 * Created by Vishwas on 20/10/15.
 * Copyright (C) 2015  Vishwas Tripathi
 */
public class Data {
    private static data.Data ourInstance = new data.Data();

    public static data.Data getInstance() {
        return ourInstance;
    }

    private void reloadData() {
      //  Jedis jedis = jedisPool.getResource();
    }

    private JedisPool jedisPool;

    private Data() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);//, , Integer.parseInt(util.ApplicationProperties.getProperty("port").toString()));
        reloadData();

        phish_list = new PhishList("phishList:");
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public PhishList getPhish_list() {
        return phish_list;
    }

    private PhishList phish_list;
    //private ArrayList<String> phish_list = new ArrayList();
    private HashSet<String> sha = new HashSet<>();
/*
    public void add(ArrayList<String> _phish_list, ArrayList<String> _sha) {
        System.out.println("Adding " + _phish_list.size() + " " + _sha.size());
        try {
            File url = new File("url_sha.txt");
            if (!url.exists()) {
                url.createNewFile();
            }
            PrintWriter urlF = new PrintWriter(new FileWriter(url, true));
            File hash = new File("hash.txt");
            if (!hash.exists()) {
                hash.createNewFile();
            }
            PrintWriter hashF = new PrintWriter(new FileWriter(hash, true));
            int i = 0;
            for (String s : _phish_list) {
                phish_list.add(s);
                urlF.println(s + " " + _sha.get(i));
                if (_sha.get(i) != null)
                    sha.add(_sha.get(i));
                i++;
            }
            Collections.sort(phish_list);
            urlF.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        */
}
