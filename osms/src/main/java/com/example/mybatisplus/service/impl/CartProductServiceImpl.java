package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.CartProduct;
import com.example.mybatisplus.mapper.CartProductMapper;
import com.example.mybatisplus.service.CartProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lxp
 * @since 2022-03-02
 */
@Service
public class CartProductServiceImpl extends ServiceImpl<CartProductMapper, CartProduct> implements CartProductService {

}
