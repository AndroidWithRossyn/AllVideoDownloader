package com.coco.m3u8lib.utils;

import java.math.BigInteger;
import java.security.MessageDigest;


public class MD5Utils {

    public static String encode(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
