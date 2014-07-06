/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vish.phishDetect;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities.EscapeMode;

/**
 *
 * @author vishwas
 */
public class BuildSha1 {
    private final String _url;
    private Document doc;

    public BuildSha1(String url) {
        this._url = url;
    }
    public static void main(String args[])
    {
        new BuildSha1("http://www.academics.mnnit.ac.in").generate();
    }
    public String generate()
    {
        Parse p = new Parse(_url);
        p.run();
        doc = p.getDoc();
        clean();
        return " ";
    }

    private void clean() 
    {
        System.out.println(doc);
        doc.outputSettings().escapeMode(EscapeMode.xhtml);
        String cleaned = doc.body().text();
        System.out.println(cleaned);
    }
    
    
}
