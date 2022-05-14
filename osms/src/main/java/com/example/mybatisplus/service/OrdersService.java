package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单明细表 服务类
 * </p>
 *
 * @author lxp
 * @since 2022-03-02
 */
public interface OrdersService extends IService<Orders> {
    List<Long> getUserInfo(Long id);
    List<Map<String, Object>> selectOrdersBySellerIdAndStatus(Object sid, Object status);

    int updateByIds(List orderIds);
}
