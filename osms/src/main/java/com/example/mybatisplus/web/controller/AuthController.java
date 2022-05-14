package com.example.mybatisplus.web.controller;


import com.example.mybatisplus.common.JsonResponse;
import com.example.mybatisplus.common.utls.SecurityUtils;
import com.example.mybatisplus.model.domain.Seller;
import com.example.mybatisplus.model.domain.User;
import com.example.mybatisplus.model.dto.UserInfoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @GetMapping("/adminInfo")
        public JsonResponse<UserInfoDTO> getAdminInfo() {
            return JsonResponse.success(SecurityUtils.getAdminInfo());
    }

    @GetMapping("/userInfo")
    public JsonResponse<User> getUserInfo() {
        return JsonResponse.success(SecurityUtils.getUserInfo());
    }

    @GetMapping("/sellerInfo")
    public JsonResponse<Seller> getSellerInfo() {
        return JsonResponse.success(SecurityUtils.getSellerInfo());
    }
}
