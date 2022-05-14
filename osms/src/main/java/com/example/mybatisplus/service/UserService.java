package com.example.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplus.model.domain.User;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * �û���Ϣ�� 服务类
 * </p>
 *
 * @author lgl
 * @since 2022-03-01
 */
public interface UserService extends IService<User> {

    Boolean hasPhone(String phone);

    Boolean hasEmail(String email);

    void addUser(User user);

    Boolean hasUserName(String username);

    Long login(String phone, String password);

    Map<String, String> findByPhone(String phone);

    Map<String, String> findByEmail(String email);
    
    void updateMessage(String image, short sex, Long id, LocalDateTime dateTime,String nickname,String phone,String username);

    void updatePhone(String phone, Long id, LocalDateTime dateTime);

    void updateEmail(String email, Long id, LocalDateTime dateTime);

    void updateLoginInfo(String password, Long id, LocalDateTime dateTime);

    void deleteById(Long id);
}
