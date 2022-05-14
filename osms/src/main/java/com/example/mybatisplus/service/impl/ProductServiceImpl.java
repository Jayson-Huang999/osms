package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.model.domain.Product;
import com.example.mybatisplus.mapper.ProductMapper;
import com.example.mybatisplus.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息表 服务实现类
 * </p>
 *
 * @author lxp
 * @since 2022-03-02
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<Product> search(String s) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(Product::getName,s);
        List<Product> list = productService.list(wrapper);
        return list;
    }

    @Override
    public List<Map<String, Object>> selectBySellerId(Long id) {
        return productMapper.selectBySellerId(id);
    }

    @Override
    public Boolean hasStored(Long userid,Long pid) {
        if( productMapper.hasStored(userid,pid) != null) {
            return true;
        }
        return false;
    }

    @Override
    public void addStore(Long userid, Long pid) {
        productMapper.addStore(userid,pid);
    }

    @Override
    public void cancelStore(Long userid, Long pid) {
        productMapper.cancelStore(userid,pid);
    }

    @Override
    public List<Map<String, Object>> getGoodsListBybid(Long id) {
        return productMapper.getGoodsListBybid(id);
    }
}
