package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.mapper.SellerMapper;
import com.example.mybatisplus.model.domain.Seller;
import com.example.mybatisplus.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商家表 服务实现类
 * </p>
 *
 * @author zp
 * @since 2022-03-02
 */
@Service
public class SellerServiceImpl extends ServiceImpl<SellerMapper, Seller> implements SellerService {

    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public void logoutById(Long id) {
        sellerMapper.logoutById(id);
    }
}
