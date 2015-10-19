/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package precompute;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author vish
 */
public class Load {
    void read() {
        
        try {
            BufferedReader r = new BufferedReader(new FileReader("url_sha.txt"));
        
            String line;
            while ((line = r.readLine()) != null) {
                Data.phish_list.add(line.split(" ")[0]);
                Data.sha.add(line.split(" ")[1]);
            }
            Collections.sort(Data.phish_list);
            r.close();
            /*
            r = new BufferedReader(new FileReader("hash.txt"));
            while ((line = r.readLine()) != null) {
                Data.sha.add(line);
            }
            r.close();
                    */
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }    
}
