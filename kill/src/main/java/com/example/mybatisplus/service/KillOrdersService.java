package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.KillOrders;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hhf
 * @since 2022-05-25
 */
public interface KillOrdersService extends IService<KillOrders> {

    List<Map<String, Object>> getOrderInfo();

    void addOrder(Long id,Long userId, Long goodsId, Long addId,BigDecimal price, LocalDateTime now);

}
