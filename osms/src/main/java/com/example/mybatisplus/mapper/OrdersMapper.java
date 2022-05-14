package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单明细表 Mapper 接口
 * </p>
 *
 * @author lxp
 * @since 2022-03-02
 */
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {

    Map<String, Object> getOrdersDetail(Long order_id);

    List<Map<String, Object>> getOrders(Long user_id, Long status);

    List<Map<String, Object>> selectOrdersBySellerIdAndStatus(Object id, Object status);

    List<Map<String, Object>> selectOrdersBySellerId(Object sid);

    List<Map<String, Object>> getOrdersByUserId(Long user_id);

    List<Long> getUserInfo(Long id);

    int ordersMapper(List orderIds);
}
