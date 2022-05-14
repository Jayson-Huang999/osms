package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.model.domain.User;
import com.example.mybatisplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * �û���Ϣ�� 服务实现类
 * </p>
 *
 * @author lgl
 * @since 2022-03-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public Boolean hasPhone(String phone) {
        return userMapper.hasPhone(phone) == null? false: true;
    }

    @Override
    public Boolean hasEmail(String email) {
        return userMapper.hasEmail(email) == null? false: true;
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public Boolean hasUserName(String username) {
        return userMapper.hasUserName(username) == null? false: true;
    }

    @Override
    public Long login(String phone, String password) {
        Long id = userMapper.login(phone, password);
        return id == null? -1: id;
    }

    @Override
    public Map<String, String> findByPhone(String phone) {
        return userMapper.getInfoByPhone(phone);
    }

    @Override
    public Map<String, String> findByEmail(String email) {
        return userMapper.getInfoByEmail(email);
    }

    @Override
    public void updateMessage(String image, short sex, Long id, LocalDateTime dateTime,String nickname,String phone,String username) {
        userMapper.updateMessage(image, sex, id, dateTime,nickname,phone,username);
    }

    @Override
    public void updatePhone(String phone, Long id, LocalDateTime dateTime) {
        userMapper.updatePhone(phone, id, dateTime);
    }

    @Override
    public void updateEmail(String email, Long id, LocalDateTime dateTime) {
        System.out.println(email+","+id);
        userMapper.updateEmail(email,id,dateTime);
    }

    @Override
    public void updateLoginInfo( String password, Long id, LocalDateTime dateTime) {
        userMapper.updateLoginInfo( password, id, dateTime);
    }

    @Override
    public void deleteById(Long id) {
        userMapper.removeById(id);
    }
}
