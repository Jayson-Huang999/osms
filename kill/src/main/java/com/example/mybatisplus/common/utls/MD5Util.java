package com.example.mybatisplus.common.utls;

import java.security.MessageDigest;

/**
 * @author Kodak
 * @version 1.0
 * @create 2019/08/04/10:30
 */
public class MD5Util {
    private static final String salt="1a2b3c4d";
    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        //第一次加盐,放在前端，防止传输泄密
        //第二次加盐，盐放在数据库，将pwd加盐后再判断是否相同
        inStr=salt.charAt(0)+salt.charAt(2)+inStr+salt.charAt(5)+salt.charAt(4);
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

    // 测试主函数
    public static void main(String args[]) {
        String s = "Kodak";
        System.out.println("原始：" + s);
        System.out.println("MD5后：" + string2MD5(s));
        System.out.println("加密的：" + convertMD5(s));
        System.out.println("解密的：" + convertMD5(convertMD5(s)));
    }



}
