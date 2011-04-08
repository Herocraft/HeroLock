package com.herocraftonline.rightlegred.herolock;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("unused")
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
