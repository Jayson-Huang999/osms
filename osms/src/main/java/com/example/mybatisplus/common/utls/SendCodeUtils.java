package com.example.mybatisplus.common.utls;

import com.example.mybatisplus.common.JsonResponse;
import javax.mail.Session;

import java.util.Properties;
import java.util.Random;

public class SendCodeUtils {
    public static final String MYEMAIL = "2256734557@qq.com";
    public static final String MYIMAP = "zghhdbiavmtvebjd";

    //随即生成六位验证码
    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    //生成指定length的随机字符串（A-Z，a-z，0-9）
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        System.out.println(getRandomString(5));

    }
}