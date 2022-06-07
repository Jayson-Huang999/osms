package com.example.mybatisplus;

import com.example.mybatisplus.common.utls.SendCodeUtils;
import com.example.mybatisplus.mapper.ProductMapper;
import com.example.mybatisplus.model.domain.Admin;
import com.example.mybatisplus.model.domain.Product;
import com.example.mybatisplus.service.AdminService;
import com.example.mybatisplus.service.MailService;
import com.example.mybatisplus.service.RedisService;
import com.example.mybatisplus.service.impl.MailServiceImpl;
import com.example.mybatisplus.web.controller.SellerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MybatisplusApplicationTests {
    @Autowired
    private SellerController sellerController;
    @Autowired
    private AdminService adminService;
    @Autowired
    private MailService mailService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ProductMapper productMapper;

    @Test
    void contextLoads() {
        Product product=productMapper.getProductInfoByID((long)6);
        System.out.println(product);
    }
//    @Test
//    void sellerlogin() {
//
//        try {
//            System.out.println(sellerController.sellerLogin("123","123456").getData().toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
