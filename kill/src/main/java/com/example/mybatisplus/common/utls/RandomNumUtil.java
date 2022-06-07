package com.example.mybatisplus.common.utls;

public class RandomNumUtil {
    private static String generateCode() {
        Long codeL = System.nanoTime();
        String codeStr = Long.toString(codeL);
        String verifyCode = codeStr.substring(codeStr.length() - 6);
        return verifyCode;
    }

    public static void main(String[] args) {
        System.out.println(generateCode());
    }
}
