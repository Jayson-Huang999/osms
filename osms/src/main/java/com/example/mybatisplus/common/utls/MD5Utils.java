package com.example.mybatisplus.common.utls;

import java.security.MessageDigest;

public class MD5Utils {

    private static final String hexDigIts[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * MD5加密数据，32位
     */
    public static String MD5Encode(String value) {
        String resultString = null;
        try {
            resultString = new String(value);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
        } catch (Exception e) {
        }
        return resultString;
    }

    /**
     * MD5加密后解密(一定要KL（）再用KL（）解密)
     * MD5是不可逆，这里的加密解密，你可以看到是对MD5算法先加密后解密，而不是对MD5的解密
     *
     * @param value
     * @return
     */
    public static String MD5Decrypt(String value) {
        char[] a = value.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String k = new String(a);
        return k;
    }


    // 可逆的加密算法
    public static String KL(String inStr) {
        // String s = new String(inStr);
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }


    public static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }
}