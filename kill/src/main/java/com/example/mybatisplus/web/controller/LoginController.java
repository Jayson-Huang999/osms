package com.example.mybatisplus.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @RequestMapping("/kill/login")
    @ResponseBody
    public Map<String,Object> login(String username,String password){
        // response.setHeader("Access-Control-Allow-Origin", "*");// 部分浏览器需加
        System.out.println(1);
        Map<String,Object> map = new HashMap<>();
        String u = "admin";
        String p = "admin";
        System.out.println(username +":" + password);
        if(u.equals(username)&&p.equals(password)){
            map.put("code",200);
        }else {
            map.put("code",-1);
        }
        return  map;
    }
}
