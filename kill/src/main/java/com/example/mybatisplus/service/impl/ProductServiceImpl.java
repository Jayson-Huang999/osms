package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Product;
import com.example.mybatisplus.mapper.ProductMapper;
import com.example.mybatisplus.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品信息表 服务实现类
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
