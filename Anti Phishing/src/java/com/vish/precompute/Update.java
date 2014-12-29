/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vish.precompute;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.*;

/**
 *
 * @author vish
 */
//fix the location of download
public class Update {
    FileOutputStream fos;
    String fileName = "online-valid.json";
    public static void main(String args[]) 
    {
        new Load().read();
        new Update().read();
    }
    public void download()
    {
        String key = "f2aadbac4691e264a737b37acf154d278612f54290ab85a56b5859513e1aeb91";
        
        String url = "http://data.phishtank.com/data/";
        
        System.out.println(url + key + "/" + fileName);
        URL link = null;
        try {
            link = new URL(url + key + "/" + fileName);;//(url + key + "/" + fileName); //The file that you want to download
        
            ReadableByteChannel rbc = Channels.newChannel(link.openStream());
            fos = new FileOutputStream(fileName);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
           // System.out.println(fos.toString());
            fos.close();
        } catch (Exception e) {
        
        e.printStackTrace();
        } 
        
    
    }
    public void read()
    {
        //ArrayList<String> phish_list = new ArrayList<String>();
        ArrayList<String> phish_list = new ArrayList();
        ArrayList<String> sha = new ArrayList<String>();
        try {
            Pattern patt = Pattern.compile("\"url\":\"([^\"]*)");
            BufferedReader r = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println("Hello");
                Matcher m = patt.matcher(line);
                int ctr = 0;
                while(m.find( ))
                {
                   // phish_list.add(m.group(1).replaceAll("\\\\", ""));
                    String url = m.group(1).replaceAll("\\\\", "");
                    System.out.println(Collections.binarySearch(Data.phish_list, url));
                    if (Collections.binarySearch(Data.phish_list, url) < 0) {
                        phish_list.add(url);
                        String sha1 = new BuildSha1(url).generate();
                        System.out.println("url = "+ url +"\nsha = " + sha1);
                        sha.add(sha1);                        
                        System.out.println(ctr);
                        if(ctr % 20 == 0) {
                            Data.add(phish_list, sha);
                            phish_list.clear();
                            sha.clear();
                        }
                        ctr++;  
                    }
                }
                for (String s :Data.phish_list)
                    System.out.println(s);
                System.out.println(ctr+" "+Data.phish_list.size());
            }
            Data.add(phish_list, sha);
            r.close();
        } catch (Exception e) {        
            e.printStackTrace();
        } 
    }
}
