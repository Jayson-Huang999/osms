package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.domain.Seller;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商家表 Mapper 接口
 * </p>
 *
 * @author tyl
 * @since 2022-03-09
 */
@Repository
public interface SellerMapper extends BaseMapper<Seller> {

    void logoutById(Long id);
}
