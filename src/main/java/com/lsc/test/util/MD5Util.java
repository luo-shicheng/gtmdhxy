package com.lsc.test.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MD5Util {
    public static String byte2hex(byte[] b) { // 二行制转十六进制字符串
        if (b == null) {
            return "";
        }
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (byte value : b) {
            stmp = Integer.toHexString(value & 0XFF);
            if (stmp.length() == 1) {
                hs.append("0");
            }
            hs.append(stmp);
        }
        return hs.toString();
    }


    public static String MD5(String src) {
        if (src == null) {
            return "";
        }
        byte[] result = null;
        try {
            MessageDigest alg = MessageDigest.getInstance("MD5");
            result = alg.digest(src.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            //donothing
        }
        return byte2hex(result);
    }

    public static void main(String[] args) {
        System.out.println(MD5("https://v.qq.com/iframe/preview.html?width=500&amp;amp;height=375&auto=0&amp;vid=t3273y0qbx8"));
    }
}
