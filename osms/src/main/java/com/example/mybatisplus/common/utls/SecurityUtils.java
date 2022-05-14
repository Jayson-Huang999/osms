package com.example.mybatisplus.common.utls;

import com.example.mybatisplus.model.domain.Admin;
import com.example.mybatisplus.model.domain.Seller;
import com.example.mybatisplus.model.domain.User;
import com.example.mybatisplus.model.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityUtils {
    /**
     * 获取当前用户
     *
     * @return
     */
    public static Admin getCurrentAdminInfo() {
        Admin adminInfo = SessionUtils.getCurrentAdminInfo();
        //模拟登录
        if (adminInfo == null) {
            adminInfo = new Admin();
            adminInfo.setLoginName("模拟");
        }

        return adminInfo;
    }

    public static UserInfoDTO getAdminInfo() {
        Admin adminInfo = SessionUtils.getCurrentAdminInfo();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        //模拟登录
        if (adminInfo == null) {
            adminInfo = new Admin();
            adminInfo.setLoginName("模拟用户");
            userInfoDTO.setId(1L);
            userInfoDTO.setName("模拟用户");
            userInfoDTO.setUserType(1L);
        }else{
            userInfoDTO.setId(1L);
            userInfoDTO.setName("模拟用户");
            userInfoDTO.setUserType(1L);
        }

        return userInfoDTO;
    }


    /**
     * 获取当前user
     *
     * @return
     */
    public static User getCurrentUserInfo() {
        User userInfo = SessionUtils.getCurrentUserInfo();
        //模拟登录
        if (userInfo == null) {
            userInfo = new User();
            userInfo.setId(123456L);
        }

        return userInfo;
    }

    public static User getUserInfo() {
        User userInfo = SessionUtils.getCurrentUserInfo();
        //UserInfoDTO userInfoDTO = new UserInfoDTO();
        //模拟登录
        if (userInfo == null) {
            userInfo = new User();
            //userInfo.setId(123456L);
            userInfo.setId(1L);
            userInfo.setUsername("模拟用户");
            //userInfoDTO.setUserType(1L);
        }else{
            userInfo.setId(1L);
            userInfo.setUsername("模拟用户");
            //userInfoDTO.setUserType(1L);
        }

        return userInfo;
    }


    /**
     * 获取当前seller
     *
     * @return
     */
    public static Seller getCurrentSellerInfo() {
        Seller sellerInfo = SessionUtils.getCurrentSellerInfo();
        //模拟登录
        if (sellerInfo == null) {
            sellerInfo = new Seller();
            sellerInfo.setId(123456L);
        }

        return sellerInfo;
    }

    public static Seller getSellerInfo() {
        Seller sellerInfo = SessionUtils.getCurrentSellerInfo();
//        UserInfoDTO userInfoDTO = new UserInfoDTO();
        //模拟登录
        if (sellerInfo == null) {
            sellerInfo = new Seller();
//            sellerInfo.setId(123456L);
            sellerInfo.setId(1L);
            sellerInfo.setName("模拟用户");
//            sellerInfo.setUserType(1L);
        }else{
            sellerInfo.setId(1L);
            sellerInfo.setName("模拟用户");
//            sellerInfo.setUserType(1L);
        }

        return sellerInfo;
    }
}
