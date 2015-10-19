package precompute;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vishwas
 */
public class Data {
        public static ArrayList<String> phish_list = new ArrayList();
        static HashSet<String> sha = new HashSet<String>();
        static void add(ArrayList<String> _phish_list, ArrayList<String> _sha) { 
            System.out.println("Adding " + _phish_list.size() +" " +_sha.size());
            try
            {
                File url = new File("url_sha.txt");
                if(!url.exists()) {
                    url.createNewFile();
                }
                PrintWriter urlF = new PrintWriter(new FileWriter(url, true)); 
                File hash = new File("hash.txt");
                if(!hash.exists()) {
                    hash.createNewFile();
                }
                PrintWriter hashF = new PrintWriter(new FileWriter(hash, true));
                int i = 0;
                for(String s : _phish_list) {
                    phish_list.add(s);
                    urlF.println(s+" "+_sha.get(i));
                    if(_sha.get(i) != null)
                        sha.add(_sha.get(i));                    
                    i++;
                }
                Collections.sort(phish_list);
                /*
                for (String sha1 : _sha) {
                    if(sha1 != null)
                    {
                        System.out.println(sha1);
                        sha.add(sha1);
                        hashF.println(sha1);
                    }
                }
                hashF.close();
                        */
                urlF.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
}
