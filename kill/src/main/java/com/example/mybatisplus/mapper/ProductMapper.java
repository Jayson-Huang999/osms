package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息表 Mapper 接口
 * </p>
 *
 * @author hhf
 * @since 2022-05-24
 */
@Repository
public interface ProductMapper extends BaseMapper<Product> {

    Product getKillProductInfoByID(Long id);

    List<Map<String,Object>> getKillProductStock(Long id);
}
