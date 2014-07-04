/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vish.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author vishwas
 */
public class SHA1Hash {

    private String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public String toSHA1(String convertme) 
    {
        MessageDigest md = null;
        try 
        {
            md = MessageDigest.getInstance("SHA-1");
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return byteArrayToHexString(md.digest(convertme.getBytes()));
    }
}
