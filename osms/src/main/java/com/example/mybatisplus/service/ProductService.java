package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息表 服务类
 * </p>
 *
 * @author lxp
 * @since 2022-03-02
 */
public interface ProductService extends IService<Product> {

    List<Product> search(String s);

    List<Map<String, Object>> selectBySellerId(Long id);

    Boolean hasStored(Long userid,Long pid);

    void addStore(Long userid, Long pid);

    void cancelStore(Long userid, Long pid);

    List<Map<String, Object>> getGoodsListBybid(Long id);
}
