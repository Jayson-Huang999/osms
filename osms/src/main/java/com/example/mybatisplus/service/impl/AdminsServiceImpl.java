package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Admins;
import com.example.mybatisplus.mapper.AdminsMapper;
import com.example.mybatisplus.service.AdminsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tyl
 * @since 2022-03-09
 */
@Service
public class AdminsServiceImpl extends ServiceImpl<AdminsMapper, Admins> implements AdminsService {

}
