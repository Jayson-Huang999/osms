package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.User;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
