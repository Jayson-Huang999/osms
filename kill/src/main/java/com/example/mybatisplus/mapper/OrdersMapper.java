package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单明细表 Mapper 接口
 * </p>
 *
 * @author hhf
 * @since 2022-05-31
 */
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {

    void addOrder(Long id, Long userId, Long specId, Long addressId, BigDecimal price, LocalDateTime now);
}
