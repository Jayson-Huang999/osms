package com.example.mybatisplus.service;

public interface MailService {
    public void sendTextMailMessage(String to, String subject, String text) ;
    public void checkMail(String to,String subject,String text);
}
