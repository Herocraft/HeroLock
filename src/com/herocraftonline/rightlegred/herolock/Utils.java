package com.herocraftonline.rightlegred.herolock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Utils {
    private HeroLock plugin;
    
    Utils(HeroLock plugin){
        this.plugin = plugin;
    }
    
    public String md5String(String s){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(),0,s.length());
            return digest.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
}
