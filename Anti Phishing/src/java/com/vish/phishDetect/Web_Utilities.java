/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vish.phishDetect;

import com.vish.hash.JenkinsHash;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author vishwas
 */
public class Web_Utilities {
    /**
      * @return google page rank of given url if it exists else returns -10
      */
    public static int getPageRank(String _url) 
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
        System.out.println("pr "+ result);
	if ("".equals(result)) {
		return -10;
	} else {
		return Integer.valueOf(result);
	}
 
  }
}
