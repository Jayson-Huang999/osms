package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.mapper.OrdersMapper;
import com.example.mybatisplus.model.domain.Orders;
import com.example.mybatisplus.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author lxp
 * @since 2022-03-02
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public List<Map<String, Object>> selectOrdersBySellerIdAndStatus(Object id,Object status) {
        List<Map<String, Object>> map = ordersMapper.selectOrdersBySellerIdAndStatus(id,status);
        return map;
    }

    @Override
    public int updateByIds(List orderIds) {
        return ordersMapper.ordersMapper(orderIds);
    }

    @Override
    public List<Long> getUserInfo(Long id) {
        List<Long> list = ordersMapper.getUserInfo(id);
        return list;
    }

}
