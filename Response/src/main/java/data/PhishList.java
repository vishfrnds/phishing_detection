package data;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Vishwas on 23/10/15.
 * Copyright (C) 2015  Vishwas Tripathi
 */
public class PhishList {
    private ArrayList<String> url_list;
    private Set<String> hash_list;
    String namespace;
    PhishList (String namespace) {
        this.namespace = namespace;
    }

    public boolean hasPage(String page_hash) {
        Jedis jedis = Data.getInstance().getJedisPool().getResource();
        boolean ans = jedis.exists(namespace + page_hash);
        Data.getInstance().getJedisPool().returnResource(jedis);
        return ans;
    }
}
