package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.CartProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lxp
 * @since 2022-03-02
 */
@Repository
public interface CartProductMapper extends BaseMapper<CartProduct> {

    List<Map<String, Object>> getCartProducts(Long user_id);
}
